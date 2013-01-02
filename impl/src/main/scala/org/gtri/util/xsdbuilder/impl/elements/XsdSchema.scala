package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes._
import org.gtri.util.xsdbuilder.api.XsdContract.{FinalDefaultCode, BlockDefaultCode, AllOrNoneCode, FormChoiceCode}
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.iteratee.api.{IssueHandlingCode, ImmutableDiagnosticLocator}
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xsdbuilder.api.XsdConstants.{ELEMENTS, ATTRIBUTES}
import org.gtri.util.xsdbuilder.api.{ XsdContract, XsdConstants}
import scalaz._
import scalaz.Scalaz._
import org.gtri.util.xsdbuilder.impl.GuavaConversions._
import org.gtri.util.xsdbuilder.impl.XsdEvent
import org.gtri.util.xsdbuilder.api
import org.gtri.util.xmlbuilder.impl.XmlElement

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
      id.map({ id => ATTRIBUTES.ID.QNAME -> id.toString}).toList :::
      List(ATTRIBUTES.TARGETNAMESPACE.QNAME -> targetNamespace.toString) :::
      List(ATTRIBUTES.VERSION.QNAME -> version.toString) :::
      attributeFormDefault.map({ attributeFormDefault => ATTRIBUTES.ATTRIBUTEFORMDEFAULT.QNAME -> attributeFormDefault.toString}).toList :::
      elementFormDefault.map({ elementFormDefault => ATTRIBUTES.ELEMENTFORMDEFAULT.QNAME -> elementFormDefault.toString}).toList :::
      blockDefaultCodes.map({ blockDefaultCodes => ATTRIBUTES.BLOCKDEFAULT.QNAME -> blockDefaultCodes.fold({ _.toString },{ _.mkString(" ") })}).toList :::
      finalDefaultCodes.map({ finalDefaultCodes => ATTRIBUTES.FINALDEFAULT.QNAME -> finalDefaultCodes.fold({ _.toString },{ _.mkString(" ") })}).toList :::
      xml_lang.map({ xml_lang => ATTRIBUTES.XML_LANG.QNAME -> xml_lang.toString}).toList

    XmlElement(
      qName = XsdConstants.ELEMENTS.SCHEMA.QNAME,
      value = None,
      attributes = attributes.toMap,
      prefixToNamespaceURIMap = prefixToNamespaceURIMap
    )
  }

  def pushTo(contract: XsdContract) {
    val blockDefaultCodesSet : Set[BlockDefaultCode] = blockDefaultCodes.orNull.right.getOrElse(Set.empty[BlockDefaultCode])
    val blockDefaultAllOrNoneCode : AllOrNoneCode = blockDefaultCodes.orNull.left.toOption.orNull
    val finalDefaultAllOrNoneCode : AllOrNoneCode = finalDefaultCodes.orNull.left.toOption.orNull
    val finalDefaultCodesSet : Set[FinalDefaultCode] = finalDefaultCodes.orNull.right.getOrElse(Set.empty[FinalDefaultCode])
    contract.addXsdSchema(
      /* XsdId _id => */id.orNull,
      /* XsdAnyURI _targetNamespace  => */targetNamespace,
      /* XsdToken _version  => */version,
      /* XsdContract.FormChoiceCode _attributeFormDefault  => */attributeFormDefault.orNull,
      /* XsdContract.FormChoiceCode _elementFormDefault  => */elementFormDefault.orNull,
      /* ImmutableSet<XsdContract.BlockDefaultCode> _blockDefaultCodes  => */blockDefaultCodesSet,
      /* XsdContract.AllOrNoneCode _blockDefaultAllOrNoneCode  => */blockDefaultAllOrNoneCode,
      /* ImmutableSet<XsdContract.FinalDefaultCode> _finalDefaultCodes  => */finalDefaultCodesSet,
      /* XsdContract.AllOrNoneCode _finalDefaultAllOrNoneCode  => */finalDefaultAllOrNoneCode,
      /* XsdToken _xml_lang  => */xml_lang.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */prefixToNamespaceURIMap
    )
  }
}

object XsdSchema {
  implicit object util extends XsdElementCompanionObject[XsdSchema] {
    def qName = ELEMENTS.SCHEMA.QNAME

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
      if(element.qName == ELEMENTS.SCHEMA.QNAME) {
        val boxId = parseOptionalAttribute(element, locator, ATTRIBUTES.ID.QNAME, XsdId.parseString)
        val boxTargetNamespace = parseRequiredAttribute(element, locator, ATTRIBUTES.TARGETNAMESPACE.QNAME,XsdAnyURI.parseString, genRandomURN)
        val boxVersion = parseRequiredAttribute(element, locator, ATTRIBUTES.VERSION.QNAME, XsdToken.parseString, new XsdToken("1"))
        val boxAttributeFormDefault = parseOptionalAttribute(element, locator, ATTRIBUTES.ATTRIBUTEFORMDEFAULT.QNAME, FormChoiceCode.parseString)
        val boxElementFormDefault = parseOptionalAttribute(element, locator, ATTRIBUTES.ELEMENTFORMDEFAULT.QNAME, FormChoiceCode.parseString)
        val boxBlockDefaultCodes = parseOptionalAttribute(element, locator, ATTRIBUTES.BLOCKDEFAULT.QNAME, parseAllOrNone(BlockDefaultCode.parseString))
        val boxFinalDefaultCodes = parseOptionalAttribute(element, locator, ATTRIBUTES.FINALDEFAULT.QNAME, parseAllOrNone(FinalDefaultCode.parseString))
        val boxXmlLang = parseOptionalAttribute(element, locator, ATTRIBUTES.XML_LANG.QNAME, XsdToken.parseString)
        for(
          innerId <- boxId;
          innerTargetNamespace <- boxTargetNamespace;
          innerVersion <- boxVersion;
          innerAttributeFormDefault <- boxAttributeFormDefault;
          innerElementFormDefault <- boxElementFormDefault;
          innerBlockDefaultCodes <- boxBlockDefaultCodes;
          innerFinalDefaultCodes <- boxFinalDefaultCodes;
          innerXmlLang <- boxXmlLang
        ) yield for(
          id <- innerId;
          targetNamespace <- innerTargetNamespace;
          version <- innerVersion;
          attributeFormDefault <- innerAttributeFormDefault;
          elementFormDefault <- innerElementFormDefault;
          blockDefaultCodes <- innerBlockDefaultCodes;
          finalDefaultCodes <- innerFinalDefaultCodes;
          xml_lang <- innerXmlLang
        ) yield
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
            )
      } else {
        Box.empty
      }
    }


  //  def childElements = List(XsdAnnotation)
    def parse(element: XsdElement) : Option[XsdSchema] = element match {
      case e : XsdSchema => Some(e)
      case _ => None
    }
  }
}