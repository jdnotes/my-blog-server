package com.easy.blog.api.service.impl;

import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.dao.BlogStriveMapper;
import com.easy.blog.api.model.BlogStrive;
import com.easy.blog.api.service.BlogStriveService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.cache.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Service
public class BlogStriveServiceImpl implements BlogStriveService {

    private final Logger logger = LoggerFactory.getLogger(BlogStriveServiceImpl.class);

    @Autowired
    private BlogStriveMapper blogStriveMapper;

    @Autowired
    private CacheService cacheService;

    @Override
    public BlogStrive get(Long id) {
        return blogStriveMapper.get(id);
    }

    @Override
    public String getInfoByRandom() {
        int n = RandomUtils.getRandomNum(1, 30);
        logger.info("blog strive random num is {}", n);
        String key = RedisConstant.BLOG_ARTICLE_STRIVE_LIST;
        Object obj = cacheService.lGet(key, (n - 1));
        if (obj != null) {
            String content = (String) obj;
            return content;
        }
        long size = cacheService.lGetSize(key);
        if (size > 0) {
            BlogStrive strive = blogStriveMapper.getInfoByCode(String.valueOf(n));
            if (strive != null) {
                return strive.getContent();
            }
        }
        List<BlogStrive> strives = blogStriveMapper.getAllByLimit(30);
        if (strives != null && strives.size() > 0) {
            List<String> list = new ArrayList<>();
            for (BlogStrive strive : strives) {
                list.add(strive.getContent());
            }
            cacheService.lSet(key, list, 604800L);
        }
        return "";
    }
}