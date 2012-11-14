/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import org.gtri.util.iteratee.api.Consumer;
import org.gtri.util.iteratee.api.Producer;
import org.gtri.util.iteratee.api.Translator;
import org.gtri.util.xmlbuilder.api.XmlEvent;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamReaderFactory;
import org.gtri.util.xmlbuilder.api.XmlFactory.XMLStreamWriterFactory;

/**
 *
 * @author Lance
 */
public interface XsdFactory {
  Producer<XsdEvent> createXsdReader(XMLStreamReaderFactory factory, int chunkSize);
  
  Consumer<XsdEvent> createXsdWriter(XMLStreamWriterFactory factory);  
  
  Translator<XmlEvent,XsdEvent> createXmlToXsdTranslator();
}
