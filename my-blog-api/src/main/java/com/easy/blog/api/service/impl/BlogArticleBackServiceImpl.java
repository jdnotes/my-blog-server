package com.easy.blog.api.service.impl;

import com.easy.blog.api.constant.GlobalConstant;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.dao.BlogArticleBackMapper;
import com.easy.blog.api.model.BlogArticleBack;
import com.easy.blog.api.model.BlogArticleBackDTO;
import com.easy.blog.api.model.BlogArticleBackEditorVO;
import com.easy.blog.api.model.BlogTheme;
import com.easy.blog.api.service.*;
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

import java.util.Arrays;
import java.util.Date;

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
    private BlogArticleImagesService blogArticleImagesService;

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
        if (StringUtils.isNotEmpty(param.getCode())
                && !param.getCode().matches("[a-z0-9]{10}")) {
            throw new IllegalArgumentException("blog article back code arg is illegal");
        }
        BlogArticleBack back = this.putBlogArticleParam(param);
        if (StringUtils.isEmpty(param.getCode())) {
            doAddArticleBack(back);
        } else {
            BlogArticleBack old = blogArticleBackMapper.getByCode(param.getCode());
            if (old != null) {
                doUpdateArticleBack(old, back);
            } else {
                doAddArticleBack(back);
            }
        }

        //通过发布口令发布博客
        if (GlobalConstant.PUBLISH_ARTICLE_WORD.equals(param.getPublishWord())) {
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

    private void doUpdateArticleBack(BlogArticleBack old, BlogArticleBack back) {
        back.setId(old.getId());
        back.setReadNum(old.getReadNum());
        back.setLikeNum(old.getLikeNum());
        if (StringUtils.isEmpty(old.getLogo())) {
            back.setLogo(this.getLogoValue(RandomUtils.getRandomNum(1, 50)));
            logger.info("update save logo:{}", back.getLogo());
        } else {
            back.setLogo(old.getLogo());
            logger.info("old save logo:{}", back.getLogo());
        }
        back.setCreateDate(old.getCreateDate());
        back.setUpdateDate(new Date());

        blogArticleBackMapper.updateSelective(back);
    }

    private void doAddArticleBack(BlogArticleBack back) {
        Long id = SnowflakeIdUtils.getSnowflakeId();
        back.setId(id);
        String code = RandomUtils.getRandomStr(10);
        back.setCode(code);
        back.setLogo(this.getLogoValue(RandomUtils.getRandomNum(1, 50)));
        logger.info("add save logo:{}", back.getLogo());
        back.setCreateDate(new Date());
        back.setUpdateDate(new Date());
        blogArticleBackMapper.insertSelective(back);
    }

    private String getLogoValue(int randomNum) {
        if (randomNum > 0) {
            String url = blogArticleImagesService.getUrlByCode(randomNum);
            return url;
        }
        return "";
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
                    vo.setTag(arr[0]);
                }
            }
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