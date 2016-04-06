package com.wex.poc.configuration;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

@SuppressWarnings("serial")
@Component
public class CustomObjectMapper extends ObjectMapper {
	public CustomObjectMapper() {
		AnnotationIntrospector one = new JsonSpringViewAnnotationIntrospector();
		/*AnnotationIntrospector two = new JacksonAnnotationIntrospector();
		AnnotationIntrospector three = AnnotationIntrospector.pair(one, two);*/

		this.setAnnotationIntrospector(one);

	}
}
