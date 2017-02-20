package com.company.cloud.web.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

/**
 * Created by yoara on 2017/2/16.
 */
@Service
public class ManagerService {
    @Resource
    RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String showManager() {
        return restTemplate.getForEntity("http://CLOUD-MANAGER/show", String.class).getBody();
    }

    @HystrixCommand(fallbackMethod = "addServiceFallback")
    public String showDemo() {
        return restTemplate.getForEntity("http://CLOUD-MANAGER/show", String.class).getBody();
    }

    public String addServiceFallback() {
        return "error";
    }
}
