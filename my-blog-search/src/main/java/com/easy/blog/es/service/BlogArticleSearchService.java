package com.easy.blog.es.service;

import com.easy.blog.es.model.BlogArticleEs;
import com.easy.blog.es.model.BlogArticleEsDTO;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
public interface BlogArticleSearchService {

    Page<BlogArticleEs> search(BlogArticleEsDTO param);

    void add(BlogArticleEs es);

    void update(BlogArticleEs es);

    BlogArticleEs getInfoById(Long id);

    List<BlogArticleEs> recommendList(int size);

    BlogArticleEs getInfoByCode(String code);

    void delete(Long articleId);
}