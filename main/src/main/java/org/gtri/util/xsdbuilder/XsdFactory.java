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

package org.gtri.util.xsdbuilder;

import java.io.InputStream;
import java.io.OutputStream;
import org.gtri.util.iteratee.api.Enumerator;
import org.gtri.util.iteratee.api.IssueHandlingCode;
import org.gtri.util.iteratee.api.Iteratee;
import org.gtri.util.xmlbuilder.XmlFactory;
import org.gtri.util.xmlbuilder.api.XmlEvent;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamReaderFactory;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamWriterFactory;
import org.gtri.util.xsdbuilder.api.XsdEvent;
import org.gtri.util.xsdbuilder.impl.XmlToXsdParser;
import org.gtri.util.xsdbuilder.impl.XsdReader;
import org.gtri.util.xsdbuilder.impl.XsdToXmlGenerator;
import org.gtri.util.xsdbuilder.impl.XsdWriter;
        
/**
 *
 * @author lance.gatlin@gmail.com
 */
public final class XsdFactory implements org.gtri.util.xsdbuilder.api.XsdFactory {
  private final IssueHandlingCode issueHandlingCode;
  public XsdFactory() { 
    issueHandlingCode = IssueHandlingCode.NORMAL;
  }
  public XsdFactory(IssueHandlingCode _issueHandlingCode) {
    issueHandlingCode = _issueHandlingCode;
  }

  public IssueHandlingCode issueHandlingCode() {
    return issueHandlingCode;
  }

  @Override
  public Enumerator<XsdEvent> createXsdReader(final XMLStreamReaderFactory factory, int chunkSize) {
    return new XsdReader(factory, issueHandlingCode(), chunkSize);
  }
  
  @Override
  public Enumerator<XsdEvent> createXsdReader(final XMLStreamReaderFactory factory) {
    return createXsdReader(factory, XmlFactory.STD_CHUNK_SIZE);
  }
  
  @Override
  public Enumerator<XsdEvent> createXsdReader(final InputStream in) {
    XmlFactory xmlFactory = new XmlFactory(issueHandlingCode());
    
    return createXsdReader(xmlFactory.createXMLStreamReaderFactory(in), XmlFactory.STD_CHUNK_SIZE);
  }
  
  public Enumerator<XsdEvent> createXsdReader(final InputStream in, int chunkSize) {
    XmlFactory xmlFactory = new XmlFactory(issueHandlingCode);
    return createXsdReader(xmlFactory.createXMLStreamReaderFactory(in), chunkSize);
  }

  @Override
  public Iteratee<XsdEvent,?> createXsdWriter(final OutputStream out) {
    XmlFactory xmlFactory = new XmlFactory(issueHandlingCode);
    return createXsdWriter(xmlFactory.createXMLStreamWriterFactory(out));
  }

  @Override
  public Iteratee<XsdEvent, ?> createXsdWriter(XMLStreamWriterFactory factory) {
    return new XsdWriter(factory, issueHandlingCode);
  }

  @Override
  public Iteratee<XmlEvent, XsdEvent> createXmlToXsdParser() {
    return new XmlToXsdParser(issueHandlingCode);
  }

  @Override
  public Iteratee<XsdEvent, XmlEvent> createXsdToXmlGenerator() {
    return new XsdToXmlGenerator(issueHandlingCode);
  }
  
  
  
  
}
