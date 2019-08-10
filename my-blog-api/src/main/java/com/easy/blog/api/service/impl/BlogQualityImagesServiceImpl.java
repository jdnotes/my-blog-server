package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogQualityImagesMapper;
import com.easy.blog.api.service.BlogQualityImagesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouyong
 * @date 2019/8/10
 */
@Service
public class BlogQualityImagesServiceImpl implements BlogQualityImagesService {

    @Autowired
    private BlogQualityImagesMapper blogQualityImagesMapper;

    @Override
    public String getUrlByCode(int code) {
        if (code <= 0) {
            return "";
        }
        return blogQualityImagesMapper.getUrlByCode(code);
    }
}