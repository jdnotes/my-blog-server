package com.easy.blog.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/9
 */
public class BlogTagListDTO implements Serializable {

    private String parentId;

    private String parentCode;

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}