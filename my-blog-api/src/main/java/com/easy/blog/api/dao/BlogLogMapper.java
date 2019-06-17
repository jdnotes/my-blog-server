package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogLog;

public interface BlogLogMapper {

    int insertSelective(BlogLog record);

    BlogLog get(Long id);

    int updateSelective(BlogLog record);

}