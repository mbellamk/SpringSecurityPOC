package com.wex.poc.service.impl;

import java.io.File;
import java.io.StringWriter;
import java.util.HashMap;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import com.wex.poc.xml.Customer;
import com.wex.poc.xml.PhoneNumber;
 
public class Demo {
 
	private static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";
    public static void main(String[] args) throws Exception {
        Customer customer = new Customer();
        customer.setName("Jane Doe");
        customer.getPhoneNumber().add(new PhoneNumber());
        customer.getPhoneNumber().add(new PhoneNumber());
        //customer.getPhoneNumber().add(new PhoneNumber());
 
        JAXBContext jc = JAXBContext.newInstance(Customer.class);
        JAXBSource source = new JAXBSource(jc, customer);
 
        System.out.println(jaxbObjectToXML(customer));
        SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI); 
        Schema schema = sf.newSchema(new File("src/main/resources/customer.xsd")); 
 
        Validator validator = schema.newValidator();
        validator.setErrorHandler(new MyErrorHandler(1, new HashMap<Integer, StringBuilder>() ));
        validator.validate(source);
    }
    
    private static String jaxbObjectToXML(Customer customer) {
        String xmlString = "";
        try {
            JAXBContext context = JAXBContext.newInstance(Customer.class);
            Marshaller m = context.createMarshaller();

            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To format XML

            StringWriter sw = new StringWriter();
            m.marshal(customer, sw);
            xmlString = sw.toString();

        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return xmlString;
    }
 
}