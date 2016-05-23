package com.enjoyor.healthhouse;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.UserInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.ui.BaseActivity;
import com.enjoyor.healthhouse.ui.LoginActivity;
import com.enjoyor.healthhouse.ui.MainTabActivity;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

public class MainActivity extends BaseActivity {
    private String LOGIN_URL = "account/applogin.action";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Handler x = new Handler();
        x.postDelayed(new splashhandler(), 3000);

    }

    class splashhandler implements Runnable {

        public void run() {
            Intent intent = new Intent(MainActivity.this, MainTabActivity.class);
            startActivity(intent);
            finish();
//            initData();
        }
    }
    private void initData() {
        UserInfo userInfo = MyApplication.getInstance().getDBHelper().getUser();
        if(userInfo!=null){
            RequestParams params = new RequestParams();
            params.add("origin", String.valueOf("AndroidApp"));
            params.add("userLoginName", userInfo.getLoginName());
            params.add("userLoginPwd", userInfo.getUserLoginPwd());
            params.add("userLoginType", String.valueOf("2"));
            AsyncHttpUtil.post(UrlInterface.TEXT_URL+LOGIN_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        Intent intent = new Intent(MainActivity.this, MainTabActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        }else{
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }

    }
}