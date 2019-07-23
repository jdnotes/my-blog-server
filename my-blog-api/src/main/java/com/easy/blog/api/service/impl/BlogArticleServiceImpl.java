package com.easy.blog.api.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.easy.blog.api.constant.GlobalConstant;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.dao.BlogArticleMapper;
import com.easy.blog.api.model.*;
import com.easy.blog.api.pager.Pager;
import com.easy.blog.api.service.*;
import com.easy.blog.cache.service.CacheService;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
import com.easy.blog.es.service.BlogArticleSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
@Transactional
@Service
public class BlogArticleServiceImpl implements BlogArticleService {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleServiceImpl.class);

    @Autowired
    private BlogArticleSearchService blogArticleSearchService;

    @Autowired
    private BlogStriveService blogStriveService;

    @Autowired
    private BlogArticleBackService blogArticleBackService;

    @Autowired
    private BlogArticleHistoryService blogArticleHistoryService;

    @Autowired
    private BlogThemeService blogThemeService;

    @Autowired
    private CacheService cacheService;

    @Autowired
    private BlogArticleMapper blogArticleMapper;

    @Override
    public Pager<BlogArticleListVO> search(BlogArticleListDTO param) {
        BlogArticleEsDTO dto = new BlogArticleEsDTO();
        if (param != null) {
            BeanUtils.copyProperties(param, dto);
        } else {
            param = new BlogArticleListDTO();
            param.setCurrentPage(1);
            param.setPageRows(10);
        }
        Page<BlogArticleEs> page = blogArticleSearchService.search(dto);
        Pager<BlogArticleListVO> pager;
        if (page != null) {
            pager = new Pager<>(param.getCurrentPage(), NumberUtils.toInt(page.getTotalElements() + ""));
            List<BlogArticleListVO> voList = putBlogArticleListValue(page.getContent());
            pager.setRecords(voList);
        } else {
            pager = new Pager<>(param.getCurrentPage(), 0);
            pager.setRecords(new ArrayList<>());
        }
        return pager;
    }

    private List<BlogArticleListVO> putBlogArticleListValue(List<BlogArticleEs> content) {
        if (content == null || content.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleListVO> voList = new ArrayList<>(content.size());
        for (BlogArticleEs es : content) {
            BlogArticleListVO vo = new BlogArticleListVO();
            BeanUtils.copyProperties(es, vo);
            vo.setId(es.getCode());
            voList.add(vo);
        }
        return voList;
    }

    private String putArticleTypeText(Integer articleType) {
        //文章类型(1-原创,2-整理,3-转载)
        if (articleType == null) {
            return GlobalConstant.ARTICLE_TYPE_TIDY;
        }
        String text;
        switch (articleType) {
            case 1:
                text = GlobalConstant.ARTICLE_TYPE_ORIGINAL;
                break;
            case 2:
                text = GlobalConstant.ARTICLE_TYPE_TIDY;
                break;
            case 3:
                text = GlobalConstant.ARTICLE_TYPE_REPRINTED;
                break;
            default:
                text = GlobalConstant.ARTICLE_TYPE_TIDY;
        }
        return text;
    }

    @Override
    public BlogArticleDetailsVO getDetails(String code) {
        BlogArticleEs articleEs = blogArticleSearchService.getInfoByCode(code);
        if (articleEs == null) {
            return null;
        }
        BlogArticleDetailsVO vo = new BlogArticleDetailsVO();
        BeanUtils.copyProperties(articleEs, vo);
        vo.setId(articleEs.getCode());
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(BlogArticlePublishDTO param) {
        BlogArticleBack articleBack = blogArticleBackService.get(param.getArticleId());
        if (articleBack == null) {
            throw new RuntimeException("article back is not exist");
        }
        this.publish(articleBack);
    }

    @Override
    public void publish(BlogArticleBack articleBack) {
        if (articleBack == null) {
            throw new RuntimeException("article back is not exist");
        }
        BlogArticle old = blogArticleMapper.get(articleBack.getId());
        BlogArticle article;
        if (old == null) {
            article = new BlogArticle();
            BeanUtils.copyProperties(articleBack, article);
            article.setStatus(NumberUtils.toByte("1"));
            blogArticleMapper.insertSelective(article);
        } else {
            article = new BlogArticle();
            BeanUtils.copyProperties(articleBack, article);
            article.setStatus(NumberUtils.toByte("1"));
            blogArticleMapper.updateSelective(article);
        }

        BlogArticleHistory history = new BlogArticleHistory();
        BeanUtils.copyProperties(article, history);
        blogArticleHistoryService.add(history);

        BlogArticleEs es = new BlogArticleEs();
        BeanUtils.copyProperties(article, es);
        es.setSubtitle(article.getTitle());
        es.setArticleType(NumberUtils.toInt(article.getArticleType() + ""));
        es.setLevel(NumberUtils.toInt(article.getLevel() + ""));
        es.setStatus(NumberUtils.toInt(article.getStatus() + ""));
        blogArticleSearchService.add(es);

        Set<String> set = new HashSet<>();
        set.add(RedisConstant.BLOG_ARTICLE_RECENT_LIST);
        set.add(RedisConstant.BLOG_QUALITY_ARTICLE);
        cacheService.del(set);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void remove(BlogArticlePublishDTO param) {
        if (param == null || StringUtils.isEmpty(param.getCode())) {
            throw new RuntimeException("Article remove param is null");
        }
        BlogArticle old = blogArticleMapper.getByCode(param.getCode());
        if (old == null) {
            throw new RuntimeException("Article remove data is null");
        }
        BlogArticle article = new BlogArticle();
        article.setId(old.getId());
        article.setStatus(NumberUtils.toByte("2"));
        article.setUpdateDate(new Date());
        blogArticleMapper.updateSelective(article);

        BlogArticleHistory history = new BlogArticleHistory();
        BeanUtils.copyProperties(old, history);
        blogArticleHistoryService.add(history);

        blogArticleSearchService.delete(old.getId());

    }

    private List<BlogTagCloudVO> putTagsValue(String tags, String tagsName) {
        List<BlogTagCloudVO> tagList = new ArrayList<>();
        if (StringUtils.isNotEmpty(tags) && StringUtils.isNotEmpty(tagsName)) {
            String[] tagArr = tags.split(",");
            String[] tagNameArr = tagsName.split(",");
            if (tagArr != null && tagNameArr != null && tagArr.length == tagNameArr.length) {
                for (int i = 0; i < tagArr.length; i++) {
                    BlogTagCloudVO vo = new BlogTagCloudVO();
                    vo.setCode(tagArr[i]);
                    vo.setTagName(tagNameArr[i]);
                    vo.setNum(0);
                    tagList.add(vo);
                }
            }
        }
        return tagList;
    }

    private List<BlogArticleSuccinctVO> putBlogArticleSuccinctValue(List<BlogArticleEs> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleSuccinctVO> voList = new ArrayList<>(list.size());
        for (BlogArticleEs es : list) {
            BlogArticleSuccinctVO vo = new BlogArticleSuccinctVO();
            vo.setId(es.getCode());
            vo.setTitle(es.getTitle());
            vo.setDate(es.getUpdateDate());
            voList.add(vo);
        }
        return voList;
    }

    @Override
    public List<BlogArticleSuccinctVO> getRecentList() {
        String key = RedisConstant.BLOG_ARTICLE_RECENT_LIST;
        String json = cacheService.get(key);
        if (StringUtils.isNotEmpty(json)) {
            List<BlogArticleSuccinctVO> voList = JSON.parseObject(json, new TypeReference<List<BlogArticleSuccinctVO>>() {
            });
            return voList;
        }
        List<BlogArticleEs> list = blogArticleSearchService.getRecentList(5);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleSuccinctVO> voList = putBlogArticleSuccinctValue(list);
        if (voList != null && voList.size() > 0) {
            cacheService.set(key, JSON.toJSONString(voList), 43200);
        }
        return voList;
    }

    @Override
    public List<BlogArticleSuccinctVO> getThemeList(BlogThemeDTO param) {
        if (param == null || (param.getHot() == null && param.getQuality() == null)) {
            return new ArrayList<>();
        }
        String key = "";
        if (param.getQuality() != null) {
            key = RedisConstant.BLOG_ARTICLE_QUALITY_LIST;
        } else if (param.getHot() != null) {
            key = RedisConstant.BLOG_ARTICLE_HOT_LIST;
        }
        String json = cacheService.get(key);
        if (StringUtils.isNotEmpty(json)) {
            List<BlogArticleSuccinctVO> voList = JSON.parseObject(json, new TypeReference<List<BlogArticleSuccinctVO>>() {
            });
            return voList;
        }
        List<Long> ids = blogThemeService.getArticleIdsByQuality(param, 5);
        if (ids == null || ids.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleSuccinctVO> voList = new ArrayList<>();
        List<BlogArticle> articles = blogArticleMapper.getListByIds(ids);
        if (articles != null && articles.size() > 0) {
            for (BlogArticle article : articles) {
                BlogArticleSuccinctVO vo = new BlogArticleSuccinctVO();
                vo.setId(article.getCode());
                vo.setTitle(article.getTitle());
                vo.setDate(article.getUpdateDate());
                voList.add(vo);
            }
            cacheService.set(key, JSON.toJSONString(voList), 43200);
        }
        return voList;
    }

    @Override
    public BlogArticleSuccinctVO getQuality() {
        String key = RedisConstant.BLOG_QUALITY_ARTICLE;
        String json = cacheService.get(key);
        BlogArticleSuccinctVO vo = null;
        if (StringUtils.isNotEmpty(json)) {
            vo = JSON.parseObject(json, BlogArticleSuccinctVO.class);
            return vo;
        }
        BlogTheme theme = blogThemeService.getByQuality();
        if (theme != null) {
            BlogArticle article = blogArticleMapper.get(theme.getArticleId());
            if (article != null) {
                vo = new BlogArticleSuccinctVO();
                vo.setId(article.getCode());
                vo.setTitle(article.getTitle());
                vo.setDate(article.getUpdateDate());
                cacheService.set(key, JSON.toJSONString(vo), 3600);
            }
        }
        return vo;
    }
}