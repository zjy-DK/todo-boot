package com.todo.config.swagger;

import io.swagger.v3.oas.models.info.Contact;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

/**
 * @author zjy
 * @date 2024/12/08  14:05
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "springdoc")
public class SpringDocProperties {

    /**
     * 标题
     */
    private String title = null;

    /**
     * 描述
     */
    private String description = null;

    /**
     * 联系人信息
     */
    @NestedConfigurationProperty
    private Contact contact = null;

    /**
     * 版本
     */
    private String version = null;
}
