package com.easy.blog.api.dao;

import com.easy.blog.api.model.BlogQualityImages;

public interface BlogQualityImagesMapper {

    int delete(Long id);

    int insert(BlogQualityImages record);

    int insertSelective(BlogQualityImages record);

    BlogQualityImages get(Long id);

    int updateSelective(BlogQualityImages record);

    int update(BlogQualityImages record);

    String getUrlByCode(int code);
}