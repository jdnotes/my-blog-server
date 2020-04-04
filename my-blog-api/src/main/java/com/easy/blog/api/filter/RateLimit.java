package com.easy.blog.api.filter;

import java.lang.annotation.*;

@Inherited
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RateLimit {
    /**
     * 默认每秒支持100个
     * @return
     */
    int limitNum() default 100;
}