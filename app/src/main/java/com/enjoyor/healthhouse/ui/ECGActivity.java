package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.url.JavaScriptInterface;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/6/20.
 */
public class ECGActivity extends BaseActivity {
    @Bind(R.id.ecg_webview)
    WebView ecg_webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ecg_layout);
        ButterKnife.bind(this);
        String ecgdata = getIntent().getIntExtra("info", 1) + "";
        Log.d("wyy----", ecgdata);
        initWebview();
        drawpicture(ecgdata);
    }

    private void drawpicture(final String ecgdata) {
        new Handler().postDelayed(new Runnable() {//异步传本地数据给网页
            @Override
            public void run() {
                transferDataToWeb(ecgdata);
            }
        }, 1000);
    }

    private void transferDataToWeb(String ecgdata) {
        if (ecg_webview != null) {
            ecg_webview.loadUrl("javascript:show(" + ecgdata + ")");   //web网页中已添加了function show(json)方法
        }
    }

    private void initWebview() {
        String url = "http://115.28.37.145/Content/statichtml/ecgChart.html";
        ecg_webview.getSettings().setDefaultTextEncodingName("utf-8");
        ecg_webview.getSettings().setJavaScriptEnabled(true);
        synCookies();//格式化写入cookie，需写在setJavaScriptEnabled之后
        ecg_webview.setWebChromeClient(chromeClient);
        ecg_webview.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface();
        ecg_webview.addJavascriptInterface(javaScriptInterface, "wyy");
        ecg_webview.loadUrl(url);
    }

    private void synCookies() {
        ecg_webview.setWebChromeClient(chromeClient);
        ecg_webview.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
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
}
