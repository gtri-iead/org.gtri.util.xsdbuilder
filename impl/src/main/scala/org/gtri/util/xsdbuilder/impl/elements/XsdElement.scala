package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.XsdContract

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
trait XsdElement {
  def pushTo(contract : XsdContract)
  def toXmlElement : XmlElement
}
