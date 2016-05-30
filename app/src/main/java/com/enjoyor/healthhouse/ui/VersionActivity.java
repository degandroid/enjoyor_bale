package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/27.
 * 个人中心版本信息页面
 */
public class VersionActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.version)
    TextView version;
    @Bind(R.id.version_check)
    TextView version_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        re_back.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("版本信息");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.version_check:
                break;
        }
    }
}
