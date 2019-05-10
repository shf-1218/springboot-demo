package com.hongfei.springbootshiro.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 22:50
 * @Author: Mr.Shen
 * @Description:
 */
@Slf4j
public class RedisShiroCacheManager implements CacheManager {

    private final ConcurrentMap<String, Cache> caches = new ConcurrentHashMap<String, Cache>();

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (log.isDebugEnabled()) {
            log.debug("获取名称为: " + name + " 的RedisCache实例");
        }
        Cache c = caches.get(name);

        if (c == null) {
            c=new RedisShiroCache<>(name, redisTemplate);
            caches.put(name, c);
        }
        return c;
    }

}
