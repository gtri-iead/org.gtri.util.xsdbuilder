package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xsddatatypes.{XsdAnyURI, XsdQName, XsdId}
import org.gtri.util.xsdbuilder.impl.elements.XsdObject.Metadata
import org.gtri.util.xsdbuilder.api.{XsdContract, XsdConstants}
import org.gtri.util.xsdbuilder.api.XsdConstants.{ATTRIBUTES, ELEMENTS}
import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.xsdbuilder.impl.XmlParser._
import org.gtri.util.xsdbuilder.impl.GuavaConversions._
import scalaz._
import Scalaz._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 1/4/13
 * Time: 6:06 AM
 * To change this template use File | Settings | File Templates.
 */
case class XsdAppInfo(
  source : Option[XsdAnyURI] = None,
  metadata : Option[Metadata] = None
) extends XsdObject {

  def qName = XsdAppInfo.util.qName

  def value = None

  def toAttributes = {
    source.map({ source => (XsdConstants.ATTRIBUTES.SOURCE.QNAME,source.toString)}).toList
  }

  def pushTo(contract: XsdContract) {
    contract.addXsdAppInfo(
      /* XsdAnyURI _source => */source.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */prefixToNamespaceURIMap
    )
  }

}

object XsdAppInfo {

  implicit object util extends XsdObjectUtil[XsdAppInfo] {

    def qName = ELEMENTS.APPINFO.QNAME

    def parse(element: XmlElement) : Box[XsdAppInfo] = {
      if (element.qName == qName) {
        val boxSource = parseOptionalAttribute(element, ATTRIBUTES.SOURCE.QNAME, XsdAnyURI.parseString)
        for(
          innerSource <- boxSource
        ) yield for (
          source <- innerSource
        ) yield
          XsdAppInfo(
            source = source,
            metadata = Some(Metadata(element))
          )
      } else {
        Box.empty
      }
    }


    def allowedChildElements(children: Seq[XsdObjectUtil[XsdObject]]) = Seq()

    def downcast(element: XsdObject) : Option[XsdAppInfo] = element match {
      case e : XsdAppInfo => Some(e)
      case _ => None
    }
  }
}