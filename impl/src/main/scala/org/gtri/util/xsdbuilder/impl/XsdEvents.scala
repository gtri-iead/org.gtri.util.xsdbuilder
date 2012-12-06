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

*/package org.gtri.util.xsdbuilder.impl

import org.gtri.util.xsdbuilder.api.{XsdContract, XsdEvent}
import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator
import GuavaConversions._

/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
case class AddXsdSchemaEvent(schema : XsdSchema, locator : ImmutableDiagnosticLocator) extends XsdEvent {
  def pushTo(contract: XsdContract) {
    contract.addXsdSchema(
      /* XsdId _id => */schema.id.orNull,
      /* XsdAnyURI _targetNamespace  => */schema.targetNamespace,
      /* XsdToken _version  => */schema.version,
      /* XsdContract.FormChoiceCode _attributeFormDefault  => */schema.attributeFormDefault.orNull,
      /* XsdContract.FormChoiceCode _elementFormDefault  => */schema.elementFormDefault.orNull,
      /* ImmutableSet<XsdContract.BlockDefaultCode> _blockDefaultCodes  => */schema.blockDefaultCodes,
      /* XsdContract.AllOrNoneCode _blockDefaultAllOrNoneCode  => */schema.blockDefaultAllOrNoneCode.orNull,
      /* ImmutableSet<XsdContract.FinalDefaultCode> _finalDefaultCodes  => */schema.finalDefaultCodes,
      /* XsdContract.AllOrNoneCode _finalDefaultAllOrNoneCode  => */schema.finalDefaultAllOrNoneCode.orNull,
      /* XsdToken _xml_lang  => */schema.xml_lang.orNull,
      /* ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap  => */schema.prefixToNamespaceURIMap
    )
  }
}
