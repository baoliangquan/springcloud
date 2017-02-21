package com.company.cloud.base.common;

/**
 * Created by yoara on 2017/2/21.
 */
public enum CacheBaseKey implements CacheKeyType{
    SESSION,
    /** 分布式锁 **/
    REDIS_LOCK,
    /** CSRF token键 **/
    REDIS_CSRF_TOKEN,
    /** 加解密相关 **/
    REDIS_ENCRYPT_RSA,
    REDIS_ENCRYPT_AES,
    /** 缓存注解相关 **/
    REDIS_CACHED_DATA;
}
