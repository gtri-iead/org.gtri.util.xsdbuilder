package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdQName, XsdNCName, XsdAnyURI, XsdId}
import org.gtri.util.iteratee.api._
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xsdbuilder.api.XsdConstants.{ELEMENTS, ATTRIBUTES}
import org.gtri.util.xsdbuilder.api.{ XsdContract, XsdConstants}
import org.gtri.util.xsdbuilder.impl.GuavaConversions._
import org.gtri.util.xmlbuilder.impl.XmlElement
import scalaz._
import Scalaz._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/14/12
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
case class XsdAnnotation(
  id : Option[XsdId] = None,
  prefixes : Seq[(XsdNCName, XsdAnyURI)]
) extends XsdElement {

  def attributeOrder = None

  def buildAttributes = {
    id.map({ id => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,id.toString)}).toList
  }

  def defaultAttributeOrder = XsdAnnotation.util.defaultAttributeOrder

  def toXmlElement : XmlElement = {
    XmlElement(
      qName = XsdConstants.ELEMENTS.ANNOTATION.QNAME,
      value = None,
      attributes = attributes,
      prefixes = prefixes
    )
  }

  def pushTo(contract: XsdContract) {
    contract.addXsdAnnotation(
      /* XsdId _id => */id.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */prefixToNamespaceURIMap
    )
  }

}

object XsdAnnotation {

  implicit object util extends XsdElementCompanionObject[XsdAnnotation] {

    def qName = ELEMENTS.ANNOTATION.QNAME

    val DEFAULT_ATTRIBUTE_ORDER : Seq[XsdQName] = Seq(ATTRIBUTES.ID.QNAME)

    def defaultAttributeOrder = DEFAULT_ATTRIBUTE_ORDER

    def parse(element: XmlElement, locator : ImmutableDiagnosticLocator) : Box[XsdAnnotation] = {
      if (element.qName == ELEMENTS.ANNOTATION.QNAME) {
        val boxId = parseOptionalAttribute(element, ATTRIBUTES.ID.QNAME, XsdId.parseString)
        for(
          innerId <- boxId
        ) yield for (
          id <- innerId
        ) yield
            XsdAnnotation(
              id = id,
              prefixes = element.prefixes
            )
      } else {
        Box.empty
      }
    }


  //  def childElements = List(XsdDocumentation)
    def downcast(element: XsdElement) : Option[XsdAnnotation] = element match {
      case e : XsdAnnotation => Some(e)
      case _ => None
    }
  }
}