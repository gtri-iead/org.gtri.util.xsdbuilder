package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdNCName, XsdAnyURI, XsdId}
import org.gtri.util.iteratee.api._
import org.gtri.util.iteratee.box._
import org.gtri.util.iteratee.box.Ops._
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xsdbuilder.api.XsdConstants.ATTRIBUTES
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xmlbuilder.impl.AddXmlElementEvent

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/14/12
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
case class XsdAnnotation(
  id : Option[XsdId] = None,
  prefixToNamespaceURIMap : Map[XsdNCName, XsdAnyURI]
) extends XsdElement

object XsdAnnotation {
  def parse(event: AddXmlElementEvent) : Box[XsdAnnotation] = parse(event.element, event.locator)
  def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdAnnotation] = {
    parseOptionalAttribute(element, locator, ATTRIBUTES.ID.QNAME, XsdId.parseString)
    {
      (id)
      =>
        XsdAnnotation(
          id = id,
          prefixToNamespaceURIMap = element.prefixToNamespaceURIMap
        ).box
    }
  }
}
