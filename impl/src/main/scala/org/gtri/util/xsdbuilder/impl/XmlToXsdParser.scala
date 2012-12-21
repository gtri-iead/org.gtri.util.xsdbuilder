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

import elements.{XsdDocumentation, XsdAnnotation, XsdSchema}
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.iteratee.api._
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator._
import org.gtri.util.xsdbuilder.api.{XsdConstants, XsdEvent}
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.impl.ImmutableBufferConversions._
import org.gtri.util.xmlbuilder.impl.{XmlElement, EndXmlElementEvent, AddXmlElementEvent}
import org.gtri.util.iteratee.impl.Iteratees.Result
import org.gtri.util.iteratee.box._
import org.gtri.util.iteratee.box.Ops._
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.xsddatatypes.XsdQName

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XmlToXsdParser(implicit val issueHandlingCode : IssueHandlingCode) extends Iteratee[XmlEvent, XsdEvent]{
  def initialState = DocRootParser()

  type Parser = SingleItemCont[XmlEvent,XsdEvent]
  type ParserResult = Iteratee.State.Result[XmlEvent,XsdEvent]

  def guardUnexpectedXmlEvent(parser : Parser) : PartialFunction[XmlEvent,ParserResult] = {
    case ev : XmlEvent =>
      val error = inputRecoverableError("Unexpected '" + ev + "'", ev.locator)
      if(issueHandlingCode.canContinue(error)) {
        val warn = inputWarning("Ignoring unexpected '" + ev + "'", ev.locator)
        Result(
          next = parser,
          issues =  Chunk(warn,error)
        )
      } else {
        Failure(issues = Chunk(error))
      }
  }

  def parseDocumentationEvent(parser : Parser) : PartialFunction[XmlEvent,ParserResult] = {
    case ev@AddXmlElementEvent(element, locator) if element.qName == XsdConstants.ELEMENTS.DOCUMENTATION.QNAME =>
      val eventBox =
        for(documentation<- XsdDocumentation.parse(element, locator))
        yield (AddXsdDocumentationEvent(documentation,locator), XsdDocumentationParser(documentation, parser))
      eventBox.toResult
  }

  def parseEndDocumentationEvent(annotation : XsdDocumentation,parent : Parser) : PartialFunction[XmlEvent,ParserResult] = {
    case ev@EndXmlElementEvent(qName, locator) if qName == XsdConstants.ELEMENTS.ANNOTATION.QNAME =>
      Result(
        next = parent,
        output = Chunk(EndXsdDocumentationEvent(annotation, locator))
      )
  }

  case class DocRootParser() extends Parser {

    private val doApply = (
      AddXsdSchemaEvent.partialParser(this, ifSuccess = { schema => XsdSchemaParser(schema, this) })
      orElse guardUnexpectedXmlEvent(this)
    )

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Success()
  }

  abstract class BaseXsdParser(parent : Parser, qName : XsdQName) extends Parser {
    val doApply : PartialFunction[XmlEvent,ParserResult]

    def apply(event : XmlEvent) = doApply(event)

    def endOfInput() = Failure(issues = Chunk(inputFatalError("Expected </" + qName + ">",nowhere)))
  }

  case class XsdSchemaParser(schema : XsdSchema, parent : Parser) extends BaseXsdParser(parent, XsdConstants.ELEMENTS.SCHEMA.QNAME) {
    val doApply = (
      AddXsdAnnotationEvent.partialParser(this, ifSuccess = { annotation => XsdAnnotationParser(annotation, this) })
      orElse EndXsdSchemaEvent.partialParser(schema, parent)
      orElse guardUnexpectedXmlEvent(this)
    )
  }

  case class XsdAnnotationParser(annotation : XsdAnnotation, parent : Parser) extends BaseXsdParser(parent, XsdConstants.ELEMENTS.ANNOTATION.QNAME) {
    val doApply = (
      parseEndAnnotationEvent(annotation, parent)
      orElse guardUnexpectedXmlEvent(this)
    )
  }

  case class XsdDocumentationParser(documentation : XsdDocumentation, parent : Parser) extends BaseXsdParser(parent, XsdConstants.ELEMENTS.DOCUMENTATION.QNAME) {
    val doApply = (
        parseEndDocumentationEvent(documentation, parent)
        orElse guardUnexpectedXmlEvent(this)
      )
  }
}
