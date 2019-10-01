//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7-b41 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.02 at 03:05:54 PM PKT 
//


package com.hourtimesheet.desktop;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;group ref="{}TxnCore"/>
 *         &lt;element ref="{}TxnDate"/>
 *         &lt;element ref="{}EntityRef"/>
 *         &lt;element ref="{}CustomerRef" minOccurs="0"/>
 *         &lt;element ref="{}ItemServiceRef" minOccurs="0"/>
 *         &lt;element ref="{}Rate" minOccurs="0"/>
 *         &lt;element ref="{}Duration"/>
 *         &lt;element ref="{}ClassRef" minOccurs="0"/>
 *         &lt;element ref="{}PayrollItemWageRef" minOccurs="0"/>
 *         &lt;element name="Notes" minOccurs="0">
 *           &lt;simpleType>
 *             &lt;restriction base="{}STRTYPE">
 *               &lt;maxLength value="4095"/>
 *             &lt;/restriction>
 *           &lt;/simpleType>
 *         &lt;/element>
 *         &lt;element ref="{}IsBillable" minOccurs="0"/>
 *         &lt;element ref="{}IsBilled" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "txnID",
    "timeCreated",
    "timeModified",
    "editSequence",
    "txnNumber",
    "txnDate",
    "entityRef",
    "customerRef",
    "itemServiceRef",
    "rate",
    "duration",
    "classRef",
    "payrollItemWageRef",
    "notes",
    "isBillable",
    "isBilled"
})
@XmlRootElement(name = "TimeTrackingRet")
public class TimeTrackingRet {

    @XmlElement(name = "TxnID", required = true)
    protected String txnID;
    @XmlElement(name = "TimeCreated", required = true)
    protected String timeCreated;
    @XmlElement(name = "TimeModified", required = true)
    protected String timeModified;
    @XmlElement(name = "EditSequence", required = true)
    protected String editSequence;
    @XmlElement(name = "TxnNumber", required = true)
    protected BigInteger txnNumber;
    @XmlElement(name = "TxnDate", required = true)
    protected String txnDate;
    @XmlElement(name = "EntityRef", required = true)
    protected EntityRef entityRef;
    @XmlElement(name = "CustomerRef")
    protected CustomerRef customerRef;
    @XmlElement(name = "ItemServiceRef")
    protected ItemServiceRef itemServiceRef;
    @XmlElement(name = "Rate")
    protected String rate;
    @XmlElement(name = "Duration", required = true)
    protected String duration;
    @XmlElement(name = "ClassRef")
    protected ClassRef classRef;
    @XmlElement(name = "PayrollItemWageRef")
    protected PayrollItemWageRef payrollItemWageRef;
    @XmlElement(name = "Notes")
    protected String notes;
    @XmlElement(name = "IsBillable")
    protected String isBillable;
    @XmlElement(name = "IsBilled")
    protected String isBilled;

    /**
     * Gets the value of the txnID property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnID() {
        return txnID;
    }

    /**
     * Sets the value of the txnID property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnID(String value) {
        this.txnID = value;
    }

    /**
     * Gets the value of the timeCreated property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeCreated() {
        return timeCreated;
    }

    /**
     * Sets the value of the timeCreated property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeCreated(String value) {
        this.timeCreated = value;
    }

    /**
     * Gets the value of the timeModified property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimeModified() {
        return timeModified;
    }

    /**
     * Sets the value of the timeModified property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimeModified(String value) {
        this.timeModified = value;
    }

    /**
     * Gets the value of the editSequence property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEditSequence() {
        return editSequence;
    }

    /**
     * Sets the value of the editSequence property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEditSequence(String value) {
        this.editSequence = value;
    }

    /**
     * Gets the value of the txnNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTxnNumber() {
        return txnNumber;
    }

    /**
     * Sets the value of the txnNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTxnNumber(BigInteger value) {
        this.txnNumber = value;
    }

    /**
     * Gets the value of the txnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnDate() {
        return txnDate;
    }

    /**
     * Sets the value of the txnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnDate(String value) {
        this.txnDate = value;
    }

    /**
     * Gets the value of the entityRef property.
     * 
     * @return
     *     possible object is
     *     {@link EntityRef }
     *     
     */
    public EntityRef getEntityRef() {
        return entityRef;
    }

    /**
     * Sets the value of the entityRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntityRef }
     *     
     */
    public void setEntityRef(EntityRef value) {
        this.entityRef = value;
    }

    /**
     * Gets the value of the customerRef property.
     * 
     * @return
     *     possible object is
     *     {@link CustomerRef }
     *     
     */
    public CustomerRef getCustomerRef() {
        return customerRef;
    }

    /**
     * Sets the value of the customerRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link CustomerRef }
     *     
     */
    public void setCustomerRef(CustomerRef value) {
        this.customerRef = value;
    }

    /**
     * Gets the value of the itemServiceRef property.
     * 
     * @return
     *     possible object is
     *     {@link ItemServiceRef }
     *     
     */
    public ItemServiceRef getItemServiceRef() {
        return itemServiceRef;
    }

    /**
     * Sets the value of the itemServiceRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ItemServiceRef }
     *     
     */
    public void setItemServiceRef(ItemServiceRef value) {
        this.itemServiceRef = value;
    }

    /**
     * Gets the value of the rate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRate(String value) {
        this.rate = value;
    }

    /**
     * Gets the value of the duration property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDuration() {
        return duration;
    }

    /**
     * Sets the value of the duration property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDuration(String value) {
        this.duration = value;
    }

    /**
     * Gets the value of the classRef property.
     * 
     * @return
     *     possible object is
     *     {@link ClassRef }
     *     
     */
    public ClassRef getClassRef() {
        return classRef;
    }

    /**
     * Sets the value of the classRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClassRef }
     *     
     */
    public void setClassRef(ClassRef value) {
        this.classRef = value;
    }

    /**
     * Gets the value of the payrollItemWageRef property.
     * 
     * @return
     *     possible object is
     *     {@link PayrollItemWageRef }
     *     
     */
    public PayrollItemWageRef getPayrollItemWageRef() {
        return payrollItemWageRef;
    }

    /**
     * Sets the value of the payrollItemWageRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link PayrollItemWageRef }
     *     
     */
    public void setPayrollItemWageRef(PayrollItemWageRef value) {
        this.payrollItemWageRef = value;
    }

    /**
     * Gets the value of the notes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Sets the value of the notes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNotes(String value) {
        this.notes = value;
    }

    /**
     * Gets the value of the isBillable property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBillable() {
        return isBillable;
    }

    /**
     * Sets the value of the isBillable property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBillable(String value) {
        this.isBillable = value;
    }

    /**
     * Gets the value of the isBilled property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsBilled() {
        return isBilled;
    }

    /**
     * Sets the value of the isBilled property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsBilled(String value) {
        this.isBilled = value;
    }

}