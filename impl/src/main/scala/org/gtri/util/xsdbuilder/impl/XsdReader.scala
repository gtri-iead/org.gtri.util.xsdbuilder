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

import org.gtri.util.iteratee.api._
import org.gtri.util.iteratee.IterateeFactory
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamReaderFactory
import org.gtri.util.xmlbuilder.impl.XmlReader
import org.gtri.util.xsdbuilder.api


/**
 * Created with IntelliJ IDEA.
 * User: Lance
 * Date: 11/14/12
 * Time: 7:44 AM
 * To change this template use File | Settings | File Templates.
 */
class XsdReader(factory : XMLStreamReaderFactory, issueHandlingCode : IssueHandlingCode = IssueHandlingCode.NORMAL,val chunkSize : Int = 256) extends Enumerator[api.XsdEvent] {
  def initialState() = {
    val iterateeFactory = new IterateeFactory(issueHandlingCode)
    val xmlReader = new XmlReader(factory, issueHandlingCode, chunkSize)
    val xmlToXsdParser = new XmlToXsdParser(issueHandlingCode)
    val plan = iterateeFactory.createPlan(xmlReader, xmlToXsdParser)
    plan.initialState
  }
}
