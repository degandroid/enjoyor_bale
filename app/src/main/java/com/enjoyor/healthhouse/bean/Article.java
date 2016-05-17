package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Article {
    String modifyTime;// 最后修改时间
    int pageViews;// 浏览量
    int sort;//排序
    String title;// 标题

    @Override
    public String toString() {
        return "Article{" +
                "modifyTime='" + modifyTime + '\'' +
                ", pageViews=" + pageViews +
                ", sort=" + sort +
                ", title='" + title + '\'' +
                '}';
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public int getPageViews() {
        return pageViews;
    }

    public void setPageViews(int pageViews) {
        this.pageViews = pageViews;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
