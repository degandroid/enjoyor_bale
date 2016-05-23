package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/5/22.
 */
public class HistoryInfoList {


    Long id;
    Long recordId; //记录编号
    String recordTime;//记录时间
    String result;//结论，
    String createTime;//数据创建时间
    /*血压*/
    Integer systolicPressure;//收缩压，高压，单位(mmHg)
    Integer diastolicPressure;//舒张压，低压，单位(mmHg)
    Integer pulse;//脉搏，单位(次/分)
    String checkTime;//详细时间点，目前是小时
    /*身高体重*/
    Double height;//身高
    Double weight;//体重
    Double bmi;//bmi
    Double idealWeight;//理想体重
    /*血糖*/
    String bloodSugar;//血糖
    Integer bloodSugarType;//血糖类型，1:空腹，2：餐后血糖，3：尿酸，4：随机血糖，5早餐后，6午餐前，7午餐后，8晚餐前，9晚餐后，10睡前
    String us;//尿酸
    String chol;//胆固醇
    /*血氧*/
    Double bo;// 血氧比，单位(%)
    String startTime;// 开始提取血氧时间
    String endTime;// 结束提取血氧时间
    String refOfBO;// 血氧参考值
    String refOfPulse;// 脉搏参考值
    /*腰围*/
    String waistLine;//腰围
    String hipLine;//臀围
    String wHR;//whr
    /*体温*/
    String temperature;//体温
    /*心电*/
    Double ecg;// 心率
    Integer ngain;// 增益
    String ecgData;// 心电图数据,因数据量庞大，暂不反回

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Integer systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public Integer getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Integer diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Integer getPulse() {
        return pulse;
    }

    public void setPulse(Integer pulse) {
        this.pulse = pulse;
    }

    public String getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(String checkTime) {
        this.checkTime = checkTime;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getBmi() {
        return bmi;
    }

    public void setBmi(Double bmi) {
        this.bmi = bmi;
    }

    public Double getIdealWeight() {
        return idealWeight;
    }

    public void setIdealWeight(Double idealWeight) {
        this.idealWeight = idealWeight;
    }

    public String getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(String bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public Integer getBloodSugarType() {
        return bloodSugarType;
    }

    public void setBloodSugarType(Integer bloodSugarType) {
        this.bloodSugarType = bloodSugarType;
    }

    public String getUs() {
        return us;
    }

    public void setUs(String us) {
        this.us = us;
    }

    public String getChol() {
        return chol;
    }

    public void setChol(String chol) {
        this.chol = chol;
    }

    public Double getBo() {
        return bo;
    }

    public void setBo(Double bo) {
        this.bo = bo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getRefOfBO() {
        return refOfBO;
    }

    public void setRefOfBO(String refOfBO) {
        this.refOfBO = refOfBO;
    }

    public String getRefOfPulse() {
        return refOfPulse;
    }

    public void setRefOfPulse(String refOfPulse) {
        this.refOfPulse = refOfPulse;
    }

    public String getWaistLine() {
        return waistLine;
    }

    public void setWaistLine(String waistLine) {
        this.waistLine = waistLine;
    }

    public String getHipLine() {
        return hipLine;
    }

    public void setHipLine(String hipLine) {
        this.hipLine = hipLine;
    }

    public String getwHR() {
        return wHR;
    }

    public void setwHR(String wHR) {
        this.wHR = wHR;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public Double getEcg() {
        return ecg;
    }

    public void setEcg(Double ecg) {
        this.ecg = ecg;
    }

    public Integer getNgain() {
        return ngain;
    }

    public void setNgain(Integer ngain) {
        this.ngain = ngain;
    }

    public String getEcgData() {
        return ecgData;
    }

    public void setEcgData(String ecgData) {
        this.ecgData = ecgData;
    }
}
