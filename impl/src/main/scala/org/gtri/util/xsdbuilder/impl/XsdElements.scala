/*
    Copyright 2012 Georgia Tech Research Institute

    Author: lance.gatlin@gtri.gatech.edu

    This file is part of org.gtri.util.xsdbuilder library.

    org.gtri.util.xsdbuilder library is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    org.gtri.util.xsdbuilder library is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with org.gtri.util.xsdbuilder library. If not, see <http://www.gnu.org/licenses/>.

*/

package org.gtri.util.xsdbuilder.impl

import org.gtri.util.xsddatatypes._
import org.gtri.util.xsdbuilder.api.XsdContract.{FinalDefaultCode, AllOrNoneCode, BlockDefaultCode, FormChoiceCode}

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:43 AM
 * To change this template use File | Settings | File Templates.
 */
trait XsdElement

class MissingRequiredAttributeException(qName : XsdQName) extends RuntimeException("Missing required attribute " + qName.toString)

case class XsdSchema(
  id : Option[XsdId] = None,
  targetNamespace : XsdAnyURI,
  version : XsdToken,
  attributeFormDefault : Option[FormChoiceCode] = Some(FormChoiceCode.QUALIFIED),
  elementFormDefault : Option[FormChoiceCode] = Some(FormChoiceCode.QUALIFIED),
  blockDefaultCodes : Set[BlockDefaultCode] = Set.empty,
  blockDefaultAllOrNoneCode : Option[AllOrNoneCode] = None,
  finalDefaultCodes : Set[FinalDefaultCode] = Set.empty,
  finalDefaultAllOrNoneCode : Option[AllOrNoneCode] = None,
  xml_lang : Option[XsdToken] = None,
  prefixToNamespaceURIMap : Map[XsdNCName, XsdAnyURI]
) extends XsdElement