package com.company.cloud.base.common;

/**
 * 缓存类型,
 * 自己添加模块的缓存类型{@link CacheKeyType}
 * Created by yoara on 2015/12/21.
 */
public interface CacheKeyType {
	default String getCacheKey(){
		return this.toString();
	}
}
