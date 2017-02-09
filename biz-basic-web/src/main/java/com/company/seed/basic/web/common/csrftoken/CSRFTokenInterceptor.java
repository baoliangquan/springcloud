package com.company.seed.basic.web.common.csrftoken;

import com.company.seed.basic.web.bean.JsonCommonCodeEnum;
import com.company.seed.basic.web.common.csrftoken.annotation.InitCSRFTokenAnnotation;
import com.company.seed.basic.web.interceptor.ZBaseInterceptorAdapter;
import com.company.seed.basic.web.common.csrftoken.annotation.CheckCSRFTokenAnnotation;
import com.company.seed.basic.web.common.csrftoken.exception.CSRFTokenException;
import com.company.seed.cache.RedisCache;
import com.company.seed.common.CacheTypeEnum;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * 用于校验CSRF跨站请求伪造的token<br>
 * 标记了注解{@link CheckCSRFTokenAnnotation}  的展示层方法，在执行方法前会判断token<br>
 * @author yoara
 */
public class CSRFTokenInterceptor extends ZBaseInterceptorAdapter {
	//token的key值
	public final static String CSRF_TOKEN_INTE = "CSRF_TOKEN_INTE";
	/**token最大存活时间，单位为秒**/
	public final static int CSRF_TOKEN_EXPIRE = 60*2;

	@Resource
	private RedisCache redisCache;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
							 Object handler) throws Exception {
		checkInitToken(request, handler);

		return checkCSRFToken(request, response, handler);
	}
	/** 检查是否需要初始化CSRF token **/
	private void checkInitToken(HttpServletRequest request, Object handler) {
		InitCSRFTokenAnnotation initAnnotation = getAnnotation(handler,InitCSRFTokenAnnotation.class);
		if(initAnnotation!=null){
			int expire = initAnnotation.expire();
			if(expire>CSRF_TOKEN_EXPIRE){
				expire = CSRF_TOKEN_EXPIRE;
			}
			String token = UUID.randomUUID().toString();
			//以token为key，表示该key存在即可
			redisCache.put(CacheTypeEnum.REDIS_CSRF_TOKEN,token,true,expire);
			//将token放入返回值中
			request.setAttribute(CSRF_TOKEN_INTE,token);
		}
	}
	/** 检查是否需要校验CSRF token **/
	private boolean checkCSRFToken(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		CheckCSRFTokenAnnotation annotation = getAnnotation(handler,CheckCSRFTokenAnnotation.class);
		if(annotation!=null){
			//存在注解标记，必须进行token校验
			String token = getString(CSRF_TOKEN_INTE);
			if(StringUtils.isEmpty(token)){		//请求未带token
				return refused(response,annotation);
			}
			long ttl = redisCache.ttl(CacheTypeEnum.REDIS_CSRF_TOKEN,token);
			if(ttl>0){	//token有效允许处理
				//是否需要使token失效
				if(annotation.makeTokenInvalid()){
					redisCache.remove(CacheTypeEnum.REDIS_CSRF_TOKEN,token);
				}
				//执行接下来的逻辑
				return super.preHandle(request, response, handler);
			}else{	//token已失效
				return refused(response,annotation);
			}
		}
		return super.preHandle(request, response, handler);
	}
	/**校验不通过行为**/
	private boolean refused(HttpServletResponse response,CheckCSRFTokenAnnotation annotation) throws Exception {
		switch (annotation.dealType()){
			case JSON:
				printJsonMsg(response, JsonCommonCodeEnum.E0009,null);
				return false;
			case ALERT:
				doDealAlert(response,JsonCommonCodeEnum.E0009.getMessage());
				return false;
			default:
				throw new CSRFTokenException("CSRFToken invalid");
		}
	}
}