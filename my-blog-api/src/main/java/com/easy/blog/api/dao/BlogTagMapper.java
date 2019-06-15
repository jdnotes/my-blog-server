package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogTag;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BlogTagMapper {

    int insertSelective(BlogTag record);

    BlogTag get(Long id);

    int updateSelective(BlogTag record);

    List<BlogTag> getTagCloud();

    List<BlogTag> getSecondTags(String parentCode);

    List<BlogTag> getTagByCodes(@Param("tags") List<String> tags);

    BlogTag getTagByCode(String tag);
}