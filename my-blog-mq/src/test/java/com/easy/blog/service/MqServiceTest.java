package com.easy.blog.service;

import com.easy.blog.mq.MqApplication;
import com.easy.blog.mq.config.MQConstant;
import com.easy.blog.mq.service.MqService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhouyong
 * @DATE 2019/6/13
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MqApplication.class)
@EnableAutoConfiguration
public class MqServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private MqService mqService;

    @Test
    public void sendDirectMessage() {
        String message = "hello world! timestamp is " + System.currentTimeMillis();
        mqService.sendMessage(MQConstant.DEFAULT_EXCHANGE, MQConstant.LOG_ROUTE_NAME, message);
    }

    @Test
    public void sendTopicMessage() {
        String message = "hello topic! timestamp is " + System.currentTimeMillis();
        mqService.sendTopicMessage(MQConstant.ORDER_EXCHANGE, MQConstant.ORDER_QUEUE_NAME, message);
    }
}
