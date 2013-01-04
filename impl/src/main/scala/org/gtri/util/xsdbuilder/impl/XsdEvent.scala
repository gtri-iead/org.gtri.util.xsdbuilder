package org.gtri.util.xsdbuilder.impl

import elements.{XsdObjectUtil, XsdObject}
import org.gtri.util.xsdbuilder.api
import api.XsdContract
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import org.gtri.util.xmlbuilder.impl.{XmlElement, StartXmlElementEvent}
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.xmlbuilder.api.XmlEvent
import scalaz._
import Scalaz._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 10:38 PM
 * To change this template use File | Settings | File Templates.
 */
sealed trait XsdEvent extends api.XsdEvent

case class StartXsdDocumentEvent(encoding : String, version : String, isStandAlone : Boolean, characterEncodingScheme : String, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) {
  }
}

case class EndXsdDocumentEvent(locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) {
  }
}

case class StartXsdEvent(item : XsdObject, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = item.pushTo(contract)
}
object StartXsdEvent {
  def parse[E <: XsdObject](e : XmlElement, locator : ImmutableDiagnosticLocator, util : XsdObjectUtil[E]) : Box[StartXsdEvent] = {
    for(inner <- util.parse(e))
    yield for(item <- inner)
    yield StartXsdEvent(item, locator)
  }
}

case class EndXsdEvent(item : XsdObject, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = contract.endXsdElement()
}

case class AddXsdXmlLexicalEvent(xmlEvent : XmlEvent, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = xmlEvent.pushTo(contract)
}
