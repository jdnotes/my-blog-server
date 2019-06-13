package com.easy.blog.dao;

import com.easy.blog.model.BlogArticleBack;

public interface BlogArticleBackMapper {

    int insertSelective(BlogArticleBack record);

    BlogArticleBack get(Long id);

    int updateSelective(BlogArticleBack record);
}