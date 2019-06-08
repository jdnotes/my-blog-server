package com.easy.blog.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class BaseListDTO implements Serializable {

    private int currentPage = 1;

    private int pageRows = 10;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageRows() {
        return pageRows;
    }

    public void setPageRows(int pageRows) {
        this.pageRows = pageRows;
    }
}