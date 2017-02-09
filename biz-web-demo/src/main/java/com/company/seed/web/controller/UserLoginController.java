package com.company.seed.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.common.CommonConstants;
import com.company.seed.common.util.CommonStringUtil;
import com.company.seed.common.util.MD5Util;
import com.company.seed.module.model.user.UserModel;
import com.company.seed.module.service.user.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yoara on 2016/3/3.
 */
@Lazy
@RestController
@RequestMapping(value = "/login/user",produces = { "application/json;charset=UTF-8" })
public class UserLoginController extends WebBaseController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "loginin")
    public String loginIn() {
        JSONObject json = new JSONObject();
        String userName = getString("username");
        String password = getString("password");
        if(CommonStringUtil.isEmpty(userName)||CommonStringUtil.isEmpty(userName)){
            json.put("result", "fail");
            json.put("msg", "empty username/password");
            return json.toString();
        }
        String passwordMD5 = MD5Util.getMD5(password.getBytes());
        UserModel user = userService.authenticateUser(userName,passwordMD5);
        if(user==null){
            json.put("result", "fail");
            json.put("msg", "wrong username/password");
            return json.toString();
        }
        getRequest().getSession().setAttribute(CommonConstants.CURRENT_LOGIN_USER, user);
        json.put("result", "ok");
        return json.toString();
    }

    @RequestMapping(value = "loginout")
    public String loginOut() {
        getRequest().getSession().removeAttribute(CommonConstants.CURRENT_LOGIN_USER);
        JSONObject json = new JSONObject();
        json.put("result", "ok");
        return json.toString();
    }
}
