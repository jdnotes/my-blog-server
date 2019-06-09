package com.easy.blog.dao;


import com.easy.blog.model.BlogTag;

import java.util.List;

public interface BlogTagMapper {

    int insertSelective(BlogTag record);

    BlogTag get(Long id);

    int updateSelective(BlogTag record);

    List<BlogTag> getTagCloud();

    List<BlogTag> getSecondTags(String parentCode);
}