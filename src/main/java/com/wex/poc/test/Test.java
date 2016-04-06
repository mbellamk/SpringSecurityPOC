package com.wex.poc.test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

public class Test {

	private static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";

	public static void main(String args[]) {
		
		String xml="<?xml version='1.0' encoding='UTF-8'><VpayableXML id='1'><amount>100.0</amount><type>Individuall</type><description>For individual account</description></VpayableXML><VpayableXML id='2'><amount>100.0</amount><type>Individuall</type><description>For individual account</description></VpayableXML><VpayableXML  id='3'><amount>100.0</amount><type>Individuall</type><description>For individual account</description></VpayableXML>";
		String xsd="<?xml version='1.0' encoding='UTF-8'?><xs:schema xmlns:xs='http://www.w3.org/2001/XMLSchema'><xs:element name='VpayableXML'><xs:complexType><xs:sequence><xs:element name='amount' type='xs:double' /><xs:element name='type' type='stringVMaxSize10' /><xs:element name='description' type='stringVMaxSize21' /></xs:sequence></xs:complexType></xs:element><xs:simpleType name='stringVMaxSize21'><xs:restriction base='xs:string'><xs:maxLength value='25' /></xs:restriction></xs:simpleType><xs:simpleType name='stringVMaxSize10'><xs:restriction base='xs:string'><xs:maxLength value='25' /></xs:restriction></xs:simpleType></xs:schema>";
		InputStream xmlStream = new ByteArrayInputStream(xml.getBytes(StandardCharsets.UTF_8));
		
		InputStream xsdStream = new ByteArrayInputStream(xsd.getBytes(StandardCharsets.UTF_8));
		System.out.println(validateAgainstXSD(xmlStream, xsdStream));
	}

	static boolean validateAgainstXSD(InputStream xml, InputStream xsd) {
		try {
			SchemaFactory factory = SchemaFactory
					.newInstance(W3C_XML_SCHEMA_NS_URI);
			Schema schema = factory.newSchema(new StreamSource(xsd));
			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(xml));
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

}