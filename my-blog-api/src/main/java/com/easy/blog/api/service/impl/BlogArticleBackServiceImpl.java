package com.easy.blog.api.service.impl;

import com.easy.blog.api.constant.GlobalConstant;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.dao.BlogArticleBackMapper;
import com.easy.blog.api.model.*;
import com.easy.blog.api.service.BlogArticleBackService;
import com.easy.blog.api.service.BlogArticleService;
import com.easy.blog.api.service.BlogTagService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import com.easy.blog.cache.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Transactional
@Service
public class BlogArticleBackServiceImpl implements BlogArticleBackService {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleBackServiceImpl.class);

    @Autowired
    private BlogArticleBackMapper blogArticleBackMapper;

    @Autowired
    private BlogTagService blogTagService;

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private CacheService cacheService;

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

    @Override
    public void save(BlogArticleBackDTO param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        BlogArticleBack back = this.putBlogArticleParam(param);
        if (StringUtils.isEmpty(param.getCode())) {
            Long id = SnowflakeIdUtils.getSnowflakeId();
            back.setId(id);
            back.setCode(RandomUtils.getRandomStr(10));
            back.setCreateDate(new Date());
            back.setUpdateDate(new Date());
            blogArticleBackMapper.insertSelective(back);
        } else {
            BlogArticleBack old = blogArticleBackMapper.getByCode(param.getCode());
            if (old == null) {
                throw new RuntimeException("blog article back is not exist!");
            }
            back.setId(old.getId());
            back.setReadNum(old.getReadNum());
            back.setLikeNum(old.getLikeNum());
            back.setCreateDate(old.getCreateDate());
            back.setUpdateDate(new Date());
            blogArticleBackMapper.updateSelective(back);
        }

        if (StringUtils.isNotBlank(param.getWord())) {
            //通过口令发布博客
            String key = RedisConstant.BLOG_ARTICLE_WORD;
            String value = cacheService.get(key);
            if (StringUtils.isEmpty(value)) {
                value = GlobalConstant.ARTICLE_WORD;
                String random = RandomUtils.getRandomStr(4);
                cacheService.set(key, random, 604800);
            }
            if (value.equals(param.getWord())) {
                blogArticleService.publish(back);
            }
        }
    }

    @Override
    public BlogArticleBackEditorVO getByCode(BlogArticleBackDTO param) {
        BlogArticleBack back = this.getByCode(param.getCode());
        BlogArticleBackEditorVO vo = null;
        if (back != null) {
            vo = new BlogArticleBackEditorVO();
            BeanUtils.copyProperties(back, vo);
            if (StringUtils.isNotEmpty(back.getTags())) {
                String[] arr = back.getTags().split(",");
                if (arr != null && arr.length > 0) {
                    vo.setTags(Arrays.asList(arr));
                }
            }
            if (back.getTagId() != null) {
                BlogTag tag = blogTagService.get(back.getTagId());
                if (tag != null) {
                    vo.setTag(tag.getCode());
                }
            }
            //md,html加密处理 todo
        }
        return vo;
    }

    private BlogArticleBack getByCode(String code) {
        return blogArticleBackMapper.getByCode(code);
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
                    tags.append(tag.getCode()).append(",");
                    tagsName.append(tag.getAlias()).append(",");
                }
            }
        }
        if (tags.length() > 1) {
            back.setTags(tags.toString().substring(0, tags.length() - 1));
        }
        if (tagsName.length() > 1) {
            back.setTagsName(tagsName.toString().substring(0, tagsName.length() - 1));
        }
    }
}