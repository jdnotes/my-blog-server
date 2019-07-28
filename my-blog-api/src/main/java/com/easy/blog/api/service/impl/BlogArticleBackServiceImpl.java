package com.easy.blog.api.service.impl;

import com.easy.blog.api.constant.GlobalConstant;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.dao.BlogArticleBackMapper;
import com.easy.blog.api.model.*;
import com.easy.blog.api.service.BlogArticleBackService;
import com.easy.blog.api.service.BlogArticleService;
import com.easy.blog.api.service.BlogTagService;
import com.easy.blog.api.service.BlogThemeService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.api.utils.SnowflakeIdUtils;
import com.easy.blog.api.utils.SubStringHTMLUtils;
import com.easy.blog.cache.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.HtmlUtils;

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
    private BlogThemeService blogThemeService;

    @Autowired
    private CacheService cacheService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(BlogArticleBack param) {
        if (param == null) {
            throw new RuntimeException("blog article back param is null");
        }
        param.setId(SnowflakeIdUtils.getSnowflakeId());
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

        if (StringUtils.isNotBlank(param.getUsername()) && StringUtils.isNotEmpty(param.getPassword())) {
            //通过口令发布博客
            if (GlobalConstant.ARTICLE_USERNAME.equals(param.getUsername())
                    && GlobalConstant.ARTICLE_PASSWORD.equals(param.getPassword())) {

                if (param.getHot() != null || param.getQuality() != null) {
                    BlogTheme theme = new BlogTheme();
                    theme.setId(SnowflakeIdUtils.getSnowflakeId());
                    theme.setArticleId(back.getId());
                    theme.setHot(param.getHot());
                    theme.setQuality(param.getQuality());
                    theme.setCreateDate(new Date());
                    theme.setUpdateDate(new Date());
                    blogThemeService.save(theme);
                }

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
        if (back.getLevel() == null) {
            back.setLevel(NumberUtils.toByte("1"));
        }
        if (StringUtils.isNotEmpty(param.getArticleHtml())) {
            back.setArticleSection(SubStringHTMLUtils.subTextHtml(param.getArticleHtml(), 150, "..."));
        }
        back.setReadNum(0);
        back.setLikeNum(0);
        return back;
    }
}