/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import org.gtri.util.iteratee.api.ImmutableDiagnosticLocator;

/**
 *
 * @author Lance
 */
public interface XsdEvent {
  ImmutableDiagnosticLocator getLocator();
  void pushTo(XsdContract contract);
}
