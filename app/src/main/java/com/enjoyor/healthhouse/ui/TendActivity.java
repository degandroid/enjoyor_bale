package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.TendInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.JavaScriptInterface;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/31.
 */
public class TendActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tend_ac_week)
    TextView tend_ac_week;
    @Bind(R.id.tend_ac_month)
    TextView tend_ac_month;
    @Bind(R.id.tend_ac_year)
    TextView tend_ac_year;
    @Bind(R.id.tend_ac_web)
    WebView tend_ac_web;
    TendInfo tendInfo;
    int type;//趋势类型
    int time = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tend_ac_layout);
        ButterKnife.bind(this);
        navigation_name.setText("趋势");
        Intent intent = getIntent();
        type = intent.getIntExtra("type", 1);
        initEebview();
        initTextColor();
        initDefault();
        initEvent();


    }

    private void initEebview() {
        String url = "http://www.bailingju.com/Content/statichtml/trend.html";
        tend_ac_web.getSettings().setDefaultTextEncodingName("utf-8");
        tend_ac_web.getSettings().setJavaScriptEnabled(true);
        synCookies();//格式化写入cookie，需写在setJavaScriptEnabled之后
        tend_ac_web.setWebChromeClient(chromeClient);
        tend_ac_web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface();
        tend_ac_web.addJavascriptInterface(javaScriptInterface, "wyy");
        tend_ac_web.loadUrl(url);
    }

    private void synCookies() {
        tend_ac_web.setWebChromeClient(chromeClient);
        tend_ac_web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    protected WebChromeClient chromeClient = new WebChromeClient() {
        // js交互提示
        public boolean onJsAlert(WebView view, String url, String message, android.webkit.JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };

    private void initDefault() {
        tend_ac_month.setTextColor(getResources().getColor(R.color.white));
        tend_ac_month.setBackgroundResource(R.mipmap.xuanzhong2);
        initData(2);
    }

    private void initEvent() {
        tend_ac_week.setOnClickListener(this);
        tend_ac_month.setOnClickListener(this);
        tend_ac_year.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    private void initTextColor() {
        tend_ac_week.setTextColor(getResources().getColor(R.color.black));
        tend_ac_week.setBackgroundColor(getResources().getColor(R.color.white));
        tend_ac_month.setTextColor(getResources().getColor(R.color.black));
        tend_ac_month.setBackgroundColor(getResources().getColor(R.color.white));
        tend_ac_year.setTextColor(getResources().getColor(R.color.black));
        tend_ac_year.setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * 获取健康趋势数据
     */
    private void initData(int i) {
        RequestParams params = new RequestParams();
        params.add("userId", MyApplication.getInstance().getDBHelper().getUser().getUserId() + "");
        if (i == 1) {
            params.add("type", "" + i);
        } else if (i == 2) {
            params.add("type", "" + i);
        } else if (i == 3) {
            params.add("type", "" + i);
        } else {
            Toast.makeText(TendActivity.this, "参数错误", Toast.LENGTH_LONG).show();
        }
        switch (type) {
            case 1:
                params.add("childType", type + "");
                break;
            case 2:
                params.add("childType", type + "");
                break;
            case 4:
                params.add("childType", type + "");
                break;
            case 5:
                params.add("childType", type + "");
                break;
        }
        AsyncHttpUtil.get(UrlInterface.TendInfo_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    TendInfo tendInfos = JsonHelper.getJson(apiMessage.Data, TendInfo.class);
                    String jsondata = null;
                    switch (type) {
                        case 1:
                            jsondata = JSON.toJSONString(tendInfos.getBptdc());
                            break;
                        case 2:
                            jsondata = JSON.toJSONString(tendInfos.getBstdc());
                            break;
                        case 4:
                            jsondata = JSON.toJSONString(tendInfos.getBotdc());
                            break;
                        case 5:
                            jsondata = JSON.toJSONString(tendInfos.getEcgtdc());
                            break;
                    }
                    initInfoFrom(jsondata);
                } else {
                    Log.d("wyy", "数据获取失败");
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("wyy", "" + throwable.toString());
            }
        });

    }

    private void initInfoFrom(String tend_json) {
        if (tend_ac_web != null) {
            if (tend_json != null) {
                String info = JSON.toJSONString(tend_json) + "," + type + "," + time;
                Log.d("wyy----====info----", info);
//                Log.d("wyy----====info----", JSON.toJSONString(tend_json));
                tend_ac_web.loadUrl("javascript:show(" + info + ")");
            }
        }
    }


    @Override
    public void onClick(View v) {
        int key = v.getId();
        initTextColor();
        switch (key) {
            case R.id.tend_ac_week:
                time = 1;
                tend_ac_week.setTextColor(getResources().getColor(R.color.white));
                tend_ac_week.setBackgroundResource(R.mipmap.xuanzhong1);
                initData(1);
                break;
            case R.id.tend_ac_month:
                time = 2;
                tend_ac_month.setTextColor(getResources().getColor(R.color.white));
                tend_ac_month.setBackgroundResource(R.mipmap.xuanzhong2);
                initData(2);
                break;
            case R.id.tend_ac_year:
                time = 3;
                tend_ac_year.setTextColor(getResources().getColor(R.color.white));
                tend_ac_year.setBackgroundResource(R.mipmap.xuanzhong3);
                initData(3);
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }
}
