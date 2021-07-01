package com.hzm.freestyle.config;

import org.springframework.context.annotation.Import;

/**
 * 自动注入前缀功能
 *
 * @author Hezeming
 * @version 1.0
 * @date 2021年04月08日
 */
@Import(DemoAutoEnablePrefixConfig.class)
public interface DemoAutoEnablePrefix {
}
