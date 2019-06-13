package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogTagMapper;
import com.easy.blog.model.BlogTag;
import com.easy.blog.model.BlogTagCloudVO;
import com.easy.blog.service.BlogTagService;
import com.easy.blog.utils.RandomUtils;
import com.easy.blog.utils.SnowflakeIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.*;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public List<BlogTagCloudVO> getTagCloud() {
        //cache todo

        List<BlogTag> tags = blogTagMapper.getTagCloud();
        if (tags == null || tags.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogTagCloudVO> voList = new ArrayList<>();
        for (BlogTag tag : tags) {
            BlogTagCloudVO vo = new BlogTagCloudVO();
            vo.setId(String.valueOf(tag.getId()));
            vo.setCode(tag.getCode());
            vo.setTagName(tag.getAlias());
            vo.setNum(1);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<BlogTagCloudVO> getSecondTags(String parentCode) {
        if (StringUtils.isEmpty(parentCode)) {
            return null;
        }
        List<BlogTag> tags = blogTagMapper.getSecondTags(parentCode);
        if (tags == null || tags.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogTagCloudVO> voList = new ArrayList<>();
        for (BlogTag tag : tags) {
            BlogTagCloudVO vo = new BlogTagCloudVO();
            vo.setId(String.valueOf(tag.getId()));
            vo.setCode(tag.getCode());
            vo.setTagName(tag.getAlias());
            vo.setNum(1);
            voList.add(vo);
        }
        return voList;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BlogTag param) {
        if (param == null) {
            throw new RuntimeException("tag param is null");
        }
        param.setId(SnowflakeIdUtils.getSnowflakeId());
        param.setParentId(1L);
        param.setCode(RandomUtils.getRandomStr(10));
        param.setSort(10);
        Date date = new Date();
        param.setCreateDate(date);
        param.setUpdateDate(date);
        blogTagMapper.insertSelective(param);
    }
}