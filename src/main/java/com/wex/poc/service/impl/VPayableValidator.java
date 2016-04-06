package com.wex.poc.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.util.JAXBSource;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.transform.Result;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.springframework.stereotype.Component;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;

import com.wex.poc.xml.Vpayable;

@Component("vPayableValidator")
public class VPayableValidator {

	private static final String W3C_XML_SCHEMA_NS_URI = "http://www.w3.org/2001/XMLSchema";


	public void validateAgainstXSD(Vpayable vpayable, int rowNumber,
			Map<Integer, StringBuilder> error) {

		// customer.getPhoneNumber().add(new PhoneNumber());

		JAXBContext jc;
		try {
			jc = JAXBContext.newInstance(Vpayable.class);

			JAXBSource source = new JAXBSource(jc, vpayable);

			SchemaFactory sf = SchemaFactory.newInstance(W3C_XML_SCHEMA_NS_URI);

			File file = new File(getClass().getResource("vpayableOne.xsd")
					.getFile());
			Schema schema = sf.newSchema(file);
			Result result = new Result() {

				public void setSystemId(String arg0) {

					System.out.println(arg0);
				}

				public String getSystemId() {
					return null;
				}
			};
			Validator validator = schema.newValidator();
			validator.setErrorHandler(new MyErrorHandler(rowNumber, error));

			try {
				validator.validate(source);
			} catch (SAXParseException e) {
				
				System.out.println("Validation error: " + e.getMessage());
				
			}
			System.out.println(jaxbObjectToXML(vpayable, schema));

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String jaxbObjectToXML(Vpayable customer, Schema schema) {
		String xmlString = "";
		try {
			JAXBContext context = JAXBContext.newInstance(Vpayable.class);
			Marshaller m = context.createMarshaller();

			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE); // To
																			// format
																			// XML

			StringWriter sw = new StringWriter();
			m.marshal(customer, sw);
			xmlString = sw.toString();

			// Creating a SAXParser for our input XML
			// First the factory
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// Must be namespace aware to receive element names
			factory.setNamespaceAware(true);
			// Setting the Schema for validation
			// factory.setSchema(schema);
			// Now the parser itself
			final SAXParser parser = factory.newSAXParser();

			// Creating an instance of our special handler
			final MyContentHandler handler = new MyContentHandler();

			// Parsing
			parser.parse(new InputSource(new StringReader(xmlString)), handler);

		} catch (JAXBException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return xmlString;
	}

	private static class MyContentHandler extends DefaultHandler {

		private String element = "";

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {

			if (localName != null && !localName.isEmpty())
				element = localName;
			else
				element = qName;

		}

		@Override
		public void warning(SAXParseException exception) throws SAXException {
			System.out.println(element + ": " + exception.getMessage());
		}

		@Override
		public void error(SAXParseException exception) throws SAXException {
			System.out.println(element + ": " + exception.getMessage());
		}

		@Override
		public void fatalError(SAXParseException exception) throws SAXException {
			System.out.println(element + ": " + exception.getMessage());
		}

		public String getElement() {
			return element;
		}

	}

}