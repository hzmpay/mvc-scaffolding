package com.hzm.freestyle.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * Spring相关操作工具类
 * 
 * @author Hezeming
 * @version 1.0
 * @date 2018年6月5日
 */
@SuppressWarnings("unchecked")
@Component
public class SpringUtil implements ApplicationContextAware {

	// Spring应用上下文环境
	private static ApplicationContext applicationContext;

	/**
	 * 实现ApplicationContextAware接口的回调方法，设置上下文环境
	 * 
	 * @param context
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) {
		applicationContext = context;
	}

	/**
	 * 获取ApplicationContext对象
	 * 
	 * @return ApplicationContext
	 */
	public static final ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 检测Spring容器中是否存在指定名称的Bean对象
	 * 
	 * @param name Spring管理的bean的名称
	 * @return boolean 如果存在返回true，否则返回false
	 */
	public static final boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	/**
	 * 根据指定的名称获取Spring管理的Bean对象
	 * 
	 * @param <T>
	 * 
	 * @param name Spring管理的bean的名称
	 * @return Object 返回Spring管理的bean实例，如果指定实例不存在将抛出异常
	 */
	public static final <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 根据指定的名称获取Spring管理的Bean对象
	 * 
	 * @param name Spring管理的bean的名称
	 * @param args 获取该Bean实例所需的构造方法或工厂方法的参数列表
	 * @return Object 返回Spring管理的bean实例，如果指定实例不存在将抛出异常
	 */
	public static final <T> T getBean(String name, Object... args) {
		return ((T) applicationContext.getBean(name, args));
	}

	/**
	 * 根据类型获取Spring管理的Bean对象
	 * 
	 * @param clazz Spring管理的bean的类型
	 * @return Object 返回Spring管理的bean实例，如果指定实例不存在或存在重复的类型冲突将抛出异常
	 */
	public static final <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

	/**
	 * 根据类型获取Spring管理的Bean对象
	 * 
	 * @param clazz Spring管理的bean的类型
	 * @param args 获取该Bean实例所需的构造方法或工厂方法的参数列表
	 * @return Object 返回Spring管理的bean实例，如果指定实例不存在或存在重复的类型冲突将抛出异常
	 */
	public static final <T> T getBean(Class<T> clazz, Object... args) {
		return applicationContext.getBean(clazz, args);
	}

	/**
	 * 根据类型获取Spring容器指定类型的Bean对象的Map集合。键为Bean的Name，值为对应的Bean实例。
	 * 
	 * @param clazz Spring管理的bean的类型
	 * @return
	 */
	public static final <T> Map<String, T> getBeansOfType(Class<T> clazz) {
		return applicationContext.getBeansOfType(clazz);
	}

	/**
	 * 根据泛型获取Spring容器具有指定注解的Bean对象的Map集合。键为Bean的Name，值为对应的Bean实例。
	 * 
	 * @param annotationType Spring管理的bean所具备的的注解类型
	 * @return
	 */
	public static final Map<String, Object> getBeansWithAnnotation(Class<? extends Annotation> annotationType) {
		return applicationContext.getBeansWithAnnotation(annotationType);
	}

	/**
	 * 发布(触发)指定的应用事件
	 * 
	 * @param event
	 * @since 7.0
	 */
	public static final void publishEvent(ApplicationEvent event) {
		applicationContext.publishEvent(event);
	}
}
