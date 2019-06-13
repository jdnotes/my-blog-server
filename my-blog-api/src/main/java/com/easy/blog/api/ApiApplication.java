package com.easy.blog.api;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableElasticsearchRepositories(basePackages = "com.easy")
public class ApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}