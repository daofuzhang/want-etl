/**
 * -------------------------------------------------------
 * @FileName：WebConfig.java
 * @Description：简要描述本文件的内容
 * @Author：Luke.Tsai
 * @Copyright  www.want-want.com  Ltd.  All rights reserved.
 * 注意：本内容仅限于旺旺集团内部传阅，禁止外泄以及用于其他商业目的
 * -------------------------------------------------------
 */
package com.want.config;

import javax.servlet.Filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.swagger2.web.Swagger2Controller;

@Configuration
public class WebConfig {
	
	@Autowired
	SwaggerFilter swaggerFilter;

	@Bean
	public FilterRegistrationBean<Filter> swaggerFilterRegistrationBean() {
		FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>();
		registration.setFilter(swaggerFilter);
		registration.addUrlPatterns(Swagger2Controller.DEFAULT_URL);
		registration.setName(SwaggerFilter.class.getSimpleName());
		registration.setOrder(7);
		return registration;
	}

}
