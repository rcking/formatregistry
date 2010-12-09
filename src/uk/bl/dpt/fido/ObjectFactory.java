//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.12.09 at 08:17:52 AM MEZ 
//


package uk.bl.dpt.fido;

import java.math.BigInteger;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the uk.bl.dpt.fido package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Extension_QNAME = new QName("", "extension");
    private final static QName _Position_QNAME = new QName("", "position");
    private final static QName _PronomId_QNAME = new QName("", "pronom_id");
    private final static QName _HasPriorityOver_QNAME = new QName("", "has_priority_over");
    private final static QName _PronomPattern_QNAME = new QName("", "pronom_pattern");
    private final static QName _Container_QNAME = new QName("", "container");
    private final static QName _Regex_QNAME = new QName("", "regex");
    private final static QName _Name_QNAME = new QName("", "name");
    private final static QName _Mime_QNAME = new QName("", "mime");
    private final static QName _Note_QNAME = new QName("", "note");
    private final static QName _Puid_QNAME = new QName("", "puid");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: uk.bl.dpt.fido
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Formats }
     * 
     */
    public Formats createFormats() {
        return new Formats();
    }

    /**
     * Create an instance of {@link Signature }
     * 
     */
    public Signature createSignature() {
        return new Signature();
    }

    /**
     * Create an instance of {@link Pattern }
     * 
     */
    public Pattern createPattern() {
        return new Pattern();
    }

    /**
     * Create an instance of {@link Format }
     * 
     */
    public Format createFormat() {
        return new Format();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "extension")
    public JAXBElement<String> createExtension(String value) {
        return new JAXBElement<String>(_Extension_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link PositionType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "position")
    public JAXBElement<PositionType> createPosition(PositionType value) {
        return new JAXBElement<PositionType>(_Position_QNAME, PositionType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BigInteger }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pronom_id")
    public JAXBElement<BigInteger> createPronomId(BigInteger value) {
        return new JAXBElement<BigInteger>(_PronomId_QNAME, BigInteger.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "has_priority_over")
    public JAXBElement<String> createHasPriorityOver(String value) {
        return new JAXBElement<String>(_HasPriorityOver_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "pronom_pattern")
    public JAXBElement<String> createPronomPattern(String value) {
        return new JAXBElement<String>(_PronomPattern_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ContainerType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "container")
    public JAXBElement<ContainerType> createContainer(ContainerType value) {
        return new JAXBElement<ContainerType>(_Container_QNAME, ContainerType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "regex")
    public JAXBElement<String> createRegex(String value) {
        return new JAXBElement<String>(_Regex_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "name")
    public JAXBElement<String> createName(String value) {
        return new JAXBElement<String>(_Name_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "mime")
    public JAXBElement<String> createMime(String value) {
        return new JAXBElement<String>(_Mime_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "note")
    public JAXBElement<String> createNote(String value) {
        return new JAXBElement<String>(_Note_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "puid")
    public JAXBElement<String> createPuid(String value) {
        return new JAXBElement<String>(_Puid_QNAME, String.class, null, value);
    }

}
