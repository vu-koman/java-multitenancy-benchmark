package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(name = "multitenant.datasource", havingValue = "true")
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private DataSourceInterceptor dataSourceInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(dataSourceInterceptor).addPathPatterns("/**");
		WebMvcConfigurer.super.addInterceptors(registry);
	}
}
