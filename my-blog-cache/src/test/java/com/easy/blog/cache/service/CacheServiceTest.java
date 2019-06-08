package com.easy.blog.cache.service;

import com.easy.blog.cache.CacheApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhouyong
 * @date 2019/6/7
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {CacheApplication.class})
public class CacheServiceTest {

    private final Logger logger = LoggerFactory.getLogger(CacheServiceTest.class);

    @Autowired
    private CacheService cacheService;

    @Test
    public void test() {
        String key = "hello";
        cacheService.set(key, "hello world", 300);
        String json = cacheService.get(key);
        logger.info(json);
    }

}