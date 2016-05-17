package com.enjoyor.healthhouse.net;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class ApiMessage {
    //通用状态,&#32;&#32;&#32;成功的以正数开头，失败的以负数（-）开头
//    public &#32;static &#32;final &#32;int&#32;STATUS_SUCCESS&#32;=&#32;1001;&#32;//操作成功（数据查询，保存，更新…………………………）
//    public &#32;static &#32;final &#32;int&#32;STATUS_FAIL&#32;=&#32;-1001;&#32;//操作失败（数据查询，保存，更新…………………………）
//    public &#32;static &#32;final &#32;int&#32;STATUS_INNERERROR&#32;=&#32;-500;//内部错误
//    public &#32;static &#32;final &#32;int&#32;STATUS_NOMORE&#32;=&#32;-200;//没有更多的数据
//    public &#32;static &#32;final &#32;int&#32;STATUS_NORECORD&#32;=&#32;-204;//没有记录
//    public &#32;static &#32;&#32;final &#32;int&#32;STATUS_ERROR_PARAM=-100;//参数验证错误
    private int STATUS_SUCCESS = 1001;//操作成功
    private int STATUS_FAIL = -1001;//操作失败
    private int STATUS_INNERERROR = -500;//内部错误
    private int STATUS_NOMORE = -200;//没有更多的数据
    private int STATUS_NORECORD = -204;//没有记录
    private int STATUS_ERROR_PARAM = -100;//参数验证错误
    public int Code;
    public String Msg = "";
    public String Data = "";

    public ApiMessage() {
        Code = 1001;
        Msg = "Success";
        Data = "";
    }

    public static ApiMessage FromJson(String jsonText) {
        if (jsonText.isEmpty())
            return null;

        ApiMessage newMsg = new ApiMessage();

        try {
            // create json analyst
            JSONTokener analyst = new JSONTokener(jsonText);

            // fetch message
            JSONObject message = new JSONObject(jsonText);
            newMsg.Code = message.getInt("Errcode");
            newMsg.Msg = message.getString("Msg");
            newMsg.Data = message.getString("Data");

        } catch (JSONException ex) {
            // handle exception
            newMsg.Code = -1;
            newMsg.Msg = ex.getMessage();
        }

        return newMsg;
    }

    public static ApiMessage Create(int code, String msg, String data) {
        ApiMessage api = new ApiMessage();

        api.Code = code;
        api.Msg = msg;
        api.Data = data;

        return api;
    }

    public void Set(int code, String msg, String data) {
        this.Code = code;
        this.Msg = msg;
        this.Data = data;
    }
}
