package com.company.seed.basic.web.common.validation;

import com.alibaba.fastjson.JSONObject;
import com.company.seed.basic.web.bean.JsonCommonCodeEnum;
import com.company.seed.basic.web.common.validation.annotation.ValidationAnnotation;
import com.company.seed.basic.web.common.validation.exception.ValidationException;
import com.company.seed.basic.web.common.validation.pool.ValidationPoolBean;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 用于校验参数form<br>
 * 标记了注解{@link ValidationAnnotation}  的展示层方法，在执行方法前会判断token<br>
 * @author yoara
 */
@Aspect
@Component
public class ValidationAspect{
	@Resource
	protected ValidationPoolBean validationPoolBean;

	@Around(value = "@annotation(va)")
	public Object checkForm(ProceedingJoinPoint pjp,ValidationAnnotation va) throws Throwable {
		Object[] args = pjp.getArgs();
		for(Object o : args){
			if(o instanceof ValidationForm){
				ValidationResult result = validationPoolBean.validationParam((ValidationForm)o);
				if(result.hasError()){
					return refused(result,va);
				}
			}
		}
		return pjp.proceed();
	}
	/**校验不通过行为**/
	private String refused(ValidationResult result,ValidationAnnotation va) throws Exception {
		StringBuffer svMessage = new StringBuffer();
		for(ValidationErrorMessage em:result.getErrorMessages()){
			svMessage.append(em.getProperty()).append(em.getMessage()).append(" ");
		}
		switch (va.dealType()){
			case JSON:
				JSONObject message = new JSONObject();
				message.put("status", JsonCommonCodeEnum.E0006);
				message.put("message", svMessage.toString());
				return message.toJSONString();
			default:
				throw new ValidationException(svMessage.toString());
		}
	}
}