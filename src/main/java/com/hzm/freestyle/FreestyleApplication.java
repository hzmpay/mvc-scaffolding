package com.hzm.freestyle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@SpringBootApplication
@EnableAsync
@MapperScan("com.hzm.freestyle.mapper")
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

