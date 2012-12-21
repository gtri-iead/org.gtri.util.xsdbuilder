package org.gtri.util.xsdbuilder.impl

import org.gtri.util.iteratee.api.{IssueHandlingCode, Issues, ImmutableDiagnosticLocator}
import org.gtri.util.xsddatatypes.XsdQName
import org.gtri.util.iteratee.box._
import org.gtri.util.iteratee.box.Ops._
import org.gtri.util.iteratee.api.Issues._
import org.gtri.util.xmlbuilder.impl.XmlElement
import scala.Some
import org.gtri.util.xmlbuilder.impl.XmlElement
import scala.Some

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 12/13/12
 * Time: 1:21 PM
 * To change this template use File | Settings | File Templates.
 */
object XmlParser {

  private def recoverRequiredAttribute[U](validValue : => U, locator : ImmutableDiagnosticLocator, qName : XsdQName) : Box[U] = {
    lazy val recoverBox = Box(validValue, inputWarning("Set required attribute to a valid value " + qName.toString + "='" + validValue.toString + "'", locator))
    Box.recover(recoverBox)
  }

  def parseRequiredAttribute[U](element: XmlElement, locator : ImmutableDiagnosticLocator, qName : XsdQName, parser: String => U, validValue : => U) : Box[U] = {
    // Is the attribute set?
    if(element.attributes.contains(qName)) {
      // Attribute is set - try to parse it
      try {
        Box(parser(element.attributes(qName)))
      } catch {
        case e : Exception =>
          recoverRequiredAttribute(validValue, locator, qName) ++ inputRecoverableError(e.getMessage, locator)
      }
    } else {
      // Attribute is not set
      recoverRequiredAttribute(validValue, locator, qName) ++ inputRecoverableError("Missing required attribute " + qName, locator)
    }
  }

  private def recoverOptionalAttribute[U](locator : ImmutableDiagnosticLocator, qName : XsdQName) : Box[Option[U]] = {
    lazy val recoverBox = Box(None, inputWarning("Ignorning optional attribute with invalid value " + qName.toString, locator))
    Box.recover(recoverBox)
  }

  def parseOptionalAttribute[U](element: XmlElement, locator : ImmutableDiagnosticLocator,  qName : XsdQName, parser: String => U) : Box[Option[U]] = {
    // Attribute set?
    if(element.attributes.contains(qName)) {
      // Attribute is set - try to parse it
      try {
        Box(Some(parser(element.attributes(qName))))
      } catch {
        case e : Exception =>
          recoverOptionalAttribute(locator, qName) ++ inputRecoverableError(e.getMessage, locator)
      }
    } else {
      // Attribute is missing, return an unset value (success)
      Box(None)
    }
  }


}
