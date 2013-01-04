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

*/
package org.gtri.util.xsdbuilder.impl

import elements._
import org.gtri.util.iteratee.api.{Iteratee, IssueHandlingCode}
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.xsdbuilder.api.{XsdConstants}
import org.gtri.util.xsdbuilder.api
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.xmlbuilder.impl._
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator._
import org.gtri.util.iteratee.impl.Iteratees
import org.gtri.util.iteratee.impl.Iteratees.Failure
import org.gtri.util.xmlbuilder.impl.StartXmlElementEvent
import org.gtri.util.xmlbuilder.impl.EndXmlElementEvent
import org.gtri.util.iteratee.impl.Iteratees.Result

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XsdToXmlGenerator(val issueHandlingCode : IssueHandlingCode) extends Iteratee[api.XsdEvent, XmlEvent]{
  implicit val IHC = issueHandlingCode

  def initialState = NoContextGenerator()

  type Generator = SingleItemCont[api.XsdEvent,XmlEvent]
  type GeneratorResult = Iteratee.State.Result[api.XsdEvent,XmlEvent]
  type PartialGenerator = PartialFunction[api.XsdEvent, GeneratorResult]

  def guardUnexpectedXsdEvent(generator : Generator) : PartialGenerator = {
    case ev : api.XsdEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'")
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected '" + ev + "'")
        Result(
          next = generator,
          issues = Chunk(warn,error)
        )
      } else {
        Failure(issues = Chunk(error))
      }
  }

  def genLexicalEvents(next : Generator) : PartialGenerator = {
    case ev@AddXsdXmlLexicalEvent(xmlEvent, locator) =>
      Result(
        output = Chunk(xmlEvent),
        next = next
      )
  }

  def createAddEventGenerator[E <: XsdObject](next: Generator)(implicit util : XsdObjectUtil[E]) =
    new PartialGenerator {
      def apply(input: api.XsdEvent) = {
        val event = StartXsdEvent.parse(input).get
        Result[api.XsdEvent, XmlEvent](
          next = next,
          output = Chunk(StartXmlElementEvent(event.item.toXmlElement, event.locator))
        )
      }
      def isDefinedAt(input: api.XsdEvent) = StartXsdEvent.parse(input).isDefined
    }

  def createEndEventGenerator[E <: XsdObject](next: Generator)(implicit util : XsdObjectUtil[E]) =
    new PartialGenerator {
      def apply(input: api.XsdEvent) = {
        val event = EndXsdEvent.parse(input).get
        Result(
          next = next,
          output = Chunk(EndXmlElementEvent(util.qName, event.locator))
        )
      }
      def isDefinedAt(input: api.XsdEvent) = EndXsdEvent.parse(input).isDefined
    }

  def parseStartDocumentEvent(next : Generator) : PartialGenerator = {
    case StartXsdDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator) =>
      Result(
        next = next,
        output = Chunk(StartXmlDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator))
      )
  }

  def parseEndDocumentEvent(next : Generator) : PartialGenerator = {
    case EndXsdDocumentEvent(locator) =>
      Result(
        next = next,
        output = Chunk(EndXmlDocumentEvent(locator))
      )
  }

  case class NoContextGenerator() extends Generator {
    
    private val doApply = (
      parseStartDocumentEvent(DocRootGenerator(this))
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Iteratees.Success()
  }

  case class DocRootGenerator(parent : Generator) extends Generator {
    
    private val doApply = (
//        createAddEventGenerator[XsdSchema](XsdSchemaGenerator(this))
        createAddEventGenerator[XsdSchema](XsdObjectGenerator(XsdSchema.util,this))
        orElse parseEndDocumentEvent(parent)
        orElse genLexicalEvents(this)
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdDocumentEvent")))
  }

//  abstract class BaseGenerator[E <: XsdObject]()(implicit util: XsdObjectUtil[E]) extends Generator {
//    val doApply : PartialGenerator
//
//    def apply(event : api.XsdEvent) = doApply(event)
//
//    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected EndXsdEvent(" + util.qName.getLocalName() + ")")))
//  }

  case class XsdObjectGenerator(util : XsdObjectUtil[XsdObject], parent: Generator) extends Generator {
    val doApply : PartialGenerator = {
      val endEventGenerator : PartialGenerator = createEndEventGenerator[XsdObject](parent)(util)
      val childGenerators : PartialGenerator =
        util.allowedChildElements(Seq.empty).foldLeft(endEventGenerator) { (z : PartialGenerator,qName : XsdQName) =>
        //          println("childQname=" + qName)
          val childUtil = XsdObjectUtil.qNameToUtilMap(qName)
          z orElse createAddEventGenerator[XsdObject](XsdObjectGenerator(childUtil, this))(childUtil)
        }
      (
        childGenerators
        orElse genLexicalEvents(this)
        orElse guardUnexpectedXsdEvent(this)
      )
    }

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Iteratees.Failure(issues = Chunk(inputFatalError("Expected EndXsdEvent(" + util.qName.getLocalName() + ")")))
  }

//  case class XsdSchemaGenerator(parent : Generator) extends BaseGenerator[XsdSchema]() {
//
//    val doApply = (
//      createAddEventGenerator[XsdAnnotation](XsdAnnotationGenerator(this))
//      orElse genLexicalEvents(this)
//      orElse guardUnexpectedXsdEvent(this)
//    )
//  }
//
//  case class XsdAnnotationGenerator(parent : Generator) extends BaseGenerator[XsdAnnotation]() {
//
//    val doApply = (
//      createAddEventGenerator[XsdDocumentation](XsdDocumentationGenerator(this))
//      orElse createEndEventGenerator[XsdAnnotation](parent)
//      orElse genLexicalEvents(this)
//      orElse guardUnexpectedXsdEvent(this)
//    )
//  }
//
//  case class XsdDocumentationGenerator(parent : Generator) extends BaseGenerator[XsdDocumentation]() {
//    val doApply = (
//      createEndEventGenerator[XsdDocumentation](parent)
//      orElse genLexicalEvents(this)
//      orElse guardUnexpectedXsdEvent(this)
//    )
//  }
}
