package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogTag;

import java.util.List;

public interface BlogTagMapper {

    int insertSelective(BlogTag record);

    BlogTag get(Long id);

    int updateSelective(BlogTag record);

    List<BlogTag> getTagCloud();

    List<BlogTag> getSecondTags(String parentCode);
}