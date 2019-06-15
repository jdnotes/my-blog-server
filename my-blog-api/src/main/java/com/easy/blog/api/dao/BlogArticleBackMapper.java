package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogArticleBack;

public interface BlogArticleBackMapper {

    int insertSelective(BlogArticleBack record);

    BlogArticleBack get(Long id);

    int updateSelective(BlogArticleBack record);

    BlogArticleBack getByCode(String code);
}