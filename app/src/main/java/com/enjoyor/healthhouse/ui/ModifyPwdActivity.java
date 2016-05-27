package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
    @Bind(R.id.modifypwd_accesnum)
    TextView modifypwd_accesnum;
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
        initCode();
        initEvent();
    }

    private void initEvent() {
        modifypwd_accesnum.setOnClickListener(this);
        modify_save.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    private void initCode() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    modifypwd_accesnum.setText("剩余" + msg.what + "秒");
                }
            }
        };
    }

    private void initView() {
        navigation_name.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.modifypwd_accesnum:
                sendMsg();
                sendMsgtoPhone();
                break;
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
        final RequestParams param = new RequestParams();
        if (!StringUtils.isBlank(modifypwd_et_access.getText().toString().trim())) {
            RequestParams params = new RequestParams();
            param.add("service", "mob");
            param.add("phone", ""+MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber());
            param.add("code", "" + modifypwd_et_access.getText().toString().trim());
            AsyncHttpUtil.post(UrlInterface.Verify_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    if (modify_pwd.getText().toString().trim().equals(modify_again_pwd.getText().toString().trim())) {
//            Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
                        param.add("newpwd", modify_pwd.getText().toString().trim());
                        param.add("phone", MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber());
                        AsyncHttpUtil.post(UrlInterface.ModifyPwd_URL, param, new AsyncHttpResponseHandler() {
                            @Override
                            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                String json = new String(bytes);
                                ApiMessage apimessage = ApiMessage.FromJson(json);
                                if (apimessage.Code == 1001) {
                                    finish();
                                    Toast.makeText(ModifyPwdActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ModifyPwdActivity.this, "该电话号码未注册，请先注册！", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                            }
                        });
                    } else {
                        Toast.makeText(ModifyPwdActivity.this, "两次输入密码不一致，请重新输入", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
//            param.add("mcode", modifypwd_et_access.getText().toString().trim());
        } else {
            Toast.makeText(this, "请输入验证码", Toast.LENGTH_LONG).show();
        }
    }

    private void sendMsgtoPhone() {
        RequestParams params = new RequestParams();
        params.add("service", "mob");
        params.add("phone", "13520036163");
        AsyncHttpUtil.post(UrlInterface.SendMsg_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    Toast.makeText(ModifyPwdActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ModifyPwdActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private void sendMsg() {
        modifypwd_accesnum.setEnabled(false);
        modifypwd_accesnum.setText("获取验证码");
        //倒记时
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        count--;
                        handler.sendEmptyMessage(count);
                        if (count == 0) {
                            timer.cancel();
                            reSet();
                        }
                    }
                });
            }
        };

        timer.schedule(task, 1000, 1000);
    }

    private void reSet() {
        modifypwd_accesnum.setText("获取验证码");
        modifypwd_accesnum.setEnabled(true);
        count = 30;
    }
}
