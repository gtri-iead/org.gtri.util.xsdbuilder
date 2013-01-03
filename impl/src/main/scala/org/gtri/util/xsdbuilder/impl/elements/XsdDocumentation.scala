package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdQName, XsdToken, XsdAnyURI, XsdNCName}
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.XsdConstants.{ELEMENTS, ATTRIBUTES}
import org.gtri.util.iteratee.impl.box._
import scalaz._
import Scalaz._
import org.gtri.util.xsdbuilder.api.{XsdContract, XsdConstants}
import org.gtri.util.xsdbuilder.impl.GuavaConversions._

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
    attributeOrder : Option[Seq[XsdQName]],
    prefixes : Seq[(XsdNCName, XsdAnyURI)]
  ) extends XsdElement {

  def buildAttributes = {
    source.map({ source => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,source.toString)}).toList :::
    xml_lang.map({ xml_lang => (XsdConstants.ATTRIBUTES.XML_LANG.QNAME,xml_lang.toString)}).toList
  }

  def defaultAttributeOrder = XsdDocumentation.util.defaultAttributeOrder

  def toXmlElement : XmlElement = {
    XmlElement(
      qName = XsdConstants.ELEMENTS.DOCUMENTATION.QNAME,
      value = value,
      attributes = attributes,
      prefixes = prefixes
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


    val DEFAULT_ATTRIBUTE_ORDER = Seq(
      ATTRIBUTES.SOURCE.QNAME,
      ATTRIBUTES.VALUE.QNAME,
      ATTRIBUTES.XML_LANG.QNAME
    )

    def defaultAttributeOrder = DEFAULT_ATTRIBUTE_ORDER

    def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdDocumentation] = {
      if(element.qName == ELEMENTS.DOCUMENTATION.QNAME) {
        val boxSource = parseOptionalAttribute(element, ATTRIBUTES.SOURCE.QNAME, XsdAnyURI.parseString)
        val boxXmlLang = parseOptionalAttribute(element, ATTRIBUTES.XML_LANG.QNAME, XsdToken.parseString)
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
            attributeOrder = Some(element.attributes.map { _._1 }),
            prefixes = element.prefixes
          )
      } else {
        Box.empty
      }
    }

    //  def childElements = Nil
    
    def downcast(element: XsdElement) : Option[XsdDocumentation] = element match {
      case e : XsdDocumentation => Some(e)
      case _ => None
    }

  }

}
