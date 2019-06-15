package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogArticle;

public interface BlogArticleMapper {

    int insertSelective(BlogArticle record);

    BlogArticle get(Long id);

    int updateSelective(BlogArticle record);

    BlogArticle getByCode(String code);
}