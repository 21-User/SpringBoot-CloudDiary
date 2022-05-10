package com.fc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
// swagger2.10.5要用这个注解
// swagger2.9.x之前用的都是@EnableSwagger2
@EnableSwagger2WebMvc
public class SwaggerConfig {
    // 一个Docket就对应一个文档
    @Bean
    public Docket t21_Docket() {
        return new Docket(DocumentationType.SWAGGER_2)
                // 添加api的基本信息
                .apiInfo(apiInfo_t21())
                .groupName("21撰写")
                // 对基本信息进行查询
                .select()
                // 扫描指定包下的所有Swagger注解
                .apis(RequestHandlerSelectors.basePackage("com.fc"))
                // 支持任意路径
                .paths(PathSelectors.ant("/**"))
                // 构建
                .build();
    }

    private ApiInfo apiInfo_t21() {
        return new ApiInfoBuilder()
                // api标题
                .title("日记主页模块")
                // 描述
                .description("分页查询日记列表")
                // 联系人(作者的联系方式)
                .contact(new Contact("T21", "https://github.com/21-User", "3170549226@qq.com"))
                // 版本号
                .version("2.1")
                // 许可证
                .license("Apache 2.0")
                // 服务条款
                .termsOfServiceUrl("https://swagger.io")
                .build();
    }

}
