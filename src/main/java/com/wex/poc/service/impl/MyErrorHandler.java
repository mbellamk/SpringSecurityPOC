package com.wex.poc.service.impl;

import java.util.Map;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class MyErrorHandler implements ErrorHandler {

	private int rowNumber;
	private Map<Integer, StringBuilder> error;

	public MyErrorHandler(int rowNumber, Map<Integer, StringBuilder> error) {
		this.error = error;
		this.rowNumber = rowNumber;
	}

	public void warning(SAXParseException exception) throws SAXException {
		System.out.println("\nWARNING");
		exception.printStackTrace();
	}

	public void error(SAXParseException exception) throws SAXException {
		System.out.println("\nERROR");
		StringBuilder sb = error.get(this.rowNumber);
		if (sb == null) {
			sb = new StringBuilder();
		}
		sb.append(exception.getMessage());
		error.put(this.rowNumber, sb);
		System.out.println(exception.getColumnNumber()+" "+exception.getLineNumber()+" "+exception.getPublicId()+" "+exception.getSystemId()+" ");
		/*System.out.println("Error at " + this.rowNumber + " Error is:"
				+ exception.getMessage());*/

	}

	public void fatalError(SAXParseException exception) throws SAXException {
		System.out.println("\nFATAL ERROR");
		exception.printStackTrace();
	}

}
