package com.want.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Value("${spring.profiles.active}")
	private String profile;

	@Bean
	public Docket docket() {
		List<Parameter> parameterList = new ArrayList<>();
		parameterList.add(
				new ParameterBuilder().name(LoginAuthorizationFilter.HEADER_AUTH_KEY).description("請輸入authorization")
						.modelRef(new ModelRef("string")).parameterType("header").required(false).build());
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).globalOperationParameters(parameterList)
				.select().apis(RequestHandlerSelectors.basePackage("com.want.controller")).paths(PathSelectors.any())
				.build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("want-etl-center").description(getVersionInfo()).build();
	}

	private String getVersionInfo() {
		return "Active profile: " + profile;
	}
}