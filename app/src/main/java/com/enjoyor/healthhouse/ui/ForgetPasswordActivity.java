package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.MatcherUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.container)
    CoordinatorLayout container;

    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Bind(R.id.et_phonenumber)
    EditText et_phonenumber;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.tv_code)
    TextView tv_code;
    @Bind(R.id.bt_commit)
    Button bt_commit;

    private int count = 60;//60秒倒计时
    private Handler handler;
    private String phoneNumber;
    private String password;
    private String MSG_CODE = "msg/send.action";
    private String VERIFY_CODE = "msg/verify.action";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassword);
        ButterKnife.bind(this);

        navigation_name.setText("忘记密码");
        initOnClick();
        initCode();
    }

    private void initCode() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    tv_code.setText(msg.what + "秒");
                }
            }
        };
    }


    private void initOnClick() {
        et_phonenumber.setOnClickListener(this);
        et_password.setOnClickListener(this);
        tv_code.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.bt_commit:
                if (isCorrect()) {
                    verify(et_phonenumber.getText().toString().trim(), et_password.getText().toString().trim());
                }
                break;
            case R.id.tv_code:
                if (StringUtils.isBlank(et_phonenumber.getText().toString())) {
                    Snackbar.make(container, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
                    et_phonenumber.requestFocus();
                }else if(!MatcherUtil.isMobileNumber(et_phonenumber.getText().toString())){
                    Snackbar.make(container, "请输入正确的手机号", Snackbar.LENGTH_SHORT).show();
                    et_phonenumber.requestFocus();
                } else {
                    sendingAutoCode();
                    getCode(et_phonenumber.getText().toString());
                }

                break;
        }
    }

    private void getCode(String phone) {
        RequestParams params = new RequestParams();
        params.add("service", String.valueOf("mob"));
        params.add("phone", phone);
        AsyncHttpUtil.post(UrlInterface.TEXT_URL + MSG_CODE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {

                } else {
                    reSet();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    private void verify(String phone,String code) {
        RequestParams params = new RequestParams();
        params.add("service", String.valueOf("mob"));
        params.add("phone", phone);
        params.add("code", code);
        AsyncHttpUtil.post(UrlInterface.TEXT_URL + VERIFY_CODE, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Intent intent = new Intent(ForgetPasswordActivity.this, NewPasswordActivity.class);
                    intent.putExtra("YanZheng", et_password.getText().toString().trim());
                    intent.putExtra("phone", et_phonenumber.getText().toString().trim());
                    startActivity(intent);
                } else {
                    Snackbar.make(container, "验证码输入错误错误", Snackbar.LENGTH_SHORT).show();
                    et_password.setText("");
                    et_password.requestFocus();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    private boolean isCorrect() {
        phoneNumber = et_phonenumber.getText().toString().trim();
        password = et_password.getText().toString().trim();
        if (StringUtils.isBlank(phoneNumber)) {
            Snackbar.make(container, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
            et_phonenumber.requestFocus();
            return false;
        }else if(!MatcherUtil.isMobileNumber(phoneNumber)){
            Snackbar.make(container, "请输入正确的手机号", Snackbar.LENGTH_SHORT).show();
            et_phonenumber.requestFocus();
            return false;
        } else if (StringUtils.isBlank(password)) {
            Snackbar.make(container, "验证码不能为空", Snackbar.LENGTH_SHORT).show();
            et_password.requestFocus();
            return false;
        }
        return true;
    }

    public void sendingAutoCode() {
        tv_code.setEnabled(false);
        tv_code.setText("获取验证码");
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
        if (phoneNumber != null && phoneNumber.equals(et_phonenumber.getText().toString().trim())) {
            tv_code.setText("重新获取");
        } else {
            tv_code.setText("获取验证码");
        }
        tv_code.setEnabled(true);
        count = 60;
    }
}
