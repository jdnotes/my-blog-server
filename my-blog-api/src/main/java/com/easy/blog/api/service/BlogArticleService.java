package com.easy.blog.api.service;


import com.easy.blog.api.model.*;
import com.easy.blog.api.pager.Pager;

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

    void publish(BlogArticleBack param);

    void remove(BlogArticlePublishDTO param);
}