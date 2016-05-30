package com.enjoyor.healthhouse.bean;

import java.io.Serializable;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class Weightyear implements Serializable {
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    /*recordId": 6,
                "weight": 70.7,
                "recordTime": "20*/
    private String recordId;
    private String weight;
    private String recordTime;
}
