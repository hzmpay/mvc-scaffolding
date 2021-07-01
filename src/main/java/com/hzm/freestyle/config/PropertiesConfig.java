package com.hzm.freestyle.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Hezeming
 * @version 1.0
 * @date 2021年04月08日
 */
@ConfigurationProperties(prefix = "demo.prefix.config")
@Data
public class PropertiesConfig {

    private String name;
    private Integer age;
}
