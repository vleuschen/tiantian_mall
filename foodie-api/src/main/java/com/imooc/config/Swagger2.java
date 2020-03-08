package com.imooc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class Swagger2 {

    // http://localhost:8088/swagger-ui.html

    //配置Swaagger2核心配置 docket
    @Bean
    public Docket createRestApi(){

        return new Docket(DocumentationType.SWAGGER_2) //指定API类为swager2
                .apiInfo(apiInfo())                            //用于定义api文档汇总信息
                .select().apis(RequestHandlerSelectors
                                                .basePackage("com.imooc.controller")) //指定controller的包
                .paths(PathSelectors.any()) //选择所有Controller
                .build();

    }


    private ApiInfo apiInfo(){
        return new ApiInfoBuilder()
                .title("电商平台接口api") //文档页标题
                .contact(new Contact("奥里给","http://www.baidu.com","abc@163.com")) //联系人信息
                .description("专为猪提供的饲料文档") //描述
                .version("1.0.1") //版本
                .termsOfServiceUrl("http://www.baidu.com")
                .build();

    }


}
