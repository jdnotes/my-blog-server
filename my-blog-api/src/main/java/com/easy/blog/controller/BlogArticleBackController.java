package com.easy.blog.controller;

import com.easy.blog.constant.CodeMsgConstant;
import com.easy.blog.constant.Result;
import com.easy.blog.model.BlogArticleBack;
import com.easy.blog.model.BlogArticleDetailsVO;
import com.easy.blog.service.BlogArticleBackService;
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
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody BlogArticleBack param) {
        if (param == null) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            blogArticleBackService.add(param);
            result = Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }
}