package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/30.
 */
public class BpRecord {

    /**
     * diastolicPressure : 72
     * healthSuggest : 正常高值，请定期参加体检，1次/年
     * recordId : 695
     * systolicPressure : 124
     */

    private int diastolicPressure;
    private String healthSuggest;
    private int recordId;
    private int systolicPressure;

    public int getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public String getHealthSuggest() {
        return healthSuggest;
    }

    public void setHealthSuggest(String healthSuggest) {
        this.healthSuggest = healthSuggest;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public int getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }
}
