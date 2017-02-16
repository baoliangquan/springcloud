package com.company.cloud.web.demo.controller;

import com.company.cloud.web.demo.service.ManagerService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yoara on 2017/2/15.
 */
@RestController
public class Demo {
    @Resource
    private ManagerService managerService;

    @RequestMapping("/showManager")
    public String show(){
        return managerService.showManager();
    }

    @RequestMapping("/showDemo")
    public String showDemo(){
        return managerService.showDemo();
    }
}
