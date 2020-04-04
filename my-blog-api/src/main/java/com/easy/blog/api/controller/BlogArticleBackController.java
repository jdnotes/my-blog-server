package com.easy.blog.api.controller;

import com.easy.blog.api.constant.CodeMsgConstant;
import com.easy.blog.api.constant.Result;
import com.easy.blog.api.filter.RateLimit;
import com.easy.blog.api.model.BlogArticle;
import com.easy.blog.api.model.BlogArticleBack;
import com.easy.blog.api.model.BlogArticleBackDTO;
import com.easy.blog.api.model.BlogArticleBackEditorVO;
import com.easy.blog.api.service.BlogArticleBackService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@RestController
@RequestMapping("/articleBack")
public class BlogArticleBackController {

    private final Logger logger = LoggerFactory.getLogger(BlogArticleBackController.class);

    @Autowired
    private BlogArticleBackService blogArticleBackService;

    /**
     * 博文草稿
     *
     * @return
     */
    @RateLimit(limitNum = 2)
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object add(@RequestBody BlogArticleBackDTO param) {
        if (param == null) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            blogArticleBackService.save(param);
            result = Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 博文草稿详情
     *
     * @return
     */
    @RateLimit(limitNum = 10)
    @RequestMapping(value = "/getByCode", method = RequestMethod.POST)
    public Object getByCode(@RequestBody BlogArticleBackDTO param) {
        if (param == null || StringUtils.isEmpty(param.getCode())) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            BlogArticleBackEditorVO back = blogArticleBackService.getByCode(param);
            result = Result.success(back);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }
}