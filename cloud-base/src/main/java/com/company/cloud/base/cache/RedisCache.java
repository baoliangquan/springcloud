package com.company.cloud.base.cache;


import com.company.cloud.base.common.CacheKeyType;

import java.util.List;
import java.util.Map;

/**
 * Cache接口
 * Created by yoara on 2016/3/3.
 */
public interface RedisCache {
	/**
	 * Get an item from the cache, nontransactionally
	 * 
	 * @param key
	 * @return the cached object or <tt>null</tt>
	 */
	Object get(CacheKeyType cacheTypeEnum, String key);
	List<Object> getAll(CacheKeyType cacheTypeEnum);
	Object getHash(CacheKeyType cacheTypeEnum, String key, String hashKey);
	Map<Object, Object> getHashAll(CacheKeyType cacheTypeEnum, String key);
	List<Object> multiGet(CacheKeyType cacheTypeEnum, List<String> keys);
	/**
	 * Add an item to the cache, nontransactionally, with failfast semantics
	 * 
	 * @param key
	 * @param value
	 */
	void put(CacheKeyType cacheTypeEnum, String key, Object value);

	void put(CacheKeyType cacheTypeEnum, String key, Object value,
			 int second);

	/**
	 * Add an item to the cache
	 * 
	 * @param key
	 * @param value
	 */
	void update(CacheKeyType cacheTypeEnum, String key, Object value);

	/**
	 * Remove an item from the cache
	 */
	void remove(CacheKeyType cacheTypeEnum, String key);
	
	/**
	 * Remove all items has same cache
	 */
	void removeAll(CacheKeyType cacheTypeEnum);
	void removeAll(CacheKeyType cacheTypeEnum, String key);

	/**
	 * Clear the cache
	 */
	void clear();

	void expire(CacheKeyType cacheTypeEnum, String id, int timeout);

	long getExpire(CacheKeyType cacheTypeEnum, String id);

	void putToday(CacheKeyType cacheTypeEnum, String key, Object value);
	void putHashToday(CacheKeyType cacheTypeEnum,
							 String key, String hashKey, Object value);
	void putHash(CacheKeyType cacheTypeEnum,
						String key, String hashKey, Object value);
	void putHash(CacheKeyType cacheTypeEnum,
						String key, String hashKey, Object value, int second);
	/**
	 * 判断指定的键是否存在
	 * @authod yoara 2015-2-10
	 */
	boolean persist(CacheKeyType cacheTypeEnum, String key);
	/**
	 * 判断指定的键剩余时间
	 * @authod yoara 2015-2-15
	 */
	long ttl(CacheKeyType cacheTypeEnum, String key);
	/**
	 * 自增方法
	 * @authod yoara 2015-2-10
	 */
	long incr(CacheKeyType cacheTypeEnum, String key);
	long incr(CacheKeyType cacheTypeEnum, String key, int second);
	/**
	 * 自增方法
	 * @authod yoara 2015-2-10
	 */
	long incr(CacheKeyType cacheTypeEnum, String key, long incrValue);
	long incr(CacheKeyType cacheTypeEnum, String key, long incrValue, int second);
	/**
	 * 由于自增的key在通过spring-redis-data插入到redis中后序列化的方式发生变化，
	 * 必须用这种方式获得值，而不能通过{@link RedisCache#get(CacheKeyType, String)}获得
	 * @authod yoara 2015-2-10
	 */
	long getIncr(CacheKeyType cacheTypeEnum, String key);
	/**
	 * 获得当天剩下的秒数
	 * @authod yoara 2015-2-15
	 **/
	int getLeftSeconds();
	/**
	 * 重置并返回旧值
	 */
	Object getAndSet(CacheKeyType cacheTypeEnum, String key, Object value);
}