package com.yj.springboot.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Swagger2配置
 *
 * @author : yj
 * @version : 1.0
 * @date : Created in 2020/8/18
 * @modified by :
 */
@Configuration
public class Swagger2Config {

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yj.springboot"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .produces(getProduces());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("yj的springboot项目文档")
                .version("1.0")
                .build();
    }

    private List<Parameter> setHeaderToken() {

        ParameterBuilder builder = new ParameterBuilder();
        List<Parameter> parameters = new ArrayList<>();
        builder.name("Authorization").description("token")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false);
        parameters.add(builder.build());
        return parameters;
    }


    private Set<String> getProduces(){
        Set<String > set = new HashSet<>();
        set.add(MediaType.APPLICATION_JSON_VALUE);
        return set;
    }
}
