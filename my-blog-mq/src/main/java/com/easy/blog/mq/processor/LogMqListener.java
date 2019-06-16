package com.easy.blog.mq.processor;

import com.easy.blog.mq.config.MQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Component
public class LogMqListener {

    private static final Logger logger = LoggerFactory.getLogger(LogMqListener.class);

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = MQConstant.LOG_QUEUE_NAME, containerFactory = "singleListenerContainer")
    public void consumeUserLogQueue(@Payload byte[] message) {
        try {
            logger.info(new String(message, "UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}