package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.{XsdConstants, XsdContract}
import org.gtri.util.xsddatatypes.{XsdQName, XsdAnyURI, XsdNCName}
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
trait XsdObject {
  def qName : XsdQName
  def value : Option[String]
  def metadata : Option[XsdObject.Metadata]
  lazy val prefixToNamespaceURIMap : Map[XsdNCName, XsdAnyURI] = {
    if(metadata.isDefined && metadata.get.prefixToNamespaceURIMap.isDefined) {
      metadata.get.prefixToNamespaceURIMap.get
    } else {
      Map.empty
    }
  }
  def pushTo(contract : XsdContract)
  def toAttributes : Seq[(XsdQName,String)]
  def toAttributesMap : Map[XsdQName, String] = toAttributes.toMap
  def toXmlElement : XmlElement  = {
    XmlElement(
      qName = qName,
      value = value,
      attributesMap = toAttributesMap,
      prefixToNamespaceURIMap = prefixToNamespaceURIMap,
      metadata = metadata.map { _.toXmlElementMetadata }
    )
  }
}

object XsdObject {
  case class Metadata(
    attributesOrder : Option[Seq[XsdQName]] = None,
    prefixesOrder : Option[Seq[XsdNCName]] = None,
    prefixToNamespaceURIMap : Option[Map[XsdNCName, XsdAnyURI]] = None,
    locator : Option[ImmutableDiagnosticLocator] = None
  ) {
    def toXmlElementMetadata : XmlElement.Metadata = {
      XmlElement.Metadata(
        attributesOrder = attributesOrder,
        prefixesOrder = prefixesOrder,
        locator = locator
      )
    }
  }
  object Metadata {
    def apply(element : XmlElement) = new Metadata(
      attributesOrder = element.metadata.flatMap({ _.attributesOrder }),
      prefixesOrder = element.metadata.flatMap({ _.prefixesOrder }),
      prefixToNamespaceURIMap = Some(element.prefixToNamespaceURIMap),
      locator = element.metadata.flatMap({ _.locator })
    )
  }
}