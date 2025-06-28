package com.todo.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

/**
 * @author zjy
 * @date 2024/12/08  14:05
 */
@Configuration
public class SpringDocAutoConfiguration {

    @Resource
    private SpringDocProperties springDocProperties;

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title(springDocProperties.getTitle())
                        .description(springDocProperties.getDescription())
                        .version(springDocProperties.getVersion())
                        .contact(springDocProperties.getContact()));
    }

}
