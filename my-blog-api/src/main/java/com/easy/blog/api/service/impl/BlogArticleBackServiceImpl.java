package com.easy.blog.api.service.impl;

import com.easy.blog.api.dao.BlogArticleBackMapper;
import com.easy.blog.api.model.BlogArticleBack;
import com.easy.blog.api.model.BlogArticleBackDTO;
import com.easy.blog.api.model.BlogTag;
import com.easy.blog.api.service.BlogArticleBackService;
import com.easy.blog.api.service.BlogTagService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Service
public class BlogArticleBackServiceImpl implements BlogArticleBackService {

    @Autowired
    private BlogArticleBackMapper blogArticleBackMapper;

    @Autowired
    private BlogTagService blogTagService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BlogArticleBack param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        param.setId(SnowflakeIdUtils.getSnowflakeId());
        if (param.getAuthorId() == null) {
            //默认站长
            param.setAuthorId(1L);
        }
        param.setCode(RandomUtils.getRandomStr(10));
        param.setReadNum(0);
        param.setLikeNum(0);
        param.setCreateDate(new Date());
        param.setUpdateDate(new Date());
        blogArticleBackMapper.insertSelective(param);
    }

    @Override
    public BlogArticleBack get(Long id) {
        return blogArticleBackMapper.get(id);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void save(BlogArticleBackDTO param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        if (param.getId() != null && param.getId() > 0) {
            BlogArticleBack old = this.get(param.getId());
            if (old != null) {
                BlogArticleBack back = this.putBlogArticleParam(param);
                back.setId(old.getId());
                back.setUpdateDate(new Date());
                blogArticleBackMapper.updateSelective(back);
            } else {
                BlogArticleBack back = this.putBlogArticleParam(param);
                Long id = SnowflakeIdUtils.getSnowflakeId();
                back.setId(id);
                back.setCreateDate(new Date());
                back.setUpdateDate(new Date());
                blogArticleBackMapper.insertSelective(back);
            }
        } else {
            BlogArticleBack back = this.putBlogArticleParam(param);
            Long id = SnowflakeIdUtils.getSnowflakeId();
            back.setId(id);
            back.setCreateDate(new Date());
            back.setUpdateDate(new Date());
            blogArticleBackMapper.insertSelective(back);
        }
    }

    private BlogArticleBack putBlogArticleParam(BlogArticleBackDTO param) {
        BlogArticleBack back = new BlogArticleBack();
        BeanUtils.copyProperties(param, back);
        if (back.getAuthorId() == null) {
            //默认站长
            back.setAuthorId(1L);
        }
        if (back.getLevel() == null) {
            back.setLevel(NumberUtils.toByte("1"));
        }
        if (StringUtils.isNotEmpty(param.getTag())) {
            BlogTag tag = blogTagService.getTagByCode(param.getTag());
            if (tag != null) {
                back.setTagId(tag.getId());
            }
        }
        this.getTagsName(param.getTags(), back);
        back.setCode(RandomUtils.getRandomStr(10));
        back.setReadNum(0);
        back.setLikeNum(0);
        return back;
    }

    private void getTagsName(List<String> tagCodes, BlogArticleBack back) {
        StringBuilder tags = new StringBuilder();
        StringBuilder tagsName = new StringBuilder();
        if (tagCodes != null && tagCodes.size() > 0) {
            List<BlogTag> tagDatas = blogTagService.getTagByCodes(tagCodes);
            if (tagDatas != null && tagDatas.size() > 0) {
                for (BlogTag tag : tagDatas) {
                    tags.append(tag.getCode());
                    tagsName.append(tag.getAlias());
                }
            }
        }
        back.setTags(tags.toString());
        back.setTagsName(tagsName.toString());
    }
}