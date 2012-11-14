/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import com.google.common.collect.ImmutableMap;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.ImmutableSet;
import org.gtri.util.xmlbuilder.api.XmlContract;
import org.gtri.util.xsddatatypes.ValidationFailedException;
import org.gtri.util.xsddatatypes.XsdAnyURI;
import org.gtri.util.xsddatatypes.XsdId;
import org.gtri.util.xsddatatypes.XsdNCName;
import org.gtri.util.xsddatatypes.XsdName;
import org.gtri.util.xsddatatypes.XsdNonNegativeInteger;
import org.gtri.util.xsddatatypes.XsdPositiveInteger;
import org.gtri.util.xsddatatypes.XsdQName;
import org.gtri.util.xsddatatypes.XsdToken;

/**
 * An interface that allows a producer to communicate the contents of an XML 
 * Schema to a consumer.
 * 
 * Note1: Unless otherwise stated in documentation below, all OPTIONAL 
 * non-collection parameters for methods use the NULL value to indicate the 
 * parameter has not been set.
 * 
 * Note2: All methods with OPTIONAL collection-style parameters use an empty 
 * collection to indicate the parameter has not been set. Enums that are used in
 * sets have a convenience static EMPTY_SET variable.
 * 
 * Note3: All Enums follow the same basic pattern. Only the first Enum has been
 * commented. It should be assumed that unless specified otherwise, all other 
 * Enums in the contract operate the same.
 * 
 * Note4: Documentation on each method begins with a specification of the 
 * contexts (parent elements xpath) allowed for the method within the schema
 * document.
 * 
* @author Lance
 */
public interface XsdContract extends XmlContract {

  /**
   * Enum for the all or none code used for the xsd:schema finalDefault and 
   * blockDefault attributes. 
   * 
   * To simultaneously satisfy the standard Java practice of using all capital 
   * letters in enum values and having an arbitrary string value that 
   * corresponds to the enum value, a string literal is stored with each enum 
   * value. This toString method is overridden, returning the string literal 
   * instead of the enum value. Additionally, two parsing methods are provided
   * to parse strings based on the string literal vice the enum value.
   */
  enum AllOrNoneCode {
    NONE(""),
    ALL("#all");
    
    /*
     * Stores the string value for the enum.
     */
    private String svalue;

    /**
     * Construct a new AllOrNodeCode with the string literal.
     * @param _svalue string literal
     */
    AllOrNoneCode(String _svalue) {
      svalue = _svalue;
    }
    /**
     * Returns the string literal for the AllOrNoneCode.
     * @return the string literal for the AllOrNoneCode.
     */
    @Override public String toString() { return svalue; }
    
    /**
     * A map to lookup an AllOrNoneCode given a string. Created on demand.
     */
    private static Map<String, XsdContract.AllOrNoneCode> parseMap = null;
    /**
     * Parse an AllOrNoneCode from a string OR return NULL if parsing fails.
     * @param _svalue a string literal to parse
     * @return AllOrNoneCode OR NULL if parsing failed
     */
    public static XsdContract.AllOrNoneCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.AllOrNoneCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    /**
     * Parse an AllOrNoneCode from a string OR return NULL if parsing fails.
     * @param _svalue a string literal to parse
     * @return AllOrNoneCode 
     * @throws ValidationFailedException if parsing failed
     */
    public static XsdContract.AllOrNoneCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.AllOrNoneCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.AllOrNoneCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  enum FormChoiceCode {
    QUALIFIED("qualified"),
    UNQUALIFIED("unqualified");
    
    private String svalue;
    FormChoiceCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.FormChoiceCode> parseMap = null;
    public static XsdContract.FormChoiceCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.FormChoiceCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.FormChoiceCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.FormChoiceCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.FormChoiceCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  enum BlockDefaultCode {
    EXTENSION("extension"),
    RESTRICTION("restriction"),
    SUBSTITUTION("substitution");

    private String svalue;
    BlockDefaultCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.BlockDefaultCode> parseMap = null;
    public static XsdContract.BlockDefaultCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.BlockDefaultCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.BlockDefaultCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.BlockDefaultCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.BlockDefaultCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
    public static final ImmutableSet<XsdContract.BlockDefaultCode> EMPTY_SET = ImmutableSet.of();
}
  
  enum FinalDefaultCode {
    EXTENSION("extension"),
    LIST("list"),
    RESTRICTION("restriction"),
    UNION("union");
    
    private String svalue;
    FinalDefaultCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.FinalDefaultCode> parseMap = null;
    public static XsdContract.FinalDefaultCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.FinalDefaultCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.FinalDefaultCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.FinalDefaultCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.FinalDefaultCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
    public static final ImmutableSet<XsdContract.FinalDefaultCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum ElementBlockCode {
    EXTENSION("extension"),
    RESTRICTION("restriction"),
    SUBSTITUTION("substitution");
    
    private String svalue;
    ElementBlockCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.ElementBlockCode> parseMap = null;
    public static XsdContract.ElementBlockCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.ElementBlockCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.ElementBlockCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.ElementBlockCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.ElementBlockCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
    public static final ImmutableSet<XsdContract.ElementBlockCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum ElementFinalCode {
    EXTENSION("extension"),
    RESTRICTION("restriction");
    
    private String svalue;
    ElementFinalCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.ElementFinalCode> parseMap = null;
    public static XsdContract.ElementFinalCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.ElementFinalCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.ElementFinalCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.ElementFinalCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.ElementFinalCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
    public static final ImmutableSet<XsdContract.ElementFinalCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum ComplexTypeBlockCode {
    EXTENSION("extension"),
    RESTRICTION("restriction");
    
    private String svalue;
    ComplexTypeBlockCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.ComplexTypeBlockCode> parseMap = null;
    public static XsdContract.ComplexTypeBlockCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.ComplexTypeBlockCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }    
    public static XsdContract.ComplexTypeBlockCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.ComplexTypeBlockCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.ComplexTypeBlockCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
    public static final ImmutableSet<XsdContract.ComplexTypeBlockCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum ComplexTypeFinalCode {
    EXTENSION("extension"),
    RESTRICTION("restriction");
    
    private String svalue;
    ComplexTypeFinalCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.ComplexTypeFinalCode> parseMap = null;
    public static XsdContract.ComplexTypeFinalCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.ComplexTypeFinalCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.ComplexTypeFinalCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.ComplexTypeFinalCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.ComplexTypeFinalCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }    
    public static final ImmutableSet<XsdContract.ComplexTypeFinalCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum SimpleTypeFinalCode {
    LIST("list"),
    RESTRICTION("restriction"),
    UNION("union");
    
    private String svalue;
    SimpleTypeFinalCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.SimpleTypeFinalCode> parseMap = null;
    public static XsdContract.SimpleTypeFinalCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.SimpleTypeFinalCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.SimpleTypeFinalCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.SimpleTypeFinalCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.SimpleTypeFinalCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }    
    public static final ImmutableSet<XsdContract.SimpleTypeFinalCode> EMPTY_SET = ImmutableSet.of();
  }
  
  enum ProcessContentsCode {
    LAX("lax"),
    SKIP("skip"),
    STRICT("strict");
    
    private String svalue;
    ProcessContentsCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.ProcessContentsCode> parseMap = null;
    public static XsdContract.ProcessContentsCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.ProcessContentsCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.ProcessContentsCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.ProcessContentsCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.ProcessContentsCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }    
  }
  
  enum AttributeUseCode {
    OPTIONAL("optional"),
    PROHIBITED("prohibited"),
    REQUIRED("required");
    
    private String svalue;
    AttributeUseCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.AttributeUseCode> parseMap = null;
    public static XsdContract.AttributeUseCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.AttributeUseCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.AttributeUseCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.AttributeUseCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.AttributeUseCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  enum WhitespaceFacetCode {
    COLLAPSE("collapse"),
    PRESERVE("preserve"),
    REPLACE("replace");
    
    private String svalue;
    WhitespaceFacetCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.WhitespaceFacetCode> parseMap = null;
    public static XsdContract.WhitespaceFacetCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.WhitespaceFacetCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.WhitespaceFacetCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.WhitespaceFacetCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.WhitespaceFacetCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  enum AllowedNamespacesCode {
    ANY("##any"),
    LOCAL("##local"),
    OTHER("##other"),
    TARGETNAMESPACE("##targetNamespace");
    
    private String svalue;
    AllowedNamespacesCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.AllowedNamespacesCode> parseMap = null;
    public static XsdContract.AllowedNamespacesCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.AllowedNamespacesCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.AllowedNamespacesCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.AllowedNamespacesCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.AllowedNamespacesCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  enum UnboundedCode {
    UNBOUNDED("unbounded");
    private String svalue;
    UnboundedCode(String _svalue) {
      svalue = _svalue;
    }
    @Override public String toString() { return svalue; }
    private static Map<String, XsdContract.UnboundedCode> parseMap = null;
    public static XsdContract.UnboundedCode fromString(String _svalue) {
      if(parseMap == null) {
        parseMap = new HashMap();
        for(XsdContract.UnboundedCode current : values()) {
          parseMap.put(current.toString(), current);
        }
      }
      return parseMap.get(_svalue);
    }
    public static XsdContract.UnboundedCode parseString(String _svalue) throws ValidationFailedException {
      XsdContract.UnboundedCode retv = fromString(_svalue);
      if(retv == null) {
        throw new ValidationFailedException("Invalid " + XsdContract.UnboundedCode.class.getCanonicalName() +  " '" + _svalue + "'");
      }
      return retv;
    }
  }
  
  /**
   * CONTEXT: *
   * 
   * Add an xsd:annotation element as a child of the active element and make
   * the xsd:annotation the active element.
   * 
   * @param _id OPTIONAL. The id of the element.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdAnnotation(
          XsdId _id,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );
  
  /**
   * CONTEXT: xsd:annotation
   * 
   * Add an xsd:appinfo element as child of the active element (xsd:annotation) 
   * and make the xsd:annotation the active element.
   * 
   * @param _source OPTIONAL. A URI reference that specifies the 
   * source of the application information.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdAppInfo(
          XsdAnyURI _source,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );
  
  /**
   * CONTEXT: xsd:annotation
   * 
   * Add an xsd:documentation element as a child of the active xsd:annotation 
   * element and make the xsd:documentation element the active element.
   * 
   * @param _source OPTIONAL. A URI reference that specifies the source of the 
   * documentation.
   * @param xml_lang OPTIONAL. This attribute MAY be inserted in documents to 
   * specify the language used in the contents and attribute values of any 
   * element in an XML document. The values are the ISO 2- and 3-letter codes. 
   * An empty string value means the 'un-declaration' of xml:lang. See RFC 3066 
   * at http://www.ietf.org/rfc/rfc3066.txt and the IANA registry at 
   * http://www.iana.org/assignments/lang-tag-apps.htm for further information.
   * @param value OPTIONAL. the documentation text
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdDocumentation(
          XsdAnyURI _source,
          XsdToken _xml_lang,
          String _value,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );
  
  /**
   * CONTEXT: /
   * 
   * Add an xsd:schema element as the document root element and make the 
   * xsd:schema element the active element.
   * 
   * @param _id OPTIONAL. Specifies a unique id for the element.
   * @param _targetNamespace OPTIONAL. Most components corresponding 
   * to representations within a given schema will have a target namespace which 
   * corresponds to the targetNamespace attribute. The appropriate form of 
   * schema document corresponding to a schema whose components have no target 
   * namespace is one which has no targetNamespace attribute specified at all.
   * @param _version OPTIONAL. Specifies the version of the schema. It is for 
   * user convenience, and the XML Schema specification defines no semantics for 
   * it.
   * @param _attributeFormDefault OPTIONAL. The form for attributes declared in 
   * the target namespace of this schema. The value must be "qualified" or 
   * "unqualified". Default is "unqualified". "unqualified" indicates that 
   * attributes from the target namespace are not required to be qualified with 
   * the namespace prefix. "qualified" indicates that attributes from the target 
   * namespace must be qualified with the namespace prefix.
   * @param _elementFormDefault OPTIONAL. The form for elements declared in the 
   * target namespace of this schema. The value must be "qualified" or 
   * "unqualified". Default is "unqualified". "unqualified" indicates that 
   * elements from the target namespace are not required to be qualified with 
   * the namespace prefix.
   * @param _blockDefaultCodes OPTIONAL. Specifies the default value of the block 
   * attribute on element and complexType elements in the target namespace. The 
   * block attribute prevents a complex type (or element) that has a specified 
   * type of derivation from being used in place of this complex type. This 
   * value can contain any of extension, restriction, or substitution. Note: if 
   * #all was specified, this set will contain all values.
   * <ul>
   * <li>extension - prevents complex types derived by extension
   * <li>restriction - prevents complex types derived by restriction</li>
   * <li>substitution - prevents substitution of elements</li>
   * </ul>
   * @param _blockDefaultAllOrNoneCode OPTIONAL. ALL if "#all" was specified, 
   * NONE if "" was specified, NULL if the blockDefault attribute was neither
   * "#all" or "".
   * @param _finalDefaultCodes OPTIONAL. Specifies the default value of the final 
   * attribute on element, simpleType, and complexType elements in the target 
   * namespace. The final attribute prevents a specified type of derivation of 
   * an element, simpleType, or complexType element. Note: if #all is specified 
   * this set will contain all values.
   * <ul>
   * <li>extension - prevents derivation by extension</li>
   * <li>restriction - prevents derivation by restriction</li>
   * <li>list - prevents derivation by list</li>
   * <li>union - prevents derivation by union</li>
   * </ul>
   * @param _finalDefaultAllOrNoneCode OPTIONAL. ALL if "#all" was specified, 
   * NONE if "" was specified, NULL if the finalDefault attribute was neither 
   * "#all" or ""
   * @param _xml_lang OPTIONAL. This attribute MAY be inserted in documents to 
   * specify the language used in the contents and attribute values of any 
   * element in an XML document. The values are the ISO 2- and 3-letter codes. 
   * An empty string value means the 'un-declaration' of xml:lang. See RFC 3066 
   * at http://www.ietf.org/rfc/rfc3066.txt and the IANA registry at 
   * http://www.iana.org/assignments/lang-tag-apps.htm for further information.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdSchema(
          XsdId _id,
          XsdAnyURI _targetNamespace,
          XsdToken _version,
          XsdContract.FormChoiceCode _attributeFormDefault,
          XsdContract.FormChoiceCode _elementFormDefault,
          ImmutableSet<XsdContract.BlockDefaultCode> _blockDefaultCodes,
          XsdContract.AllOrNoneCode _blockDefaultAllOrNoneCode,
          ImmutableSet<XsdContract.FinalDefaultCode> _finalDefaultCodes,
          XsdContract.AllOrNoneCode _finalDefaultAllOrNoneCode,
          XsdToken _xml_lang,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );
  
  /**
   * CONTEXT: /xsd:schema
   * 
   * Add an xsd:import to the active xsd:schema element and make the xsd:import
   * the active element.
   * 
   * @param _id OPTIONAL. Specifies a unique id for the element.
   * @param _namespace REQUIRED. Specifies the URI of the namespace to import.
   * @param _schemaLocation OPTIONAL. Specifies the URI to the schema for the 
   * imported namespace.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdImport(
          XsdId _id,
          XsdAnyURI _namespace,
          XsdAnyURI _schemaLocation,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * 
   * Add an xsd:include to the active xsd:schema element.
   * 
   * @param _id OPTIONAL. Specifies a unique id for the element.
   * @param _schemaLocation REQUIRED. Specifies the URI to the schema to include 
   * in the target namespace of the containing schema.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdInclude(
          XsdId _id,
          XsdAnyURI _schemaLocation,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * 
   * Add an xsd:notation element to the active xsd:schema element.
   * 
   * @param _id OPTIONAL. Specifies a unique id for the element.
   * @param _name REQUIRED. Specifies a name for the notation.
   * @param _public REQUIRED. Specifies a URI corresponding to the public 
   * identifier.
   * @param system OPTIONAL. Specifies a URI corresponding to the system 
   * identifier.
   * @param _prefixToNamespaceURIMap OPTIONAL. a map that specifies the 
   * namespace attributes present on the element. (xmlns:[prefix]="URI")
   */
  void addXsdNotation(
          XsdId _id,
          XsdName _name,
          XsdAnyURI _public,
          XsdAnyURI _system,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:redine element to the current xsd:element
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * schema document.
   * @param schemaLocation [xsd:anyURI] Required. A URI to the location of a 
   * schema document.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists
   */
  void addXsdRedefine(
          XsdId _id,
          XsdAnyURI _schemaLocation,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * 
   * Add an xsd:element to the current xsd:schema element
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param type [xsd:QName] Required. Specifies a type definition for this 
   * element by reference to a defined type.
   * @param _abstract [xsd:Boolean] Optional. Specifies if the element is 
   * abstract, default is false. Element declarations for which abstract is true 
   * can appear in content models only when substitution is allowed.
   * @param substitutionGroup [xsd:QName] Optional. Defines the substitution 
   * group head element. XML Schema provides a mechanism, called substitution 
   * groups, that allows elements to be substituted for other elements. More 
   * specifically, elements can be assigned to a special group of elements that 
   * are said to be substitutable for a particular named element called the head 
   * element. (Note that the head element must be declared as a global 
   * element.).
   * @param blockCodes [xsd:token set] Optional. Specifies the value of the 
   * block attribute on this element. The block attribute prevents an element 
   * that has a specified type of derivation from being used in place of this 
   * element. This value can contain a subset of extension, restriction, or 
   * substitution. If #all was specified, this set will contain all of the 
   * above.
   * @param blockAll [xsd:token] Optional. TRUE if #all was specified, 
   * FALSE or NULL otherwise.
   * @param _default [xsd:string] Optional, may be NULL. If fixed specified 
   * must be NULL. Specifies a default value for this element. The element must 
   * have either a simple _type, a complex type with simple content or a complex 
   * type with mixed content.
   * @param fixed [xsd:string] Optional, may be NULL. Specifies a fixed value 
   * for this element. If fixed is specified, then the element's content must 
   * either be empty, in which case fixed behaves as default, or its value must 
   * match the supplied constraint value.
   * @param finalCodes [xsd:token set] Optional. Specifies the value of the 
   * final attribute for this element. The final attribute prevents a specified 
   * type of derivation of an element. For elements this value can contain a 
   * subset of extension or restriction. If #all was specified, this set will
   * contain all values.
   * <ul>
   * <li>extension - prevents derivation by extension</li>
   * <li>restriction - prevents derivation by restriction</li>
   * </ul>
   * @param finalAll [xsd:token] Optional. TRUE if #all was specified, 
   * FALSE or NULL otherwise.
   * @param nillable [xsd:Boolean] Optional. Specifies if the element is 
   * nillable, default is false. If nillable is true then an element may also be 
   * valid if it specifies xsi:nil="true" even if it has no text or element 
   * content despite a content type which would otherwise require content.
   * @param anyAttributes Optional. Non-XML Schema namespace attributes
   * @param namespacePrefixes Optional. Namespace prefixes assignments 
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists
   */
  void addXsdElementDeclaration(
          XsdId _id,
          XsdName _name,
          XsdQName _type,
          Boolean _abstract,
          XsdQName _substitutionGroup,
          ImmutableSet<XsdContract.ElementBlockCode> _blockCodes,
          Boolean _blockCodeAll,
          String _default,
          String _fixed,
          ImmutableSet<XsdContract.ElementFinalCode> _finalCodes,
          Boolean _finalAll,
          Boolean _nillable,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:attribute element to the current xsd:schema element.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param type [xsd:QName] Required. Specifies a type definition for this 
   * element by reference to a defined type.
   * @param _default [xsd:string] Optional, may be NULL. If fixed specified 
   * must be NULL. Specifies a default value for this element. 
   * type with mixed content.
   * @param fixed [xsd:string] Optional, may be NULL. Specifies a fixed value 
   * for this element. 
   * @param anyAttributes Optional. Non-XML Schema namespace attributes
   * @param namespacePrefixes Optional. Namespace prefixes assignments 
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdAttributeDeclaration(
          XsdId _id,
          XsdName _name,
          XsdQName _type,
          String _default,
          String _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:complexType element to the current xsd:schema element.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param _abstract [xsd:Boolean] Optional. Specifies if the element is 
   * abstract, default is false. Element declarations for which abstract is true 
   * can appear in content models only when substitution is allowed.
   * @param mixed [xsd:Boolean] Optional, defaults to false. Specifies if the 
   * content model is formed both from elements and character data. Not allowed 
   * if simpleContent child is chosen. May be overridden by setting on 
   * complexContent child.
   * @param block1 [xsd:token list] Optional, may be NULL. If block2 is NOT NULL
   * then must be NULL. Specifies the value of the block attribute on this 
   * complexType. The block attribute prevents a complex type that has a 
   * specified type of derivation from being used in place of this complex type. 
   * This value can contain #all or a list that is a subset of extension and 
   * restriction:
   * <ul>
   * <li>extension - prevents complex types derived by extension</li>
   * <li>restriction - prevents complex types derived by restriction</li>
   * <li>#all - prevents all derived complex types.</li>
   * </ul>
   * @param block2 [xsd:otken] Optional, may be NULL. If block1 is NOT NULL then
   * must be NULL.
   * @param final1 [xsd:token list] Optional, may be NULL. If final2 is NOT NULL 
   * then must be NULL. A complex type with an empty specification for final can 
   * be used as a base type definition for other types derived by either of 
   * extension or restriction; the explicit values extension, and restriction 
   * prevent further derivations by extension and restriction respectively. If 
   * all values are specified, then the complex type is said to be final, 
   * because no further derivations are possible. Finality is not inherited, 
   * that is, a type definition derived by restriction from a type definition 
   * which is final for extension is not itself, in the absence of any explicit 
   * final attribute of its own, final for anything.
   * @param final2 [xsd:token] Optional, may be NULL. If block1 is NOT NULL then
   * must be NULL.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdComplexType(
          XsdId _id,
          XsdName _name,
          Boolean _abstract,
          Boolean _mixed,
          ImmutableSet<XsdContract.ComplexTypeBlockCode> _blockCodes,
          Boolean _blockAll,
          ImmutableSet<XsdContract.ComplexTypeFinalCode> _finalCodes,
          Boolean _finalAll,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:simpleType element to the current xsd:schema element.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param finalCodes [xsd:token list] Specifies the value of the final 
   * attribute on this simpleType. The final attribute prevents a specified type 
   * of derivation. This value can contain a subset of restriction, list or 
   * union. If #all was specified, the value will contain 
   * <ul>
   * <li>restriction - prevents derivation by restriction</li>
   * <li>list - prevents derivation by list</li>
   * <li>union - prevents derivation by union</li>
   * <li>#all - prevents all derivation</li>
   * </ul>
   * @param anyAttributes Optional. Non-XML Schema namespace attributes
   * @param namespacePrefixes Optional. Namespace prefixes assignments 
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdSimpleType(
          XsdId _id,
          XsdName _name,
          ImmutableSet<XsdContract.SimpleTypeFinalCode> _finalCodes,
          Boolean _finalAll,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:group element to the current xsd:schema element.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdGroup(
          XsdId _id,
          XsdName _name,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: /xsd:schema
   * Add an xsd:attributeGroup element to the current xsd:schema element.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdAttributeGroup(
          XsdId _id,
          XsdName _name,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );


  /*
   * CONTEXT: xsd:element
   */
  /**
   * CONTEXT: xsd:element
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param mixed [xsd:Boolean] Optional, defaults to false. Specifies if the 
   * content model is formed both from elements and character data. Not allowed 
   * if simpleContent child is chosen. May be overridden by setting on 
   * complexContent child.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdLocalComplexType(
          XsdId _id,
          Boolean _mixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * 
   * 
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdKey(
          XsdId _id,
          XsdName _name,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdKeyRef(
          XsdId _id,
          XsdName _name,
          String _refer,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdUnique(
          XsdId _id,
          XsdName _name,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: key or keyref
   */
  void addXsdField(
          XsdId _id,
          String _xpath,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdSelector(
          XsdId _id,
          String _xpath,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:element or xsd:attribute
   */
  void addXsdLocalSimpleType(
          XsdId _id,
          XsdName _name,
          ImmutableSet<XsdContract.SimpleTypeFinalCode> _finalCodes,
          Boolean _finalAll,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:complexType
   */
  void addXsdComplexContent(
          XsdId _id,
          Boolean _mixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdSimpleContent(
          XsdId _id,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );


  /*
   * CONTEXT : xsd:complexContent
   */
  void addXsdComplexContentRestriction(
          XsdId _id,
          XsdQName _base,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdSimpleContentRestriction(
          XsdId _id,
          XsdQName _base,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );
  
  void addXsdExtension(
          XsdId _id,
          XsdQName _base,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );


  /*
   * CONTEXT: xsd:complexType, xsd:complexType/xsd:complexContent
   */
  void addXsdAll(
          XsdId _id,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:complexType,
   *          xsd:complexType/xsd:complexContent/xsd:extension
   *          xsd:complexType/xsd:complexContent/xsd:restriction
   *          xsd:sequence
   *          xsd:choice
   */
  void addXsdSequence(
          XsdId _id,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdChoice(
          XsdId _id,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:sequence, xsd:choice
   */
  void addXsdAny(
          XsdId _id,
          ImmutableSet<XsdAnyURI> _allowedNamespaces,
          XsdContract.AllowedNamespacesCode _allowedNamespaceCode,
          XsdContract.ProcessContentsCode _processContentsCode,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:complexType, 
   *          xsd:complexType/xsd:complexContent/xsd:extension, 
   *          xsd:complexType/xsd:complexContent/xsd:restriction
   */
  /**
   * CONTEXT: xsd:sequence,
   *          xsd:all,
   *          xsd:choice
   * 
   * Add an xsd:group reference element to the current mode group.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param ref [xsd:QName] Required. Specifies a reference.
   * @param minOccurs [xsd:nonNegativeInteger] Optional. Specifies the minimum 
   * number of occurrences for this particle. Default is 1.
   * @param maxOccurs1 [xsd:positiveInteger] Optional. If maxOccurs2 is NOT NULL 
   * then the value of maxOccurs1 is undefined.
   * @param maxOccurs2 [xsd:token] Optional, may be NULL. If maxOccurs2 is NULL
   * then the value of maxOccurs1 is undefined.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdGroupUse(
          XsdId _id,
          XsdQName _ref,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: 
   *  xsd:sequence,
   *  xsd:all,
   *  xsd:choice
   * 
   * Add an xsd:element reference to another xsd:element declaration to the 
   * current model group.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param ref [xsd:QName] Required. Specifies a reference.
   * @param minOccurs [xsd:nonNegativeInteger] Optional. Specifies the minimum 
   * number of occurrences for this particle. Default is 1.
   * @param maxOccurs1 [xsd:positiveInteger] Optional. If maxOccurs2 is NOT NULL 
   * then the value of maxOccurs1 is undefined.
   * @param maxOccurs2 [xsd:token] Optional, may be NULL. If maxOccurs2 is NULL
   * then the value of maxOccurs1 is undefined.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdElementUse(
          XsdId _id,
          XsdQName _ref,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: 
   *  xsd:sequence,
   *  xsd:all,
   *  xsd:choice
   * 
   * Add a local xsd:element to the current model group.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param type [xsd:QName] Required. Specifies a type definition for this 
   * element by reference to a defined type.
   * @param _abstract [xsd:Boolean] Optional. Specifies if the element is 
   * abstract, default is false. Element declarations for which abstract is true 
   * can appear in content models only when substitution is allowed.
   * @param block1 [xsd:token list] Optional, may be NULL. If block2 is NOT NULL
   * then must be NULL. Specifies the value of the block attribute on this 
   * element. The block attribute prevents an element that has a specified type 
   * of derivation from being used in place of this element. This value can 
   * contain #all or a list that is a subset of extension, restriction, or 
   * substitution.
   * @param block2 [xsd:token] Optional, may be NULL. If block1 is NOT NULL then
   * must be NULL.
   * @param _default [xsd:string] Optional, may be NULL. If fixed specified 
   * must be NULL. Specifies a default value for this element. The element must 
   * have either a simple _type, a complex type with simple content or a complex 
   * type with mixed content.
   * @param fixed [xsd:string] Optional, may be NULL. Specifies a fixed value 
   * for this element. If fixed is specified, then the element's content must 
   * either be empty, in which case fixed behaves as default, or its value must 
   * match the supplied constraint value.
   * @param form [xsd:token] Optional, may be NULL. Specifies the target 
   * namespace for this element. The value must be "qualified" or "unqualified". 
   * The default value is provided by the elementFormDefault attribute on the 
   * enclosing <schema> element. "unqualified" indicates that a local element 
   * belongs to no namespace. "qualified" indicates that a local element belongs 
   * to the schema target namespace.
   * @param nillable [xsd:Boolean] Optional. Specifies if the element is 
   * nillable, default is false. If nillable is true then an element may also be 
   * valid if it specifies xsi:nil="true" even if it has no text or element 
   * content despite a content type which would otherwise require content.
   * @param minOccurs [xsd:nonNegativeInteger] Optional. Specifies the minimum 
   * number of occurrences for this particle. Default is 1.
   * @param maxOccurs1 [xsd:positiveInteger] Optional. If maxOccurs2 is NOT NULL 
   * then the value of maxOccurs1 is undefined.
   * @param maxOccurs2 [xsd:token] Optional, may be NULL. If maxOccurs2 is NULL
   * then the value of maxOccurs1 is undefined.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XsdException 
   */
  void addXsdLocalElement(
          XsdId _id,
          XsdName _name,
          XsdQName _type,
          ImmutableSet<XsdContract.ElementBlockCode> _blockCodes,
          Boolean _blockAll,
          String _default,
          String _fixed,
          XsdContract.FormChoiceCode _form,
          Boolean _nillable,
          XsdNonNegativeInteger _minOccurs,
          XsdNonNegativeInteger _maxOccurs,
          Boolean _maxOccursUnbounded,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:complexType, 
   *          xsd:complexType/xsd:complexContent/xsd:extension, 
   *          xsd:complexType/xsd:complexContent/xsd:restriction,
   *          xsd:complexType/xsd:simpleContent/xsd:extension
   *          xsd:complexType/xsd:simpleContent/xsd:restriction
   */
  void addXsdAnyAttribute(
          XsdId _id,
          ImmutableSet<XsdAnyURI> _namespace,
          XsdContract.AllowedNamespacesCode _allowedNamespaceCode,
          XsdContract.ProcessContentsCode _processContents,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: xsd:complexType, 
   *          xsd:complexType/xsd:complexContent/xsd:extension, 
   *          xsd:complexType/xsd:complexContent/xsd:restriction,
   *          xsd:complexType/xsd:simpleContent/xsd:extension
   *          xsd:complexType/xsd:simpleContent/xsd:restriction
   * 
   * Add an xsd:attribute reference to another attribute declaration to the 
   * current model group.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param ref [xsd:QName] Required. Specifies a reference.
   * @param _default [xsd:string] Optional, may be NULL. If fixed specified 
   * must be NULL. Specifies a default value for this element. 
   * type with mixed content.
   * @param fixed [xsd:string] Optional, may be NULL. Specifies a fixed value 
   * for this element. 
   * @param use [xsd:token] Optional. Specifies the attribute use: prohibited, 
   * optional or required, default is optional.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdAttributeUse(
          XsdId _id,
          XsdQName _ref,
          String _default,
          String _fixed,
          XsdContract.AttributeUseCode _use,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: xsd:complexType, 
   *          xsd:complexType/xsd:complexContent/xsd:extension, 
   *          xsd:complexType/xsd:complexContent/xsd:restriction,
   *          xsd:complexType/xsd:simpleContent/xsd:extension
   *          xsd:complexType/xsd:simpleContent/xsd:restriction
   * 
   * Add a local xsd:attribute declaration to the current model group.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param name [xsd:NCName] Required. Specifies the required top level element 
   * name.
   * @param type [xsd:QName] Required. Specifies a type definition for this 
   * element by reference to a defined type.
   * @param _default [xsd:string] Optional, may be NULL. If fixed specified 
   * must be NULL. Specifies a default value for this element. 
   * type with mixed content.
   * @param fixed [xsd:string] Optional, may be NULL. Specifies a fixed value 
   * for this element. 
   * @param use [xsd:token] Optional. Specifies the attribute use: prohibited, 
   * optional or required, default is optional.
   * @throws XmlIdExistsException if the id already exists 
   */
  void addXsdLocalAttribute(
          XsdId _id,
          XsdName _name,
          XsdQName _type,
          String _default,
          String _fixed,
          XsdContract.AttributeUseCode _use,
          XsdContract.FormChoiceCode _form,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /**
   * CONTEXT: xsd:complexType, 
   *          xsd:complexType/xsd:complexContent/xsd:extension, 
   *          xsd:complexType/xsd:complexContent/xsd:restriction,
   *          xsd:complexType/xsd:simpleContent/xsd:extension
   *          xsd:complexType/xsd:simpleContent/xsd:restriction
   * 
   * Add an xsd:attributeGroup reference to a complex type.
   * 
   * @param id [xsd:ID] Optional. Specifies a unique id for the element.
   * @param ref [xsd:QName] Required. Specifies a reference.
   * @param _publicId OPTIONAL. The public identifier of the document entity or 
   * of the external parsed entity
   * @param _systemId OPTIONAL. the system identifier of the document entity or 
   * of the external parsed entity. May be a URL. If the system identifier is a 
   * URL it must be fully resolved before passing to this method.
   * @param _lineNumber OPTIONAL. the line number of the element in the source 
   * document
   * @param _columnNumber OPTIONAL. the column number of the element in the 
   * source document
   * @throws XsdException 
   */
  void addXsdAttributeGroupUse(
          XsdId _id,
          XsdQName _ref,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: xsd:simpleType
   */
  void addXsdSimpleTypeRestriction(
          XsdId _id,
          XsdQName _base,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdUnion(
          XsdId _id,
          ImmutableSet<XsdQName> _memberTypes,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdList(
          XsdId _id,
          XsdQName _itemType,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  /*
   * CONTEXT: /xsd:schema/xsd:simpleType/xsd:restriction
   */
  void addXsdEnumeration(
          XsdId _id,
          String _facetValue,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdFractionDigits(
          XsdId _id,
          XsdNonNegativeInteger _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdLength(
          XsdId _id,
          XsdNonNegativeInteger _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMinLength(
          XsdId _id,
          XsdNonNegativeInteger _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMaxLength(
          XsdId _id,
          XsdNonNegativeInteger _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMaxExclusive(
          XsdId _id,
          String _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMaxInclusive(
          XsdId _id,
          String _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMinExclusive(
          XsdId _id,
          String _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdMinInclusive(
          XsdId _id,
          String _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdPattern(
          XsdId _id,
          String _facetValue,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdTotalDigits(
          XsdId _id,
          XsdPositiveInteger _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void addXsdWhitespace(
          XsdId _id,
          XsdContract.WhitespaceFacetCode _facetValue,
          Boolean _fixed,
          ImmutableMap<XsdNCName, XsdAnyURI> _prefixToNamespaceURIMap
          );

  void endXsdElement();
}
