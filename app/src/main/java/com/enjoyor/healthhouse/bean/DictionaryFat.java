package com.enjoyor.healthhouse.bean;

import java.io.Serializable;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class DictionaryFat implements Serializable {

    /**
     * basicMetaBolismBest : 1483
     * bmiBestMax : 23.9
     * bmiBestMin : 18.5
     * dictionaryFat : [{"descText":"低","id":1,"itemMax":1,"itemMin":0,"itemType":1,"orderSeq":1,"resultLevel":"1"},{"descText":"低","id":5,"itemMax":1383,"itemMin":0,"itemType":2,"orderSeq":1,"resultLevel":"1"},{"descText":"极瘦","id":8,"itemMax":5,"itemMin":0,"itemType":3,"orderSeq":1,"resultLevel":"0"},{"descText":"标准","id":2,"itemMax":9,"itemMin":1,"itemType":1,"orderSeq":2,"resultLevel":"2"},{"descText":"消瘦","id":9,"itemMax":11,"itemMin":5,"itemType":3,"orderSeq":2,"resultLevel":"1"},{"descText":"标准","id":6,"itemMax":1583,"itemMin":1383,"itemType":2,"orderSeq":2,"resultLevel":"2"},{"descText":"标准","id":10,"itemMax":22,"itemMin":11,"itemType":3,"orderSeq":3,"resultLevel":"2"},{"descText":"高","id":7,"itemMax":9999,"itemMin":1583,"itemType":2,"orderSeq":3,"resultLevel":"3"},{"descText":"偏高","id":3,"itemMax":18,"itemMin":9,"itemType":1,"orderSeq":3,"resultLevel":"3"},{"descText":"高","id":4,"itemMax":99,"itemMin":18,"itemType":1,"orderSeq":4,"resultLevel":"4"},{"descText":"超重","id":11,"itemMax":27,"itemMin":22,"itemType":3,"orderSeq":4,"resultLevel":"3"},{"descText":"肥胖","id":12,"itemMax":45,"itemMin":27,"itemType":3,"orderSeq":5,"resultLevel":"4"},{"descText":"极胖","id":13,"itemMax":99,"itemMin":45,"itemType":3,"orderSeq":6,"resultLevel":"5"}]
     * fatBestMax : 13.5
     * fatBestMin : 6.7
     * fatRateBestMax : 22
     * fatRateBestMin : 11
     * fatyear : [{"recordId":5,"fatRate":19.1,"recordTime":"2016-03-06 15:57:45.0"},{"recordId":6,"fatRate":23.8,"recordTime":"2016-03-07 15:57:47.0"},{"recordId":7,"fatRate":18,"recordTime":"2016-03-09 15:57:48.0"},{"recordId":14,"fatRate":23.4,"recordTime":"2016-03-16 13:11:41.0"},{"recordId":17,"fatRate":23.2,"recordTime":"2016-03-16 13:34:42.0"},{"recordId":23,"fatRate":16,"recordTime":"2016-03-16 14:55:20.0"}]
     * healthSuggest :
     * muscleBestMax : 52.4
     * muscleBestMin : 44
     * recordBMI : {"bmi":22,"height":180,"id":22,"idealWeight":74,"recordId":23,"weight":70}
     * recordFat : {"basicMetaBolism":1140,"bmc":3.1,"bodyFat":8,"bodyMuscle":30.9,"exceptFat":49.8,"fat":26.2,"fatAdjust":-0.3,"fatRate":16,"fic":17.8,"foc":9,"id":21,"leftArmFat":0.7,"leftArmMuscle":2.9,"leftLegFat":3,"leftLegMuscle":9.7,"minerals":1.2,"muscle":34.4,"muscleAdjust":0.4,"muscleRate":75.2,"protein":7.6,"recordId":23,"result":"","rightArmFat":0.7,"rightArmMuscle":3.1,"rightLegFat":3,"rightLegMuscle":9.9,"viscera":7,"water":26.8,"waterRate":36.4,"weightAdjust":0.1}
     * recordsTime : 2016-03-16 14:55:20.20
     * visceraBestMax : 9
     * visceraBestMin : 1
     * waterRateBestMax : 65
     * waterRateBestMin : 55
     * weightyear : [{"recordId":5,"weight":68.7,"recordTime":"2016-03-06 15:57:45.0"},{"recordId":6,"weight":70.7,"recordTime":"2016-03-07 15:57:47.0"},{"recordId":7,"weight":44.6,"recordTime":"2016-03-09 15:57:48.0"},{"recordId":14,"weight":71,"recordTime":"2016-03-16 13:11:41.0"},{"recordId":17,"weight":70.9,"recordTime":"2016-03-16 13:34:42.0"},{"recordId":23,"weight":70,"recordTime":"2016-03-16 14:55:20.0"}]
     */


    /**
     * descText : 低
     * id : 1
     * itemMax : 1
     * itemMin : 0
     * itemType : 1
     * orderSeq : 1
     * resultLevel : 1
     */


    private String descText;
    private int id;
    private int itemMax;
    private int itemMin;
    private int itemType;
    private int orderSeq;
    private String resultLevel;

    public String getDescText() {
        return descText;
    }

    public void setDescText(String descText) {
        this.descText = descText;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemMax() {
        return itemMax;
    }

    public void setItemMax(int itemMax) {
        this.itemMax = itemMax;
    }

    public int getItemMin() {
        return itemMin;
    }

    public void setItemMin(int itemMin) {
        this.itemMin = itemMin;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public int getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(int orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getResultLevel() {
        return resultLevel;
    }

    public void setResultLevel(String resultLevel) {
        this.resultLevel = resultLevel;
    }
}
