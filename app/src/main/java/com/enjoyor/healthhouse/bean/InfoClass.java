package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/31.
 * 资讯分类实体类
 */
public class InfoClass {


    /**
     * createTime : 2016-05-16 16:06:48
     * id : 1
     * modifyTime : 2016-05-16 16:06:48
     * name : 减肥
     * pageViewFlag : 1
     * sort : 1
     * status : 1
     */

    private String createTime;
    private int id;
    private String modifyTime;
    private String name;
    private int pageViewFlag;
    private int sort;
    private String status;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPageViewFlag() {
        return pageViewFlag;
    }

    public void setPageViewFlag(int pageViewFlag) {
        this.pageViewFlag = pageViewFlag;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
