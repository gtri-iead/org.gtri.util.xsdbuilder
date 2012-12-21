package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdToken, XsdAnyURI, XsdNCName }
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.XsdConstants.ATTRIBUTES
import org.gtri.util.iteratee.box._
import org.gtri.util.iteratee.box.Ops._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/19/12
 * Time: 4:15 AM
 * To change this template use File | Settings | File Templates.
 */
case class XsdDocumentation(
source : Option[XsdAnyURI] = None,
xml_lang : Option[XsdToken] = None,
value : Option[String] = None,
prefixToNamespaceURIMap : Map[XsdNCName, XsdAnyURI]
) extends XsdElement

object XsdDocumentation {
  def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdDocumentation] = {
    val boxSource = parseOptionalAttribute(element, locator, ATTRIBUTES.SOURCE.QNAME, XsdAnyURI.parseString)
    val boxXmlLang = parseOptionalAttribute(element, locator, ATTRIBUTES.XML_LANG.QNAME, XsdToken.parseString)
    (boxSource, boxXmlLang).cram
    {
      (source,xml_lang)
      =>
        XsdDocumentation(
          source = source,
          xml_lang = xml_lang,
          value = element.value,
          prefixToNamespaceURIMap = element.prefixToNamespaceURIMap
        ).box
    }
  }

}
