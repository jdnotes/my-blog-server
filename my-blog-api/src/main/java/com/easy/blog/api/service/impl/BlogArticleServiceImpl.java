package com.easy.blog.api.service.impl;

import com.easy.blog.api.constant.GlobalConstant;
import com.easy.blog.api.dao.BlogArticleMapper;
import com.easy.blog.api.model.*;
import com.easy.blog.api.pager.Pager;
import com.easy.blog.api.service.BlogArticleBackService;
import com.easy.blog.api.service.BlogArticleHistoryService;
import com.easy.blog.api.service.BlogArticleService;
import com.easy.blog.api.service.BlogStriveService;
import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
import com.easy.blog.es.service.BlogArticleSearchService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
@Service
public class BlogArticleServiceImpl implements BlogArticleService {

    @Autowired
    private BlogArticleSearchService blogArticleSearchService;

    @Autowired
    private BlogStriveService blogStriveService;

    @Autowired
    private BlogArticleBackService blogArticleBackService;

    @Autowired
    private BlogArticleHistoryService blogArticleHistoryService;

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
    public List<BlogArticleRecommendVO> recommendList() {
        //缓存处理:一天
        // TODO: 2019/6/9

        List<BlogArticleEs> list = blogArticleSearchService.recommendList(5);
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleRecommendVO> voList = putBlogArticleRecommendValue(list);
        return voList;
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
        String articleTypeText = putArticleTypeText(articleEs.getArticleType());
        vo.setArticleTypeText(articleTypeText);
        List<BlogTagCloudVO> tags = putTagsValue(articleEs.getTags(), articleEs.getTagsName());
        vo.setTags(tags);
        String mind = blogStriveService.getInfoByRandom();
        vo.setMind(mind);
        return vo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void publish(BlogArticlePublishDTO param) {
        BlogArticleBack articleBack = blogArticleBackService.get(param.getArticleId());
        if (articleBack == null) {
            throw new RuntimeException("article back is not exist");
        }
        BlogArticle old = blogArticleMapper.get(articleBack.getId());
        BlogArticle article;
        if (old == null) {
            article = new BlogArticle();
            BeanUtils.copyProperties(articleBack, article);
            blogArticleMapper.insertSelective(article);
        } else {
            BlogArticleHistory history = new BlogArticleHistory();
            BeanUtils.copyProperties(old, history);
            blogArticleHistoryService.add(history);

            article = new BlogArticle();
            BeanUtils.copyProperties(articleBack, article);
            blogArticleMapper.updateSelective(article);
        }

        BlogArticleEs es = new BlogArticleEs();
        BeanUtils.copyProperties(article, es);
        if (article.getAuthorId() == null) {
            es.setAuthor(GlobalConstant.ARTICLE_AUTHOR);
        } else {
            //查询DB
            es.setAuthor(GlobalConstant.ARTICLE_AUTHOR);
        }
        blogArticleSearchService.add(es);
    }

    @Override
    public void remove(BlogArticlePublishDTO param) {

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

    private List<BlogArticleRecommendVO> putBlogArticleRecommendValue(List<BlogArticleEs> list) {
        if (list == null || list.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogArticleRecommendVO> voList = new ArrayList<>(list.size());
        for (BlogArticleEs es : list) {
            BlogArticleRecommendVO vo = new BlogArticleRecommendVO();
            vo.setId(es.getCode());
            vo.setTitle(es.getTitle());
            voList.add(vo);
        }
        return voList;
    }
}