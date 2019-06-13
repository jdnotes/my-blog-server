package com.easy.blog.dao;

import com.easy.blog.model.BlogArticleHistory;

public interface BlogArticleHistoryMapper {

    int insertSelective(BlogArticleHistory record);

    BlogArticleHistory get(Long id);

    int updateSelective(BlogArticleHistory record);
}