package org.gtri.util.xsdbuilder.impl

import elements.{XsdElementCompanionObject, XsdElement}
import org.gtri.util.xsdbuilder.api
import api.XsdContract
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import org.gtri.util.xmlbuilder.impl.{XmlElement, AddXmlElementEvent}
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

case class AddXsdEvent[E <: XsdElement](element : E, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = element.pushTo(contract)
}
object AddXsdEvent {
  def parse[E <: XsdElement](element : XmlElement, locator : ImmutableDiagnosticLocator)(implicit util : XsdElementCompanionObject[E]) : Box[AddXsdEvent[E]] = {
    for(inner <- util.parse(element, locator))
    yield for(e <- inner)
    yield AddXsdEvent[E](e, locator)
  }
  def parse[E <: XsdElement](event : api.XsdEvent)(implicit util : XsdElementCompanionObject[E]) : Option[AddXsdEvent[E]] = {
    event match {
      case AddXsdEvent(element, locator) =>
        util.downcast(element).map { e => AddXsdEvent(e, locator) }
      case _ => None
    }
  }
}

case class EndXsdEvent[E <: XsdElement](element : E, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract : XsdContract) = contract.endXsdElement()
}
object EndXsdEvent {
  def parse[E <: XsdElement](event : api.XsdEvent)(implicit util : XsdElementCompanionObject[E]) : Option[AddXsdEvent[E]] = {
    event match {
      case EndXsdEvent(element, locator) =>
        util.downcast(element).map { e => AddXsdEvent(e, locator) }
      case _ => None
    }
  }
}
