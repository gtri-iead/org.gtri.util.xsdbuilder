/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import org.gtri.util.iteratee.api.Enumerator;
import org.gtri.util.iteratee.api.Iteratee;
import org.gtri.util.xmlbuilder.api.XmlEvent;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamReaderFactory;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamWriterFactory;

/**
 *
 * @author Lance
 */
public interface XsdFactory {
  Enumerator<XsdEvent> createXsdReader(XMLStreamReaderFactory factory, int chunkSize);
  
  Iteratee<XsdEvent,?> createXsdWriter(XMLStreamWriterFactory factory);  
  
  Iteratee<XmlEvent,XsdEvent> createXmlToXsdParser();
  Iteratee<XsdEvent,XmlEvent> createXsdToXmlGenerator();
}
