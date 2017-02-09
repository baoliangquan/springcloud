/**
 * 
 */
package com.company.seed.basic.web.bean;

/**
 * 
 * @author yoara
 * @date 2015-3-10 下午3:38:16
 */
public interface JsonReturnCodeEnum {

	/**
	 * 返回码对应提示消息
	 * @return
	 * @author HeYuanxun
	 * @date 2015-3-11 上午11:25:22
	 */
	public String getMessage();
	
	/**
	 * 提示消息在返回结果中的字段名称
	 * @return
	 * @author HeYuanxun
	 * @date 2015-3-11 下午3:48:58
	 */
	public String getMessageField();
	
	/**
	 * 响应状态码
	 * @return
	 * @author HeYuanxun
	 * @date 2015-3-11 上午11:25:52
	 */
	public String getStatus();
	
	/**
	 * 响应状态码在返回结果中的字段名称
	 * @return
	 * @author HeYuanxun
	 * @date 2015-3-11 下午3:49:27
	 */
	public String getStatusField();
}
