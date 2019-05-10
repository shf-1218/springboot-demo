package com.hongfei.springbootshiro.common.redis;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @program: springboot-demo
 * @Date: 2019-04-23 22:51
 * @Author: Mr.Shen
 * @Description:
 */
@Slf4j
public class RedisShiroCache<K, V> implements Cache<K, V> {

    private static final String REDIS_SHIRO_CACHE = "shiro-cache:";

    private String cacheKey;

    private RedisTemplate<K, V> redisTemplate;

    private long globExpire = 30;

    @SuppressWarnings("rawtypes")
    public RedisShiroCache(String name, RedisTemplate client) {
        this.cacheKey = REDIS_SHIRO_CACHE +":" +name ;
        this.redisTemplate = client;
    }

    @Override
    public V get(K key) throws CacheException {
        if(log.isDebugEnabled()){
            log.debug("根据key从Redis中获取对象 key [" + key + "]");
        }
        try {
            if(key == null){
                return null;
            }else {
                redisTemplate.boundValueOps(getCacheKey(key)).expire(globExpire, TimeUnit.MINUTES);
                V v = redisTemplate.boundValueOps(getCacheKey(key)).get();
                return v;
            }
        }catch (Throwable t){
            throw new CacheException(t);
        }
    }

    @Override
    public V put(K key, V value) throws CacheException {
        if(log.isDebugEnabled()){
            log.info("根据key从存储 key [" + key + "]");
        }
        try {
            V v = get(key);
            redisTemplate.boundValueOps(getCacheKey(key)).set(value);
            System.out.println(redisTemplate.boundValueOps(getCacheKey(key)).get().toString());
            return v;
        }catch (Throwable t){
            throw  new CacheException(t);
        }
    }

    @Override
    public V remove(K key) throws CacheException {
        if(log.isDebugEnabled()){
            log.info("从redis中删除 key [" + key + "]");
        }
        try {
            V v = get(key);
            redisTemplate.delete(getCacheKey(key));
            return v;
        }catch (Throwable t){
            throw new CacheException(t);
        }

    }

    @Override
    public void clear() throws CacheException {
        try {
            redisTemplate.delete(keys());
        }catch (Throwable t){
            throw new CacheException(t);
        }
    }

    @Override
    public int size() {
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        return redisTemplate.keys(getCacheKey("*"));
    }

    @Override
    public Collection<V> values() {
        Set<K> set = keys();
        List<V> list = new ArrayList<>();
        for (K s : set) {
            list.add(get(s));
        }
        return list;
    }

    private K getCacheKey(Object k) {
        return (K) (this.cacheKey + k);
    }
}
