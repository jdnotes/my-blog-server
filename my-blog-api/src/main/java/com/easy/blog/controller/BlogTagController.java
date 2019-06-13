package com.easy.blog.controller;

import com.easy.blog.constant.CodeMsgConstant;
import com.easy.blog.constant.Result;
import com.easy.blog.model.BlogTag;
import com.easy.blog.model.BlogTagCloudVO;
import com.easy.blog.model.BlogTagListDTO;
import com.easy.blog.service.BlogTagService;
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
     * 二级标签
     *
     * @return
     */
    @RequestMapping(value = "/secondTags", method = RequestMethod.POST)
    public Object secondTags(@RequestBody BlogTagListDTO param) {
        if (param == null || StringUtils.isEmpty(param.getParentCode())) {
            return Result.error(CodeMsgConstant.PARAM_BIND_ERROR);
        }
        Result result;
        try {
            List<BlogTagCloudVO> tags = blogTagService.getSecondTags(param.getParentCode());
            result = Result.success(tags);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = Result.error();
        }
        return result;
    }
}