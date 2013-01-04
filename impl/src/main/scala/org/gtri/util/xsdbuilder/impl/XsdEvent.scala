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

case class StartXsdEvent[E <: XsdObject](item : E, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = item.pushTo(contract)
}
object StartXsdEvent {
  def parse[E <: XsdObject](item : XmlElement, locator : ImmutableDiagnosticLocator)(implicit util : XsdObjectUtil[E]) : Box[StartXsdEvent[E]] = {
    for(inner <- util.parse(item))
    yield for(e <- inner)
    yield StartXsdEvent[E](e, locator)
  }
  def parse[E <: XsdObject](event : api.XsdEvent)(implicit util : XsdObjectUtil[E]) : Option[StartXsdEvent[E]] = {
    event match {
      case StartXsdEvent(item, locator) =>
        util.downcast(item).map { e => StartXsdEvent(e, locator) }
      case _ => None
    }
  }
}

case class EndXsdEvent[E <: XsdObject](item : E, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = contract.endXsdElement()
}
object EndXsdEvent {
  def parse[E <: XsdObject](event : api.XsdEvent)(implicit util : XsdObjectUtil[E]) : Option[StartXsdEvent[E]] = {
    event match {
      case EndXsdEvent(item, locator) =>
        util.downcast(item).map { e => StartXsdEvent(e, locator) }
      case _ => None
    }
  }
}

case class AddXsdXmlLexicalEvent(xmlEvent : XmlEvent, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = xmlEvent.pushTo(contract)
}
