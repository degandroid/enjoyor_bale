package com.enjoyor.healthhouse.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by YuanYuan on 2016/5/13.
 */
@DatabaseTable(tableName = "physicall_location_info")
public class PhyicallLocation_info {
    @DatabaseField(id = true)
    int id;
    @DatabaseField
    private Long machineId;//机器ID
    @DatabaseField
    private Long companyId;                        //机构ID
    @DatabaseField
    private String machineCode;                //机器码
    @DatabaseField
    private String macAddress;                //机器MAC地址
    @DatabaseField
    private String AddressName;//机器所在点描述
    @DatabaseField
    private String MachineAddress;//机器详细地址信息
    @DatabaseField
    private String MachineLong;//机器所在经度

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getMachineId() {
        return machineId;
    }

    public void setMachineId(Long machineId) {
        this.machineId = machineId;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getMachineCode() {
        return machineCode;
    }

    public void setMachineCode(String machineCode) {
        this.machineCode = machineCode;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public String getAddressName() {
        return AddressName;
    }

    public void setAddressName(String addressName) {
        AddressName = addressName;
    }

    public String getMachineAddress() {
        return MachineAddress;
    }

    public void setMachineAddress(String machineAddress) {
        MachineAddress = machineAddress;
    }

    public String getMachineLong() {
        return MachineLong;
    }

    public void setMachineLong(String machineLong) {
        MachineLong = machineLong;
    }

    public String getMachineLat() {
        return MachineLat;
    }

    public void setMachineLat(String machineLat) {
        MachineLat = machineLat;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    @DatabaseField
    private String MachineLat;//机器所在维度
    @DatabaseField
    private String compName;//机器所属公司名称
    @DatabaseField
    private Double distance;//直线距离，单位：米

}
