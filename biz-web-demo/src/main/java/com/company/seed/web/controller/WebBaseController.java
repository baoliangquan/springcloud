package com.company.seed.web.controller;

import com.company.seed.common.CommonConstants;
import com.company.seed.module.model.manager.ManagerModel;
import com.company.seed.basic.web.controller.BaseController;

/**
 * Created by yoara on 2016/5/23.
 */
public class WebBaseController extends BaseController {
    public ManagerModel getCurrentManager(){
        return (ManagerModel) getRequest().
                getSession().getAttribute(CommonConstants.CURRENT_LOGIN_MANAGER);
    }
    public ManagerModel getCurrentUser(){
        return (ManagerModel) getRequest().
                getSession().getAttribute(CommonConstants.CURRENT_LOGIN_USER);
    }
}
