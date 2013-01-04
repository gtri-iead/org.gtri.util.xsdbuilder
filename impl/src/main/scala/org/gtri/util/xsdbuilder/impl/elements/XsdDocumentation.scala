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
import org.gtri.util.xsdbuilder.impl.elements.XsdObject.Metadata

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
    metadata : Option[Metadata] = None
  ) extends XsdObject {

  def qName = XsdDocumentation.util.qName

  def toAttributes = {
    source.map({ source => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,source.toString)}).toList :::
    xml_lang.map({ xml_lang => (XsdConstants.ATTRIBUTES.XML_LANG.QNAME,xml_lang.toString)}).toList
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
  implicit object util extends XsdObjectUtil[XsdDocumentation] {

    def qName = XsdConstants.ELEMENTS.DOCUMENTATION.QNAME

    def parse(element: XmlElement) : Box[XsdDocumentation] = {
      if(element.qName == qName) {
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
            metadata = Some(Metadata(element))
          )
      } else {
        Box.empty
      }
    }


    def allowedChildElements(children: Seq[XsdObjectUtil[XsdObject]]) = Seq.empty

    def downcast(element: XsdObject) : Option[XsdDocumentation] = element match {
      case e : XsdDocumentation => Some(e)
      case _ => None
    }

  }

}
