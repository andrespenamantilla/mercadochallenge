package com.mercadolibre.challenge.configuration;


import com.google.common.base.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SpringFoxConfig {

    @Value("${swagger.host.url}")
    private String hostUrl;
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .host(hostUrl)
                .groupName("Spring Actuator")
                .select()
                .apis((Predicate<RequestHandler>) RequestHandlerSelectors.any())
                .paths((Predicate<String>) PathSelectors.any())
                .build();
    }
}