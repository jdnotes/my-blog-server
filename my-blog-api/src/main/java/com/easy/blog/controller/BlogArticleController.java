package com.easy.blog.controller;

import com.easy.blog.constant.Result;
import com.easy.blog.model.BlogArticleListDTO;
import com.easy.blog.model.BlogArticleListVO;
import com.easy.blog.pager.Pager;
import com.easy.blog.service.BlogArticleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
     * 搜索列表
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Object search(BlogArticleListDTO param) {
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
}