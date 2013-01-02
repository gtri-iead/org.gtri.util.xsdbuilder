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
import org.gtri.util.xmlbuilder.impl.AddXmlElementEvent
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

  def guardUnexpectedXsdEvent(generator : Generator) : PartialFunction[api.XsdEvent,GeneratorResult] = {
    case ev : api.XsdEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'", ev.locator)
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected '" + ev + "'", ev.locator)
        Result(
          next = generator,
          issues = Chunk(warn,error)
        )
      } else {
        Failure(issues = Chunk(error))
      }
  }

  def createAddEventGenerator[E <: XsdElement](next: Iteratee.State[api.XsdEvent,XmlEvent])(implicit util : XsdElementCompanionObject[E]) =
    new PartialFunction[api.XsdEvent, Iteratee.State.Result[api.XsdEvent,XmlEvent]] {

      def apply(input: api.XsdEvent) = {
        val event = AddXsdEvent.parse(input).get
        Result[api.XsdEvent, XmlEvent](
          next = next,
          output = Chunk(AddXmlElementEvent(event.element.toXmlElement, event.locator))
        )
      }


      def isDefinedAt(input: api.XsdEvent) = AddXsdEvent.parse(input).isDefined
    }

  def createEndEventGenerator[E <: XsdElement](next: Iteratee.State[api.XsdEvent,XmlEvent])(implicit util : XsdElementCompanionObject[E]) =
    new PartialFunction[api.XsdEvent, Iteratee.State.Result[api.XsdEvent,XmlEvent]] {

      def apply(input: api.XsdEvent) = {
        val event = EndXsdEvent.parse(input).get
        Result(
          next = next,
          output = Chunk(EndXmlElementEvent(util.qName, event.locator))
        )
      }
      def isDefinedAt(input: api.XsdEvent) = EndXsdEvent.parse(input).isDefined
    }

  def parseStartDocumentEvent(next : Iteratee.State[api.XsdEvent,XmlEvent]) : PartialFunction[api.XsdEvent, Iteratee.State.Result[api.XsdEvent,XmlEvent]] = {
    case StartXsdDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator) =>
      Result(
        next = next,
        output = Chunk(StartXmlDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator))
      )
  }

  def parseEndDocumentEvent(next : Iteratee.State[api.XsdEvent,XmlEvent]) : PartialFunction[api.XsdEvent, Iteratee.State.Result[api.XsdEvent,XmlEvent]] = {
    case EndXsdDocumentEvent(locator) =>
      Result(
        next = next,
        output = Chunk(EndXmlDocumentEvent(locator))
      )
  }

  case class NoContextGenerator() extends Generator {
    println(this)
    private val doApply = (
      parseStartDocumentEvent(DocRootGenerator(this))
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Iteratees.Success()
  }

  case class DocRootGenerator(parent : Generator) extends Generator {
    println(this)
    private val doApply = (
        createAddEventGenerator[XsdSchema](XsdSchemaGenerator(this))
        orElse parseEndDocumentEvent(parent)
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdDocumentEvent",nowhere)))
  }

  abstract class BaseGenerator(parent : Generator, qName : XsdQName) extends Generator {
    val doApply : PartialFunction[api.XsdEvent,GeneratorResult]

    def apply(event : api.XsdEvent) = doApply(event)

//    def endOfInput() = InputFailure(issues = Chunk(inputFatalError("Expected " + qName + ">",nowhere)))
  }

  case class XsdSchemaGenerator(parent : Generator) extends BaseGenerator(parent, XsdConstants.ELEMENTS.SCHEMA.QNAME) {
    println(this)

    val doApply = (
      createAddEventGenerator[XsdAnnotation](XsdAnnotationGenerator(this))
      orElse guardUnexpectedXsdEvent(this)
    )

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdSchemaEvent",nowhere)))
  }

  case class XsdAnnotationGenerator(parent : Generator) extends BaseGenerator(parent, XsdConstants.ELEMENTS.ANNOTATION.QNAME) {
    println(this)
    val doApply = (
      createAddEventGenerator[XsdDocumentation](XsdDocumentationGenerator(this))
      orElse createEndEventGenerator[XsdAnnotation](parent)
      orElse guardUnexpectedXsdEvent(this)
    )

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdAnnotationEvent",nowhere)))
  }

  case class XsdDocumentationGenerator(parent : Generator) extends BaseGenerator(parent, XsdConstants.ELEMENTS.ANNOTATION.QNAME) {
    println(this)

    val doApply = (
      createEndEventGenerator[XsdDocumentation](parent)
      orElse guardUnexpectedXsdEvent(this)
    )

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdDocumentationEvent",nowhere)))
  }
}
