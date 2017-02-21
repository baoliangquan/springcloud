package com.company.cloud.module.service;

import com.company.cloud.base.cache.RedisCache;
import com.company.cloud.module.common.manager.ManagerCacheKey;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by yoara on 2017/2/21.
 */
@Service
public class ManagerService {
    @Resource
    private RedisCache redisCache;

    public void makeCacheData(){
        redisCache.put(ManagerCacheKey.TEST_DATA,"test","test");
    }
    public String queryCacheData(){
        return redisCache.get(ManagerCacheKey.TEST_DATA,"test").toString();
    }
}
