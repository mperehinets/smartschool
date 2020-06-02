package com.mper.smartschool.config;

import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

import static com.mper.smartschool.config.MessageConfig.REQUEST_PARAM_LANGUAGE_NAME;
import static com.mper.smartschool.config.SecurityConfigProduction.HEADER_NAME;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mper.smartschool"))
                .paths(PathSelectors.any())
                .build()
                .securitySchemes(Lists.newArrayList(apiKey()))
                .securityContexts(Lists.newArrayList(securityContext()))
                .globalOperationParameters(Lists.newArrayList(language()));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", HEADER_NAME, "header");
    }

    private Parameter language() {
        return new ParameterBuilder()
                .name(REQUEST_PARAM_LANGUAGE_NAME)
                .description("Language")
                .modelRef(new ModelRef("string"))
                .parameterType("query")
                .required(true)
                .allowableValues(new AllowableListValues(Lists.newArrayList("en", "uk"), "string"))
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        var authorizationScope = new AuthorizationScope("global", "accessEverything");
        var authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Lists.newArrayList(new SecurityReference("JWT", authorizationScopes));
    }
}
