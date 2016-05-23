package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/23.
 */
public class MySelfCheckActivity extends BaseActivity {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.myselfcheck_web)
    WebView myselfcheck_web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myselfcheck_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navigation_name.setText("我的自检");
    }
}
