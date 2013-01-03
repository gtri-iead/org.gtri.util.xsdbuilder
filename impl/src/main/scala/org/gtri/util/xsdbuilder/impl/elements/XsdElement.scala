package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsdbuilder.api.XsdContract
import org.gtri.util.xsddatatypes.{XsdQName, XsdAnyURI, XsdNCName}

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 10:37 PM
 * To change this template use File | Settings | File Templates.
 */
trait XsdElement {
  def attributeOrder : Option[Seq[XsdQName]]
  def buildAttributes : Seq[(XsdQName, String)]
  def defaultAttributeOrder : Seq[XsdQName]
  lazy val attributes = sortAttributes
  lazy val attributesMap = attributes.toMap

  def prefixes : Seq[(XsdNCName, XsdAnyURI)]
  lazy val prefixToNamespaceURIMap = prefixes.toMap

  def pushTo(contract : XsdContract)
  def toXmlElement : XmlElement

  private def sortAttributes : Seq[(XsdQName, String)] = {
    // Get the initial ordering or empty sequence if not specified
    val initialOrdering = attributeOrder.getOrElse { Seq.empty }
    // Figure out if any of the attributes are missing from the initial ordering
    val missing = initialOrdering.diff(defaultAttributeOrder)
    val unsortedAttributes = buildAttributes
    // Order attributes by ordering (with missing appended)
    val optResult = for(qName <- initialOrdering ++ missing) yield unsortedAttributes.find({ _._1 == qName})
    // If for some reason an attribute is not present in attributes it will be removed here
    optResult.flatten
  }
}
