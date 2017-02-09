/**
 * 
 */
package com.company.seed.basic.web.bean;

/**
 * API处理结果状态码
 * @date 2014-12-23 下午6:50:18
 */
public enum JsonCommonCodeEnum implements JsonReturnCodeEnum {
	/** 正常返回 **/
	C0000("处理成功"),
	C0001("处理失败"),
	
	/** 常规的错误码START **/
	E0000("未知服务器错误"),
	E0001("无效的城市"),
	E0002("参数不全或无效"),
	E0003("暂时没有开放"),
	E0004("服务器正忙，请稍后"),
	E0005("服务器异常!"),
	E0006("参数格式不符合要求"),
	E0007("用户权限未通过校验"),
	E0008("用户未登录"),
	E0009("CSRF校验不通过"),
	E0010("RSA加密校验不通过"),
	/** 常规的错误码END **/
	
	/** 01 **/
	F0101("01模块错误"),

	;
	
	
	
	private String msg;
	
	private JsonCommonCodeEnum(String msg){
		this.msg = msg;
	}
	
	@Override
	public String getMessage() {
		return msg;
	}
	
	@Override
	public String getStatus() {
		return name();
	}

	@Override
	public String getMessageField() {
		return "message";
	}
	
	@Override
	public String getStatusField() {
		return "status";
	}
}
