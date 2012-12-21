package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes._
import org.gtri.util.xsdbuilder.api.XsdContract.{FinalDefaultCode, BlockDefaultCode, AllOrNoneCode, FormChoiceCode}
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.iteratee.api.{IssueHandlingCode, ImmutableDiagnosticLocator}
import org.gtri.util.iteratee.box._
import org.gtri.util.iteratee.box.Ops._
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xsdbuilder.api.XsdConstants.ATTRIBUTES
import org.gtri.util.xsdbuilder.api.XsdConstants

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/14/12
 * Time: 6:31 PM
 * To change this template use File | Settings | File Templates.
 */
case class XsdSchema(
  id : Option[XsdId] = None,
  targetNamespace : XsdAnyURI,
  version : XsdToken,
  attributeFormDefault : Option[FormChoiceCode] = None,
  elementFormDefault : Option[FormChoiceCode] = None,
  blockDefaultCodes : Option[Either[AllOrNoneCode, Set[BlockDefaultCode]]] = None,
  finalDefaultCodes : Option[Either[AllOrNoneCode, Set[FinalDefaultCode]]] = None,
  xml_lang : Option[XsdToken] = None,
  prefixToNamespaceURIMap : Map[XsdNCName, XsdAnyURI]
) extends XsdElement {
  
  def toXmlElement : XmlElement = {

    val attributes =
      id.map({ id => (XsdConstants.ATTRIBUTES.ID.QNAME,id.toString)}).toList :::
      List((XsdConstants.ATTRIBUTES.TARGETNAMESPACE.QNAME, targetNamespace.toString)) :::
      List((XsdConstants.ATTRIBUTES.VERSION.QNAME, version.toString)) :::
      attributeFormDefault.map({ attributeFormDefault => (XsdConstants.ATTRIBUTES.ATTRIBUTEFORMDEFAULT.QNAME,attributeFormDefault.toString)}).toList :::
      elementFormDefault.map({ elementFormDefault => (XsdConstants.ATTRIBUTES.ELEMENTFORMDEFAULT.QNAME,elementFormDefault.toString)}).toList :::
      blockDefaultCodes.map({ blockDefaultCodes => (XsdConstants.ATTRIBUTES.BLOCKDEFAULT.QNAME,blockDefaultCodes.fold({ _.toString },{ _.mkString(" ") }))}).toList :::
      finalDefaultCodes.map({ finalDefaultCodes => (XsdConstants.ATTRIBUTES.FINALDEFAULT.QNAME,finalDefaultCodes.fold({ _.toString },{ _.mkString(" ") }))}).toList :::
      xml_lang.map({ xml_lang => (XsdConstants.ATTRIBUTES.XML_LANG.QNAME,xml_lang.toString)}).toList

    XmlElement(
      qName = XsdConstants.ELEMENTS.SCHEMA.QNAME,
      value = None,
      attributes = attributes.toMap,
      prefixToNamespaceURIMap = prefixToNamespaceURIMap
    )
  }
}

object XsdSchema {
  def randomString = java.lang.Long.toHexString(java.lang.Double.doubleToLongBits(java.lang.Math.random()))
  def genRandomURN = new XsdAnyURI(new StringBuilder().append("urn:").append(randomString).append(":").append(randomString).toString())

  def parseAllOrNone[A](parser: String => A)(s : String) : Either[AllOrNoneCode, Set[A]] = {
    s match {
      case s : String if s == AllOrNoneCode.NONE.toString => Left(AllOrNoneCode.NONE)
      case s : String if s == AllOrNoneCode.ALL.toString => Left(AllOrNoneCode.ALL)
      case _ =>
        val members =
          for(member <- s.split("\\s+"))
          yield parser(member)
        Right(members.toSet)
    }
  }

  def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdSchema] = {
    val boxId = parseOptionalAttribute(element, locator, ATTRIBUTES.ID.QNAME, XsdId.parseString)
    val boxTargetNamespace = parseRequiredAttribute(element, locator, ATTRIBUTES.TARGETNAMESPACE.QNAME,XsdAnyURI.parseString, genRandomURN)
    val boxVersion = parseRequiredAttribute(element, locator, ATTRIBUTES.VERSION.QNAME, XsdToken.parseString, new XsdToken("1"))
    val boxAttributeFormDefault = parseOptionalAttribute(element, locator, ATTRIBUTES.ATTRIBUTEFORMDEFAULT.QNAME, FormChoiceCode.parseString)
    val boxElementFormDefault = parseOptionalAttribute(element, locator, ATTRIBUTES.ELEMENTFORMDEFAULT.QNAME, FormChoiceCode.parseString)
    val boxBlockDefaultCodes = parseOptionalAttribute(element, locator, ATTRIBUTES.BLOCKDEFAULT.QNAME, parseAllOrNone(BlockDefaultCode.parseString))
    val boxFinalDefaultCodes = parseOptionalAttribute(element, locator, ATTRIBUTES.FINALDEFAULT.QNAME, parseAllOrNone(FinalDefaultCode.parseString))
    val boxXmlLang = parseOptionalAttribute(element, locator, ATTRIBUTES.XML_LANG.QNAME, XsdToken.parseString)
    (boxId,boxTargetNamespace,boxVersion,boxAttributeFormDefault,boxElementFormDefault,boxBlockDefaultCodes,boxFinalDefaultCodes,boxXmlLang).cram
    {
      (id,targetNamespace,version,attributeFormDefault,elementFormDefault,blockDefaultCodes,finalDefaultCodes,xml_lang)
      =>
        XsdSchema(
          id = id,
          targetNamespace = targetNamespace,
          version = version,
          attributeFormDefault = attributeFormDefault,
          elementFormDefault = elementFormDefault,
          blockDefaultCodes = blockDefaultCodes,
          finalDefaultCodes = finalDefaultCodes,
          xml_lang = xml_lang,
          prefixToNamespaceURIMap = element.prefixToNamespaceURIMap
        ).box
    }
  }

}
