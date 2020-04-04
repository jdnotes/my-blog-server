package com.easy.blog.api.service;


import com.easy.blog.api.model.BlogArticleBack;
import com.easy.blog.api.model.BlogArticleBackDTO;
import com.easy.blog.api.model.BlogArticleBackEditorVO;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public interface BlogArticleBackService {

    void add(BlogArticleBack param);

    BlogArticleBack get(Long id);

    void save(BlogArticleBackDTO param);

    BlogArticleBackEditorVO getByCode(BlogArticleBackDTO param);
}