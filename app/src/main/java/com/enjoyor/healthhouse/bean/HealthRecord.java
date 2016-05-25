package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/24.
 * 获取健康评估信息的实体类
 */
public class HealthRecord {

    /**
     * recordId : 695
     * unusuals : [{"advice":"正常高值，请定期参加体检，1次/年","disease":"hypertension","diseaseId":1,"id":799,"level":3}]
     */

    private int recordId;
    /**
     * advice : 正常高值，请定期参加体检，1次/年
     * disease : hypertension
     * diseaseId : 1
     * id : 799
     * level : 3
     */

    private List<UnusualsEntity> unusuals;

    public int getRecordId() {
        return recordId;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    public List<UnusualsEntity> getUnusuals() {
        return unusuals;
    }

    public void setUnusuals(List<UnusualsEntity> unusuals) {
        this.unusuals = unusuals;
    }

    public static class UnusualsEntity {
        private String advice;
        private String disease;
        private int diseaseId;
        private int id;
        private int level;

        public String getAdvice() {
            return advice;
        }

        public void setAdvice(String advice) {
            this.advice = advice;
        }

        public String getDisease() {
            return disease;
        }

        public void setDisease(String disease) {
            this.disease = disease;
        }

        public int getDiseaseId() {
            return diseaseId;
        }

        public void setDiseaseId(int diseaseId) {
            this.diseaseId = diseaseId;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getLevel() {
            return level;
        }

        public void setLevel(int level) {
            this.level = level;
        }
    }
}
