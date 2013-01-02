package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
trait XsdElementCompanionObject[E] {
  def qName : XsdQName
  def parse(element : XmlElement, locator : ImmutableDiagnosticLocator) : Box[E]
  def parse(element : XsdElement) : Option[E]
//  def childElements : List[XsdElementCompanionObject]
}