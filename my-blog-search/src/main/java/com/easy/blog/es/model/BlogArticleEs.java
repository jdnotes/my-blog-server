package com.easy.blog.es.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
public class BlogArticleEs implements Serializable {

    private String title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}