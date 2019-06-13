package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogArticleHistory;

public interface BlogArticleHistoryMapper {

    int insertSelective(BlogArticleHistory record);

    BlogArticleHistory get(Long id);

    int updateSelective(BlogArticleHistory record);
}