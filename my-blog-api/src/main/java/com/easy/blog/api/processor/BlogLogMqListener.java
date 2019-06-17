package com.easy.blog.api.processor;

import com.alibaba.fastjson.JSON;
import com.easy.blog.api.model.BlogLog;
import com.easy.blog.api.service.BlogLogService;
import com.easy.blog.mq.config.MQConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Component
public class BlogLogMqListener {

    private static final Logger logger = LoggerFactory.getLogger(BlogLogMqListener.class);

    @Autowired
    private BlogLogService blogLogService;

    /**
     * 监听消费用户日志
     *
     * @param message
     */
    @RabbitListener(queues = MQConstant.BlogMqConstant.LOG_QUEUE_NAME, containerFactory = "singleListenerContainer")
    public void msgHandler(Message message, Channel channel) throws IOException {
        boolean success = false;
        try {
            if (message == null || message.getBody() == null) {
                success = true;
                logger.info("log message is null");
                return;
            }
            String msg = new String(message.getBody(), "UTF-8");
            logger.info(msg);
            BlogLog log = JSON.parseObject(msg, BlogLog.class);
            if (log != null) {
                blogLogService.add(log);
            }
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (success) {
                logger.info("ACK log");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                logger.info("NACK log");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}