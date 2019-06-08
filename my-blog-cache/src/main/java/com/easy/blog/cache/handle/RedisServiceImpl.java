package com.easy.blog.cache.handle;

import com.easy.blog.cache.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author zhouyong
 * @DATE 2018/3/13
 */
@Service
public class RedisServiceImpl implements CacheService {

    @Autowired
    @Qualifier(value = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Autowired
    private StringRedisSerializer stringSerializer;

    @Autowired
    private GenericJackson2JsonRedisSerializer jsonSerializer;

    @Autowired
    private JdkSerializationRedisSerializer jdkSerializer;

    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, String value, long expire) {
        if (expire > 0) {
            redisTemplate.opsForValue().set(key, value, expire, TimeUnit.SECONDS);
        } else {
            this.set(key, value);
        }
    }

    @Override
    public String get(final String key) {
        if (key == null || "".equals(key)) {
            return null;
        }
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void expire(final String key, long expire) {
        redisTemplate.expire(key, expire, TimeUnit.SECONDS);
    }

    @Override
    public Set<String> keys(String regex) {
        return redisTemplate.keys(regex);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void del(Set<String> keys) {
        redisTemplate.delete(keys);
    }

    @Override
    public long increment(String key, long delta) {
        if (delta < 0) {
            throw new RuntimeException("The increasing factor must be more than 0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    @Override
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    @Override
    public void hset(String key, String item, Object value) {
        redisTemplate.opsForHash().put(key, item, value);
    }

    @Override
    public void hset(String key, String item, Object value, long expire) {
        redisTemplate.opsForHash().put(key, item, value);
        if (expire > 0) {
            expire(key, expire);
        }
    }

    @Override
    public void hmset(String key, Map<String, String> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    @Override
    public void hmset(String key, Map<String, String> map, long time) {
        redisTemplate.opsForHash().putAll(key, map);
        if (time > 0) {
            expire(key, time);
        }
    }

    @Override
    public void hdel(String key, String... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    @Override
    public void lSet(String key, String value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    @Override
    public void lSet(String key, String value, long expire) {
        redisTemplate.opsForList().rightPush(key, value);
        if (expire > 0) {
            expire(key, expire);
        }
    }

    @Override
    public void lSet(String key, List<String> value) {
        redisTemplate.opsForList().rightPushAll(key, value);
    }

    @Override
    public void lSet(String key, List<String> value, long time) {
        redisTemplate.opsForList().rightPushAll(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    @Override
    public void lSet(String key, long index, String value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    @Override
    public List<String> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public Object lGet(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    @Override
    public long lGetSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public Boolean setnx(String key, String value, long expire) {
        if (key == null || "".equals(key)) {
            return null;
        }
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer = redisTemplate.getStringSerializer();
                Boolean result = connection.setNX(serializer.serialize(key), serializer.serialize(value));
                return result;
            }
        });
        if (expire > 0) {
            expire(key, expire);
        }
        return result;
    }

    @Override
    public void setObject(String key, Object value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {

                connection.set(stringSerializer.serialize(key), jsonSerializer.serialize(value));
                return null;
            }
        });
    }

    @Override
    public void setObject(String key, Object value, long expire) {
        this.setObject(key, value);
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public <T> T getObject(String key, Class<T> clazz) {
        if (key == null) {
            return null;
        }
        T result = redisTemplate.execute(new RedisCallback<T>() {

            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] bytes = redisConnection.get(stringSerializer.serialize(key));
                return jsonSerializer.deserialize(bytes, clazz);
            }
        });
        return result;
    }

    @Override
    public void setObjectByBtye(String key, Object value, long expire) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(stringSerializer.serialize(key), jdkSerializer.serialize(value));
                return null;
            }
        });
        if (expire > 0) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    @Override
    public <T> T getObjectByBtye(String key) {
        if (key == null) {
            return null;
        }
        T result = redisTemplate.execute(new RedisCallback<T>() {

            @Override
            public T doInRedis(RedisConnection redisConnection) throws DataAccessException {
                byte[] bytes = redisConnection.get(stringSerializer.serialize(key));
                return (T) jdkSerializer.deserialize(bytes);
            }
        });
        return result;
    }

    @Override
    public Boolean exists(String key) {
        if (key == null) {
            return false;
        }
        return redisTemplate.hasKey(key);
    }

    @Override
    public void hsetObject(String key, String field, Object value) {
        if (key == null || field == null) {
            return;
        }
        Boolean result = redisTemplate.execute(new RedisCallback<Boolean>() {
            @Override
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(stringSerializer.serialize(key), stringSerializer.serialize(field), jsonSerializer.serialize(value));
            }
        });
    }

    @Override
    public <T> T hgetObject(String key, String field, Class<T> clazz) {
        if (key == null || field == null) {
            return null;
        }
        T result = redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] value = connection.hGet(stringSerializer.serialize(key), stringSerializer.serialize(field));
                return jsonSerializer.deserialize(value, clazz);
            }
        });
        return (T) result;
    }

    @Override
    public Object hdelObjectByByte(String key, Object field) {
        if (key == null) {
            return null;
        }
        Object result = redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hDel(stringSerializer.serialize(key), jdkSerializer.serialize(field));
            }
        });
        return result;
    }

    @Override
    public Long hlen(String key) {
        if (key == null) {
            return null;
        }
        Object result = redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getKeySerializer();
                return connection.hLen(serializer.serialize(key));
            }
        });
        return (Long) result;
    }

    @Override
    public Set<String> hkeys(String key) {
        if (key == null) {
            return null;
        }
        Set<String> result = redisTemplate.execute(new RedisCallback<Set<String>>() {
            @Override
            public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getKeySerializer();
                Set<byte[]> bytes = connection.hKeys(serializer.serialize(key));
                Set<String> set = null;
                if (bytes != null && bytes.size() > 0) {
                    set = new HashSet<>(bytes.size());
                    for (byte[] objByte : bytes) {
                        String obj = (String) serializer.deserialize(objByte);
                        set.add(obj);
                    }
                }
                return set;
            }
        });
        return result;
    }

    @Override
    public <V> List<V> hvalsObject(String key, Class<V> clazz) {
        if (key == null) {
            return null;
        }
        List<V> result = redisTemplate.execute(new RedisCallback<List<V>>() {
            @Override
            public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> bytes = connection.hVals(stringSerializer.serialize(key));
                List<V> list = null;
                if (bytes != null && bytes.size() > 0) {
                    list = new ArrayList<>(bytes.size());
                    for (byte[] objByte : bytes) {
                        Object obj = jsonSerializer.deserialize(objByte, clazz);
                        list.add((V) obj);
                    }
                }

                return list;
            }
        });
        return result;
    }

    @Override
    public Map<String, String> hmgetAll(String key) {
        if (key == null) {
            return null;
        }
        Map<String, String> result = redisTemplate.execute(new RedisCallback<Map<String, String>>() {
            @Override
            public Map<String, String> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer serializer = redisTemplate.getKeySerializer();
                RedisSerializer<?> hashValueSerializer = redisTemplate.getHashValueSerializer();
                Map<byte[], byte[]> byteMap = connection.hGetAll(serializer.serialize(key));
                Map<String, String> resultMap = null;
                if (byteMap != null && byteMap.size() > 0) {
                    resultMap = new HashMap<>(byteMap.size() * 2);
                    for (byte[] keyByte : byteMap.keySet()) {
                        byte[] valueByte = byteMap.get(keyByte);
                        resultMap.put((String) serializer.deserialize(keyByte), (String) hashValueSerializer.deserialize(valueByte));
                    }
                }
                return resultMap;
            }
        });
        return result;
    }

    @Override
    public void hsetObjectByByte(String key, Object field, Object value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.hSet(stringSerializer.serialize(key), jdkSerializer.serialize(field), jdkSerializer.serialize(value));
            }
        });
    }

    @Override
    public <T> T hgetObjectByByte(String key, Object field) {
        return redisTemplate.execute(new RedisCallback<T>() {
            @Override
            public T doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] bytes = connection.hGet(stringSerializer.serialize(key), jdkSerializer.serialize(field));
                return (T) jdkSerializer.deserialize(bytes);
            }
        });
    }

    @Override
    public <K> Set<K> hkeysByByte(String key) {
        return redisTemplate.execute(new RedisCallback<Set<K>>() {
            @Override
            public Set<K> doInRedis(RedisConnection connection) throws DataAccessException {
                Set<byte[]> bytes = connection.hKeys(stringSerializer.serialize(key));
                Set<K> keys = new HashSet<>();
                if (bytes != null && bytes.size() > 0) {
                    for (byte[] keyByte : bytes) {
                        keys.add((K) jdkSerializer.deserialize(keyByte));
                    }
                }
                return keys;
            }
        });
    }

    @Override
    public <V> List<V> hvalsByByte(String key) {
        return redisTemplate.execute(new RedisCallback<List<V>>() {
            @Override
            public List<V> doInRedis(RedisConnection connection) throws DataAccessException {
                List<byte[]> bytes = connection.hVals(stringSerializer.serialize(key));
                List<V> values = new ArrayList<>();
                if (bytes != null && bytes.size() > 0) {
                    for (byte[] valueByte : bytes) {
                        values.add((V) jdkSerializer.deserialize(valueByte));
                    }
                }
                return values;
            }
        });
    }

    @Override
    public Set<String> members(String key) {
        return redisTemplate.opsForSet().members(key);
    }

}
