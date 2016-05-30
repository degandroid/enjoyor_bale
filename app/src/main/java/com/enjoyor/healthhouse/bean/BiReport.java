package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/30.
 * 血糖评估实体类
 */
public class BiReport {

    /**
     * beanList : [{"bloodSugar":"3.8","bloodSugarType":10,"chol":"","createTime":"2016-05-18 16:05:38","id":201,"recordId":601,"result":"","us":""},{"bloodSugar":"3.7","bloodSugarType":9,"chol":"","createTime":"2016-05-18 16:05:30","id":200,"recordId":601,"result":"","us":""},{"bloodSugar":"3.6","bloodSugarType":8,"chol":"","createTime":"2016-05-18 16:05:23","id":199,"recordId":601,"result":"","us":""},{"bloodSugar":"3.5","bloodSugarType":7,"chol":"","createTime":"2016-05-18 16:05:17","id":198,"recordId":601,"result":"","us":""},{"bloodSugar":"3.4","bloodSugarType":6,"chol":"","createTime":"2016-05-18 16:05:10","id":197,"recordId":601,"result":"","us":""},{"bloodSugar":"3.3","bloodSugarType":5,"chol":"","createTime":"2016-05-18 16:05:05","id":196,"recordId":601,"result":"","us":""},{"bloodSugar":"3.2","bloodSugarType":1,"chol":"","createTime":"2016-05-18 16:04:51","id":195,"recordId":601,"result":"","us":""}]
     * healthSuggest : 您本次测定结果正常，请坚持健康饮食和良好的生活习惯.
     * recordId : 601
     */

    private String healthSuggest;
    private int recordId;
    /**
     * bloodSugar : 3.8
     * bloodSugarType : 10
     * chol :
     * createTime : 2016-05-18 16:05:38
     * id : 201
     * recordId : 601
     * result :
     * us :
     */

    private List<BeanListEntity> beanList;

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

    public List<BeanListEntity> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<BeanListEntity> beanList) {
        this.beanList = beanList;
    }

    public static class BeanListEntity {
        private String bloodSugar;
        private int bloodSugarType;
        private String chol;
        private String createTime;
        private int id;
        private int recordId;
        private String result;
        private String us;

        public String getBloodSugar() {
            return bloodSugar;
        }

        public void setBloodSugar(String bloodSugar) {
            this.bloodSugar = bloodSugar;
        }

        public int getBloodSugarType() {
            return bloodSugarType;
        }

        public void setBloodSugarType(int bloodSugarType) {
            this.bloodSugarType = bloodSugarType;
        }

        public String getChol() {
            return chol;
        }

        public void setChol(String chol) {
            this.chol = chol;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getRecordId() {
            return recordId;
        }

        public void setRecordId(int recordId) {
            this.recordId = recordId;
        }

        public String getResult() {
            return result;
        }

        public void setResult(String result) {
            this.result = result;
        }

        public String getUs() {
            return us;
        }

        public void setUs(String us) {
            this.us = us;
        }
    }
}
