package com.enjoyor.healthhouse.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/31.
 */
@DatabaseTable(tableName = "tb_BMI")
public class BMI implements Parcelable {
    @DatabaseField(generatedId = true)
    Long id;
    @DatabaseField
    Double height;
    @DatabaseField
    String recordTime;
    @DatabaseField
    String createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.id);
        dest.writeValue(this.height);
        dest.writeString(this.recordTime);
        dest.writeString(this.createTime);
    }

    public BMI() {
    }

    protected BMI(Parcel in) {
        this.id = (Long) in.readValue(Long.class.getClassLoader());
        this.height = (Double) in.readValue(Double.class.getClassLoader());
        this.recordTime = in.readString();
        this.createTime = in.readString();
    }

    public static final Parcelable.Creator<BMI> CREATOR = new Parcelable.Creator<BMI>() {
        public BMI createFromParcel(Parcel source) {
            return new BMI(source);
        }

        public BMI[] newArray(int size) {
            return new BMI[size];
        }
    };
}
