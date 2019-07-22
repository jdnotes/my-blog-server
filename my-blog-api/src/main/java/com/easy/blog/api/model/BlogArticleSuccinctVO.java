package com.easy.blog.api.model;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouyong
 * @date 2019/7/21
 */
public class BlogArticleSuccinctVO implements Serializable {

    private String id;

    private String title;

    private Date date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}