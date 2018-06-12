
package vn.com.fwd.sms.webservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.fwd.sms.service package. 
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

    private final static QName _CreateSmsRequest_QNAME = new QName("http://service.sms.fwd.com/", "CreateSmsRequest");
    private final static QName _CreateSmsRequestResponse_QNAME = new QName("http://service.sms.fwd.com/", "CreateSmsRequestResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.fwd.sms.service
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateSmsRequest }
     * 
     */
    public CreateSmsRequest createCreateSmsRequest() {
        return new CreateSmsRequest();
    }

    /**
     * Create an instance of {@link CreateSmsRequestResponse }
     * 
     */
    public CreateSmsRequestResponse createCreateSmsRequestResponse() {
        return new CreateSmsRequestResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSmsRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sms.fwd.com/", name = "CreateSmsRequest")
    public JAXBElement<CreateSmsRequest> createCreateSmsRequest(CreateSmsRequest value) {
        return new JAXBElement<CreateSmsRequest>(_CreateSmsRequest_QNAME, CreateSmsRequest.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateSmsRequestResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.sms.fwd.com/", name = "CreateSmsRequestResponse")
    public JAXBElement<CreateSmsRequestResponse> createCreateSmsRequestResponse(CreateSmsRequestResponse value) {
        return new JAXBElement<CreateSmsRequestResponse>(_CreateSmsRequestResponse_QNAME, CreateSmsRequestResponse.class, null, value);
    }

}
