package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NoteInfo {
    private String content;
    private String createtime;
    private String images;
    private List<String> imgs;
    private String lat;
    private String lng;
    private Long voice;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public List<String> getImgs() {
        return imgs;
    }

    public void setImgs(List<String> imgs) {
        this.imgs = imgs;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public Long getVoice() {
        return voice;
    }

    public void setVoice(Long voice) {
        this.voice = voice;
    }
}