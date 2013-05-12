
package com.sapint.wsclient;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.sapint.wsclient package. 
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

    private final static QName _AddCriteriaResponse_QNAME = new QName("http://ws.sapint.com/", "addCriteriaResponse");
    private final static QName _GetDataTableResponse_QNAME = new QName("http://ws.sapint.com/", "getDataTableResponse");
    private final static QName _AddFieldResponse_QNAME = new QName("http://ws.sapint.com/", "addFieldResponse");
    private final static QName _AddCriteria_QNAME = new QName("http://ws.sapint.com/", "addCriteria");
    private final static QName _GetDataTable_QNAME = new QName("http://ws.sapint.com/", "getDataTable");
    private final static QName _ResetResponse_QNAME = new QName("http://ws.sapint.com/", "resetResponse");
    private final static QName _AddField_QNAME = new QName("http://ws.sapint.com/", "addField");
    private final static QName _Reset_QNAME = new QName("http://ws.sapint.com/", "reset");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.sapint.wsclient
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDataTableResponse }
     * 
     */
    public GetDataTableResponse createGetDataTableResponse() {
        return new GetDataTableResponse();
    }

    /**
     * Create an instance of {@link AddCriteriaResponse }
     * 
     */
    public AddCriteriaResponse createAddCriteriaResponse() {
        return new AddCriteriaResponse();
    }

    /**
     * Create an instance of {@link AddField }
     * 
     */
    public AddField createAddField() {
        return new AddField();
    }

    /**
     * Create an instance of {@link Reset }
     * 
     */
    public Reset createReset() {
        return new Reset();
    }

    /**
     * Create an instance of {@link ResetResponse }
     * 
     */
    public ResetResponse createResetResponse() {
        return new ResetResponse();
    }

    /**
     * Create an instance of {@link GetDataTable }
     * 
     */
    public GetDataTable createGetDataTable() {
        return new GetDataTable();
    }

    /**
     * Create an instance of {@link AddCriteria }
     * 
     */
    public AddCriteria createAddCriteria() {
        return new AddCriteria();
    }

    /**
     * Create an instance of {@link AddFieldResponse }
     * 
     */
    public AddFieldResponse createAddFieldResponse() {
        return new AddFieldResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCriteriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "addCriteriaResponse")
    public JAXBElement<AddCriteriaResponse> createAddCriteriaResponse(AddCriteriaResponse value) {
        return new JAXBElement<AddCriteriaResponse>(_AddCriteriaResponse_QNAME, AddCriteriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTableResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "getDataTableResponse")
    public JAXBElement<GetDataTableResponse> createGetDataTableResponse(GetDataTableResponse value) {
        return new JAXBElement<GetDataTableResponse>(_GetDataTableResponse_QNAME, GetDataTableResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddFieldResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "addFieldResponse")
    public JAXBElement<AddFieldResponse> createAddFieldResponse(AddFieldResponse value) {
        return new JAXBElement<AddFieldResponse>(_AddFieldResponse_QNAME, AddFieldResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddCriteria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "addCriteria")
    public JAXBElement<AddCriteria> createAddCriteria(AddCriteria value) {
        return new JAXBElement<AddCriteria>(_AddCriteria_QNAME, AddCriteria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDataTable }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "getDataTable")
    public JAXBElement<GetDataTable> createGetDataTable(GetDataTable value) {
        return new JAXBElement<GetDataTable>(_GetDataTable_QNAME, GetDataTable.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResetResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "resetResponse")
    public JAXBElement<ResetResponse> createResetResponse(ResetResponse value) {
        return new JAXBElement<ResetResponse>(_ResetResponse_QNAME, ResetResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddField }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "addField")
    public JAXBElement<AddField> createAddField(AddField value) {
        return new JAXBElement<AddField>(_AddField_QNAME, AddField.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Reset }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws.sapint.com/", name = "reset")
    public JAXBElement<Reset> createReset(Reset value) {
        return new JAXBElement<Reset>(_Reset_QNAME, Reset.class, null, value);
    }

}
