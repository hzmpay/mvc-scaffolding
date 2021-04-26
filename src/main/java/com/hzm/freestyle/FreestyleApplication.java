package com.hzm.freestyle;

import com.github.pagehelper.autoconfigure.PageHelperAutoConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableAsync
@MapperScan("com.hzm.freestyle.mapper")
@EnableTransactionManagement
@SpringBootApplication(exclude = PageHelperAutoConfiguration.class)
public class FreestyleApplication {

    public static void main(String[] args) {
        SpringApplication.run(FreestyleApplication.class, args);
    }

//    @Bean(destroyMethod = "shutdown")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler threadPoolTaskScheduler = new ThreadPoolTaskScheduler();
        threadPoolTaskScheduler.setPoolSize(16);
        return threadPoolTaskScheduler;
    }

}

