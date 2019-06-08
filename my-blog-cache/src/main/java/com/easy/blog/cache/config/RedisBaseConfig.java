package com.easy.blog.cache.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author zhouyong
 * @DATE 2018/3/13
 */
@Configuration
@PropertySource(value = "classpath:/redis.properties", ignoreResourceNotFound = true)
public class RedisBaseConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Value("${spring.redis.timeout}")
    private int timeout;

    @Value("${spring.redis.password}")
    private String password;

    @Value("${spring.redis.database}")
    private int database;

    @Value("${spring.redis.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.redis.pool.min-idle}")
    private int minIdle;

    /**
     * redis模板，存储关键字是字符串，值是string类型
     *
     * @param factory
     * @return
     * @Description:
     */
    @Bean(name = "stringRedisTemplate")
    public StringRedisTemplate stringRedisTemplate(@Qualifier("redisConnectionFactory") RedisConnectionFactory factory) {
        logger.info("===== init redis StringRedisTemplate =====");
        StringRedisTemplate redisTemplate = new StringRedisTemplate();
        redisTemplate.setConnectionFactory(factory);
        StringRedisSerializer redisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(redisSerializer);
        redisTemplate.setHashKeySerializer(redisSerializer);

        redisTemplate.setValueSerializer(redisSerializer);
        redisTemplate.setHashValueSerializer(redisSerializer);
        redisTemplate.afterPropertiesSet();
        return redisTemplate;
    }

    /**
     * json序列化
     *
     * @return
     * @Description:
     */
    @Bean(name = "stringSerializer")
    public StringRedisSerializer stringSerializer() {
        StringRedisSerializer stringSerializer = new StringRedisSerializer();
        return stringSerializer;
    }

    /**
     * json序列化
     *
     * @return
     * @Description:
     */
    @Bean(name = "jsonSerializer")
    public GenericJackson2JsonRedisSerializer jsonSerializer() {
        GenericJackson2JsonRedisSerializer jackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(getObjectMapper());
        return jackson2JsonRedisSerializer;
    }

    /**
     * Jdk序列化类, byte[]
     *
     * @return
     * @Description:
     */
    @Bean(name = "jdkSerializer")
    public JdkSerializationRedisSerializer jdkSerializer() {
        JdkSerializationRedisSerializer jdkSerializer = new JdkSerializationRedisSerializer();
        return jdkSerializer;
    }

    /**
     * jackson
     *
     * @return
     */
    public ObjectMapper getObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule((new SimpleModule()));
        // 解析器支持解析结束符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //反序列化忽略不存在字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //将该标记放在属性上，如果该属性为NULL则不参与序列化
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        return objectMapper;
    }
}
