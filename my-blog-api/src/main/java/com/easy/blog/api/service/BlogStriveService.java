package com.easy.blog.api.service;

import com.easy.blog.api.model.BlogStrive;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
public interface BlogStriveService {

    BlogStrive get(Long id);

    String getInfoByRandom();
}