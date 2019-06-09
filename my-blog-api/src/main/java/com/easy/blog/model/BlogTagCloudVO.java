package com.easy.blog.model;

import java.io.Serializable;

public class BlogTagCloudVO implements Serializable {

    private String code;

    private String tagName;

    private Integer num;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}