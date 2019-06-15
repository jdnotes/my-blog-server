package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogTagMapper;
import com.easy.blog.api.model.BlogTag;
import com.easy.blog.api.model.BlogTagCloudVO;
import com.easy.blog.api.service.BlogTagService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
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

    @Override
    public BlogTag getTagByCode(String tag) {
        if (StringUtils.isEmpty(tag)) {
            return null;
        }
        return blogTagMapper.getTagByCode(tag);
    }

    @Override
    public List<BlogTag> getTagByCodes(List<String> tags) {
        if (tags == null || tags.size() == 0) {
            return null;
        }
        return blogTagMapper.getTagByCodes(tags);
    }

    @Override
    public List<BlogTagCloudVO> getTags() {
        //cache todo

        List<BlogTag> tags = blogTagMapper.getTagsByLimit(8);
        if (tags == null || tags.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogTagCloudVO> voList = new ArrayList<>();
        for (BlogTag tag : tags) {
            BlogTagCloudVO vo = new BlogTagCloudVO();
            vo.setId(String.valueOf(tag.getId()));
            vo.setCode(tag.getCode());
            vo.setTagName(tag.getAlias());
            vo.setNum(0);
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public BlogTag get(Long id) {
        return blogTagMapper.get(id);
    }
}