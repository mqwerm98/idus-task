package com.idus.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

import static java.util.Collections.singleton;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${spring.application.name}") private String applicationName;

    @Bean
    public Docket swaggerApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(swaggerInfo())
                .produces(singleton("application/json"))
                .consumes(singleton("application/json"))
                .select()
                    .apis(RequestHandlerSelectors.basePackage("com.idus.demo.controller"))
                    .paths(PathSelectors.ant("/api/v1/**"))
                .build()
                .useDefaultResponseMessages(false)
                .globalOperationParameters(
                        Collections.singletonList(new ParameterBuilder()
                                .name("X-AUTH-TOKEN")
                                .description("JWT Authorization token")
                                .modelRef(new ModelRef("string"))
                                .parameterType("header")
                                .required(false)
                                .build()));
    }

    private ApiInfo swaggerInfo() {
        return new ApiInfoBuilder()
                .title(applicationName)
                .description("아이디어스 과제 API 문서입니다.")
                .version("1")
                .build();
    }
}
