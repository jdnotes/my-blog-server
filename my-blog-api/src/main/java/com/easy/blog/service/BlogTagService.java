package com.easy.blog.service;

import com.easy.blog.model.BlogTagCloudVO;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
public interface BlogTagService {

    List<BlogTagCloudVO> getTagCloud();
}