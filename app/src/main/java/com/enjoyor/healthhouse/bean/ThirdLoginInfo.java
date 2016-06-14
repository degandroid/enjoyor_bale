package com.enjoyor.healthhouse.bean;


/**
 * Created by YuanYuan on 2016/6/13.
 * 第三方登录用户信息实体类
 */
public class ThirdLoginInfo {


    /**
     * accId : 21
     * createTime : 2016-06-14 11:28:56
     * expiresIn : 120747
     * expiresTime : 2016-06-15 02:59:59
     * id : 2
     * platformNname : SinaWeibo
     * platformVersion : 1
     * token : 2.005eE6hDTkX87B075a952488BMXPTB
     * tokenSecret :
     * userGender :
     * userIcon :
     * userId : 3389757024
     * userName :
     * valid : true
     */

    private PlatUserEntity platUser;

    public PlatUserEntity getPlatUser() {
        return platUser;
    }

    public void setPlatUser(PlatUserEntity platUser) {
        this.platUser = platUser;
    }

    public static class PlatUserEntity {
        private int accId;
        private String createTime;
        private int expiresIn;
        private String expiresTime;
        private int id;
        private String platformNname;
        private String platformVersion;
        private String token;
        private String tokenSecret;
        private String userGender;
        private String userIcon;
        private String userId;
        private String userName;
        private boolean valid;

        public int getAccId() {
            return accId;
        }

        public void setAccId(int accId) {
            this.accId = accId;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public int getExpiresIn() {
            return expiresIn;
        }

        public void setExpiresIn(int expiresIn) {
            this.expiresIn = expiresIn;
        }

        public String getExpiresTime() {
            return expiresTime;
        }

        public void setExpiresTime(String expiresTime) {
            this.expiresTime = expiresTime;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPlatformNname() {
            return platformNname;
        }

        public void setPlatformNname(String platformNname) {
            this.platformNname = platformNname;
        }

        public String getPlatformVersion() {
            return platformVersion;
        }

        public void setPlatformVersion(String platformVersion) {
            this.platformVersion = platformVersion;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getTokenSecret() {
            return tokenSecret;
        }

        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        public String getUserGender() {
            return userGender;
        }

        public void setUserGender(String userGender) {
            this.userGender = userGender;
        }

        public String getUserIcon() {
            return userIcon;
        }

        public void setUserIcon(String userIcon) {
            this.userIcon = userIcon;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public boolean isValid() {
            return valid;
        }

        public void setValid(boolean valid) {
            this.valid = valid;
        }
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    private UserInfo userInfo;
}
