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
    public static String ModifyPwd_URL = TEXT_URL + "account/resetpwd.action";
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
    //个人中心我的资料接口
    public static String Data_URL = TEXT_URL + "account/getaccountinfo.action";
    //验证码验证接口
    public static String Verify_URL = TEXT_URL + "msg/verify.action";
    //变更绑定手机接口
    public static String Modify_Bind_Phone_URL = TEXT_URL + "account/app/resetphone.action";
    //个人中心意见反馈接口
    public static String Suggest_URL = TEXT_URL + "feedback/app.do";
    //修改用户信息接口
    public static String ModifyInfo_URL = TEXT_URL + "account/app/updateUserInfo.action";
    //获取血压报告接口
    public static String BpReport_URL = TEXT_URL + "app/bp/evaluate.do";
    //获取血糖评估接口
    public static String BiReport_URL = TEXT_URL + "app/bs/evaluate.do";
    //血氧评估接口
    public static String BoReport_URL = TEXT_URL + "app/bo/evaluate.do";
    //获取心电评估接口
    public static String EcgReport_URL = TEXT_URL + "app/ecg/evaluate.do";
    //获取人体成分评估接口
    public static String BodyReport_URL = TEXT_URL + "app/body/evaluate.do";
    //获取资讯分类文章接口
    public static String InfoClass_URL = TEXT_URL + "articles/classifys.do";
    //资讯文章查询
    public static String InfoClassSelect_URL = TEXT_URL + "articles/.do";
    //用户上传头像接口
    public static String UpLogo_URL = TEXT_URL + "doupload/saveheadimg.do";
}
