package com.enjoyor.healthhouse.bean;

/**
 * Created by Administrator on 2016/5/24.
 */
public class HealthBsInfoList {
    public static final int TYPE_NULL = 1;
    public static final int TYPE_BREAKFAST = 5;
    public static final int TYPE_LUNCH_BEFORE = 6;
    public static final int TYPE_LUNCH_AFTER = 7;
    public static final int TYPE_DINNER_BEFOR = 8;
    public static final int TYPE_DINNER_AFTER = 9;
    public static final int TYPE_SLEEP = 10;

    String createTime;//数据保存时间
    String bloodSugar;//血糖
    Integer bloodSugarType;//血糖类型，1:空腹，2：餐后血糖，3：尿酸，4：随机血糖，5早餐后，6午餐前，7午餐后，8晚餐前，9晚餐后，10睡前

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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
}
