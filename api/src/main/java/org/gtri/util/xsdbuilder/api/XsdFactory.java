/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import java.io.InputStream;
import java.io.OutputStream;
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
  Enumerator<XsdEvent> createXsdReader(XMLStreamReaderFactory factory);
  Enumerator<XsdEvent> createXsdReader(InputStream in);
  
  Iteratee<XsdEvent,?> createXsdWriter(XMLStreamWriterFactory factory);  
  Iteratee<XsdEvent,?> createXsdWriter(OutputStream out);  
  
  Iteratee<XmlEvent,XsdEvent> createXmlToXsdParser();
  Iteratee<XsdEvent,XmlEvent> createXsdToXmlGenerator();
}
