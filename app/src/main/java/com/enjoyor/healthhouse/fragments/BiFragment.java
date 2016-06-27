package com.enjoyor.healthhouse.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.BiReport;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.ui.HistoryActivity;
import com.enjoyor.healthhouse.ui.PhysicallocationActivity;
import com.enjoyor.healthhouse.ui.TendActivity;
import com.enjoyor.healthhouse.url.JavaScriptInterface;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/29.
 * 血糖评估
 */
public class BiFragment extends BaseFragment implements View.OnClickListener {
    private View view;
    @Bind(R.id.bp_fg_web)
    WebView bp_fg_web;
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
    @Bind(R.id.bp_fg_history)
    RelativeLayout bp_fg_history;
    @Bind(R.id.bp_fg_tend)
    RelativeLayout bp_fg_tend;
    @Bind(R.id.bp_fg_top)
    RelativeLayout bp_fg_top;
    @Bind(R.id.health_ry_empty)
    RelativeLayout health_ry_empty;
    @Bind(R.id.button)
    Button button;
    BiReport biReport;
    @Bind(R.id.bp_fg_bottom)
    LinearLayout bp_fg_bottom;
    Dialog dialog;
    @Bind(R.id.bp_fg_title)
    TextView bp_fg_title;
    @Bind(R.id.bp_fg_img)
    ImageView bp_fg_img;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bp_fg_layout, null);
        dialog = createLoadingDialog(getActivity(), "正在加载数据...");
        dialog.show();
        ButterKnife.bind(this, view);
        bp_fg_title.setText("当前血糖值");
        bp_fg_img.setImageResource(R.mipmap.bs_ab);
        initView();
        initEvent();
        return view;
    }

    private void initWebview() {
        String url = "http://115.28.37.145/Content/statichtml/sugarAss.html";
        bp_fg_web.getSettings().setDefaultTextEncodingName("utf-8");
        bp_fg_web.getSettings().setJavaScriptEnabled(true);
//        WebSettings webSettings = bp_fg_web.getSettings();
//        webSettings.setSavePassword(false);
//        webSettings.setSaveFormData(false);
//        webSettings.setSupportZoom(false);
//        bp_fg_web.loadUrl(url);
//        bp_fg_web.addJavascriptInterface(new JsInterface(getActivity()), "AndroidWebView");
//        bp_fg_web.setWebChromeClient(new WebChromeClient());
        synCookies();//格式化写入cookie，需写在setJavaScriptEnabled之后
        bp_fg_web.setWebChromeClient(chromeClient);
        bp_fg_web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface();
        bp_fg_web.addJavascriptInterface(javaScriptInterface, "wyy");
        bp_fg_web.loadUrl(url);
        if (biReport.getBeanList().size() > 0) {
            String _json_xuetang = biReport.getBeanList().get(0).getBloodSugar() + "," + biReport.getBeanList().get(0).getBloodSugarType();
            Log.d("wyy-------", _json_xuetang);
//            bp_fg_web.loadUrl("javascript:show(" + _json_xuetang + ")");
            drawpicture(_json_xuetang);
        }
        dialog.dismiss();
    }

    private void drawpicture(final String json_xuetang) {
        new Handler().postDelayed(new Runnable() {//异步传本地数据给网页
            @Override
            public void run() {
                transferDataToWeb(json_xuetang);
            }
        }, 1000);
    }

    private void transferDataToWeb(String json_xuetang) {
        if (bp_fg_web != null) {
            bp_fg_web.loadUrl("javascript:show(" + json_xuetang + ")");
        }  //web网页中已添加了function show(json)方法
    }

    private void synCookies() {
        bp_fg_web.setWebChromeClient(chromeClient);
        bp_fg_web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
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

    private void initEvent() {
        button.setOnClickListener(this);
        bp_fg_history.setOnClickListener(this);
        bp_fg_tend.setOnClickListener(this);
    }

    private void initView() {
        RequestParams params = new RequestParams();
        params.add("recordId", "" + MyApplication.getInstance().getDBHelper().getHealthRecord().getRecordId());
        AsyncHttpUtil.get(UrlInterface.BiReport_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("wyy-------", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    bp_fg_top.setVisibility(View.VISIBLE);
                    biReport = JsonHelper.getJson(apiMessage.Data, BiReport.class);
                    saveInfo(biReport);
                    initWebview();
                } else {
                    health_ry_empty.setVisibility(View.VISIBLE);
                    bp_fg_bottom.setVisibility(View.GONE);
                    dialog.dismiss();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void saveInfo(BiReport biReport) {
        bp_fg_suggest.setText(biReport.getHealthSuggest());
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.button:
                Intent intent_go = new Intent(getActivity(), PhysicallocationActivity.class);
                startActivity(intent_go);
                break;
            case R.id.bp_fg_history:
                Intent intent_xuetang = new Intent(getActivity(), HistoryActivity.class);
                intent_xuetang.putExtra("fromWhere", Constant.FROM_XUETANG);
                startActivity(intent_xuetang);
                break;
            case R.id.bp_fg_tend:
                Intent intent_tend = new Intent(getActivity(), TendActivity.class);
                intent_tend.putExtra("type", 2);
                startActivity(intent_tend);
                break;
        }
    }

//    private class JsInterface {
//        private Context mContext;
//
//        public JsInterface(Context context) {
//            this.mContext = context;
//        }
//
//        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
//        @JavascriptInterface
//        public void showInfoFromJs(String name) {
//            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
//        }
//    }
}
