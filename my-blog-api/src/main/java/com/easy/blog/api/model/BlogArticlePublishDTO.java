package com.easy.blog.api.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class BlogArticlePublishDTO implements Serializable {

    private Long articleId;

    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getArticleId() {
        return articleId;
    }

    public void setArticleId(Long articleId) {
        this.articleId = articleId;
    }
}