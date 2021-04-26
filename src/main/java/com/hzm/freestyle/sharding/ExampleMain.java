package com.hzm.freestyle.sharding;

import com.hzm.freestyle.sharding.api.ExampleExecuteTemplate;
import com.hzm.freestyle.sharding.service.ExampleService;
import com.hzm.freestyle.sharding.service.impl.OrderServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;

/**
 * sharding-jdbc测试启动类，启动前注释掉application.yml中的数据源
 *
 * @author Hezeming
 * @version 1.0
 * @data 2021年04月25日
 */
@MapperScan(basePackages = "com.hzm.freestyle.sharding.repository.impl")
@PropertySource(value = {"classpath:sharding/application-sharding-databases-tables.properties"})
//@PropertySource(value = {"classpath:sharding/application-sharding-databases-tables-spring.yml"})
//@PropertySource(value = {"classpath:sharding/application-sharding-tables.yml"})
//@PropertySource(value = {"classpath:sharding/application-sharding-tables.properties"})
@SpringBootApplication(scanBasePackages = "com.hzm.freestyle.sharding")
public class ExampleMain {

    public static void main(final String[] args) throws Exception {
//        DataSource dataSource = YamlShardingSphereDataSourceFactory.createDataSource(new File("/"));
        ConfigurableApplicationContext context = SpringApplication.run(ExampleMain.class, args);

        ExampleService exampleService = context.getBean(OrderServiceImpl.class);
        ExampleExecuteTemplate.run(exampleService);

//        try (ConfigurableApplicationContext applicationContext = SpringApplication.run(ExampleMain.class, args)) {
////            ExampleExecuteTemplate.run(applicationContext.getBean(ExampleService.class));
//            ExampleService exampleService = applicationContext.getBean(OrderServiceImpl.class);
//            ExampleExecuteTemplate.run(exampleService);
//        }
    }
}
