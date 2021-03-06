package com.enjoyor.healthhouse.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/6/2.
 */
public class NoteInfo {
    private String content;
    private String createtime;
    private String images;
    private ArrayList<String> imgs;
    private String lat;
    private String lng;
    private int voice;
    private String position;

    @Override
    public String toString() {
        return "NoteInfo{" +
                "content='" + content + '\'' +
                ", createtime='" + createtime + '\'' +
                ", images='" + images + '\'' +
                ", imgs=" + imgs.toString() +
                ", lat='" + lat + '\'' +
                ", lng='" + lng + '\'' +
                ", voice=" + voice +
                '}';
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

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

    public ArrayList<String> getImgs() {
        return imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
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

    public int getVoice() {
        return voice;
    }

    public void setVoice(int voice) {
        this.voice = voice;
    }
}
