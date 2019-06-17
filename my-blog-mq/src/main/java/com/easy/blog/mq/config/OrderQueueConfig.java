package com.easy.blog.mq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Configuration
public class OrderQueueConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(MQConstant.ORDER_EXCHANGE, true, false);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(MQConstant.ORDER_QUEUE_NAME, true);
    }

    @Bean
    public Binding topicBinding() {
        return BindingBuilder.bind(topicQueue()).to(topicExchange()).with(MQConstant.ORDER_ROUTE_NAME);
    }

}