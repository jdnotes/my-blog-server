package com.easy.blog.utils;

import java.io.Serializable;
import java.util.List;

/**
 * @author zhouyong
 * @DATE 2019/5/30
 */
public class Pager<E> implements Serializable {

    /**
     * 当前页初始值
     */
    private static int CURRENT_PAGE_SIZE = 1;

    /**
     * 当前页行数初始值
     */
    private static int PAGE_ROW_SIZE = 10;

    /**
     * 页码显示个数
     */
    private static int PAGE_NUM = 9;

    /**
     * 当前页
     */
    private int currentPage = CURRENT_PAGE_SIZE;

    /**
     * 每页显示行数
     */
    private int pageRow = PAGE_ROW_SIZE;

    /**
     * 总记录数
     */
    private int totalRecords;

    /**
     * 总页数
     */
    private int totalPage;

    /**
     * 记录初始位置
     */
    private int startIndex;

    /**
     * 结果集
     */
    private List<E> records;

    /**
     * 上一页数
     */
    private int prePageNum;

    /**
     * 下一页数
     */
    private int nextPageNum;

    /**
     * 开始页码
     */
    private int beginPageNum;

    /**
     * 结束页码
     */
    private int endPageNum;

    public Pager() {
        this.startIndex = 0;
    }

    public Pager(int currentPage, int totalRecords) {
        if (currentPage < 0) {
            currentPage = CURRENT_PAGE_SIZE;
        }
        this.currentPage = currentPage;
        this.totalRecords = totalRecords;
        startIndex = (currentPage - 1) * pageRow;
        totalPage = totalRecords % pageRow == 0 ? totalRecords / pageRow : totalRecords / pageRow + 1;

        //自定义页码(最多显示9个页码)
        if (totalPage < pageRow) {
            beginPageNum = 1;
            endPageNum = totalPage;
        } else {
            int pageNumber = PAGE_NUM;
            if (pageNumber % 2 != 0) {
                pageNumber = pageNumber - 1;
            }
            int pageAvg = pageNumber / 2;
            beginPageNum = currentPage - pageAvg;
            endPageNum = currentPage + pageAvg;
            if (beginPageNum < 1) {
                beginPageNum = 1;
                endPageNum = beginPageNum + pageNumber;
            }
            if (endPageNum > totalPage) {
                endPageNum = totalPage;
                beginPageNum = endPageNum - pageNumber;
            }
        }
    }

    public int getPrePageNum() {
        int prePageNum = currentPage - 1;
        prePageNum = prePageNum < 1 ? 1 : prePageNum;
        return prePageNum;
    }

    public int getNextPageNum() {
        int nextPageNum = currentPage + 1;
        nextPageNum = nextPageNum > totalPage ? totalPage : nextPageNum;
        return nextPageNum;
    }

    public boolean isPrePage() {
        return (currentPage - 1 >= 1);
    }

    public boolean isNextPage() {
        return (currentPage + 1 <= getTotalPage());
    }

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

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public List<E> getRecords() {
        return records;
    }

    public int getBeginPageNum() {
        return beginPageNum;
    }

    public int getEndPageNum() {
        return endPageNum;
    }

    @Override
    public String toString() {
        return "Pager{" +
                "currentPage=" + currentPage +
                ", pageRow=" + pageRow +
                ", totalRecords=" + totalRecords +
                ", totalPage=" + totalPage +
                ", startIndex=" + startIndex +
                ", prePage=" + isPrePage() +
                ", prePageNum=" + getPrePageNum() +
                ", nextPage=" + isNextPage() +
                ", nextPageNum=" + getNextPageNum() +
                ", beginPageNum=" + beginPageNum +
                ", endPageNum=" + endPageNum +
                ", records=" + records +
                '}';
    }

    public static void main(String[] args) {
        int total = 134;
        Pager pager = new Pager(1, total);
        System.out.println(pager);
        pager = new Pager(2, total);
        System.out.println(pager);
        pager = new Pager(3, total);
        System.out.println(pager);
        pager = new Pager(3, total);
        System.out.println(pager);
        pager = new Pager(4, total);
        System.out.println(pager);
        pager = new Pager(12, total);
        System.out.println(pager);

        //String json = JSON.toJSONString(pager);
        //System.out.println(json);
    }

}
