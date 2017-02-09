package com.company.seed.web.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.common.util.MD5Util;
import com.company.seed.module.model.user.UserModel;
import com.company.seed.module.service.user.UserService;
import com.company.seed.web.controller.WebBaseController;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by yoara on 2016/3/3.
 */
@Lazy
@RestController
@RequestMapping(value = "/openapi/operate",produces = { "application/json;charset=UTF-8" })
public class DemoOperateController extends WebBaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "object")
    public String operateObject() {
        UserModel model = new UserModel();
        model.setId(getString("id"));
        model.setPassword( MD5Util.getMD5(getString("psw").getBytes()));
        userService.updatePsw(model);
        return JSONObject.toJSONString(model);
    }

    @RequestMapping(value = "map")
    public String operateMap() {
        Map<String,Object> model = new HashMap<>();
        model.put("id",getString("id"));
        model.put("password",MD5Util.getMD5(getString("psw").getBytes()));
        userService.updatePsw(model);
        return JSONObject.toJSONString(model);
    }
}
