package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogArticleImages;

public interface BlogArticleImagesMapper {

    int delete(Long id);

    int insert(BlogArticleImages record);

    int insertSelective(BlogArticleImages record);

    BlogArticleImages get(Long id);

    int updateSelective(BlogArticleImages record);

    int update(BlogArticleImages record);

    String getUrlByCode(int code);
}