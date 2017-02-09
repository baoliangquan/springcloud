package com.company.seed.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.common.CommonConstants;
import com.company.seed.common.util.CommonStringUtil;
import com.company.seed.common.util.MD5Util;
import com.company.seed.module.model.manager.ManagerModel;
import com.company.seed.module.model.manager.enums.AuthorityAnnotationEnums;
import com.company.seed.module.service.manager.AuthorityService;
import com.company.seed.module.service.manager.ManagerService;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Set;

/**
 * Created by yoara on 2016/3/3.
 */
@Lazy
@RestController
@RequestMapping(value = "/login/manager",produces = { "application/json;charset=UTF-8" })
public class ManagerLoginController extends WebBaseController {
    @Resource
    private ManagerService managerService;
    @Resource
    private AuthorityService authorityService;

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
        ManagerModel manager = managerService.authenticateManager(userName,passwordMD5);
        if(manager==null){
            json.put("result", "fail");
            json.put("msg", "wrong username/password");
            return json.toString();
        }
        Set<AuthorityAnnotationEnums> authSet = authorityService.queryAuthLeafsByManagerId(manager.getId());
        manager.setAuthSet(authSet);
        getRequest().getSession().setAttribute(CommonConstants.CURRENT_LOGIN_MANAGER, manager);

        json.put("result", "ok");
        return json.toString();
    }

    @RequestMapping(value = "loginout")
    public String loginOut() {
        getRequest().getSession().removeAttribute(CommonConstants.CURRENT_LOGIN_MANAGER);
        JSONObject json = new JSONObject();
        json.put("result", "ok");
        return json.toString();
    }
}
