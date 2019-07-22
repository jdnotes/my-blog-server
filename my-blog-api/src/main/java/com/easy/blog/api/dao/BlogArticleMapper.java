package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogArticle;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogArticleMapper {

    int insertSelective(BlogArticle record);

    BlogArticle get(Long id);

    int updateSelective(BlogArticle record);

    BlogArticle getByCode(String code);

    BlogArticle getRecentByLevel(int level);

    BlogArticle getRecentByDate();

    List<BlogArticle> getListByIds(@Param("ids") List<Long> ids);
}