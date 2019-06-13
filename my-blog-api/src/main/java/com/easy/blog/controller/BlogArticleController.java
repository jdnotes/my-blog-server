package com.easy.blog.controller;

import com.easy.blog.constant.CodeMsgConstant;
import com.easy.blog.constant.Result;
import com.easy.blog.model.*;
import com.easy.blog.pager.Pager;
import com.easy.blog.service.BlogArticleService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

    /**
     * 发布博文
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/publish", method = RequestMethod.POST)
    public Object publish(@RequestBody BlogArticlePublishDTO param) {
        if (param == null || param.getArticleId() == null) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            blogArticleService.publish(param);
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
     * 最新推荐列表
     *
     * @return
     */
    @RequestMapping(value = "/recommends", method = RequestMethod.POST)
    public Object recommendList() {
        Result result;
        try {
            List<BlogArticleRecommendVO> datas = blogArticleService.recommendList();
            result = Result.success(datas);
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