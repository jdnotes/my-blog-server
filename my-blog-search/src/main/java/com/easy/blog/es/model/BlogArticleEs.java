package com.easy.blog.es.model;

import com.easy.blog.es.constant.EsConstants;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zhouyong
 * @date 2019/6/6
 */
@Document(indexName = EsConstants.ARTICLE_INDEX, type = EsConstants.ARTICLE_TYPE, useServerConfiguration = true)
public class BlogArticleEs implements Serializable {

    @Id
    @Field(type = FieldType.Long, store = true, index = false)
    private Long id;

    @Field(type = FieldType.Text, store = true, index = false)
    private String code;

    @Field(type = FieldType.Text, store = true, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String title;

    @Field(type = FieldType.Text, store = true, index = false)
    private String logo;

    @Field(type = FieldType.Text, store = true, index = false)
    private String author;

    @Field(type = FieldType.Text, store = true, index = false)
    private String tagId;

    @Field(type = FieldType.Text, store = true, index = false)
    private String tag;

    @Field(type = FieldType.Text, store = true, searchAnalyzer = "ik_smart", analyzer = "ik_smart")
    private String tags;

    @Field(type = FieldType.Text, store = true, index = false)
    private String tagsName;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer articleType;

    @Field(type = FieldType.Text, store = true, index = false)
    private String articleSection;

    @Field(type = FieldType.Text, store = true, index = false)
    private String articleHtml;

    @Field(type = FieldType.Text, store = true, index = false)
    private String remark;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer readNum;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer likeNum;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer sort;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer level;

    @Field(type = FieldType.Integer, store = true, index = false)
    private Integer status;

    @Field(type = FieldType.Date, store = true, index = false)
    private Date createDate;

    @Field(type = FieldType.Date, store = true, index = false)
    private Date updateDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTagId() {
        return tagId;
    }

    public void setTagId(String tagId) {
        this.tagId = tagId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
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

    public Integer getArticleType() {
        return articleType;
    }

    public void setArticleType(Integer articleType) {
        this.articleType = articleType;
    }

    public String getArticleSection() {
        return articleSection;
    }

    public void setArticleSection(String articleSection) {
        this.articleSection = articleSection;
    }

    public String getArticleHtml() {
        return articleHtml;
    }

    public void setArticleHtml(String articleHtml) {
        this.articleHtml = articleHtml;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getReadNum() {
        return readNum;
    }

    public void setReadNum(Integer readNum) {
        this.readNum = readNum;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
}