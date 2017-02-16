package com.company.cloud.module.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yoara on 2017/2/10.
 */
@SpringBootApplication
@EnableAutoConfiguration
@Configuration
@EnableEurekaClient
@ComponentScan("com.company.cloud.module.manager")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
