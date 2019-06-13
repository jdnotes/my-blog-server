package com.easy.blog.api.model;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class BlogArticleListDTO extends BaseListDTO {

    private String keywords;

    private Integer level;

    private String tags;

    private String tagsName;

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getTagsName() {
        return tagsName;
    }

    public void setTagsName(String tagsName) {
        this.tagsName = tagsName;
    }
}