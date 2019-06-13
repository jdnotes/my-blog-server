package com.easy.blog.api.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class BlogArticleDetailsVO implements Serializable {

    private String id;

    private String title;

    private String logo;

    private String author;

    private String articleTypeText;

    private String remark;

    private Integer readNum;

    private Integer likeNum;

    private Date createDate;

    private Date updateDate;

    private String articleHtml;

    private String mind;

    private List<BlogTagCloudVO> tags;

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

    public String getArticleTypeText() {
        return articleTypeText;
    }

    public void setArticleTypeText(String articleTypeText) {
        this.articleTypeText = articleTypeText;
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

    public String getArticleHtml() {
        return articleHtml;
    }

    public void setArticleHtml(String articleHtml) {
        this.articleHtml = articleHtml;
    }

    public String getMind() {
        return mind;
    }

    public void setMind(String mind) {
        this.mind = mind;
    }

    public List<BlogTagCloudVO> getTags() {
        return tags;
    }

    public void setTags(List<BlogTagCloudVO> tags) {
        this.tags = tags;
    }
}