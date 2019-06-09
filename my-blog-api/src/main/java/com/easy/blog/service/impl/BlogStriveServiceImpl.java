package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogStriveMapper;
import com.easy.blog.model.BlogStrive;
import com.easy.blog.service.BlogStriveService;
import com.easy.blog.utils.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Service
public class BlogStriveServiceImpl implements BlogStriveService {

    private final Logger logger = LoggerFactory.getLogger(BlogStriveServiceImpl.class);

    @Autowired
    private BlogStriveMapper blogStriveMapper;

    @Override
    public BlogStrive get(Long id) {
        return blogStriveMapper.get(id);
    }

    @Override
    public String getInfoByRandom() {
        //cache // TODO: 2019/6/9

        int n = RandomUtils.getRandomNum(1, 2);
        logger.info("blog strive random num is {}", n);
        BlogStrive strive = blogStriveMapper.getInfoByCode(String.valueOf(n));
        if (strive != null) {
            return strive.getContent();
        }
        return "";
    }
}