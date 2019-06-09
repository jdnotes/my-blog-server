package com.easy.blog.service;

import com.easy.blog.model.BlogArticleDetailsVO;
import com.easy.blog.model.BlogArticleListDTO;
import com.easy.blog.model.BlogArticleListVO;
import com.easy.blog.model.BlogArticleRecommendVO;
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
}