package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class MyZijianInfo {
    private List<BOS> bos;
    private List<BPS> bps;
    private List<BSES> bses;
    private List<ECGS> ecgs;
    private List<HEIGHTS> heights;
    private List<TEMPERATURES> temperatures;
    private List<WAISTLINES> waistlines;
    private List<WEIGHTS> weights;

    public List<BOS> getBos() {
        return bos;
    }

    public void setBos(List<BOS> bos) {
        this.bos = bos;
    }

    public List<BPS> getBps() {
        return bps;
    }

    public void setBps(List<BPS> bps) {
        this.bps = bps;
    }

    public List<BSES> getBses() {
        return bses;
    }

    public void setBses(List<BSES> bses) {
        this.bses = bses;
    }

    public List<ECGS> getEcgs() {
        return ecgs;
    }

    public void setEcgs(List<ECGS> ecgs) {
        this.ecgs = ecgs;
    }

    public List<HEIGHTS> getHeights() {
        return heights;
    }

    public void setHeights(List<HEIGHTS> heights) {
        this.heights = heights;
    }

    public List<TEMPERATURES> getTemperatures() {
        return temperatures;
    }

    public void setTemperatures(List<TEMPERATURES> temperatures) {
        this.temperatures = temperatures;
    }

    public List<WAISTLINES> getWaistlines() {
        return waistlines;
    }

    public void setWaistlines(List<WAISTLINES> waistlines) {
        this.waistlines = waistlines;
    }

    public List<WEIGHTS> getWeights() {
        return weights;
    }

    public void setWeights(List<WEIGHTS> weights) {
        this.weights = weights;
    }

    public class BOS{
        private Double bo;
        private String checkTime;

        public Double getBo() {
            return bo;
        }

        public void setBo(Double bo) {
            this.bo = bo;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class BPS{
        private Double diastolicPressure;
        private Double systolicPressure;
        private String checkTime;

        public Double getDiastolicPressure() {
            return diastolicPressure;
        }

        public void setDiastolicPressure(Double diastolicPressure) {
            this.diastolicPressure = diastolicPressure;
        }

        public Double getSystolicPressure() {
            return systolicPressure;
        }

        public void setSystolicPressure(Double systolicPressure) {
            this.systolicPressure = systolicPressure;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class BSES{
        private Double bloodSugar;
        private Integer bloodSugarType;
        private String checkTime;

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

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class ECGS{
        private Double ecg;
        private String checkTime;

        public Double getEcg() {
            return ecg;
        }

        public void setEcg(Double ecg) {
            this.ecg = ecg;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class HEIGHTS{
        private Double height;
        private String checkTime;

        public Double getHeight() {
            return height;
        }

        public void setHeight(Double height) {
            this.height = height;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class WAISTLINES{
        private Double waistLine;
        private String checkTime;

        public Double getWaistLine() {
            return waistLine;
        }

        public void setWaistLine(Double waistLine) {
            this.waistLine = waistLine;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class TEMPERATURES{
        private Double temperature;
        private String checkTime;

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
    public class WEIGHTS{
        private Double weight;
        private String checkTime;

        public Double getWeight() {
            return weight;
        }

        public void setWeight(Double weight) {
            this.weight = weight;
        }

        public String getCheckTime() {
            return checkTime;
        }

        public void setCheckTime(String checkTime) {
            this.checkTime = checkTime;
        }
    }
}
