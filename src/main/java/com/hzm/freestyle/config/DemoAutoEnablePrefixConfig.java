package com.hzm.freestyle.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hezeming
 * @version 1.0
 * @date 2021年04月08日
 */
@Configuration
@EnableConfigurationProperties({PropertiesConfig.class})
@ConditionalOnProperty(
        prefix = "demo.prefix.config",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class DemoAutoEnablePrefixConfig {



}
