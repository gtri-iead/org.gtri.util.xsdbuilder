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

import org.gtri.util.iteratee.api.{Iteratee, IssueHandlingCode}
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.xsdbuilder.api.{XsdConstants, XsdEvent}
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.xmlbuilder.impl.{EndXmlElementEvent, AddXmlElementEvent}
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XsdToXmlGenerator(implicit val issueHandlingCode : IssueHandlingCode) extends Iteratee[XsdEvent, XmlEvent]{
  def initialState = DocRootGenerator()

  type Generator = SingleItemCont[XsdEvent,XmlEvent]
  type GeneratorResult = Iteratee.State.Result[XsdEvent,XmlEvent]

  def guardUnexpectedXsdEvent(generator : Generator) : PartialFunction[XsdEvent,GeneratorResult] = {
    case ev : XsdEvent =>
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

  def genAddSchemaEvent(generator: Generator) : PartialFunction[XsdEvent,GeneratorResult] = {
    case ev@AddXsdSchemaEvent(schema, locator) =>
      Result(
        next = XsdSchemaGenerator(generator),
        output = Chunk(AddXmlElementEvent(schema.toXmlElement, locator))
      )
  }

  def genEndSchemaEvent(generator: Generator) : PartialFunction[XsdEvent,GeneratorResult] = {
    case ev@EndXsdSchemaEvent(schema, locator) =>
      Result(
        next = generator,
        output = Chunk(EndXmlElementEvent(XsdConstants.ELEMENTS.SCHEMA.QNAME, locator))
      )
  }

  case class DocRootGenerator() extends Generator {
    private val doApply = (
        genAddSchemaEvent(this)
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : XsdEvent) = doApply(event)

    def endOfInput() = Success()
  }

  abstract class BaseGenerator(parent : Generator, qName : XsdQName) extends Generator {
    val doApply : PartialFunction[XsdEvent,GeneratorResult]

    def apply(event : XsdEvent) = doApply(event)

//    def endOfInput() = InputFailure(issues = Chunk(inputFatalError("Expected " + qName + ">",nowhere)))
  }

  case class XsdSchemaGenerator(parent : Generator) extends BaseGenerator(parent, XsdConstants.ELEMENTS.SCHEMA.QNAME) {

    val doApply = (
      genEndSchemaEvent(this)
      orElse guardUnexpectedXsdEvent(this)
    )

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdSchemaEvent",nowhere)))
  }

}
