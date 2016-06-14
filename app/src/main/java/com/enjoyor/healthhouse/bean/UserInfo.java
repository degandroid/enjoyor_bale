package com.enjoyor.healthhouse.bean;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Administrator on 2016/5/11.
 */
@DatabaseTable(tableName = "tb_user")
public class UserInfo {
    @DatabaseField(id = true)
    int id;
    @DatabaseField
    Long accountId;//APP账户ID
    @DatabaseField
    Long userId;//系统健康用户ID， 如果此项为空值时，该用户没有绑定身份证相关信息，可以让用户完善信息
    @DatabaseField
    String loginName;//系统登录名称
    @DatabaseField
    String userLoginPwd;//系统登录密码
    @DatabaseField
    String nickName;//用户昵称
    @DatabaseField
    String headImg;//头像
    @DatabaseField
    String accsex;//性别
    @DatabaseField
    String signature;//个性签名
    @DatabaseField
    String accountText;//个人简介
    @DatabaseField
    String userName;//姓名
    @DatabaseField
    String phoneNumber;//电话
    @DatabaseField
    String idCard;//身份证,如果此项为空值时，该用户没有绑定身份证相关信息，可以让用户完善信息
    @DatabaseField
    String idCardPic;//身份证图片
    @DatabaseField
    String age;//年龄
    @DatabaseField
    String sex;//性别
    @DatabaseField
    String address;//地址
    @DatabaseField
    String birthday;//生日
    @DatabaseField
    int status;
    @DatabaseField
    String positionName;//最新体检位置描述
    @DatabaseField
    String positionLong;//最新体检经度
    @DatabaseField
    String positionLat;//最新体检维度
    @DatabaseField
    String compName;//最新体检商家描述
    @DatabaseField
    String addressName;//机器体检点简称
    @DatabaseField
    String recordContent;//健康描述,目前是估值分数

    public UserInfo() {
    }

    public String getUserLoginPwd() {
        return userLoginPwd;
    }

    public void setUserLoginPwd(String userLoginPwd) {
        this.userLoginPwd = userLoginPwd;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getAccsex() {
        return accsex;
    }

    public void setAccsex(String accsex) {
        this.accsex = accsex;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAccountText() {
        return accountText;
    }

    public void setAccountText(String accountText) {
        this.accountText = accountText;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getIdCardPic() {
        return idCardPic;
    }

    public void setIdCardPic(String idCardPic) {
        this.idCardPic = idCardPic;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getPositionLong() {
        return positionLong;
    }

    public void setPositionLong(String positionLong) {
        this.positionLong = positionLong;
    }

    public String getPositionLat() {
        return positionLat;
    }

    public void setPositionLat(String positionLat) {
        this.positionLat = positionLat;
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

    public String getRecordContent() {
        return recordContent;
    }

    public void setRecordContent(String recordContent) {
        this.recordContent = recordContent;
    }
}
