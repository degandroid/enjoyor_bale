package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MyRecordInfo {
    Long recordId;//记录唯一标示
    String recordNo;//记录编号
    String compName;//公司名称
    String recordTime;//体检时间
    Double height;//身高，单位(cm)
    Double weight;//体重，单位(kg)
    Double bmi;
    Double idealWeight;//理想体重，单位(kg)
    Double bo;//血氧比，单位(%)
    Double systolicPressure;//收缩压，高压，单位(mmHg)
    Double diastolicPressure;//舒张压，低压，单位(mmHg)
    Double pulse;//脉搏，单位(次/分)
    Double bloodSugar;//血糖值，单位(mmol/L)
    Integer bloodSugarType;//血糖类型，1:空腹，2：餐后血糖，3：尿酸，4：随机血糖
    Double us;//尿酸，单位(μmol/L)
    Double chol;//胆固醇，单位(mmol/L)
    Double ecg;//心率，单位(次/分钟)
    Double fatRate;//体脂率，单位(%)
    Double fat;//脂肪量，单位(kg)
    Double exceptFat;//非脂肪量，单位(kg)
    Double waterRate;//体水率，单位(%)
    Double water;//含水量，单位(kg)
    Double minerals;//无机盐量，单位(kg)
    Double protein;//蛋白质含量，单位(kg)
    Double fic;//细胞内液，单位(kg)
    Double foc;//细胞外液，单位(kg)
    Double muscle;//肌肉量，单位(kg)
    Double basicMetaBolism;//基础代谢
    Double viscera;//内脏脂肪等级
    Double bmc;//骨骼量，单位(kg)
    Double muscleRate;//肌肉率，单位(%)
    Double temperature;//体温，单位(℃)
    Double waistLine;//腰围，单位(cm)
    Double hipLine;//臀围，单位(cm)
    Double whr;//腰臀比，单位(%)
    Double fatAdjust;// 脂肪调整，单位(kg)
    Double weightAdjust;// 体重调整，单位(kg)
    Double muscleAdjust;// 肌肉调整，单位(kg)
    String healthScore;//体检评分
    List<HealthAdviceModel> unusuals;//异常项目列表，如果没有异常项目，则为null

    public Long getRecordId() {
        return recordId;
    }

    public void setRecordId(Long recordId) {
        this.recordId = recordId;
    }

    public String getRecordNo() {
        return recordNo;
    }

    public void setRecordNo(String recordNo) {
        this.recordNo = recordNo;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
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

    public Double getBo() {
        return bo;
    }

    public void setBo(Double bo) {
        this.bo = bo;
    }

    public Double getSystolicPressure() {
        return systolicPressure;
    }

    public void setSystolicPressure(Double systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public Double getDiastolicPressure() {
        return diastolicPressure;
    }

    public void setDiastolicPressure(Double diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public Double getPulse() {
        return pulse;
    }

    public void setPulse(Double pulse) {
        this.pulse = pulse;
    }

    public Double getBloodSugar() {
        return bloodSugar;
    }

    public void setBloodSugar(Double bloodSugar) {
        this.bloodSugar = bloodSugar;
    }

    public Integer getBloodSugarType() {
        return bloodSugarType;
    }

    public void setBloodSugarType(Integer bloodSugarType) {
        this.bloodSugarType = bloodSugarType;
    }

    public Double getUs() {
        return us;
    }

    public void setUs(Double us) {
        this.us = us;
    }

    public Double getChol() {
        return chol;
    }

    public void setChol(Double chol) {
        this.chol = chol;
    }

    public Double getEcg() {
        return ecg;
    }

    public void setEcg(Double ecg) {
        this.ecg = ecg;
    }

    public Double getFatRate() {
        return fatRate;
    }

    public void setFatRate(Double fatRate) {
        this.fatRate = fatRate;
    }

    public Double getFat() {
        return fat;
    }

    public void setFat(Double fat) {
        this.fat = fat;
    }

    public Double getExceptFat() {
        return exceptFat;
    }

    public void setExceptFat(Double exceptFat) {
        this.exceptFat = exceptFat;
    }

    public Double getWaterRate() {
        return waterRate;
    }

    public void setWaterRate(Double waterRate) {
        this.waterRate = waterRate;
    }

    public Double getWater() {
        return water;
    }

    public void setWater(Double water) {
        this.water = water;
    }

    public Double getMinerals() {
        return minerals;
    }

    public void setMinerals(Double minerals) {
        this.minerals = minerals;
    }

    public Double getProtein() {
        return protein;
    }

    public void setProtein(Double protein) {
        this.protein = protein;
    }

    public Double getFic() {
        return fic;
    }

    public void setFic(Double fic) {
        this.fic = fic;
    }

    public Double getFoc() {
        return foc;
    }

    public void setFoc(Double foc) {
        this.foc = foc;
    }

    public Double getMuscle() {
        return muscle;
    }

    public void setMuscle(Double muscle) {
        this.muscle = muscle;
    }

    public Double getBasicMetaBolism() {
        return basicMetaBolism;
    }

    public void setBasicMetaBolism(Double basicMetaBolism) {
        this.basicMetaBolism = basicMetaBolism;
    }

    public Double getViscera() {
        return viscera;
    }

    public void setViscera(Double viscera) {
        this.viscera = viscera;
    }

    public Double getBmc() {
        return bmc;
    }

    public void setBmc(Double bmc) {
        this.bmc = bmc;
    }

    public Double getMuscleRate() {
        return muscleRate;
    }

    public void setMuscleRate(Double muscleRate) {
        this.muscleRate = muscleRate;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }

    public Double getWaistLine() {
        return waistLine;
    }

    public void setWaistLine(Double waistLine) {
        this.waistLine = waistLine;
    }

    public Double getHipLine() {
        return hipLine;
    }

    public void setHipLine(Double hipLine) {
        this.hipLine = hipLine;
    }

    public Double getWhr() {
        return whr;
    }

    public void setWhr(Double whr) {
        this.whr = whr;
    }

    public Double getFatAdjust() {
        return fatAdjust;
    }

    public void setFatAdjust(Double fatAdjust) {
        this.fatAdjust = fatAdjust;
    }

    public Double getWeightAdjust() {
        return weightAdjust;
    }

    public void setWeightAdjust(Double weightAdjust) {
        this.weightAdjust = weightAdjust;
    }

    public Double getMuscleAdjust() {
        return muscleAdjust;
    }

    public void setMuscleAdjust(Double muscleAdjust) {
        this.muscleAdjust = muscleAdjust;
    }

    public String getHealthScore() {
        return healthScore;
    }

    public void setHealthScore(String healthScore) {
        this.healthScore = healthScore;
    }

    public List<HealthAdviceModel> getUnusuals() {
        return unusuals;
    }

    public void setUnusuals(List<HealthAdviceModel> unusuals) {
        this.unusuals = unusuals;
    }

    public static class HealthAdviceModel{
        Long id;//异常ID
        Integer level;//异常等级，暂时无用
        Long diseaseId;//疾病ID
        String disease;//疾病名称，详细见，体检信息Apis中的疾病对照表
        String advice;//评测结果

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public Integer getLevel() {
            return level;
        }

        public void setLevel(Integer level) {
            this.level = level;
        }

        public Long getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(Long diseaseId) {
            this.diseaseId = diseaseId;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }
    }

}
