
package io.uouo.wechatbot.webservice.weather;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the cn.com.webxml package. 
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

    private final static QName _DataSet_QNAME = new QName("http://WebXml.com.cn/", "DataSet");
    private final static QName _ArrayOfString_QNAME = new QName("http://WebXml.com.cn/", "ArrayOfString");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: cn.com.webxml
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetSupportDataSetResponse }
     * 
     */
    public GetSupportDataSetResponse createGetSupportDataSetResponse() {
        return new GetSupportDataSetResponse();
    }

    /**
     * Create an instance of {@link GetSupportProvince }
     * 
     */
    public GetSupportProvince createGetSupportProvince() {
        return new GetSupportProvince();
    }

    /**
     * Create an instance of {@link GetSupportCity }
     * 
     */
    public GetSupportCity createGetSupportCity() {
        return new GetSupportCity();
    }

    /**
     * Create an instance of {@link GetSupportDataSetResponse.GetSupportDataSetResult }
     * 
     */
    public GetSupportDataSetResponse.GetSupportDataSetResult createGetSupportDataSetResponseGetSupportDataSetResult() {
        return new GetSupportDataSetResponse.GetSupportDataSetResult();
    }

    /**
     * Create an instance of {@link DataSet }
     * 
     */
    public DataSet createDataSet() {
        return new DataSet();
    }

    /**
     * Create an instance of {@link GetSupportDataSet }
     * 
     */
    public GetSupportDataSet createGetSupportDataSet() {
        return new GetSupportDataSet();
    }

    /**
     * Create an instance of {@link ArrayOfString }
     * 
     */
    public ArrayOfString createArrayOfString() {
        return new ArrayOfString();
    }

    /**
     * Create an instance of {@link GetWeatherbyCityNamePro }
     * 
     */
    public GetWeatherbyCityNamePro createGetWeatherbyCityNamePro() {
        return new GetWeatherbyCityNamePro();
    }

    /**
     * Create an instance of {@link GetSupportProvinceResponse }
     * 
     */
    public GetSupportProvinceResponse createGetSupportProvinceResponse() {
        return new GetSupportProvinceResponse();
    }

    /**
     * Create an instance of {@link GetWeatherbyCityNameResponse }
     * 
     */
    public GetWeatherbyCityNameResponse createGetWeatherbyCityNameResponse() {
        return new GetWeatherbyCityNameResponse();
    }

    /**
     * Create an instance of {@link GetWeatherbyCityNameProResponse }
     * 
     */
    public GetWeatherbyCityNameProResponse createGetWeatherbyCityNameProResponse() {
        return new GetWeatherbyCityNameProResponse();
    }

    /**
     * Create an instance of {@link GetWeatherbyCityName }
     * 
     */
    public GetWeatherbyCityName createGetWeatherbyCityName() {
        return new GetWeatherbyCityName();
    }

    /**
     * Create an instance of {@link GetSupportCityResponse }
     * 
     */
    public GetSupportCityResponse createGetSupportCityResponse() {
        return new GetSupportCityResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DataSet }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "DataSet")
    public JAXBElement<DataSet> createDataSet(DataSet value) {
        return new JAXBElement<DataSet>(_DataSet_QNAME, DataSet.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ArrayOfString }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://WebXml.com.cn/", name = "ArrayOfString")
    public JAXBElement<ArrayOfString> createArrayOfString(ArrayOfString value) {
        return new JAXBElement<ArrayOfString>(_ArrayOfString_QNAME, ArrayOfString.class, null, value);
    }

}
