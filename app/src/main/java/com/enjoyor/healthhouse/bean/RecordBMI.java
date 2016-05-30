package com.enjoyor.healthhouse.bean;

import java.io.Serializable;

/**
 * Created by YuanYuan on 2016/4/11.
 */
public class RecordBMI implements Serializable {
    /* "bmi": 22,
             "height": 180,
             "id": 22,
             "idealWeight": 74,
             "recordId": 23,
             "weight": 70*/
    private String bmi;
    private String height;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBmi() {
        return bmi;
    }

    public void setBmi(String bmi) {
        this.bmi = bmi;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(String idealWeight) {
        this.idealWeight = idealWeight;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    private String id;
    private String idealWeight;
    private String recordId;
    private String weight;

}
