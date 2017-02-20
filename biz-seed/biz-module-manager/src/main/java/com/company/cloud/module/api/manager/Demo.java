package com.company.cloud.module.api.manager;

import com.company.cloud.module.dao.manager.ManagerDao;
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

    @RequestMapping("/show")
    public String show(){
        System.out.println(managerDao.selectAll());
        System.out.println("1");
        return "";
    }
}
