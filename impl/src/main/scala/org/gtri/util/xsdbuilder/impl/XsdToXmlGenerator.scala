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
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.xmlbuilder.impl._
import org.gtri.util.xsdbuilder.api

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

  /**
   * PartialGenerator to guard against unexpected XsdEvents
   * @param next
   * @return
   */
  def guardUnexpectedXsdEvent(next : Generator) : PartialGenerator = {
    case ev : api.XsdEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'")
      // Try to recover if allowed by issue handling code
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
   * PartialGenerator to convert Xsd lexical events back to XmlEvents
   * @param next
   * @return
   */
  def genLexicalEvents(next : Generator) : PartialGenerator = {
    case ev@AddXsdXmlLexicalEvent(xmlEvent, locator) =>
      Result(
        output = Chunk(xmlEvent),
        next = next
      )
  }

  /**
   * PartialGenerator to generate a StartXmlElementEvent for a StartXsdEvent of a particular type of element
   * @param next
   * @param util
   * @return
   */
  def createStartEventGenerator(next: Generator, util : XsdObjectUtil[XsdObject]) : PartialGenerator = {
    case ev@StartXsdEvent(item, locator) if item.qName == util.qName =>
      Result(
        next = next,
        output = Chunk(StartXmlElementEvent(item.toXmlElement, locator))
      )
  }

  /**
   * PartialGenerator to generate a EndXmlElementEvent for a EndXsdEvent of particular type of element
   * @param next
   * @param util
   * @return
   */
  def createEndEventGenerator(next: Generator, util : XsdObjectUtil[XsdObject]) : PartialGenerator = {
    case ev@EndXsdEvent(item, locator) if item.qName == util.qName =>
      Result(
        next = next,
        output = Chunk(EndXmlElementEvent(util.qName, locator))
      )
  }

  /**
   * PartialGenerator to generate a StartXmlDocumentEvent from a StartXsdDocumentEvent
   * @param next
   * @return
   */
  def parseStartDocumentEvent(next : Generator) : PartialGenerator = {
    case StartXsdDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator) =>
      Result(
        next = next,
        output = Chunk(StartXmlDocumentEvent(encoding, version, isStandAlone, characterEncodingScheme, locator))
      )
  }

  /**
   * PartialGenerator to generate a EndXmlDocumentEvent from a EndXsdDocumentEvent
   * @param next
   * @return
   */
  def parseEndDocumentEvent(next : Generator) : PartialGenerator = {
    case EndXsdDocumentEvent(locator) =>
      Result(
        next = next,
        output = Chunk(EndXmlDocumentEvent(locator))
      )
  }

  /**
   * The starting Generator - awaiting the StartXsdDocumentEvent
   */
  case class NoContextGenerator() extends Generator {
    
    private val doApply = (
      parseStartDocumentEvent(DocRootGenerator(this))
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Success()
  }

  /**
   * The document root generator - awaiting the StartXsdEvent(XsdSchema)
   * @param parent
   */
  case class DocRootGenerator(parent : Generator) extends Generator {
    
    private val doApply = (
        createStartEventGenerator(ElementGenerator(XsdSchema.util,this), XsdSchema.util)
        orElse parseEndDocumentEvent(parent)
        orElse genLexicalEvents(this)
        orElse guardUnexpectedXsdEvent(this)
      )

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdDocumentEvent")))
  }

  /**
   * Element generator
   * @param util
   * @param parent
   */
  case class ElementGenerator(util : XsdObjectUtil[XsdObject], parent: Generator) extends Generator {
    val doApply : PartialGenerator = {
      val endEventGenerator = createEndEventGenerator(parent,util)
      val childGenerators =
        util.allowedChildElements(Seq.empty).foldLeft(endEventGenerator)
        { (z,childUtil) =>
          z orElse createStartEventGenerator(ElementGenerator(childUtil, this),childUtil)
        }
      (
        childGenerators
        orElse genLexicalEvents(this)
        orElse guardUnexpectedXsdEvent(this)
      )
    }

    def apply(event : api.XsdEvent) = doApply(event)

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected EndXsdEvent(" + util.qName.getLocalName() + ")")))
  }
}
