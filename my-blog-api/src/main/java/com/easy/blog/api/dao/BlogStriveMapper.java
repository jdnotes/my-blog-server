package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogStrive;

public interface BlogStriveMapper {

    int insertSelective(BlogStrive record);

    BlogStrive get(Long id);

    int updateSelective(BlogStrive record);

    BlogStrive getInfoByCode(String code);
}