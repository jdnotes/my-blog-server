package com.easy.blog.cache.service;

import java.util.*;

/**
 * @author zhouyong
 * @DATE 2018/3/13
 */
public interface CacheService {

    /**
     * 设置缓存信息
     *
     * @param key
     * @param value
     * @return
     */
    void set(String key, String value);

    /**
     * 设置缓存信息
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    void set(String key, String value, long expire);

    /**
     * 获取缓存信息
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 指定缓存失效时间
     *
     * @param key
     * @param expire
     * @return
     */
    void expire(String key, long expire);

    /**
     * 获取key列表
     *
     * @param regex
     * @return
     */
    Set<String> keys(String regex);

    /**
     * 删除缓存
     *
     * @param key
     */
    void del(String key);

    /**
     * 删除缓存
     *
     * @param keys
     */
    void del(Set<String> keys);

    /**
     * 递增
     *
     * @param key
     * @param delta
     * @return
     */
    long increment(String key, long delta);

    /**
     * Hash表数据获取
     *
     * @param key
     * @param item
     * @return
     */
    Object hget(String key, String item);

    /**
     * Hash表设置数据
     *
     * @param key
     * @param map
     */
    void hmset(String key, Map<String, String> map);

    /**
     * Hash表设置数据
     *
     * @param key
     * @param map
     * @param time
     */
    void hmset(String key, Map<String, String> map, long time);

    /**
     * Hash表设置数据
     *
     * @param key
     * @param item
     * @param value
     */
    void hset(String key, String item, Object value);

    /**
     * Hash表设置数据
     *
     * @param key
     * @param item
     * @param value
     * @param expire
     */
    void hset(String key, String item, Object value, long expire);

    /**
     * 删除hash表中的值
     *
     * @param key
     * @param item
     */
    void hdel(String key, String... item);


    /**
     * 设置List数据
     *
     * @param key
     * @param value
     */
    void lSet(String key, String value);

    /**
     * 设置List数据
     *
     * @param key
     * @param value
     * @param expire
     */
    void lSet(String key, String value, long expire);

    /**
     * 设置List数据
     *
     * @param key
     * @param value
     */
    void lSet(String key, List<String> value);

    /**
     * 设置List数据
     *
     * @param key
     * @param value
     * @param time
     */
    void lSet(String key, List<String> value, long time);

    /**
     * 设置List数据
     *
     * @param key
     * @param index
     * @param value
     */
    void lSet(String key, long index, String value);

    /**
     * 获取list缓存的内容
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    List<String> lGet(String key, long start, long end);

    /**
     * 通过索引 获取list中的值
     *
     * @param key
     * @param index
     * @return
     */
    Object lGet(String key, long index);

    /**
     * 获取list缓存的长度
     *
     * @param key
     * @return
     */
    long lGetSize(String key);

    /**
     * 当缓存不存在时,设置缓存信息
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    Boolean setnx(String key, String value, long expire);


    /**
     * 设置对象型缓存信息
     *
     * @param key
     * @param value
     * @return
     */
    void setObject(String key, Object value);

    /**
     * 设置对象缓存信息
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    void setObject(String key, Object value, long expire);


    /**
     * 获取缓存信息
     *
     * @param key
     * @return
     */
    <T> T getObject(String key, Class<T> clazz);

    /**
     * 设置对象缓存信息
     *
     * @param key
     * @param value
     * @param expire
     * @return
     */
    void setObjectByBtye(String key, Object value, long expire);


    /**
     * 获取缓存信息
     *
     * @param key
     * @return
     */
    <T> T getObjectByBtye(String key);

    /**
     * 判断key是否存在
     *
     * @param key
     * @return
     */
    Boolean exists(String key);


    /**
     * 设置hash对象缓存信息
     *
     * @param key
     * @param value
     * @return
     */
    void hsetObject(String key, String field, Object value);

    /**
     * 获取hash对象缓存信息
     *
     * @param key
     * @return
     */
    <T> T hgetObject(String key, String field, Class<T> clazz);

    /**
     * 获取hash型缓存的长度
     *
     * @param key
     * @return
     */
    Long hlen(String key);

    /**
     * 获取hash型缓存的key集合
     *
     * @param key
     * @return
     */
    Set<String> hkeys(String key);

    /**
     * 获取hash型缓存的value集合
     *
     * @param key
     * @param <V>
     * @return
     */
    <V> List<V> hvalsObject(String key, Class<V> clazz);

    /**
     * 获取hash型缓存的value集合
     *
     * @param key
     * @return
     */
    Map<String, String> hmgetAll(String key);


    /**
     * 设置hash对象缓存信息
     *
     * @param key
     * @param value
     * @return
     */
    void hsetObjectByByte(String key, Object field, Object value);

    /**
     * 获取hash对象缓存信息
     *
     * @param key
     * @return
     */
    <T> T hgetObjectByByte(String key, Object field);

    /**
     * 获取hash对象缓存信息
     *
     * @param key
     * @return
     */
    Object hdelObjectByByte(String key, Object field);

    /**
     * 获取所有的key
     *
     * @param key
     * @param <K>
     * @return
     */
    <K> Set<K> hkeysByByte(String key);

    /**
     * 获取所有的value
     *
     * @param key
     * @param <V>
     * @return
     */
    <V> List<V> hvalsByByte(String key);

    /**
     * 获取set集合中的所有元素
     *
     * @param key
     * @return
     */
    Set<String> members(String key);

}
