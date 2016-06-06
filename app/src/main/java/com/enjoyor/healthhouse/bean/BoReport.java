package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/30.
 * 血氧评估实体类
 */
public class BoReport {

    /**
     * bo : 99
     * healthSuggest : 您的血液血氧浓度正常，请继续保持。
     * recodId : 389
     */

    private Integer bo;
    private String healthSuggest;
    private int recodId;

    public Integer getBo() {
        return bo;
    }

    public void setBo(Integer bo) {
        this.bo = bo;
    }

    public String getHealthSuggest() {
        return healthSuggest;
    }

    public void setHealthSuggest(String healthSuggest) {
        this.healthSuggest = healthSuggest;
    }

    public int getRecodId() {
        return recodId;
    }

    public void setRecodId(int recodId) {
        this.recodId = recodId;
    }
}
