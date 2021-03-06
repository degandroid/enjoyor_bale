package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.utils.AppManagerUtil;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 * 个人中心设置页面
 */
public class SettingActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    //    @Bind(R.id.setting_ac_help)
//    RelativeLayout setting_ac_help;
//    @Bind(R.id.setting_ac_secret)
//    RelativeLayout setting_ac_secret;
    @Bind(R.id.setting_ac_cach)
    RelativeLayout setting_ac_cach;
    @Bind(R.id.setting_ac_about)
    RelativeLayout setting_ac_about;
    @Bind(R.id.setting_ac_version)
    RelativeLayout setting_ac_version;
    @Bind(R.id.setting_ac_speak)
    RelativeLayout setting_ac_speak;
    @Bind(R.id.setting_exit)
    Button setting_exit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_av_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        re_back.setOnClickListener(this);
        setting_exit.setOnClickListener(this);
        setting_ac_version.setOnClickListener(this);
        setting_ac_about.setOnClickListener(this);
        setting_ac_speak.setOnClickListener(this);
        setting_ac_cach.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("设置");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.setting_exit:
                if (BaseDate.getSessionId(SettingActivity.this) != null) {
                    if (MyApplication.getInstance().getDBHelper().clearUser()) {
                        Intent intent = new Intent(SettingActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    AppManagerUtil.getAppManager().finishAllActivity();
                } else {
                    Snackbar.make(container, "您还未登陆，请先登陆", Snackbar.LENGTH_SHORT).show();
                }
                break;
            case R.id.setting_ac_version:
                Intent intent_version = new Intent(SettingActivity.this, VersionActivity.class);
                startActivity(intent_version);
                break;
            case R.id.setting_ac_about:
                Intent intent_about = new Intent(this, AboutOursActivity.class);
                startActivity(intent_about);
                break;
            case R.id.setting_ac_speak:
                Intent intent_agreement = new Intent(this, AgreeMentActivity.class);
                startActivity(intent_agreement);
                break;
            case R.id.setting_ac_cach:
                ImageLoader.getInstance().clearDiskCache();
                ImageLoader.getInstance().clearMemoryCache();
                Snackbar.make(container, "缓存清理干净了哦！手机棒棒哒！", Snackbar.LENGTH_SHORT).show();
                break;
        }
    }

    //结束当前应运进程
    private void exiting() {
        try {
            System.exit(0);
        } catch (Exception e) {
            try {
                android.os.Process.killProcess(android.os.Process.myPid());
            } catch (Exception e1) {
            }
        }
    }
}
