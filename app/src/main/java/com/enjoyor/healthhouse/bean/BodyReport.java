package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/30.
 * 人体成分评估实体类
 */
public class BodyReport {

    /**
     * fatyear : [{"recordId":119,"fatRate":16,"recordTime":"2016-03-16 17:28:13.0"},{"recordId":431,"fatRate":22.1,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":432,"fatRate":22.1,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":433,"fatRate":22.1,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":452,"fatRate":26.1,"recordTime":"2016-05-10 11:55:46.0"},{"recordId":467,"fatRate":26.1,"recordTime":"2016-05-10 11:55:46.0"},{"recordId":26,"fatRate":18.2,"recordTime":"2016-05-17 16:04:46.0"}]
     * healthSuggest :
     * recordBMI : {"bmi":27.3,"height":181.5,"id":307,"idealWeight":0,"recordId":473,"weight":89.8}
     * recordFat : null
     * recordsTime : 2016-05-23 10:47:20
     * weightyear : [{"recordId":119,"weight":70,"recordTime":"2016-03-16 17:28:13.0"},{"recordId":431,"weight":72.7,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":432,"weight":72.7,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":433,"weight":72.7,"recordTime":"2016-05-09 14:54:20.0"},{"recordId":452,"weight":89.9,"recordTime":"2016-05-10 11:55:46.0"},{"recordId":467,"weight":89.9,"recordTime":"2016-05-10 11:55:46.0"},{"recordId":473,"weight":89.8,"recordTime":"2016-05-10 18:32:24.0"},{"recordId":26,"weight":61,"recordTime":"2016-05-17 16:04:46.0"},{"recordId":601,"weight":58,"recordTime":"2016-05-18 12:12:12.0"},{"recordId":601,"weight":59,"recordTime":"2016-05-18 12:12:12.0"},{"recordId":601,"weight":60,"recordTime":"2016-05-18 12:12:12.0"}]
     */

    private String healthSuggest;
    /**
     * bmi : 27.3
     * height : 181.5
     * id : 307
     * idealWeight : 0
     * recordId : 473
     * weight : 89.8
     */

    private RecordBMIEntity recordBMI;
    private Object recordFat;
    private String recordsTime;
    /**
     * recordId : 119
     * fatRate : 16
     * recordTime : 2016-03-16 17:28:13.0
     */

    private List<FatyearEntity> fatyear;
    /**
     * recordId : 119
     * weight : 70
     * recordTime : 2016-03-16 17:28:13.0
     */

    private List<WeightyearEntity> weightyear;

    public String getHealthSuggest() {
        return healthSuggest;
    }

    public void setHealthSuggest(String healthSuggest) {
        this.healthSuggest = healthSuggest;
    }

    public RecordBMIEntity getRecordBMI() {
        return recordBMI;
    }

    public void setRecordBMI(RecordBMIEntity recordBMI) {
        this.recordBMI = recordBMI;
    }

    public Object getRecordFat() {
        return recordFat;
    }

    public void setRecordFat(Object recordFat) {
        this.recordFat = recordFat;
    }

    public String getRecordsTime() {
        return recordsTime;
    }

    public void setRecordsTime(String recordsTime) {
        this.recordsTime = recordsTime;
    }

    public List<FatyearEntity> getFatyear() {
        return fatyear;
    }

    public void setFatyear(List<FatyearEntity> fatyear) {
        this.fatyear = fatyear;
    }

    public List<WeightyearEntity> getWeightyear() {
        return weightyear;
    }

    public void setWeightyear(List<WeightyearEntity> weightyear) {
        this.weightyear = weightyear;
    }

    public static class RecordBMIEntity {
        private double bmi;
        private double height;
        private int id;
        private int idealWeight;
        private int recordId;
        private double weight;

        public double getBmi() {
            return bmi;
        }

        public void setBmi(double bmi) {
            this.bmi = bmi;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdealWeight() {
            return idealWeight;
        }

        public void setIdealWeight(int idealWeight) {
            this.idealWeight = idealWeight;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public double getWeight() {
            return weight;
        }

        public void setWeight(double weight) {
            this.weight = weight;
        }
    }

    public static class FatyearEntity {
        private int recordId;
        private int fatRate;
        private String recordTime;

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public int getFatRate() {
            return fatRate;
        }

        public void setFatRate(int fatRate) {
            this.fatRate = fatRate;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }
    }

    public static class WeightyearEntity {
        private int recordId;
        private int weight;
        private String recordTime;

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }
    }
}
