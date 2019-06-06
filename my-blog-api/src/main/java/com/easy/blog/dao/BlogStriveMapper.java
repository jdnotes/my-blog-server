package com.easy.blog.dao;

import com.easy.blog.model.BlogStrive;

public interface BlogStriveMapper {

    int insertSelective(BlogStrive record);

    BlogStrive get(Long id);

    int updateSelective(BlogStrive record);

}