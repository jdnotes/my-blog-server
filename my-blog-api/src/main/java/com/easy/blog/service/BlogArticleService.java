package com.easy.blog.service;

import com.easy.blog.model.*;
import com.easy.blog.pager.Pager;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public interface BlogArticleService {

    Pager<BlogArticleListVO> search(BlogArticleListDTO param);

    List<BlogArticleRecommendVO> recommendList();

    BlogArticleDetailsVO getDetails(String code);

    void publish(BlogArticlePublishDTO param);
}