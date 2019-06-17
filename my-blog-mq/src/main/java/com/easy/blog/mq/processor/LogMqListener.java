package com.easy.blog.mq.processor;

import com.easy.blog.mq.config.MQConstant;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
     * @Payload byte[] message
     * Message message
     */
    @RabbitListener(queues = MQConstant.LOG_QUEUE_NAME, containerFactory = "singleListenerContainer")
    public void msgHandler(Message message, Channel channel) throws IOException {
        boolean success = false;
        try {
            logger.info(new String(message.getBody(), "UTF-8"));
            success = true;
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            if (success) {
                logger.info("ACK");
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {
                logger.info("NACK");
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }
}