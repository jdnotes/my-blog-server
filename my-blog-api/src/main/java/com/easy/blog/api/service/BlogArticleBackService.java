package com.easy.blog.api.service;


import com.easy.blog.api.model.BlogArticleBack;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public interface BlogArticleBackService {

    void add(BlogArticleBack param);

    BlogArticleBack get(Long id);

    String save(BlogArticleBack param);
}