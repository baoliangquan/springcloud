package com.company.seed.common;

/**
 * 缓存枚举定义
 * Created by yoara on 2015/12/21.
 */
public enum CacheBaseEnum {
    SESSION,
	USERINFO,
	/** 基础数据缓存 **/
	BASIC_DATA,
	
	/** 分布式锁 **/
	REDIS_LOCK,
	/** CSRF token键 **/
	REDIS_CSRF_TOKEN,
	/** 加解密相关 **/
	REDIS_ENCRYPT,
	/** 缓存注解相关 **/
	REDIS_CACHED
	;

}
