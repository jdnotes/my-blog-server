package com.easy.blog.service;

import com.easy.blog.model.BlogArticleBack;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public interface BlogArticleBackService {

    void add(BlogArticleBack param);

    BlogArticleBack get(Long id);
}