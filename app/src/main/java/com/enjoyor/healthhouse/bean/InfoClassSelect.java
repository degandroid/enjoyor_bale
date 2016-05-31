package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/31.
 * 资讯文章查询实体类
 */
public class InfoClassSelect {

    /**
     * classify : null
     * id : 18
     * images : []
     * interval : 2016-05-18 09:56
     * modifyTime : 2016-05-18 09:56:01
     * pageViews : null
     * title : 原发性高血压有哪些病因
     */

    private Object classify;
    private int id;
    private String interval;
    private String modifyTime;
    private Object pageViews;
    private String title;
    private List<?> images;

    public Object getClassify() {
        return classify;
    }

    public void setClassify(Object classify) {
        this.classify = classify;
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

    public Object getPageViews() {
        return pageViews;
    }

    public void setPageViews(Object pageViews) {
        this.pageViews = pageViews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<?> getImages() {
        return images;
    }

    public void setImages(List<?> images) {
        this.images = images;
    }
}
