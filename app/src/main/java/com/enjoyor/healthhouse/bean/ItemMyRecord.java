package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/6/3.
 */
public class ItemMyRecord {

    private String name;
    private Double result;
    private Double max;
    private Double min;

    public ItemMyRecord(String name,Double result,Double min,Double max){
        this.name = name;
        this.result = result;
        this.min = min;
        this.max = max;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }

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
}
