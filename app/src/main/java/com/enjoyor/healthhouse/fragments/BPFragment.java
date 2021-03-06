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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.BpRecord;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.ui.HistoryActivity;
import com.enjoyor.healthhouse.ui.PhysicallocationActivity;
import com.enjoyor.healthhouse.ui.TendActivity;
import com.enjoyor.healthhouse.url.JavaScriptInterface;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/24.
 * 血压报告
 */
public class BPFragment extends BaseFragment implements View.OnClickListener {
    View view;
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
    @Bind(R.id.bp_fg_history)
    RelativeLayout bp_fg_history;
    @Bind(R.id.bp_fg_tend)
    RelativeLayout bp_fg_tend;
    @Bind(R.id.bp_fg_web)
    WebView bp_fg_web;
    @Bind(R.id.bp_fg_top)
    RelativeLayout bp_fg_top;
    @Bind(R.id.health_ry_empty)
    RelativeLayout health_ry_empty;
    @Bind(R.id.button)
    Button button;
    BpRecord bpRecord;
    @Bind(R.id.bp_fg_bottom)
    LinearLayout bp_fg_bottom;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        dialog = createLoadingDialog(getActivity(), "正在加载数据...");
        dialog.show();
        view = inflater.inflate(R.layout.bp_fg_layout, null);
        ButterKnife.bind(this, view);
        initView();
        initWebview();
        initEvent();
        return view;
    }

    private void initWebview() {
        String url = "http://115.28.37.145/Content/statichtml/bloodCircle.html";
        bp_fg_web.getSettings().setDefaultTextEncodingName("utf-8");
        bp_fg_web.getSettings().setJavaScriptEnabled(true);
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
        if (!StringUtils.isEmpty(MyApplication.getInstance().getDBHelper().getHealthRecord().getRecordId() + "")) {
            params.add("recordId", MyApplication.getInstance().getDBHelper().getHealthRecord().getRecordId() + "");
            AsyncHttpUtil.get(UrlInterface.BpReport_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        bpRecord = JsonHelper.getJson(apiMessage.Data, BpRecord.class);
                        saveInfo(bpRecord);
                        drawpicture(json);
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
    }

    private void drawpicture(final String json) {
        new Handler().postDelayed(new Runnable() {//异步传本地数据给网页
            @Override
            public void run() {
                transferDataToWeb(json);
            }
        }, 1000);
    }

    private void transferDataToWeb(String json) {
        if (bp_fg_web != null) {
            if (bpRecord != null) {
                String info = bpRecord.getDiastolicPressure() + "," + bpRecord.getSystolicPressure() + "," + "0.5";
                bp_fg_web.loadUrl("javascript:show(" + info + ")");   //web网页中已添加了function show(json)方法
                bp_fg_top.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        }
    }

    private void saveInfo(BpRecord bpRecord) {
        bp_fg_suggest.setText(bpRecord.getHealthSuggest());
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.bp_fg_history:
                Intent intent_xueya = new Intent(getActivity(), HistoryActivity.class);
                intent_xueya.putExtra("fromWhere", Constant.FROM_XUEYA);
                startActivity(intent_xueya);
                break;
            case R.id.bp_fg_tend:
                Intent intent_bp = new Intent(getActivity(), TendActivity.class);
                intent_bp.putExtra("type", 1);
                startActivity(intent_bp);
                break;
            case R.id.button:
                Intent intent_go = new Intent(getActivity(), PhysicallocationActivity.class);
                startActivity(intent_go);
                break;
        }
    }
}
