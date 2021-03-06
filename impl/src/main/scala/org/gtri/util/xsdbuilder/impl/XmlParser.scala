package org.gtri.util.xsdbuilder.impl

import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.iteratee.impl.box._
import org.gtri.util.xmlbuilder.impl.XmlElement
import scalaz._
import Scalaz._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/13/12
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
object XmlParser {

  private def recoverRequiredAttribute[U](
    validValue : => U,
    qName : XsdQName
  ) : Box[U] = {
    lazy val recoverBox = Box(
      inputWarning("Set required attribute to a valid value " + qName.toString + "='" + validValue.toString + "'"),
      validValue
    )
    Box.recover(recoverBox)
  }

  def parseRequiredAttribute[U](
    element: XmlElement,
    qName : XsdQName,
    parser: String => U,
    validValue : => U
  ) : Box[U] = {
    // Is the attribute set?
    if(element.attributesMap.contains(qName)) {
      // Attribute is set - try to downcast it
      try {
        Box(parser(element.attributesMap(qName)))
      } catch {
        case e : Exception =>
          recoverRequiredAttribute(validValue, qName) :++> List(inputRecoverableError(e.getMessage))
      }
    } else {
      // Attribute is not set
      recoverRequiredAttribute(validValue, qName) :++> List(inputRecoverableError("Missing required attribute " + qName))
    }
  }

  private def recoverOptionalAttribute[U](qName : XsdQName) : Box[Option[U]] = {
    lazy val recoverBox = Box(
      inputWarning("Ignorning optional attribute with invalid value " + qName.toString),
      None
    )
    Box.recover(recoverBox)
  }

  def parseOptionalAttribute[U](element: XmlElement, qName : XsdQName, parser: String => U) : Box[Option[U]] = {
    // Attribute set?
    if(element.attributesMap.contains(qName)) {
      // Attribute is set - try to downcast it
      try {
        Box(Some(parser(element.attributesMap(qName))))
      } catch {
        case e : Exception =>
          recoverOptionalAttribute(qName) :++> List(inputRecoverableError(e.getMessage))
      }
    } else {
      // Attribute is missing, return an unset value (success)
      Box(None)
    }
  }
}
