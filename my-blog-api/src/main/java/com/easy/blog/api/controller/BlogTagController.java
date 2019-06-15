package com.easy.blog.api.controller;

import com.easy.blog.api.constant.CodeMsgConstant;
import com.easy.blog.api.constant.Result;
import com.easy.blog.api.model.BlogTag;
import com.easy.blog.api.model.BlogTagCloudVO;
import com.easy.blog.api.model.BlogTagListDTO;
import com.easy.blog.api.service.BlogTagService;
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
 * @date 2019/6/9
 */
@RestController
@RequestMapping("/tag")
public class BlogTagController {

    private final Logger logger = LoggerFactory.getLogger(BlogTagController.class);

    @Autowired
    private BlogTagService blogTagService;

    /**
     * 添加
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object add(@RequestBody BlogTag param) {
        if (param == null || StringUtils.isEmpty(param.getEnName())
                || StringUtils.isEmpty(param.getTagName()) || StringUtils.isEmpty(param.getAlias())
                || param.getType() == null) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            blogTagService.add(param);
            result = Result.success();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }

    /**
     * 标签云
     *
     * @return
     */
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

    /**
     * 推荐标签列表
     *
     * @return
     */
    @RequestMapping(value = "/tags", method = RequestMethod.POST)
    public Object getTags() {
        Result result;
        try {
            List<BlogTagCloudVO> tags = blogTagService.getTags();
            result = Result.success(tags);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }
}