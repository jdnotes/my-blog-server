package com.easy.blog.mq.config;

/**
 * @author zhouyong
 * @date 2019/6/16
 */
public class MQConstant {

    /**
     * 交换器
     */
    public static final String DEFAULT_EXCHANGE = "blogDemo";

    /**
     * 日志队列
     */
    public static final String LOG_QUEUE_NAME = "log.demo.queue";

    /**
     * 日志队列路由
     */
    public static final String LOG_ROUTE_NAME = "log.demo.route";

    /**
     * 订单交换器
     */
    public static final String ORDER_EXCHANGE = "orderDemo";

    /**
     * 订单队列
     */
    public static final String ORDER_QUEUE_NAME = "order.demo.queue";

    /**
     * 订单队列路由
     */
    public static final String ORDER_ROUTE_NAME = "order.demo.route";

    /**
     * blog mq
     */
    public class BlogMqConstant {
        /**
         * 交换器
         */
        public static final String BLOG_EXCHANGE = "blogExchange";

        /**
         * 日志队列
         */
        public static final String LOG_QUEUE_NAME = "blog.log.queue";

        /**
         * 日志队列路由
         */
        public static final String LOG_ROUTE_NAME = "blog.log.route";
    }
}