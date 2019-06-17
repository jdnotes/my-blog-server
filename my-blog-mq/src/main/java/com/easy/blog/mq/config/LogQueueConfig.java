package com.easy.blog.mq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Configuration
public class LogQueueConfig {

    @Bean
    public Queue logQueue() {
        return new Queue(MQConstant.LOG_QUEUE_NAME, true);
    }

    @Bean
    public DirectExchange logExchange() {
        return new DirectExchange(MQConstant.DEFAULT_EXCHANGE, true, false);
    }

    @Bean
    public Binding logBinding() {
        return BindingBuilder.bind(logQueue()).to(logExchange()).with(MQConstant.LOG_ROUTE_NAME);
    }

}