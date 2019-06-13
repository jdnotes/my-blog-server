package com.easy.blog.dao;

import com.easy.blog.model.BlogArticle;

public interface BlogArticleMapper {

    int insertSelective(BlogArticle record);

    BlogArticle get(Long id);

    int updateSelective(BlogArticle record);
}