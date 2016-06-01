package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/12.
 */
public class Article {
    private int id;
    private String modifyTime;// 最后修改时间
    private int pageViews;// 浏览量
    private int sort;//排序
    private String title;// 标题
    private String interval;// 间隔
    private List<ImagePath> images;

    public List<ImagePath> getImages() {
        return images;
    }

    public void setImages(List<ImagePath> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "Article{" +
                "modifyTime='" + modifyTime + '\'' +
                ", pageViews=" + pageViews +
                ", sort=" + sort +
                ", title='" + title + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
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
