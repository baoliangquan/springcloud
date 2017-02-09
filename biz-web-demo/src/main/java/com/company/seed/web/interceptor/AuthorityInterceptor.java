package com.company.seed.web.interceptor;

import com.company.seed.basic.web.bean.JsonCommonCodeEnum;
import com.company.seed.basic.web.interceptor.ZBaseInterceptorAdapter;
import com.company.seed.common.CommonConstants;
import com.company.seed.module.model.manager.ManagerModel;
import com.company.seed.module.model.manager.annotation.AuthorityAnnotation;
import com.company.seed.module.model.manager.enums.AuthorityAnnotationEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

/**
 * @author yoara
 */
public class AuthorityInterceptor extends ZBaseInterceptorAdapter {
    private Logger visitLog = LoggerFactory.getLogger(this.getClass());

    private List<String> superManagers;
    /**设置超级用户列表，超级用户将绕过权限校验**/
    public void setSuperManagers(List<String> superManagers) {
        this.superManagers = superManagers;
    }

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        try {
            //仅校验方法级别的Handler
            if(!(handler instanceof HandlerMethod)){
                return super.preHandle(request, response, handler);
            }
            AuthorityAnnotation auth = getAnnotation((HandlerMethod) handler);
            if(auth==null){
                return super.preHandle(request, response, handler);
            }
            if (checkHasAuth(request,auth)) {
                return super.preHandle(request, response, handler);
            } else {
                printJsonMsg(response, JsonCommonCodeEnum.E0007, null);
                return false;
            }
        } catch (Exception e) {
            visitLog.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
    }

    /**设置注解值**/
    private AuthorityAnnotation getAnnotation(HandlerMethod handler) {
		HandlerMethod handlerMethod = handler;
		AuthorityAnnotation annotation =
				handlerMethod.getClass().getAnnotation(AuthorityAnnotation.class);
        AuthorityAnnotation methodAnnotation =
				handlerMethod.getMethod().getAnnotation(AuthorityAnnotation.class);
		return methodAnnotation==null?annotation:methodAnnotation;
    }

    /**
     * 验证当前用户是否有权限
     * @return
     */
    private boolean checkHasAuth(HttpServletRequest request,AuthorityAnnotation auth) {
        ManagerModel object = (ManagerModel) request.getSession().getAttribute(CommonConstants.CURRENT_LOGIN_MANAGER);
        if (object != null) {
            if(superManagers !=null && superManagers.contains(object.getPhoneNumber())){
                return true;
            }

            Set<AuthorityAnnotationEnums> authSet = object.getAuthSet();
            if(authSet!=null && authSet.contains(auth.value())){
                return true;
            }
        }
        return false;
    }
}
