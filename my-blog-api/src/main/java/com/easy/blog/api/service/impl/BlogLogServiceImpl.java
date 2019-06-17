package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogLogMapper;
import com.easy.blog.api.model.BlogLog;
import com.easy.blog.api.service.BlogLogService;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhouyong
 * @date 2019/6/17
 */
@Service
public class BlogLogServiceImpl implements BlogLogService {

    @Autowired
    private BlogLogMapper blogLogMapper;


    @Override
    public void add(BlogLog log) {
        if (log == null) {
            throw new RuntimeException("log add is null");
        }
        log.setId(SnowflakeIdUtils.getSnowflakeId());
        log.setCreateDate(new Date());
        blogLogMapper.insertSelective(log);
    }
}