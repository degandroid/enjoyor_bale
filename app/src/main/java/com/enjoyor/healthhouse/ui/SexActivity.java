package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 */
public class SexActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.sex_rl_mail)
    RelativeLayout sex_rl_mail;
    @Bind(R.id.sex_male_delete)
    ImageView sex_male_delete;
    @Bind(R.id.sex_female_)
    RelativeLayout sex_female_;
    @Bind(R.id.sex_female_delete)
    ImageView sex_female_delete;
    boolean TAG = false;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sex_ac_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        sex_rl_mail.setOnClickListener(this);
        sex_female_.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        re_back .setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("性别");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
//        sex_male_delete.setVisibility(View.GONE);
        sex_female_delete.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.sex_rl_mail:
                sex_male_delete.setVisibility(View.VISIBLE);
                sex_female_delete.setVisibility(View.GONE);
                TAG = true;
                break;
            case R.id.sex_female_:
                sex_male_delete.setVisibility(View.GONE);
                sex_female_delete.setVisibility(View.VISIBLE);
                TAG = false;
                break;
            case R.id.tv_right:
                saveSex();
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }

    private void saveSex() {
        if (TAG = true) {
            sex = "男";
        } else {
            sex = "nv";
        }
        RequestParams params = new RequestParams();
        params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
        params.add("sex", sex);
        AsyncHttpUtil.post(UrlInterface.ModifyInfo_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Toast.makeText(SexActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                    Intent intent = getIntent();
                    setResult(200,intent);
                    intent.putExtra("sex", sex);
                    finish();
                } else {
                    Toast.makeText(SexActivity.this, "性别修改失败", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
