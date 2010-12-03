package uk.bl.dpt.fido;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "format"
})
@XmlRootElement(name = "formats")
public class Formats {

    protected List<Formats.Format> format;
    @XmlAttribute
    protected String version;

    /**
     * Gets the value of the format property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the format property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getFormat().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Formats.Format }
     * 
     * 
     */
    public List<Formats.Format> getFormat() {
        if (format == null) {
            format = new ArrayList<Formats.Format>();
        }
        return this.format;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVersion(String value) {
        this.version = value;
    }

    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "puid",
        "name",
        "pronomId",
        "container",
        "mime",
        "extension",
        "hasPriorityOver",
        "signature"
    })
    public static class Format {

        protected String puid;
        protected String name;
        @XmlElement(name = "pronom_id")
        protected String pronomId;
        protected String container;
        @XmlElement(nillable = true)
        protected List<Formats.Format.Mime> mime;
        @XmlElement(nillable = true)
        protected List<Formats.Format.Extension> extension;
        @XmlElement(name = "has_priority_over", nillable = true)
        protected List<Formats.Format.HasPriorityOver> hasPriorityOver;
        protected List<Formats.Format.Signature> signature;

        /**
         * Gets the value of the puid property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPuid() {
            return puid;
        }

        /**
         * Sets the value of the puid property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPuid(String value) {
            this.puid = value;
        }

        /**
         * Gets the value of the name property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getName() {
            return name;
        }

        /**
         * Sets the value of the name property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setName(String value) {
            this.name = value;
        }

        /**
         * Gets the value of the pronomId property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getPronomId() {
            return pronomId;
        }

        /**
         * Sets the value of the pronomId property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setPronomId(String value) {
            this.pronomId = value;
        }

        /**
         * Gets the value of the container property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getContainer() {
            return container;
        }

        /**
         * Sets the value of the container property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setContainer(String value) {
            this.container = value;
        }

        /**
         * Gets the value of the mime property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the mime property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getMime().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Formats.Format.Mime }
         * 
         * 
         */
        public List<Formats.Format.Mime> getMime() {
            if (mime == null) {
                mime = new ArrayList<Formats.Format.Mime>();
            }
            return this.mime;
        }

        /**
         * Gets the value of the extension property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the extension property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getExtension().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Formats.Format.Extension }
         * 
         * 
         */
        public List<Formats.Format.Extension> getExtension() {
            if (extension == null) {
                extension = new ArrayList<Formats.Format.Extension>();
            }
            return this.extension;
        }

        /**
         * Gets the value of the hasPriorityOver property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the hasPriorityOver property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getHasPriorityOver().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Formats.Format.HasPriorityOver }
         * 
         * 
         */
        public List<Formats.Format.HasPriorityOver> getHasPriorityOver() {
            if (hasPriorityOver == null) {
                hasPriorityOver = new ArrayList<Formats.Format.HasPriorityOver>();
            }
            return this.hasPriorityOver;
        }

        /**
         * Gets the value of the signature property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the signature property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getSignature().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link Formats.Format.Signature }
         * 
         * 
         */
        public List<Formats.Format.Signature> getSignature() {
            if (signature == null) {
                signature = new ArrayList<Formats.Format.Signature>();
            }
            return this.signature;
        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Extension {

            @XmlValue
            protected String value;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class HasPriorityOver {

            @XmlValue
            protected String value;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "value"
        })
        public static class Mime {

            @XmlValue
            protected String value;

            /**
             * Gets the value of the value property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getValue() {
                return value;
            }

            /**
             * Sets the value of the value property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setValue(String value) {
                this.value = value;
            }

        }

        @XmlAccessorType(XmlAccessType.FIELD)
        @XmlType(name = "", propOrder = {
            "name",
            "note",
            "pattern"
        })
        public static class Signature {

            protected String name;
            protected String note;
            protected List<Formats.Format.Signature.Pattern> pattern;

            /**
             * Gets the value of the name property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getName() {
                return name;
            }

            /**
             * Sets the value of the name property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setName(String value) {
                this.name = value;
            }

            /**
             * Gets the value of the note property.
             * 
             * @return
             *     possible object is
             *     {@link String }
             *     
             */
            public String getNote() {
                return note;
            }

            /**
             * Sets the value of the note property.
             * 
             * @param value
             *     allowed object is
             *     {@link String }
             *     
             */
            public void setNote(String value) {
                this.note = value;
            }

            /**
             * Gets the value of the pattern property.
             * 
             * <p>
             * This accessor method returns a reference to the live list,
             * not a snapshot. Therefore any modification you make to the
             * returned list will be present inside the JAXB object.
             * This is why there is not a <CODE>set</CODE> method for the pattern property.
             * 
             * <p>
             * For example, to add a new item, do as follows:
             * <pre>
             *    getPattern().add(newItem);
             * </pre>
             * 
             * 
             * <p>
             * Objects of the following type(s) are allowed in the list
             * {@link Formats.Format.Signature.Pattern }
             * 
             * 
             */
            public List<Formats.Format.Signature.Pattern> getPattern() {
                if (pattern == null) {
                    pattern = new ArrayList<Formats.Format.Signature.Pattern>();
                }
                return this.pattern;
            }

            @XmlAccessorType(XmlAccessType.FIELD)
            @XmlType(name = "", propOrder = {
                "position",
                "pronomPattern",
                "regex"
            })
            public static class Pattern {

                protected String position;
                @XmlElement(name = "pronom_pattern")
                protected String pronomPattern;
                protected String regex;

                /**
                 * Gets the value of the position property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPosition() {
                    return position;
                }

                /**
                 * Sets the value of the position property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPosition(String value) {
                    this.position = value;
                }

                /**
                 * Gets the value of the pronomPattern property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getPronomPattern() {
                    return pronomPattern;
                }

                /**
                 * Sets the value of the pronomPattern property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setPronomPattern(String value) {
                    this.pronomPattern = value;
                }

                /**
                 * Gets the value of the regex property.
                 * 
                 * @return
                 *     possible object is
                 *     {@link String }
                 *     
                 */
                public String getRegex() {
                    return regex;
                }

                /**
                 * Sets the value of the regex property.
                 * 
                 * @param value
                 *     allowed object is
                 *     {@link String }
                 *     
                 */
                public void setRegex(String value) {
                    this.regex = value;
                }

            }

        }

    }

}
