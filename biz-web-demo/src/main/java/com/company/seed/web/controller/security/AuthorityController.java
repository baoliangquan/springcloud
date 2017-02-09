package com.company.seed.web.controller.security;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.basic.web.bean.JsonCommonCodeEnum;
import com.company.seed.basic.web.common.validation.ValidationResult;
import com.company.seed.basic.web.common.validation.annotation.ValidationAnnotation;
import com.company.seed.common.CommonStatusEnum;
import com.company.seed.module.model.manager.AuthorityLeafModel;
import com.company.seed.module.model.manager.ManagerModel;
import com.company.seed.module.model.manager.PositionAuthorityModel;
import com.company.seed.module.model.manager.PositionModel;
import com.company.seed.module.model.manager.annotation.AuthorityAnnotation;
import com.company.seed.module.model.manager.enums.AuthorityAnnotationEnums;
import com.company.seed.module.service.manager.AuthorityService;
import com.company.seed.module.service.manager.ManagerService;
import com.company.seed.module.service.manager.PositionService;
import com.company.seed.web.controller.WebBaseController;
import com.company.seed.web.controller.security.form.ManagerForm;
import com.company.seed.web.controller.security.form.PaModifyForm;
import com.company.seed.web.controller.security.form.PositionForm;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by yoara on 2016/8/3.
 */
@Lazy
@RestController
@RequestMapping(value = "/security/authority",produces = { "application/json;charset=UTF-8" })
public class AuthorityController extends WebBaseController {
    @Resource
    private PositionService positionService;
    @Resource
    private ManagerService managerService;
    @Resource
    private AuthorityService authorityService;

    /**
     * 获取岗位信息
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.SYSTEM_SELECT)
    @RequestMapping(value = "positioninfo")
    public String positionInfo() {
        List<PositionModel> list = positionService.selectAll();
        return returnJsonInfo(list, JsonCommonCodeEnum.C0000);
    }

    /**
     * 编辑岗位
     * @param form
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.SYSTEM_EDIT)
    @RequestMapping(value = "positionmodify")
    public String positionModify(PositionForm form) {
        ValidationResult result = validationParam(form);
        if(result.hasError()){
            return returnJsonInfo(result.getErrorMessages(),JsonCommonCodeEnum.E0006);
        }
        PositionModel model = new PositionModel();
        model.setId(form.getId());
        model.setName(form.getName());
        model.setStatus(form.isEnabled()? CommonStatusEnum.ENABLED:CommonStatusEnum.DISABLED);
        positionService.savePosition(model);
        return returnJsonInfo("",JsonCommonCodeEnum.C0000);
    }

    /**
     * 获取manager信息
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.MANAGER_SELECT)
    @RequestMapping(value = "managerinfo")
    public String managerInfo() {
        List<ManagerModel> list = managerService.selectAll();
        return returnJsonInfo(list, JsonCommonCodeEnum.C0000);
    }

    /**
     * 编辑manager
     * @param form
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.MANAGER_EDIT)
    @RequestMapping(value = "managermodify")
    public String managerModify(ManagerForm form) {
        ValidationResult result = validationParam(form);
        if(result.hasError()){
            return returnJsonInfo(result.getErrorMessages(),JsonCommonCodeEnum.E0006);
        }
        ManagerModel model = new ManagerModel();
        model.setId(form.getId());
        model.setName(form.getName());
        model.setPhoneNumber(form.getPhoneNumber());
        model.setPositionId(form.getPositionId());
        model.setStatus(form.isEnabled()? CommonStatusEnum.ENABLED:CommonStatusEnum.DISABLED);
        managerService.saveManager(model);
        return returnJsonInfo("",JsonCommonCodeEnum.C0000);
    }

    /**
     * 获取权限信息
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.SYSTEM_SELECT)
    @RequestMapping(value = "authorityinfo")
    public String authorityInfo() {
        List<AuthorityLeafModel> listAuth = authorityService.selectAll();
        List<PositionAuthorityModel> listPAndA = authorityService.selectAllPositionWithAuthority();
        JSONObject object = new JSONObject();
        object.put("listAuth",listAuth);
        object.put("listPAndA",listPAndA);
        return returnJsonInfo(object, JsonCommonCodeEnum.C0000);
    }

    /**
     * 修改岗位权限绑定信息
     * @param form
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.SYSTEM_EDIT)
    @RequestMapping(value = "modifypa")
    @ValidationAnnotation
    public String modifyPa(PaModifyForm form) {
        authorityService.modifyPositionWithAuth(form.getAuthorityId(),form.getPositionId(),form.isCheck());
        return returnJsonInfo("",JsonCommonCodeEnum.C0000);
    }

    /**
     * 通过枚举初始化权限
     * @return
     */
    @AuthorityAnnotation(AuthorityAnnotationEnums.SYSTEM_EDIT)
    @RequestMapping(value = "initauthority")
    public String initAuthority() {
        authorityService.initAllAuthority();
        return returnJsonInfo("",JsonCommonCodeEnum.C0000);
    }
}
