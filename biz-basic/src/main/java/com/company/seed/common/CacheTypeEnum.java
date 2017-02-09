package com.company.seed.common;

/**
 * 缓存类型,
 * 自己添加模块的缓存类型{@link CacheTypeEnum}
 * Created by yoara on 2015/12/21.
 */
public enum CacheTypeEnum {
    SESSION(CacheBaseEnum.SESSION),

	USERINFO(CacheBaseEnum.SESSION),
	/** 基础数据缓存START **/
	/** 基础数据缓存END **/
	
	/** 分布式锁 **/
	REDIS_LOCK(CacheBaseEnum.REDIS_LOCK),
	/** CSRF token键 **/
	REDIS_CSRF_TOKEN(CacheBaseEnum.REDIS_CSRF_TOKEN),
	/** 加解密相关 **/
	REDIS_ENCRYPT_RSA(CacheBaseEnum.REDIS_ENCRYPT),
	REDIS_ENCRYPT_AES(CacheBaseEnum.REDIS_ENCRYPT),
	/** 缓存注解相关 **/
	REDIS_CACHED_USERDEMO(CacheBaseEnum.REDIS_CACHED),
	;
    
	private CacheBaseEnum cacheType;
	
	private CacheTypeEnum(CacheBaseEnum cacheType) {
		this.cacheType = cacheType;
	}

	public CacheBaseEnum getCacheType() {
		return cacheType;
	}

	public void setCacheType(CacheBaseEnum cacheType) {
		this.cacheType = cacheType;
	}
}
