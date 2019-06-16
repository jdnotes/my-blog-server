package com.easy.blog.mq.config;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
public class MQConstant {

    /**
     * 交换器
     */
    public static final String DEFAULT_EXCHANGE = "blog";

    /**
     * 日志队列
     */
    public static final String LOG_QUEUE_NAME = "log.queue";

    /**
     * 日志队列路由
     */
    public static final String LOG_ROUTE_NAME = "log.route";

    /**
     * 订单交换器
     */
    public static final String ORDER_EXCHANGE = "order";

    /**
     * 订单队列
     */
    public static final String ORDER_QUEUE_NAME = "order.queue";

    /**
     * 订单队列路由
     */
    public static final String ORDER_ROUTE_NAME = "order.route";


}