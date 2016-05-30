package com.enjoyor.healthhouse.bean;

import java.io.Serializable;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class Fatyear implements Serializable {
    /* recordId": 5,
             "fatRate": 19.1,
             "recordTime":*/
    private String recordId;

    public String getFatRate() {
        return fatRate;
    }

    public void setFatRate(String fatRate) {
        this.fatRate = fatRate;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    private String fatRate;
    private String recordTime;

}
