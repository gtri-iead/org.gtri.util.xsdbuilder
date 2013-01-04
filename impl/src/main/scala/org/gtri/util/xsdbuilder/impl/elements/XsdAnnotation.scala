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
import org.gtri.util.xsdbuilder.impl.elements.XsdObject.Metadata

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/14/12
 * Time: 6:46 PM
 * To change this template use File | Settings | File Templates.
 */
case class XsdAnnotation(
  id : Option[XsdId] = None,
  metadata : Option[Metadata] = None
) extends XsdObject {

  def qName = XsdAnnotation.util.qName

  def value = None

  def toAttributes = {
    id.map({ id => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,id.toString)}).toList
  }

  def pushTo(contract: XsdContract) {
    contract.addXsdAnnotation(
      /* XsdId _id => */id.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */prefixToNamespaceURIMap
    )
  }

}

object XsdAnnotation {

  implicit object util extends XsdObjectUtil[XsdAnnotation] {

    def qName = ELEMENTS.ANNOTATION.QNAME

    def parse(element: XmlElement) : Box[XsdAnnotation] = {
      if (element.qName == qName) {
        val boxId = parseOptionalAttribute(element, ATTRIBUTES.ID.QNAME, XsdId.parseString)
        for(
          innerId <- boxId
        ) yield for (
          id <- innerId
        ) yield
            XsdAnnotation(
              id = id,
              metadata = Some(Metadata(element))
            )
      } else {
        Box.empty
      }
    }


    def allowedChildElements(children: Seq[XsdQName]) = Seq(XsdDocumentation.util.qName)

    def downcast(element: XsdObject) : Option[XsdAnnotation] = element match {
      case e : XsdAnnotation => Some(e)
      case _ => None
    }
  }
}