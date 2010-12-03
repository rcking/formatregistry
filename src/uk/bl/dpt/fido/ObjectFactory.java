package uk.bl.dpt.fido;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Formats.Format.Extension }
     * 
     */
    public Formats.Format.Extension createFormatsFormatExtension() {
        return new Formats.Format.Extension();
    }

    /**
     * Create an instance of {@link Formats.Format.Signature }
     * 
     */
    public Formats.Format.Signature createFormatsFormatSignature() {
        return new Formats.Format.Signature();
    }

    /**
     * Create an instance of {@link Formats.Format }
     * 
     */
    public Formats.Format createFormatsFormat() {
        return new Formats.Format();
    }

    /**
     * Create an instance of {@link Formats.Format.Signature.Pattern }
     * 
     */
    public Formats.Format.Signature.Pattern createFormatsFormatSignaturePattern() {
        return new Formats.Format.Signature.Pattern();
    }

    /**
     * Create an instance of {@link Formats.Format.HasPriorityOver }
     * 
     */
    public Formats.Format.HasPriorityOver createFormatsFormatHasPriorityOver() {
        return new Formats.Format.HasPriorityOver();
    }

    /**
     * Create an instance of {@link Formats }
     * 
     */
    public Formats createFormats() {
        return new Formats();
    }

    /**
     * Create an instance of {@link Formats.Format.Mime }
     * 
     */
    public Formats.Format.Mime createFormatsFormatMime() {
        return new Formats.Format.Mime();
    }

}
