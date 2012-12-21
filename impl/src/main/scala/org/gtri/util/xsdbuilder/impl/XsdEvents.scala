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
import org.gtri.util.xsdbuilder.api.{XsdConstants, XsdContract, XsdEvent}
import org.gtri.util.iteratee.api.{Iteratee, ImmutableDiagnosticLocator}
import GuavaConversions._
import org.gtri.util.xsdbuilder.api.XsdContract.{AllOrNoneCode, FinalDefaultCode, BlockDefaultCode}
import org.gtri.util.xmlbuilder.api.XmlEvent
import org.gtri.util.xmlbuilder.impl.{EndXmlElementEvent, AddXmlElementEvent}
import org.gtri.util.iteratee.impl.Iteratees._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
case class AddXsdSchemaEvent(schema : XsdSchema, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    val blockDefaultCodes : Set[BlockDefaultCode] = schema.blockDefaultCodes.orNull.right.getOrElse(Set.empty[BlockDefaultCode])
    val blockDefaultAllOrNoneCode : AllOrNoneCode = schema.blockDefaultCodes.orNull.left.toOption.orNull
    val finalDefaultAllOrNoneCode : AllOrNoneCode = schema.finalDefaultCodes.orNull.left.toOption.orNull
    val finalDefaultCodes : Set[FinalDefaultCode] = schema.finalDefaultCodes.orNull.right.getOrElse(Set.empty[FinalDefaultCode])
    contract.addXsdSchema(
      /* XsdId _id => */schema.id.orNull,
      /* XsdAnyURI _targetNamespace  => */schema.targetNamespace,
      /* XsdToken _version  => */schema.version,
      /* XsdContract.FormChoiceCode _attributeFormDefault  => */schema.attributeFormDefault.orNull,
      /* XsdContract.FormChoiceCode _elementFormDefault  => */schema.elementFormDefault.orNull,
      /* ImmutableSet<XsdContract.BlockDefaultCode> _blockDefaultCodes  => */blockDefaultCodes,
      /* XsdContract.AllOrNoneCode _blockDefaultAllOrNoneCode  => */blockDefaultAllOrNoneCode,
      /* ImmutableSet<XsdContract.FinalDefaultCode> _finalDefaultCodes  => */finalDefaultCodes,
      /* XsdContract.AllOrNoneCode _finalDefaultAllOrNoneCode  => */finalDefaultAllOrNoneCode,
      /* XsdToken _xml_lang  => */schema.xml_lang.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */schema.prefixToNamespaceURIMap
    )
  }
}
object AddXsdSchemaEvent {
  def partialParser(parser : Iteratee.State[XmlEvent,XsdEvent], ifSuccess: XsdEvent => Iteratee.State[XmlEvent, XsdEvent]) : PartialFunction[XmlEvent,Iteratee.State.Result[XmlEvent,XsdEvent]] = {
    case AddXmlElementEvent(element, locator) if element.qName == XsdConstants.ELEMENTS.SCHEMA.QNAME =>
      val eventBox = for(schema <- XsdSchema.parse(element, locator)) yield AddXsdSchemaEvent(schema, locator)
      eventBox.toResult(ifSuccess)
  }
}
case class EndXsdSchemaEvent(schema : XsdSchema, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.endXsdElement()
  }
}
object EndXsdSchemaEvent {
  def partialParser(schema : XsdSchema, parent : Iteratee.State[XmlEvent, XsdEvent]) : PartialFunction[XmlEvent,Iteratee.State.Result[XmlEvent,XsdEvent]] = {
    case ev@EndXmlElementEvent(qName, locator) if qName == XsdConstants.ELEMENTS.SCHEMA.QNAME =>
      Result(
        next = parent,
        output = Chunk(EndXsdSchemaEvent(schema, locator))
      )
  }
}

case class AddXsdAnnotationEvent(annotation : XsdAnnotation, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.addXsdAnnotation(
      /* XsdId _id => */annotation.id.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */annotation.prefixToNamespaceURIMap
    )
  }
}

object AddXsdAnnotationEvent {
  def partialParser(parser : Iteratee.State[XmlEvent,XsdEvent], ifSuccess: XsdEvent => Iteratee.State[XmlEvent, XsdEvent]) : PartialFunction[XmlEvent,Iteratee.State.Result[XmlEvent,XsdEvent]] = {
    case AddXmlElementEvent(element, locator) if element.qName == XsdConstants.ELEMENTS.ANNOTATION.QNAME =>
      val eventBox = for(annotation <- XsdAnnotation.parse(element, locator)) yield AddXsdAnnotationEvent(annotation, locator)
      eventBox.toResult(ifSuccess)
  }
}

case class EndXsdAnnotationEvent(annotation : XsdAnnotation, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.endXsdElement()
  }
}

object EndXsdAnnotationEvent {
  def partialParser(annotation : XsdAnnotation, parent : Iteratee.State[XmlEvent, XsdEvent]) : PartialFunction[XmlEvent,Iteratee.State.Result[XmlEvent,XsdEvent]] = {
    case ev@EndXmlElementEvent(qName, locator) if qName == XsdConstants.ELEMENTS.ANNOTATION.QNAME =>
      Result(
        next = parent,
        output = Chunk(EndXsdAnnotationEvent(annotation, locator))
      )
  }
}

case class AddXsdDocumentationEvent(documentation : XsdDocumentation, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.addXsdDocumentation(
      /* XsdAnyURI _source => */documentation.source.orNull,
      /* XsdToken _xml_lang => */documentation.xml_lang.orNull,
      /* String _value => */documentation.value.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */documentation.prefixToNamespaceURIMap
    )
  }
}

case class EndXsdDocumentationEvent(annotation : XsdDocumentation, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.endXsdElement()
  }
}
