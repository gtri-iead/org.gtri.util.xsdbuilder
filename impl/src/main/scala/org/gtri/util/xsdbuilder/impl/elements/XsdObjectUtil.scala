package org.gtri.util.xsdbuilder.impl.elements

import org.gtri.util.xmlbuilder.impl.XmlElement
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/31/12
 * Time: 12:28 PM
 * To change this template use File | Settings | File Templates.
 */
trait XsdObjectUtil[+E <: XsdObject] {
  def qName : XsdQName
  def parse(e : XmlElement) : Box[E]
  def allowedChildElements(children: Seq[XsdQName]) : Seq[XsdQName]
  def downcast(o : XsdObject) : Option[E]
}
object XsdObjectUtil {
  val qNameToUtilMap : Map[XsdQName, XsdObjectUtil[XsdObject]] = Map(
    XsdSchema.util.qName -> XsdSchema.util,
    XsdAnnotation.util.qName -> XsdAnnotation.util,
    XsdDocumentation.util.qName -> XsdDocumentation.util
  )
}