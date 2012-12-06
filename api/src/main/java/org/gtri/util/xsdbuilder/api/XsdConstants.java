/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.gtri.util.xsdbuilder.api;

import java.util.HashSet;
import java.util.Set;
import javax.xml.XMLConstants;
import org.gtri.util.xmlbuilder.api.XmlConstants;
import org.gtri.util.xsddatatypes.XsdAnyURI;
import org.gtri.util.xsddatatypes.XsdNCName;
import org.gtri.util.xsddatatypes.XsdQName;

/**
 *
 * @author Lance
 */
public interface XsdConstants {

    XsdAnyURI URI = new XsdAnyURI(XMLConstants.W3C_XML_SCHEMA_NS_URI);
    XsdNCName PREFIX = new XsdNCName("xsd");

    interface BUILTIN_DATATYPES {

        Set<XsdQName> ALL = new HashSet() {

            {
                add(ANY_TYPE.QNAME);
                add(ANY_SIMPLE_TYPE.QNAME);
                add(DURATION.QNAME);
                add(DATE_TIME.QNAME);
                add(TIME.QNAME);
                add(DATE.QNAME);
                add(G_YEAR_MONTH.QNAME);
                add(G_YEAR.QNAME);
                add(G_MONTH_DAY.QNAME);
                add(G_DAY.QNAME); // 10
                add(G_MONTH.QNAME);
                add(BOOLEAN.QNAME);
                add(BASE_64_BINARY.QNAME);
                add(HEX_BINARY.QNAME);
                add(FLOAT.QNAME);
                add(DOUBLE.QNAME);
                add(ANY_URI.QNAME);
                add(QNAME.QNAME);
                add(NOTATION.QNAME);
                add(STRING.QNAME); // 20
                add(DECIMAL.QNAME);
                add(NORMALIZED_STRING.QNAME);
                add(INTEGER.QNAME);
                add(TOKEN.QNAME);
                add(NON_POSITIVE_INTEGER.QNAME);
                add(LONG.QNAME);
                add(NON_NEGATIVE_INTEGER.QNAME);
                add(LANGUAGE.QNAME);
                add(NAME.QNAME);
                add(NMTOKEN.QNAME); // 30
                add(NEGATIVE_INTEGER.QNAME);
                add(INT.QNAME);
                add(UNSIGNED_LONG.QNAME);
                add(POSITIVE_INTEGER.QNAME);
                add(NCNAME.QNAME);
                add(NMTOKENS.QNAME);
                add(SHORT.QNAME);
                add(UNSIGNED_INT.QNAME);
                add(ID.QNAME);
                add(IDREF.QNAME); // 40
                add(ENTITY.QNAME);
                add(BYTE.QNAME);
                add(UNSIGNED_SHORT.QNAME);
                add(IDREFS.QNAME);
                add(ENTITIES.QNAME);
                add(UNSIGNED_BYTE.QNAME); // 46
            }

        };

        interface ANY_TYPE {

            XsdNCName LOCALNAME = new XsdNCName("anyType");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ANY_SIMPLE_TYPE {

            XsdNCName LOCALNAME = new XsdNCName("anySimpleType");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DURATION {

            XsdNCName LOCALNAME = new XsdNCName("duration");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DATE_TIME {

            XsdNCName LOCALNAME = new XsdNCName("dateTime");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface TIME {

            XsdNCName LOCALNAME = new XsdNCName("time");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DATE {

            XsdNCName LOCALNAME = new XsdNCName("date");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface G_YEAR_MONTH {

            XsdNCName LOCALNAME = new XsdNCName("gYearMonth");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface G_YEAR {

            XsdNCName LOCALNAME = new XsdNCName("gYear");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface G_MONTH_DAY {

            XsdNCName LOCALNAME = new XsdNCName("gMonthDay");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface G_DAY {

            XsdNCName LOCALNAME = new XsdNCName("gDay");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface G_MONTH {

            XsdNCName LOCALNAME = new XsdNCName("gMonth");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface BOOLEAN {

            XsdNCName LOCALNAME = new XsdNCName("boolean");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface BASE_64_BINARY {

            XsdNCName LOCALNAME = new XsdNCName("base64binary");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface HEX_BINARY {

            XsdNCName LOCALNAME = new XsdNCName("hexBinary");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface FLOAT {

            XsdNCName LOCALNAME = new XsdNCName("float");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DECIMAL {

            XsdNCName LOCALNAME = new XsdNCName("decimal");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DOUBLE {

            XsdNCName LOCALNAME = new XsdNCName("double");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ANY_URI {

            XsdNCName LOCALNAME = new XsdNCName("anyURI");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface QNAME {

            XsdNCName LOCALNAME = new XsdNCName("QName");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NOTATION {

            XsdNCName LOCALNAME = new XsdNCName("NOTATION");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface STRING {

            XsdNCName LOCALNAME = new XsdNCName("string");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NORMALIZED_STRING {

            XsdNCName LOCALNAME = new XsdNCName("normalizedString");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface TOKEN {

            XsdNCName LOCALNAME = new XsdNCName("token");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface LANGUAGE {

            XsdNCName LOCALNAME = new XsdNCName("language");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NAME {

            XsdNCName LOCALNAME = new XsdNCName("Name");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NCNAME {

            XsdNCName LOCALNAME = new XsdNCName("NCName");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ID {

            XsdNCName LOCALNAME = new XsdNCName("ID");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface IDREF {

            XsdNCName LOCALNAME = new XsdNCName("IDREF");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface IDREFS {

            XsdNCName LOCALNAME = new XsdNCName("IDREFS");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ENTITY {

            XsdNCName LOCALNAME = new XsdNCName("ENTITY");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ENTITIES {

            XsdNCName LOCALNAME = new XsdNCName("ENTITIES");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NMTOKEN {

            XsdNCName LOCALNAME = new XsdNCName("NMTOKEN");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NMTOKENS {

            XsdNCName LOCALNAME = new XsdNCName("NMTOKENS");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface INTEGER {

            XsdNCName LOCALNAME = new XsdNCName("integer");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NON_POSITIVE_INTEGER {

            XsdNCName LOCALNAME = new XsdNCName("nonPositiveInteger");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NEGATIVE_INTEGER {

            XsdNCName LOCALNAME = new XsdNCName("negativeInteger");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface LONG {

            XsdNCName LOCALNAME = new XsdNCName("long");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface INT {

            XsdNCName LOCALNAME = new XsdNCName("int");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SHORT {

            XsdNCName LOCALNAME = new XsdNCName("short");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface BYTE {

            XsdNCName LOCALNAME = new XsdNCName("byte");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NON_NEGATIVE_INTEGER {

            XsdNCName LOCALNAME = new XsdNCName("nonNegativeInteger");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNSIGNED_LONG {

            XsdNCName LOCALNAME = new XsdNCName("unsignedLong");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNSIGNED_INT {

            XsdNCName LOCALNAME = new XsdNCName("unsignedInt");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNSIGNED_SHORT {

            XsdNCName LOCALNAME = new XsdNCName("unsignedShort");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNSIGNED_BYTE {

            XsdNCName LOCALNAME = new XsdNCName("unsignedByte");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface POSITIVE_INTEGER {

            XsdNCName LOCALNAME = new XsdNCName("positiveInteger");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

    } // BUILTIN_DATATYPES

    interface ELEMENTS {

        interface ANNOTATION {

            XsdNCName LOCALNAME = new XsdNCName("annotation");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface APPINFO {

            XsdNCName LOCALNAME = new XsdNCName("appinfo");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface DOCUMENTATION {

            XsdNCName LOCALNAME = new XsdNCName("documentation");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SCHEMA {

            XsdNCName LOCALNAME = new XsdNCName("schema");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface IMPORT {

            XsdNCName LOCALNAME = new XsdNCName("import");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface INCLUDE {

            XsdNCName LOCALNAME = new XsdNCName("include");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface NOTATION {

            XsdNCName LOCALNAME = new XsdNCName("notation");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface REDEFINE {

            XsdNCName LOCALNAME = new XsdNCName("redefine");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ELEMENT {

            XsdNCName LOCALNAME = new XsdNCName("element");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ATTRIBUTE {

            XsdNCName LOCALNAME = new XsdNCName("attribute");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface COMPLEXTYPE {

            XsdNCName LOCALNAME = new XsdNCName("complexType");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SIMPLETYPE {

            XsdNCName LOCALNAME = new XsdNCName("simpleType");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface GROUP {

            XsdNCName LOCALNAME = new XsdNCName("group");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ATTRIBUTEGROUP {

            XsdNCName LOCALNAME = new XsdNCName("attributeGroup");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface KEY {

            XsdNCName LOCALNAME = new XsdNCName("key");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface KEYREF {

            XsdNCName LOCALNAME = new XsdNCName("keyref");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNIQUE {

            XsdNCName LOCALNAME = new XsdNCName("unique");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface FIELD {

            XsdNCName LOCALNAME = new XsdNCName("field");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SELECTOR {

            XsdNCName LOCALNAME = new XsdNCName("selector");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface COMPLEXCONTENT {

            XsdNCName LOCALNAME = new XsdNCName("complexContent");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SIMPLECONTENT {

            XsdNCName LOCALNAME = new XsdNCName("simpleContent");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface RESTRICTION {

            XsdNCName LOCALNAME = new XsdNCName("restriction");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface EXTENSION {

            XsdNCName LOCALNAME = new XsdNCName("extension");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ALL {

            XsdNCName LOCALNAME = new XsdNCName("all");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface SEQUENCE {

            XsdNCName LOCALNAME = new XsdNCName("sequence");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface CHOICE {

            XsdNCName LOCALNAME = new XsdNCName("choice");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ANY {

            XsdNCName LOCALNAME = new XsdNCName("any");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ANYATTRIBUTE {

            XsdNCName LOCALNAME = new XsdNCName("anyAttribute");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface UNION {

            XsdNCName LOCALNAME = new XsdNCName("union");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface LIST {

            XsdNCName LOCALNAME = new XsdNCName("list");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface ENUMERATION {

            XsdNCName LOCALNAME = new XsdNCName("enumeration");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface FRACTIONDIGITS {

            XsdNCName LOCALNAME = new XsdNCName("fractionDigits");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface LENGTH {

            XsdNCName LOCALNAME = new XsdNCName("length");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MINLENGTH {

            XsdNCName LOCALNAME = new XsdNCName("minLength");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MAXLENGTH {

            XsdNCName LOCALNAME = new XsdNCName("maxLength");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MAXEXCLUSIVE {

            XsdNCName LOCALNAME = new XsdNCName("maxExclusive");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MINEXCLUSIVE {

            XsdNCName LOCALNAME = new XsdNCName("minExclusive");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MAXINCLUSIVE {

            XsdNCName LOCALNAME = new XsdNCName("maxInclusive");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface MININCLUSIVE {

            XsdNCName LOCALNAME = new XsdNCName("minInclusive");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface PATTERN {

            XsdNCName LOCALNAME = new XsdNCName("pattern");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface TOTALDIGITS {

            XsdNCName LOCALNAME = new XsdNCName("totalDigits");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

        interface WHITESPACE {

            XsdNCName LOCALNAME = new XsdNCName("whiteSpace");
            XsdQName QNAME = new XsdQName(URI, LOCALNAME);
        }

    } // ELEMENTS

    interface ATTRIBUTES {

        interface ID {

            XsdNCName LOCALNAME = new XsdNCName("id");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface NAME {

            XsdNCName LOCALNAME = new XsdNCName("name");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface SOURCE {

            XsdNCName LOCALNAME = new XsdNCName("source");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface XML_LANG {

            XsdNCName LOCALNAME = new XsdNCName("lang");
            XsdQName QNAME = new XsdQName(XmlConstants.XML_NS_URI, LOCALNAME);
        }

        interface VALUE {

            XsdNCName LOCALNAME = new XsdNCName("value");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface TARGETNAMESPACE {

            XsdNCName LOCALNAME = new XsdNCName("targetNamespace");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface VERSION {

            XsdNCName LOCALNAME = new XsdNCName("version");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface ATTRIBUTEFORMDEFAULT {

            XsdNCName LOCALNAME = new XsdNCName("attributeFormDefault");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface ELEMENTFORMDEFAULT {

            XsdNCName LOCALNAME = new XsdNCName("elementFormDefault");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface BLOCKDEFAULT {

            XsdNCName LOCALNAME = new XsdNCName("blockDefault");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface FINALDEFAULT {

            XsdNCName LOCALNAME = new XsdNCName("finalDefault");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface NAMESPACE {

            XsdNCName LOCALNAME = new XsdNCName("namespace");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface SCHEMALOCATION {

            XsdNCName LOCALNAME = new XsdNCName("schemaLocation");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface PUBLIC {

            XsdNCName LOCALNAME = new XsdNCName("public");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface SYSTEM {

            XsdNCName LOCALNAME = new XsdNCName("system");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface TYPE {

            XsdNCName LOCALNAME = new XsdNCName("type");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface REF {

            XsdNCName LOCALNAME = new XsdNCName("ref");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface ABSTRACT {

            XsdNCName LOCALNAME = new XsdNCName("abstract");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface SUBSTITUTIONGROUP {

            XsdNCName LOCALNAME = new XsdNCName("substitutionGroup");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface BLOCK {

            XsdNCName LOCALNAME = new XsdNCName("block");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface DEFAULT {

            XsdNCName LOCALNAME = new XsdNCName("default");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface FIXED {

            XsdNCName LOCALNAME = new XsdNCName("fixed");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface FINAL {

            XsdNCName LOCALNAME = new XsdNCName("final");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface NILLABLE {

            XsdNCName LOCALNAME = new XsdNCName("nillable");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface MINOCCURS {

            XsdNCName LOCALNAME = new XsdNCName("minOccurs");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface MAXOCCURS {

            XsdNCName LOCALNAME = new XsdNCName("maxOccurs");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface FORM {

            XsdNCName LOCALNAME = new XsdNCName("form");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface USE {

            XsdNCName LOCALNAME = new XsdNCName("use");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface MIXED {

            XsdNCName LOCALNAME = new XsdNCName("mixed");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface REFER {

            XsdNCName LOCALNAME = new XsdNCName("refer");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface XPATH {

            XsdNCName LOCALNAME = new XsdNCName("xpath");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface BASE {

            XsdNCName LOCALNAME = new XsdNCName("base");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface PROCESSCONTENTS {

            XsdNCName LOCALNAME = new XsdNCName("processContents");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface MEMBERTYPES {

            XsdNCName LOCALNAME = new XsdNCName("memberTypes");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

        interface ITEMTYPE {

            XsdNCName LOCALNAME = new XsdNCName("itemType");
            XsdQName QNAME = new XsdQName(XmlConstants.NULL_NS_URI, LOCALNAME);
        }

    } // ATTRIBUTES

}