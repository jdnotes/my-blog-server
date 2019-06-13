package com.easy.blog.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class BlogArticlePublishDTO implements Serializable {

    private Long articleId;

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}