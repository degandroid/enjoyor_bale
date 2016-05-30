package com.enjoyor.healthhouse.url;

import android.text.TextUtils;
import android.webkit.JavascriptInterface;

/**
 * Created by YuanYuan on 2016/4/13.
 */
public class JavaScriptInterface {
    @JavascriptInterface
    public void command(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        //根据网页交互回传的json串进行操作。可以将其传递给外部页面进行处理增加灵活性
    }

    @JavascriptInterface
    public void show(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return;
        }
        //根据网页交互回传的json串进行操作。可以将其传递给外部页面进行处理增加灵活性
    }
}
