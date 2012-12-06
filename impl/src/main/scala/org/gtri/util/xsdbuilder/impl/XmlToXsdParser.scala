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

import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.iteratee.api._
import org.gtri.util.xsdbuilder.api.{XsdConstants, XsdEvent}
import org.gtri.util.iteratee.impl.Iteratees._
import org.gtri.util.iteratee.impl.Iteratees.unbuffered._
import org.gtri.util.xmlbuilder.impl.{XmlElement, AddXmlElementEvent}
import org.gtri.util.xsddatatypes.{XsdQName, XsdAnyURI, XsdToken}
import org.gtri.util.xsdbuilder.api.XsdConstants.{ATTRIBUTES, ELEMENTS}
import org.gtri.util.iteratee.impl.{OptIssueWriter => Box }
import org.gtri.util.iteratee.impl.Issues.RecoverableError

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XmlToXsdParser(implicit val issueHandlingCode : IssueHandlingCode) extends Iteratee[XmlEvent, XsdEvent]{
  def initialState() = DocRootParser()

  abstract class Parser extends BaseCont[XmlEvent,XsdEvent]
  case class DocRootParser() extends Parser {

    def apply(event : XmlEvent) = {
      event match {
        case AddXmlElementEvent(element, locator) if element.qName == XsdConstants.ELEMENTS.SCHEMA.QNAME =>
          val schemaBox = parse(element)
          Result(
            next = XsdSchemaParser(this),
            output = Chunk(AddXsdSchemaEvent(schemaBox.item.get, locator)),
            issues = schemaBox.issues
          )
      }
    }

    def endOfInput() = Success()
  }

  case class XsdSchemaParser(parent : Parser) extends Parser {
    def apply(input : XmlEvent) = throw new RuntimeException("")

    def endOfInput() = Failure()
  }


  def parseToken(s : String) : Box[XsdToken] = Box.tryApply(new XsdToken(s))
  def parseURI(s: String) : Box[XsdAnyURI] = Box.tryApply(XsdAnyURI.parseString(s))

  def parseRequiredAttribute[U](element : XmlElement, qName : XsdQName, parser: String => Box[U], default : => U) : Box[U] = {
    element.attributes.get(qName) match {
      case Some(s) => parser(s)
      case None => Box(default) << RecoverableError(new MissingRequiredAttributeException(qName))
    }
  }

  def randomString = java.lang.Long.toHexString(java.lang.Double.doubleToLongBits(java.lang.Math.random()))
  def genRandomURN = new XsdAnyURI(new StringBuilder().append("urn:").append(randomString).append(":").append(randomString).toString())

  def parse(element : XmlElement) : Box[XsdSchema] = {
    require(element.qName == ELEMENTS.SCHEMA.QNAME)

    for (
      targetNamespace <- parseRequiredAttribute(element, ATTRIBUTES.TARGETNAMESPACE.QNAME,parseURI, genRandomURN);
      version <- parseRequiredAttribute(element, ATTRIBUTES.VERSION.QNAME, parseToken, new XsdToken("1"))
    )
    yield
      new XsdSchema(
        targetNamespace = targetNamespace,
        version = version,
        prefixToNamespaceURIMap = element.prefixToNamespaceURIMap
      )
  }
}