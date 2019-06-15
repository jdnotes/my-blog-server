package com.easy.blog.api.service;

import com.easy.blog.api.model.BlogTag;
import com.easy.blog.api.model.BlogTagCloudVO;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
public interface BlogTagService {

    List<BlogTagCloudVO> getTagCloud();

    List<BlogTagCloudVO> getSecondTags(String parentCode);

    void add(BlogTag param);

    BlogTag getTagByCode(String tag);

    List<BlogTag> getTagByCodes(List<String> tags);
}