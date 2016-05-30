package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/5/30.
 * 心电评估实体类
 */
public class EcgReport {

    /**
     * healthSuggest : 心率正常，请您定期检查，关注健康。
     * recordEcg : {"createTime":"2016-05-18 14:53:37","ecg":75,"ecgData":"2034,2038,2039,2039,2037,2035,2032,2021","id":232,"ngain":4,"recordId":389,"recordsTime":"","result":"节律无异常"}
     */

    private String healthSuggest;
    /**
     * createTime : 2016-05-18 14:53:37
     * ecg : 75
     * ecgData : 2034,2038,2039,2039,2037,2035,2032,2021
     * id : 232
     * ngain : 4
     * recordId : 389
     * recordsTime :
     * result : 节律无异常
     */

    private RecordEcgEntity recordEcg;

    public String getHealthSuggest() {
        return healthSuggest;
    }

    public void setHealthSuggest(String healthSuggest) {
        this.healthSuggest = healthSuggest;
    }

    public RecordEcgEntity getRecordEcg() {
        return recordEcg;
    }

    public void setRecordEcg(RecordEcgEntity recordEcg) {
        this.recordEcg = recordEcg;
    }

    public static class RecordEcgEntity {
        private String createTime;
        private int ecg;
        private String ecgData;
        private int id;
        private int ngain;
        private int recordId;
        private String recordsTime;
        private String result;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getEcg() {
            return ecg;
        }

        public void setEcg(int ecg) {
            this.ecg = ecg;
        }

        public String getEcgData() {
            return ecgData;
        }

        public void setEcgData(String ecgData) {
            this.ecgData = ecgData;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getNgain() {
            return ngain;
        }

        public void setNgain(int ngain) {
            this.ngain = ngain;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public String getRecordsTime() {
            return recordsTime;
        }

        public void setRecordsTime(String recordsTime) {
            this.recordsTime = recordsTime;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }
    }
}
