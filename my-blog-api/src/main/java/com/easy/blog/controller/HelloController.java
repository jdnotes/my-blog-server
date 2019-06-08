package com.easy.blog.controller;

import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.service.BlogArticleSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private BlogArticleSearchService blogArticleSearchService;

    @RequestMapping("/get")
    public Object get(Long id) {
        BlogArticleEs blogArticleEs = blogArticleSearchService.getInfoById(id);
        return blogArticleEs;
    }
}