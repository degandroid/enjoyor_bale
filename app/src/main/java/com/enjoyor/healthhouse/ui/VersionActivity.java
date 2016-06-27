package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.VersionInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.enjoyor.healthhouse.utils.VersionUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

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
        version_check.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("版本信息");
        version.setText("v" + VersionUtils.getVersionName(this) + "." + VersionUtils.getVersionCode(this));
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.version_check:
                Log.v("wyy----version---", "v" + VersionUtils.getVersionName(this) + "." + VersionUtils.getVersionCode(this));
                String version = VersionUtils.getVersionName(this) + "." + VersionUtils.getVersionCode(this);
//                Intent intent = new Intent();
//                intent.setAction("android.intent.action.VIEW");
                isUpdateVersion(version);//更新版本
                break;
        }
    }

    private void isUpdateVersion(final String version) {
        RequestParams params = new RequestParams();
        params.add("device", "android");
        AsyncHttpUtil.get(UrlInterface.Update_Version_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.v("wyy----version--json---",json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    VersionInfo versionInfo = JsonHelper.getJson(apiMessage.Data, VersionInfo.class);
                    if (!version.equals(versionInfo)) {
                        //更新版本
                        if (!StringUtils.isEmpty(versionInfo.getUrl())) {
                            Intent intent = new Intent();
//                            Log.v("wyy----versionInfo.getUrl())---",versionInfo.getUrl());
                            intent.setAction("android.intent.action.VIEW");
                            Uri content_url = Uri.parse(versionInfo.getUrl());
                            intent.setData(content_url);
                            startActivity(intent);
                        } else {
                            Toast.makeText(VersionActivity.this, "请检查下载地址是否正确", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(VersionActivity.this, "当前已是最新版本", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
