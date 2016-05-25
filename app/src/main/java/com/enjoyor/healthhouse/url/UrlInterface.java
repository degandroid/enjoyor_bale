package com.enjoyor.healthhouse.url;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UrlInterface {
    public static String RELEASE_URL = "http://www.cnbale.com:9008/healthstationserver/";

    public static String TEXT_URL = "http://115.28.37.145:9008/healthstationserver/";
    //    public static String TEXT_URL = "http://192.168.66.37:8080/bale/";
    //    用户注册接口
    public static String Regist_URL = TEXT_URL + "account/appregister.action";
    //发送验证码接口
    public static String SendMsg_URL = TEXT_URL + "msg/send.action";
    //修改密码的接口
    public static String ModifyPwd_URL = TEXT_URL + "account/apprestpwd.action";
    //获取体检地点接口
    public static String PhysicallAddr_URL = TEXT_URL + "base/dis/machine/list.do";
    //    所有的文件路径主路径为
    public static String FILE_URL = "http://115.28.37.145:9008/healthstationserver/";
    //附近监测点接口
    public static String NearAddress_URL = TEXT_URL + "base/dis/machine/list.do";
    //上传文件接口
    public static String UpDateFile_URL = TEXT_URL + "/doupload/save.do";
    //    public static String UpDateFile_URL =  "http://192.168.66.33/doupload/save.do";
    //随手记提交接口
    public static String Notes_URL = TEXT_URL + "/record/notes.do";
    //获取健康评估信息的接口
    public static String AccessHealInfo_URL = TEXT_URL + "/app/evaluate.do";

}
