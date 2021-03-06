package com.enjoyor.healthhouse.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.ArtitleDetail;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/6/1.
 */
public class CommunitityCommonActivity extends BaseActivity {
    @Bind(R.id.com_ac_layout_title)
    TextView com_ac_layout_title;
    @Bind(R.id.com_ac_layout_time)
    TextView com_ac_layout_time;
    @Bind(R.id.com_ac_layout_num)
    TextView com_ac_layout_num;
    @Bind(R.id.com_ac_layout_webview)
    WebView com_ac_layout_webview;
    int id;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communtity_com_ac_layout);
        dialog = createLoadingDialog(CommunitityCommonActivity.this, "正在加载数据....");
        ButterKnife.bind(this);
        dialog.show();
        WebSettings settings = com_ac_layout_webview.getSettings();
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setJavaScriptEnabled(true);
        navigation_name.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 11);
        initData();
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        RequestParams params = new RequestParams();
        AsyncHttpUtil.get(UrlInterface.getArticles(id), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    ArtitleDetail artitleDetail = JsonHelper.getJson(apiMessage.Data, ArtitleDetail.class);
                    saveInfo(artitleDetail);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void saveInfo(ArtitleDetail artitleDetail) {
        com_ac_layout_title.setText(artitleDetail.getTitle());
        com_ac_layout_time.setText(artitleDetail.getModifyTime());
        if (!StringUtils.isEmpty(artitleDetail.getPageViews())) {
            com_ac_layout_num.setText("阅读量   " + artitleDetail.getPageViews() + "");
        } else {
            com_ac_layout_num.setText("阅读量:   0");
        }
        setWebView(artitleDetail.getContent());
    }

    private void setWebView(String content) {
        com_ac_layout_webview.getSettings().setBlockNetworkImage(true);
        com_ac_layout_webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                view.setVisibility(View.VISIBLE);
                com_ac_layout_webview.getSettings().setBlockNetworkImage(false);
                if (!com_ac_layout_webview.getSettings().getLoadsImagesAutomatically()) {
                    com_ac_layout_webview.getSettings().setLoadsImagesAutomatically(true);
                }
            }
        });
        com_ac_layout_webview.loadDataWithBaseURL(null, content, "text/html", "utf-8", null);
        dialog.dismiss();
    }
}