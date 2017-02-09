package com.company.seed.web.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.company.seed.basic.web.bean.JsonCommonCodeEnum;
import com.company.seed.module.model.manager.annotation.AuthorityAnnotation;
import com.company.seed.module.model.manager.enums.AuthorityAnnotationEnums;
import com.company.seed.module.model.user.UserModel;
import com.company.seed.module.service.user.UserService;
import com.company.seed.web.bean.resultfilter.UserJsonPropertyFilterConstants;
import com.company.seed.web.controller.WebBaseController;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by yoara on 2016/3/3.
 */
@Lazy
@RestController
@RequestMapping(value = "/security/user",produces = { "application/json;charset=UTF-8" })
public class UserController extends WebBaseController {
    @Resource
    private UserService userService;

    @AuthorityAnnotation(AuthorityAnnotationEnums.USER_EDIT)
    @RequestMapping(value = "insert/{userName}")
    public String insert(@PathVariable String userName) {
        userService.insertUser(userName);
        JSONObject json = new JSONObject();
        json.put("result", "ok");
        return json.toString();
    }

    @AuthorityAnnotation(AuthorityAnnotationEnums.USER_SELECT)
    @RequestMapping(value = "select/{param}")
    public String select(@PathVariable String param) {
        UserModel user = userService.selectUser(param);
        UserModel user_detail = new UserModel();
        user.setDetail(user_detail);
        user_detail.setName("detail");


		/*1.简单模式
		return returnJsonInfoWithFilter(json,
				JsonCommonCodeEnum.C0000, new SimplePropertyPreFilter(
						Person.class,
						JsonPropertyFilterConstants.PERSON_LIST_PROPERTY));
		*/
		/*2.复杂模式 ，需要在Constants定义两个*/
        return returnJsonInfoWithFilter(user, JsonCommonCodeEnum.C0000,
                new SerializeFilter[]{UserJsonPropertyFilterConstants.USER_FILTER});

    }
}
