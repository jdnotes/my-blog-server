package com.easy.blog.api.model;

import java.io.Serializable;

/**
 * @author zhouyong
 * @date 2019/6/8
 */
public class BaseListDTO implements Serializable {

    private int currentPage = 1;

    private int pageRow = 10;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageRow() {
        return pageRow;
    }

    public void setPageRow(int pageRow) {
        this.pageRow = pageRow;
    }
}