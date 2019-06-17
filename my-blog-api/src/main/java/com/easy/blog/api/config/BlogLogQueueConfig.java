package com.easy.blog.api.config;

import com.easy.blog.mq.config.MQConstant;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
@Configuration
public class BlogLogQueueConfig {

    @Bean("blogLogQueue")
    public Queue logQueue() {
        return new Queue(MQConstant.BlogMqConstant.LOG_QUEUE_NAME, true);
    }

    @Bean("blogLogExchange")
    public DirectExchange logExchange() {
        return new DirectExchange(MQConstant.BlogMqConstant.BLOG_EXCHANGE, true, false);
    }

    @Bean("blogLogBinding")
    public Binding logBinding(@Qualifier("blogLogQueue") Queue queue, @Qualifier("blogLogExchange") DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(MQConstant.BlogMqConstant.LOG_ROUTE_NAME);
    }

}