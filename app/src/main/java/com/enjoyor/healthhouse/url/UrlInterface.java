package com.enjoyor.healthhouse.url;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UrlInterface {
    public static String RELEASE_URL = "http://www.cnbale.com:9008/healthstationserver/";

    public static String TEXT_URL = "http://115.28.37.145:9008/healthstationserver/";
    //    用户注册接口
    public static String Regist_URL = TEXT_URL + "account/appregister.action";
    //发送验证码接口
    public static String SendMsg_URL = TEXT_URL + "msg/send.action";
    //修改密码的接口
    public static String ModifyPwd_URL = TEXT_URL + "account/apprestpwd.action";
    //获取体检地点接口
    public static String PhysicallAddr_URL = TEXT_URL + "base/dis/machine/list.do";
    //    所有的文件路径主路径为
    public static String FILE_URL = "http://115.28.37.145:9008/healthstationserver/files/";
    //附近监测点接口
    public static String NearAddress_URL = TEXT_URL + "base/dis/machine/list.do";


}
