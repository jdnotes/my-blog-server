package com.easy.blog.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

/**
 * Api启动类
 *
 * @SpringBootApplication 扫描各个模块的bean
 * @MapperScan 扫描Mybatis的mapper
 * @EnableElasticsearchRepositories 解决Springboot集成elasticsearch 报扫描不到Repository
 */
@SpringBootApplication(scanBasePackages = {"com.easy"})
@MapperScan("com.easy.blog.api.dao")
@EnableElasticsearchRepositories(basePackages = "com.easy.blog.es")
public class ApiApplication {

    public static void main(String[] args) {
        //解决spring boot 集成redis和elasticsearch报elasticsearchClient is null
        //参考https://blog.csdn.net/sinat_29899265/article/details/81772037
        System.setProperty("es.set.netty.runtime.available.processors", "false");

        SpringApplication.run(ApiApplication.class, args);
    }
}