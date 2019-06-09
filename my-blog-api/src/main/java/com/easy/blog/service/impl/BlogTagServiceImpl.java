package com.easy.blog.service.impl;

import com.easy.blog.dao.BlogTagMapper;
import com.easy.blog.model.BlogTag;
import com.easy.blog.model.BlogTagCloudVO;
import com.easy.blog.service.BlogTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
@Service
public class BlogTagServiceImpl implements BlogTagService {

    @Autowired
    private BlogTagMapper blogTagMapper;

    @Override
    public List<BlogTagCloudVO> getTagCloud() {
        //cache todo

        List<BlogTag> tags = blogTagMapper.getTagCloud();
        if (tags == null || tags.size() == 0) {
            return new ArrayList<>();
        }
        List<BlogTagCloudVO> voList = new ArrayList<>();
        for (BlogTag tag : tags) {
            BlogTagCloudVO vo = new BlogTagCloudVO();
            vo.setCode(tag.getCode());
            vo.setTagName(tag.getAlias());
            vo.setNum(5);
            voList.add(vo);
        }
        return voList;
    }
}