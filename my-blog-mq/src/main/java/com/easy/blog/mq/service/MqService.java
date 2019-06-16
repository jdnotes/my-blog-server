package com.easy.blog.mq.service;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
public interface MqService {

    /**
     * direct message send
     *
     * @param exchange
     * @param routingKey
     * @param message
     */
    void sendMessage(String exchange, String routingKey, Object message);

    /**
     * topic message send
     *
     * @param exchange
     * @param topic
     * @param message
     */
    void sendTopicMessage(String exchange, String topic, String message);
}