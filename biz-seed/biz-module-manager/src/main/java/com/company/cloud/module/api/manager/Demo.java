package com.company.cloud.module.api.manager;

import com.company.cloud.module.dao.manager.ManagerDao;
import com.company.cloud.module.service.ManagerService;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yoara on 2017/2/15.
 */
@RestController
public class Demo {
    @Resource
    private Environment env;

    @Resource
    private ManagerDao managerDao;

    @Resource
    private ManagerService managerService;

    @RequestMapping("/show")
    public String show(){
        System.out.println(managerDao.selectAll());
        return env.getProperty("spring.datasource.initialize");
    }

    @RequestMapping("/cachePut")
    public String cachePut(){
        managerService.makeCacheData();
        return "ok";
    }

    @RequestMapping("/cacheGet")
    public String cacheGet(){
        return managerService.queryCacheData();
    }
}
