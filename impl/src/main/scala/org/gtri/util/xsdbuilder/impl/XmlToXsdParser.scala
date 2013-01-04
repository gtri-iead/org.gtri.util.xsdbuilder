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
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.api.{IssueHandlingCode, Iteratee }
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.impl.Iteratees
import org.gtri.util.iteratee.impl.Iteratees.Result
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.xmlbuilder.impl._
import org.gtri.util.xmlbuilder.impl.EndXmlDocumentEvent
import org.gtri.util.xmlbuilder.impl.StartXmlElementEvent
import org.gtri.util.xmlbuilder.impl.EndXmlElementEvent
import org.gtri.util.xmlbuilder.impl.AddXmlTextEvent
import org.gtri.util.xsdbuilder.api


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

  /**
   * Parser to ignore an element. If another element with the same qName is encountered, recurses on itself to ignore
   * the child element as well. Returns to parent once element is finished.
   * @param qName
   * @param parent
   */
  case class IgnoreElementParser(qName : XsdQName, parent : Parser) extends Parser {

    def apply(event : XmlEvent) = event match {
      case add@StartXmlElementEvent(element, locator) if element.qName == qName =>
        Result(next = IgnoreElementParser(qName, this))
      case end@EndXmlElementEvent(eventQName, locator) if eventQName == qName =>
        Result(next = parent)
      case _ =>
        Result(next = this)
    }

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected </" + qName + ">")))
  }

  /**
   * PartialParser to guard against unexpected XmlEvents.
   * @param next
   * @return
   */
  def guardUnexpectedXmlEvent(next : Parser) : PartialParser = {
    // Handle start of unexpected element
    case ev@StartXmlElementEvent(element, locator) =>
      val error = inputRecoverableError("Unexpected element '" + element.qName + "'")
      // Ignore it if we can
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected element '" + element.qName + "'")
        Result(
          next = next,
          issues = Chunk(warn,error)
        )
      } else {
        // Otherwise fail
        Failure(issues = Chunk(error))
      }
    // Handle any other unexpected event
    case ev : XmlEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'")
      // Ignore it if we can
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected '" + ev + "'")
        Result(
          next = next,
          issues = Chunk(warn,error)
        )
      } else {
        // Otherwise fail
        Failure(issues = Chunk(error))
      }
  }

  /**
   * PartialParser to parse lexical (formatting etc) XmlEvents
   * @param next
   * @return
   */
  def parseLexicalEvents(next: Parser) : PartialParser = {
    // Turn whitespace into lexical event
    case ev@AddXmlTextEvent(text, locator) if text.matches("\\s+") =>
      Result(
        output = Chunk(AddXsdXmlLexicalEvent(ev, locator)),
        next = next
      )
    // Turn comments into lexical event
    case ev@AddXmlCommentEvent(comment, locator) =>
      Result(
        output = Chunk(AddXsdXmlLexicalEvent(ev, locator)),
        next = next
      )
  }

  /**
   * PartialParser to parse the start of an Xml Element
   * @param next
   * @param util
   * @return
   */
  def createStartElementParser[E <: XsdObject](next: StartXsdEvent => Parser, util : XsdObjectUtil[E]) : PartialParser = {
    case add@StartXmlElementEvent(element, locator) if element.qName == util.qName =>
      // Parsing might fail
      val box = StartXsdEvent.parse(element,locator, util)
      // Convert box to a result based on the current issueHandlingCode (implicit)
      box.toResult[XmlEvent, api.XsdEvent](ifGo = next)
  }

  /**
   * PartialParser to parse the end of an XmlElement
   * @param e
   * @param next
   * @return
   */
  def createEndEventParser(e : XsdObject, next: EndXsdEvent => Parser) : PartialParser = {
    case end@EndXmlElementEvent(eventQName, locator) if eventQName == e.qName =>
      val endEvent = EndXsdEvent(e,locator)
      Result(
        output = Chunk(endEvent),
        next = next(endEvent)
      )
  }

  /**
   * PartialParser to parse the StartXmlDocumentEvent
   * @param next
   * @return
   */
  def parseStartDocEvent(next: Parser) : PartialParser = {
    case StartXmlDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator) =>
      Result(
        output = Chunk(StartXsdDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator)),
        next = next
      )
  }

  /**
   * PartialParser to parse EndXmlDocumentEvent
   * @param next
   * @return
   */
  def parseEndDocEvent(next: Parser) : PartialParser = {
    case EndXmlDocumentEvent(locator) =>
      Result(
        output = Chunk(EndXsdDocumentEvent(locator)),
        next = next
      )
  }

  /**
   * Parser for the starting context - awaiting the StartXmlDocumentEvent
   */
  case class NoContextParser() extends Parser {
    private val doApply = (
        parseStartDocEvent(DocRootParser(this))
        orElse guardUnexpectedXmlEvent(this)
      )

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Iteratees.Success()
  }

  /**
   * Parser for the document root - awaiting the xsd:schema element
   * @param parent
   */
  case class DocRootParser(parent : Parser) extends Parser {

    private val doApply = (
      createStartElementParser(
        { event =>
          ElementParser(
            util = XsdSchema.util,
            parent = this,
            item = event.item
          )
        },
        XsdSchema.util
      )
      orElse parseEndDocEvent(parent)
      orElse parseLexicalEvents(this)
      orElse guardUnexpectedXmlEvent(this)
    )

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected EndXmlDocumentEvent")))
  }

  /**
   * Element parser
   * @param util
   * @param parent
   * @param item
   * @param children
   */
  case class ElementParser(util : XsdObjectUtil[XsdObject], parent : Parser, item : XsdObject, children : Seq[XsdObjectUtil[XsdObject]] = Seq.empty) extends Parser {
    val doApply : PartialParser = {
      val endEventParser = createEndEventParser(item, { _ => parent })
      val childParsers =
        util.allowedChildElements(children).foldLeft(endEventParser) { (z,childUtil) =>
          z orElse createStartElementParser(
            { event =>
              ElementParser(
                util = childUtil,
                parent = this,
                item = event.item
              )
            },
            childUtil
          )
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
}
