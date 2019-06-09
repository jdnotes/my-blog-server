package com.easy.blog.controller;

import com.easy.blog.constant.Result;
import com.easy.blog.model.BlogTagCloudVO;
import com.easy.blog.service.BlogTagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@RestController
@RequestMapping("/tag")
public class BlogTagController {

    private final Logger logger = LoggerFactory.getLogger(BlogTagController.class);

    @Autowired
    private BlogTagService blogTagService;

    @RequestMapping(value = "/cloud", method = RequestMethod.POST)
    public Object tagCloud() {
        Result result;
        try {
            List<BlogTagCloudVO> tags = blogTagService.getTagCloud();
            result = Result.success(tags);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }

        return result;
    }
}