package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.ArtitleDetail;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.net.URL;

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
    TextView com_ac_layout_webview;
    int id;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communtity_com_ac_layout);
        ButterKnife.bind(this);
        progress();
        navigation_name.setVisibility(View.INVISIBLE);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 11);
//        articles/{id}.do
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
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + "articles/" + id + ".do", params, new AsyncHttpResponseHandler() {
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
        com_ac_layout_num.setText("阅读量   " + artitleDetail.getPageViews() + "");
        com_ac_layout_webview.setText(artitleDetail.getContent());
        cancel();
    }
}