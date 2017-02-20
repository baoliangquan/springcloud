package com.company.cloud.module;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by yoara on 2017/2/10.
 */
@SpringBootApplication
@EnableEurekaClient
@ComponentScan("com.company.cloud")
public class ModuleManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleManagerApplication.class, args);
    }
}
