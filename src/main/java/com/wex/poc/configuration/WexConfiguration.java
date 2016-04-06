package com.wex.poc.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
//import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;

@Configuration
@EnableWebMvc
@EnableAsync
@ComponentScan(basePackages = "com.wex.poc")
public class WexConfiguration extends WebMvcConfigurerAdapter {

	@Autowired
	private CustomObjectMapper objectMapper;

/*	
	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {

		for (HttpMessageConverter<?> converter : converters) {
			if (converter instanceof AbstractJackson2HttpMessageConverter) {
				AbstractJackson2HttpMessageConverter c = (AbstractJackson2HttpMessageConverter) converter;

				c.setObjectMapper(objectMapper);
			}
		}
		super.extendMessageConverters(converters);
	}*/


	/*
	 * private HttpMessageConverter<Object> createXmlHttpMessageConverter() {
	 * MarshallingHttpMessageConverter xmlConverter = new
	 * MarshallingHttpMessageConverter();
	 * 
	 * XStreamMarshaller xstreamMarshaller = new XStreamMarshaller();
	 * xmlConverter.setMarshaller(xstreamMarshaller);
	 * xmlConverter.setUnmarshaller(xstreamMarshaller);
	 * 
	 * return xmlConverter; }
	 * 
	 * @Override public void configureMessageConverters(
	 * List<HttpMessageConverter<?>> converters) {
	 * 
	 * for (HttpMessageConverter<?> messageConverter : converters) { if
	 * (messageConverter instanceof MappingJackson2HttpMessageConverter) {
	 * MappingJackson2HttpMessageConverter m =
	 * (MappingJackson2HttpMessageConverter) messageConverter;
	 * m.setObjectMapper(objectMapper); } }
	 * super.configureMessageConverters(converters); }
	 */@Bean(name = "Wex")
	public InternalResourceViewResolver viewResolver() {
		InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
		viewResolver.setViewClass(JstlView.class);
		viewResolver.setPrefix("/WEB-INF/views/");
		viewResolver.setSuffix(".jsp");

		/*
		 * List<ViewResolver> resolvers = new ArrayList<ViewResolver>();
		 * resolvers.add(viewResolver); resolvers.add(htmlViewResolver());
		 * ContentNegotiatingViewResolver resolver = new
		 * ContentNegotiatingViewResolver();
		 * resolver.setViewResolvers(resolvers);
		 */
		return viewResolver;
	}

	/*
	 * @Bean(name = "WexHTML") public ViewResolver htmlViewResolver() {
	 * InternalResourceViewResolver viewResolver = new
	 * InternalResourceViewResolver();
	 * viewResolver.setViewClass(JstlView.class);
	 * viewResolver.setPrefix("/WEB-INF/views/");
	 * viewResolver.setSuffix(".html"); viewResolver.setOrder(2);
	 * 
	 * return viewResolver; }
	 */

	/*
	 * Configure ResourceHandlers to serve static resources like CSS/ Javascript
	 * etc...
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/css/**").addResourceLocations(
				"/WEB-INF/css/");
		registry.addResourceHandler("/js/**").addResourceLocations(
				"/WEB-INF/views/js/");

	}
	
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
	    CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
	    //resolver.setDefaultEncoding("utf-8");
	    multipartResolver.setMaxUploadSize(1000000);
	    multipartResolver.setMaxInMemorySize(1000000);
	    return multipartResolver;
	}
}