package com.enjoyor.healthhouse.url;

/**
 * Created by Administrator on 2016/5/9.
 */
public class UrlInterface {
    //    静态页面接口
    public static String JS_PAGE_URL = "http://www.bailingju.com/Content/";
    //    正式库数据接口
//    public static String TEXT_URL = "http://ejk.bailingju.com:8099/BaleServer/";


    public static String RELEASE_URL = "http://www.cnbale.com:9008/healthstationserver/";
    public static String TEXT_URL = "http://115.28.37.145:9008/healthstationserver/";

//    public static String BasePath = "http://192.168.8/healthstationserver";

//        public static String TEXT_URL = "http://192.168.66.37:8080/bale/";

    //    用户注册接口
    public static String Regist_URL = TEXT_URL + "account/appregister.action";
    //发送验证码接口
    public static String SendMsg_URL = TEXT_URL + "msg/send.action";
    //修改密码的接口
    public static String ModifyPwd_URL = TEXT_URL + "account/apprestpwd.action";
    //获取体检地点接口
    public static String PhysicallAddr_URL = TEXT_URL + "base/dis/machine/list.do";
    //    所有的文件路径主路径为
    public static String FILE_URL = "http://ejk.bailingju.com:8099/BaleServer";
    //附近监测点接口
    public static String NearAddress_URL = TEXT_URL + "base/dis/machine/list.do";
    //上传文件接口
    public static String UpDateFile_URL = TEXT_URL + "doupload/save.do";
    //    public static String UpDateFile_URL =  "http://192.168.66.33/doupload/save.do";
    //随手记提交接口
    public static String Notes_URL = TEXT_URL + "record/notes.do";
    //获取健康评估信息的接口
    public static String AccessHealInfo_URL = TEXT_URL + "app/evaluate.do";
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
    //    个人资料修改密码
    public static String InfoModifyPwd_URL = TEXT_URL + "account/resetpwd.action";
    //健康趋势接口
    public static String TendInfo_URL = TEXT_URL + "app/tendency.do";
    //第三方登录接口
    public static String Login_Third_URL = TEXT_URL + "plat/login.action";
    //获取第三方登录信息接口
    public static String Get_Thhird_LoginInfo_URL = TEXT_URL + "plat/info.do";
    //绑定第三方登录手机
    public static String Bind_Phone_URL = TEXT_URL + "plat/bind.action";
    //浏览器调用百度地图接口
    public static String Web_URL = "http://api.map.baidu.com/marker?";
    //搜索书屋列表--------------卡路里换算
    public static String FOOD_URL = TEXT_URL + "app/food/search.do";
    //用户登录------------------登录
    public static String LOGIN_URL = TEXT_URL + "account/applogin.action";
    //体检报告------------------我的报告
    public static String RECORD_URL = TEXT_URL + "app/getrecordofinfo.do";
    //轮播图--------------------首页
    public static String BANNER_URL = TEXT_URL + "display/index/banner.do";
    //咨询----------------------首页
    public static String ARTICLES_URL = TEXT_URL + "articles/app/index.do";
    //健康文档------------------随手记
    public static String HEALTHFILE_URL = TEXT_URL + "app/getrecordlist.do";

    //八项入口的历史记录--------历史
    public static String BP_URL = TEXT_URL + "app/bplist.do";//血压历史
    public static String BMI_URL = TEXT_URL + "app/bmilist.do";//身高体重历史
    public static String BS_URL = TEXT_URL + "app/bslist.do";//血糖历史
    public static String BO_URL = TEXT_URL + "app/bolist.do";//血氧历史
    public static String WL_URL = TEXT_URL + "app/whrlist.do";//腰围历史
    public static String TL_URL = TEXT_URL + "app/temperlist.do";//体温历史
    public static String ECG_URL = TEXT_URL + "app/ecglist.do";//心电历史

    //八项入口录入--------------录入
    public static String SAVE_BP_URL = TEXT_URL + "app/savebp.action";//血压
    public static String SAVE_BMI_URL = TEXT_URL + "app/savebmi.action";//体重，身高
    public static String SAVE_BS_URL = TEXT_URL + "app/savebs.action";//血糖
    public static String SAVE_BO_URL = TEXT_URL + "app/savebo.action";//血氧
    public static String SAVE_WL_URL = TEXT_URL + "app/savewaistLine.action";//腰围
    public static String SAVE_TP_URL = TEXT_URL + "app/savetmper.action";//体温
    public static String SAVE_ECG_URL = TEXT_URL + "app/saveecg.action";//心率

    public static String getArticles(int id) {
        return TEXT_URL + "articles/" + id + ".do";
    }

    public static String getRecord(String id) {
        return TEXT_URL + "advice/record/" + id + ".action";
    }

    public static String getResources(int voice) {
        return TEXT_URL + "resources/file/" + voice + ".action";
    }

    public static String getNotes(Long recordId) {
        return TEXT_URL + "record/notes/" + recordId + ".action";
    }

    public static String getSelf(Long recordId) {
        return TEXT_URL + "record/self/" + recordId + ".action";
    }

    //    趋势列表数据接口
    public static String Tend_Url = JS_PAGE_URL + "statichtml/trend.html";
public  static String Text_Version = "http://www.bailingju.com:9008/healthstationserver/";
    //更新版本接口
    public static String Update_Version_URL = Text_Version + "config/version/android.do";
}