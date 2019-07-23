package com.easy.blog.api.service;

import com.easy.blog.api.model.BlogTheme;
import com.easy.blog.api.model.BlogThemeDTO;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/7/22
 */
public interface BlogThemeService {

    BlogTheme getByQuality();

    List<Long> getArticleIdsByQuality(BlogThemeDTO param, int size);

    void save(BlogTheme theme);
}