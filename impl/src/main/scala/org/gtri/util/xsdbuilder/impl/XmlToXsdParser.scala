/*
    Copyright 2012 Georgia Tech Research Institute

    Author: lance.gatlin@gtri.gatech.edu

    This file is part of org.gtri.util.xsdbuilder library.

    org.gtri.util.xsdbuilder library is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    org.gtri.util.xsdbuilder library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with org.gtri.util.xsdbuilder library. If not, see <http://www.gnu.org/licenses/>.

*/package org.gtri.util.xsdbuilder.impl

import elements._
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.iteratee.api._
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator._
import org.gtri.util.xsdbuilder.api.{XsdConstants }
import org.gtri.util.xsdbuilder.api
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.impl.Iteratees
import org.gtri.util.xsddatatypes.XsdQName
import scalaz._
import Scalaz._
import org.gtri.util.xmlbuilder.impl._
import org.gtri.util.iteratee.impl.Iteratees.Result
import org.gtri.util.xsdbuilder.impl.elements.XsdSchema._
import org.gtri.util.xsdbuilder.impl.elements.XsdAnnotation._
import org.gtri.util.xsdbuilder.impl.elements.XsdDocumentation._
import org.gtri.util.xmlbuilder.impl.EndXmlDocumentEvent
import org.gtri.util.xmlbuilder.impl.StartXmlElementEvent
import org.gtri.util.xmlbuilder.impl.EndXmlElementEvent
import org.gtri.util.iteratee.impl.Iteratees.Result
import org.gtri.util.xmlbuilder.impl.AddXmlTextEvent


/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XmlToXsdParser(val issueHandlingCode : IssueHandlingCode) extends Iteratee[XmlEvent, api.XsdEvent]{
  implicit val IHC = issueHandlingCode

  def initialState = NoContextParser()

  type Parser = SingleItemCont[XmlEvent,api.XsdEvent]
  type ParserResult = Iteratee.State.Result[XmlEvent,api.XsdEvent]
  type PartialParser = PartialFunction[XmlEvent,ParserResult]

  def guardUnexpectedXmlEvent(parser : Parser) : PartialParser = {
    case ev : XmlEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'")
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected '" + ev + "'")
        Result(
          next = parser,
          issues = Chunk(warn,error)
        )
      } else {
        Iteratees.Failure(issues = Chunk(error))
      }
  }

//  def ignoreWhitespace(parser : Parser) : PartialParser = {
//    case ev@AddXmlTextEvent(text, locator) if text.matches("\\s+") =>
//      Result(
//        next = parser
//      )
//  }
//
//  def parseComment(parser : Parser) : PartialParser = {
//    case ev@AddXmlCommentEvent(comment, locator) =>
//      Result(
//        output = Chunk(AddXsdCommentEvent(comment, locator)),
//        next = parser
//      )
//  }
  def parseLexicalEvents(next: Parser) : PartialParser = {
    case ev@AddXmlTextEvent(text, locator) if text.matches("\\s+") =>
      Result(
        output = Chunk(AddXsdXmlLexicalEvent(ev, locator)),
        next = next
      )
    case ev@AddXmlCommentEvent(comment, locator) =>
      Result(
        output = Chunk(AddXsdXmlLexicalEvent(ev, locator)),
        next = next
      )
}

  def createAddEventParser[E <: XsdObject](next: StartXsdEvent[E] => Parser)(implicit util : XsdObjectUtil[E]) : PartialParser = {
    case add@StartXmlElementEvent(element, locator) if element.qName == util.qName =>
      StartXsdEvent.parse(element,locator).toResult[XmlEvent, api.XsdEvent](ifGo = next)
  }

  def createEndEventParser[E <: XsdObject](e : E, next: EndXsdEvent[E] => Parser)(implicit util : XsdObjectUtil[E]) : PartialParser = {
    case end@EndXmlElementEvent(eventQName, locator) if eventQName == util.qName =>
      val endEvent = EndXsdEvent(e,locator)
      Iteratees.Result[XmlEvent, api.XsdEvent](
        output = Chunk(endEvent),
        next = next(endEvent)
      )
  }

  def parseStartDocEvent(next: Parser) : PartialParser = {
    case StartXmlDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator) =>
      Iteratees.Result[XmlEvent, api.XsdEvent](
        output = Chunk(StartXsdDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator)),
        next = next
      )
  }

  def parseEndDocEvent(next: Parser) : PartialParser = {
    case EndXmlDocumentEvent(locator) =>
      Iteratees.Result[XmlEvent, api.XsdEvent](
        output = Chunk(EndXsdDocumentEvent(locator)),
        next = next
      )
  }

  case class NoContextParser() extends Parser {
    private val doApply = (
        parseStartDocEvent(DocRootParser(this))
        orElse guardUnexpectedXmlEvent(this)
      )

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Iteratees.Success()
  }

  case class DocRootParser(parent : Parser) extends Parser {

    private val doApply = (
//      createAddEventParser[XsdSchema]({ event =>
//        XsdSchemaParser(
//          item = event.item,
//          parent = this
//        )
//      })
      createAddEventParser[XsdSchema]({ event =>
        XsdObjectParser(
          util = XsdSchema.util,
          parent = this,
          item = event.item
        )
      })
      orElse parseEndDocEvent(parent)
      orElse parseLexicalEvents(this)
      orElse guardUnexpectedXmlEvent(this)
    )

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected EndXmlDocumentEvent")))
  }

//  abstract class BaseXsdParser[E <: XsdObject]()(implicit util : XsdObjectUtil[E]) extends Parser {
//    val doApply : PartialParser
//
//    def apply(event : XmlEvent) = doApply(event)
//
//    def item : XsdObject
//
//    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected </" + util.qName + ">")))
//  }

  case class XsdObjectParser(util : XsdObjectUtil[XsdObject], parent : Parser, item : XsdObject, children : Seq[XsdQName] = Seq.empty) extends Parser {
    val doApply : PartialParser = {
      val endEventParser = createEndEventParser[XsdObject](item, { _ => parent })(util)
//      println("allowedChildElements=" + util.allowedChildElements(children))
      val childParsers =
        util.allowedChildElements(children).foldLeft(endEventParser) { (z,qName) =>
//          println("childQname=" + qName)
          val childUtil = XsdObjectUtil.qNameToUtilMap(qName)
          z orElse createAddEventParser[XsdObject]({ event =>
            XsdObjectParser(
              util = childUtil,
              parent = this,
              item = event.item
            )
          })(childUtil)
        }
      (
        childParsers
        orElse parseLexicalEvents(this)
        orElse guardUnexpectedXmlEvent(this)
      )
    }

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected </" + util.qName + ">")))
  }
  
//  case class XsdSchemaParser(item : XsdSchema, parent : Parser) extends BaseXsdParser[XsdSchema]() {
//    val doApply = (
//      createAddEventParser[XsdAnnotation]({ event =>
//        XsdAnnotationParser(
//          item = event.item,
//          parent = this
//        )
//      })
//      orElse createEndEventParser[XsdSchema](item, { _ => parent })
//      orElse parseLexicalEvents(this)
//      orElse guardUnexpectedXmlEvent(this)
//    )
//  }
//
//  case class XsdAnnotationParser(item : XsdAnnotation, parent : Parser) extends BaseXsdParser[XsdAnnotation]() {
//    val doApply = (
//      createAddEventParser[XsdDocumentation]({ event =>
//        XsdDocumentationParser(
//          item = event.item,
//          parent = this
//        )
//      })
//      orElse createEndEventParser[XsdAnnotation](item, { _ => parent })
//      orElse parseLexicalEvents(this)
//      orElse guardUnexpectedXmlEvent(this)
//    )
//  }
//
//  case class XsdDocumentationParser(item : XsdDocumentation, parent : Parser) extends BaseXsdParser[XsdDocumentation]() {
//    val doApply = (
//        createEndEventParser[XsdDocumentation](item, { _ => parent })
//        orElse parseLexicalEvents(this)
//        orElse guardUnexpectedXmlEvent(this)
//      )
//  }
}
