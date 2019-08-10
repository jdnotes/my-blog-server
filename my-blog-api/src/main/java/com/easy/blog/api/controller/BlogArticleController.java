package com.easy.blog.api.controller;

import com.easy.blog.api.constant.CodeMsgConstant;
import com.easy.blog.api.constant.RedisConstant;
import com.easy.blog.api.constant.Result;
import com.easy.blog.api.model.*;
import com.easy.blog.api.pager.Pager;
import com.easy.blog.api.service.BlogArticleService;
import com.easy.blog.api.utils.RandomUtils;
import com.easy.blog.cache.service.CacheService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@RestController
@RequestMapping("/article")
public class BlogArticleController {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleController.class);

    @Autowired
    private BlogArticleService blogArticleService;

    @Autowired
    private CacheService cacheService;

    /**
     * top文章
     *
     * @return
     */
    @RequestMapping(value = "/getQuality", method = RequestMethod.POST)
    public Object getQuality(HttpServletRequest request) {
        Result result;
        try {
            BlogArticleSuccinctVO vo = blogArticleService.getQuality();
            result = Result.success(vo);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 最新文章列表
     *
     * @return
     */
    @RequestMapping(value = "/getRecentList", method = RequestMethod.POST)
    public Object getRecentList(HttpServletRequest request) {
        Result result;
        try {
            List<BlogArticleSuccinctVO> voList = blogArticleService.getRecentList();
            result = Result.success(voList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 优质文章列表
     *
     * @return
     */
    @RequestMapping(value = "/getQualityList", method = RequestMethod.POST)
    public Object getQualityList(HttpServletRequest request) {
        Result result;
        try {
            BlogThemeDTO param = new BlogThemeDTO();
            param.setQuality((byte) 1);
            List<BlogArticleSuccinctVO> voList = blogArticleService.getThemeList(param);
            result = Result.success(voList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 优质文章列表
     *
     * @return
     */
    @RequestMapping(value = "/getHotList", method = RequestMethod.POST)
    public Object getHotList(HttpServletRequest request) {
        Result result;
        try {
            BlogThemeDTO param = new BlogThemeDTO();
            param.setHot((byte) 1);
            List<BlogArticleSuccinctVO> voList = blogArticleService.getThemeList(param);
            result = Result.success(voList);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 下架博文
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/remove", method = RequestMethod.POST)
    public Object remove(@RequestBody BlogArticlePublishDTO param, HttpServletRequest request) {
        if (param == null || StringUtils.isEmpty(param.getCode())) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        String word = request.getHeader("word");
        if (StringUtils.isEmpty(word)) {
            return Result.error(CodeMsgConstant.SESSION_ERROR);
        }
        String key = RedisConstant.BLOG_ARTICLE_WORD;
        String value = cacheService.get(key);
        if (StringUtils.isEmpty(value)) {
            String random = RandomUtils.getRandomStr(4);
            value = random;
            cacheService.set(key, random, 604800);
        }
        if (!value.equals(word)) {
            return Result.error(CodeMsgConstant.SESSION_ERROR);
        }
        Result result;
        try {
            blogArticleService.remove(param);
            result = Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 搜索列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(@RequestBody BlogArticleListDTO param) {
        Result result;
        try {
            Pager<BlogArticleListVO> pager = blogArticleService.search(param);
            result = Result.success(pager);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 博文详情
     *
     * @return
     */
    @RequestMapping(value = "/details", method = RequestMethod.POST)
    public Object details(@RequestBody BlogArticleDetailsDTO param) {
        if (param == null || StringUtils.isEmpty(param.getId())) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            BlogArticleDetailsVO details = blogArticleService.getDetails(param.getId());
            result = Result.success(details);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }
}