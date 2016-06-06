package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/31.
 * 资讯文章查询实体类
 */
public class InfoClassSelect {

    /**
     * classify : null
     * id : 14
     * images : [{"article":null,"createTime":null,"id":null,"path":"files/res/app/start/9e401363f556961bace7ac6cd6d108e9.jpg"}]
     * interval : 2016-05-19 08:56
     * modifyTime : 2016-05-19 08:56:37
     * pageViews : null
     * title : 瘦腰瘦肚子 瑜珈让您更有魅力
     */

    private Object classify;
    private int id;
    private String interval;
    private String modifyTime;
    private String pageViews;
    private String title;
    /**
     * article : null
     * createTime : null
     * id : null
     * path : files/res/app/start/9e401363f556961bace7ac6cd6d108e9.jpg
     */

    private List<ImagesEntity> images;

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

    public String getPageViews() {
        return pageViews;
    }

    public void setPageViews(String pageViews) {
        this.pageViews = pageViews;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ImagesEntity> getImages() {
        return images;
    }

    public void setImages(List<ImagesEntity> images) {
        this.images = images;
    }

    public static class ImagesEntity {
        private Object article;
        private Object createTime;
        private Object id;
        private String path;

        public Object getArticle() {
            return article;
        }

        public void setArticle(Object article) {
            this.article = article;
        }

        public Object getCreateTime() {
            return createTime;
        }

        public void setCreateTime(Object createTime) {
            this.createTime = createTime;
        }

        public Object getId() {
            return id;
        }

        public void setId(Object id) {
            this.id = id;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }
    }
}
