package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdToken, XsdAnyURI, XsdNCName }
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.XsdConstants.{ELEMENTS, ATTRIBUTES}
import org.gtri.util.iteratee.impl.box._
import scalaz._
import Scalaz._
import org.gtri.util.xsdbuilder.api.{XsdContract, XsdConstants}
import org.gtri.util.xsdbuilder.impl.GuavaConversions._
import org.gtri.util.xsdbuilder.impl.XsdEvent
import org.gtri.util.xsdbuilder.api

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
) extends XsdElement {
  def toXmlElement : XmlElement = {
    val attributes =
      source.map({ source => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,source.toString)}).toList :::
      xml_lang.map({ xml_lang => (XsdConstants.ATTRIBUTES.XML_LANG.QNAME,xml_lang.toString)}).toList

    XmlElement(
      qName = XsdConstants.ELEMENTS.DOCUMENTATION.QNAME,
      value = value,
      attributes = attributes.toMap,
      prefixToNamespaceURIMap = prefixToNamespaceURIMap
    )
  }

  def pushTo(contract: XsdContract) {
    contract.addXsdDocumentation(
      /* XsdAnyURI _source => */source.orNull,
      /* XsdToken _xml_lang => */xml_lang.orNull,
      /* String _value => */value.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */prefixToNamespaceURIMap
    )
  }
}

object XsdDocumentation {
  implicit object util extends XsdElementCompanionObject[XsdDocumentation] {

    def qName = XsdConstants.ELEMENTS.DOCUMENTATION.QNAME

    def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdDocumentation] = {
      if(element.qName == ELEMENTS.DOCUMENTATION.QNAME) {
        val boxSource = parseOptionalAttribute(element, locator, ATTRIBUTES.SOURCE.QNAME, XsdAnyURI.parseString)
        val boxXmlLang = parseOptionalAttribute(element, locator, ATTRIBUTES.XML_LANG.QNAME, XsdToken.parseString)
        for(
          innerSource <- boxSource;
          innerXmlLang <- boxXmlLang
        ) yield for(
          source <- innerSource;
          xml_lang <- innerXmlLang
        ) yield
          XsdDocumentation(
            source = source,
            xml_lang = xml_lang,
            value = element.value,
            prefixToNamespaceURIMap = element.prefixToNamespaceURIMap
          )
      } else {
        Box.empty
      }
    }

    //  def childElements = Nil
    
    def parse(element: XsdElement) : Option[XsdDocumentation] = element match {
      case e : XsdDocumentation => Some(e)
      case _ => None
    }

  }

}
