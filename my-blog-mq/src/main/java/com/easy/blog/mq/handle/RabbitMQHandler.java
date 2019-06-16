package com.easy.blog.mq.handle;

import com.easy.blog.mq.service.MqService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.AbstractJavaTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Service
public class RabbitMQHandler implements MqService, RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendMessage(String exchange, String routingKey, Object msg) {
        if (StringUtils.isEmpty(exchange) || StringUtils.isEmpty(routingKey)
                || msg == null || "".equals(msg)) {
            throw new RuntimeException("exchange or routingKey or msg is empty");
        }
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replace("-", ""));
        Message message;
        String temp;
        try {
            temp = objectMapper.writeValueAsString(msg);
            message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(msg)).setDeliveryMode(MessageDeliveryMode.PERSISTENT).build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json Processing Exception", e);
        }
        message.getMessageProperties().setHeader(AbstractJavaTypeMapper.DEFAULT_CONTENT_CLASSID_FIELD_NAME, MessageProperties.CONTENT_TYPE_JSON);
        logger.info("send Message exchange:{},routingKey:{},msg:{},id:{}", exchange, routingKey, temp, correlationData.getId());
        rabbitTemplate.convertAndSend(exchange, routingKey, message, correlationData);
    }

    @Override
    public void sendTopicMessage(String exchange, String topic, String message) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString().replace("-", ""));
        logger.info("send Message topic:{},message:{},id:{}", topic, message, correlationData.getId());
        // 用RabbitMQ发送MQTT需将exchange配置为amq.topic
        this.rabbitTemplate.convertAndSend(exchange, topic, message, correlationData);
    }

    @Override
    public void confirm(@Nullable CorrelationData correlationData, boolean ack, @Nullable String cause) {
        if (ack) {
            logger.info("The message was sent to confirm success:{}", correlationData.getId());
        } else {
            logger.info("Message id :{} delivery confirmation failed:{}", correlationData.getId(), cause);
        }
    }

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("return message:{},replyCode:{},replyText:{},exchange:{},routingKey:{}",
                new String(message.getBody()), replyCode, replyText, exchange, routingKey);
    }
}