package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ScreenUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 * 个人中心修改密码页面
 */
public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.modifypwd_et_access)
    EditText modifypwd_et_access;
    @Bind(R.id.modify_pwd)
    EditText modify_pwd;
    @Bind(R.id.modify_again_pwd)
    EditText modify_again_pwd;
    @Bind(R.id.modify_save)
    Button modify_save;
    private int count = 60;//30秒倒计时
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_pwd_ac_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        modify_save.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.modify_save:
                modifycommit();
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }

    //保存修改密码的方法
    private void modifycommit() {
        if (!StringUtils.isBlank(modifypwd_et_access.getText().toString().trim())) {
            if (!StringUtils.isBlank(modify_pwd.getText().toString().trim()) && !StringUtils.isBlank(modify_again_pwd.getText().toString().trim())) {
                RequestParams params = new RequestParams();
                params.add("id", "" + MyApplication.getInstance().getDBHelper().getUser().getAccountId());
                params.add("name", "" + MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber());
                params.add("oldpwd", "" + modifypwd_et_access.getText().toString().trim());
                params.add("newpwd", "" + modify_pwd.getText().toString().trim());
                AsyncHttpUtil.post(UrlInterface.InfoModifyPwd_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String json = new String(bytes);
                        Log.d("wyy----",json);
                        ApiMessage apiMessage = ApiMessage.FromJson(json);
                        if (apiMessage.Code == 1001) {
                            Toast.makeText(ModifyPwdActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                            Intent intent_login = new Intent(ModifyPwdActivity.this, LoginActivity.class);
                            startActivity(intent_login);
                            finish();
                        } else {
                            Toast.makeText(ModifyPwdActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });

            } else {
                Toast.makeText(ModifyPwdActivity.this, "请输入新密码", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(ModifyPwdActivity.this, "原密码不能为空", Toast.LENGTH_LONG).show();
        }
    }
}
