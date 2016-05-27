package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/26.
 */
public class HealthFileInfo {
    private List<HealthFileList> recordList;
    private Page page;

    public List<HealthFileList> getRecordList() {
        return recordList;
    }

    public void setRecordList(List<HealthFileList> recordList) {
        this.recordList = recordList;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public class HealthFileList{
        private Long recordId;//数据唯一标识
        String compLogo;//公司LOGO
        String compName;//公司名称
        String addressName;//检测点名称
        String idCardpic;//用户头像
        String userName;//健康用户姓名
        String recordNo;//报告单编号
        String recordTime;//体检报告日期
        Integer type;//1：一体机体检，2：自检

        public Long getRecordId() {
            return recordId;
        }

        public void setRecordId(Long recordId) {
            this.recordId = recordId;
        }

        public String getCompLogo() {
            return compLogo;
        }

        public void setCompLogo(String compLogo) {
            this.compLogo = compLogo;
        }

        public String getCompName() {
            return compName;
        }

        public void setCompName(String compName) {
            this.compName = compName;
        }

        public String getAddressName() {
            return addressName;
        }

        public void setAddressName(String addressName) {
            this.addressName = addressName;
        }

        public String getIdCardpic() {
            return idCardpic;
        }

        public void setIdCardpic(String idCardpic) {
            this.idCardpic = idCardpic;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getRecordNo() {
            return recordNo;
        }

        public void setRecordNo(String recordNo) {
            this.recordNo = recordNo;
        }

        public String getRecordTime() {
            return recordTime;
        }

        public void setRecordTime(String recordTime) {
            this.recordTime = recordTime;
        }

        public Integer getType() {
            return type;
        }

        public void setType(Integer type) {
            this.type = type;
        }
    }
}
