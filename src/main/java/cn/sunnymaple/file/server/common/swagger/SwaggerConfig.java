package cn.sunnymaple.file.server.common.swagger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author wangzb
 * @date 2019/7/3 11:37
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Autowired
    private SwaggerProperties fileServerSwaggerProperties;

    @Bean
    public Docket docket(){
        Contact contact = new Contact(fileServerSwaggerProperties.getName(),
                fileServerSwaggerProperties.getUrl(), fileServerSwaggerProperties.getEmail());
        ApiInfo apiInfo = new ApiInfoBuilder().title(fileServerSwaggerProperties.getTitle())
                .description(fileServerSwaggerProperties.getDescription())
                .contact(contact)
                .version(fileServerSwaggerProperties.getVersion())

                .build();
        Docket docket = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo);
        docket = docket.select().apis(RequestHandlerSelectors.basePackage(fileServerSwaggerProperties.getBasePackage())).build();
        //配置全局异常
        return docket;
    }
}
