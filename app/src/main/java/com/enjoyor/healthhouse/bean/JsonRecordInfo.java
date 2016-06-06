package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/6/3.
 */
public class JsonRecordInfo {
    private String name;
    private Double value;
    private String refer;
    private Double max;
    private Double min;

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getRefer() {
        return refer;
    }

    public void setRefer(String refer) {
        this.refer = refer;
    }

}
