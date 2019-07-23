package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogThemeMapper;
import com.easy.blog.api.model.BlogTheme;
import com.easy.blog.api.model.BlogThemeDTO;
import com.easy.blog.api.service.BlogThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/7/22
 */
@Transactional
@Service
public class BlogThemeServiceImpl implements BlogThemeService {

    @Autowired
    private BlogThemeMapper blogThemeMapper;

    @Override
    public BlogTheme getByQuality() {
        return blogThemeMapper.getByQuality();
    }

    @Override
    public List<Long> getArticleIdsByQuality(BlogThemeDTO param, int size) {
        return blogThemeMapper.getArticleIdsByQuality(param.getQuality(), param.getHot(), size);
    }

    @Override
    public void save(BlogTheme theme) {
        blogThemeMapper.save(theme);
    }
}