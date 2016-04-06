package com.wex.poc.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class UtilityConfiguration {

	
	@Bean
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    System.out.println("Coming to Multipart");
	    multipartResolver.setMaxUploadSize(1000000);
	    multipartResolver.setMaxInMemorySize(1000000);
	    return multipartResolver;
	}
}
