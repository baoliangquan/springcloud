package com.company.seed.web.controller.openapi;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.basic.web.common.csrftoken.CSRFTokenInterceptor;
import com.company.seed.basic.web.common.csrftoken.annotation.CheckCSRFTokenAnnotation;
import com.company.seed.basic.web.common.csrftoken.annotation.InitCSRFTokenAnnotation;
import com.company.seed.web.controller.WebBaseController;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by yoara on 2016/3/3.
 */
@Lazy
@RestController
@InitCSRFTokenAnnotation
@RequestMapping(value = "/openapi/csrfdemo/",produces = { "application/json;charset=UTF-8" })
public class CSRFTokenCheckController extends WebBaseController {
    @RequestMapping(value = "inittoken")
    public String initToken() {
        JSONObject json = new JSONObject();
        json.put("result", "do nothing but initToken");
        json.put("token", getRequest().getAttribute(CSRFTokenInterceptor.CSRF_TOKEN_INTE));
        return json.toString();
    }

    @RequestMapping(value = "checktoken")
    @CheckCSRFTokenAnnotation
    public String checkToken() {
        JSONObject json = new JSONObject();
        json.put("result","do nothing but checkToken");
        return json.toString();
    }
}
