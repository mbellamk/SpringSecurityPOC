package com.wex.poc.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter;

@Component
public class JacksonFix {
	/*private AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter;
	private CustomObjectMapper objectMapper;

	@PostConstruct
	public void init() {
		HttpMessageConverter<?>[] messageConverters = annotationMethodHandlerAdapter
				.getMessageConverters();
		for (HttpMessageConverter<?> messageConverter : messageConverters) {
			if (messageConverter instanceof MappingJackson2HttpMessageConverter) {
				MappingJackson2HttpMessageConverter m = (MappingJackson2HttpMessageConverter) messageConverter;
				m.setObjectMapper(objectMapper);
			}
		}
	}

	// this will exist due to the <mvc:annotation-driven/> bean
	@Autowired
	public void setAnnotationMethodHandlerAdapter(
			AnnotationMethodHandlerAdapter annotationMethodHandlerAdapter) {
		this.annotationMethodHandlerAdapter = annotationMethodHandlerAdapter;
	}

	@Autowired
	public void setObjectMapper(CustomObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}
*/}
