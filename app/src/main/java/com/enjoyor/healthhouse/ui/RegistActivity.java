package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.custom.Watcher;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.MatcherUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.hyphenate.EMError;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/9.
 */
public class RegistActivity extends BaseActivity implements View.OnClickListener {

    @Bind(R.id.container)
    CoordinatorLayout container;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.registphonenumber)
    EditText registphonenumber;
    @Bind(R.id.regist_password)
    EditText regist_password;
    @Bind(R.id.regist_tv_yanzheng)
    EditText regist_tv_yanzheng;
    @Bind(R.id.regist_yanzheng)
    TextView regist_yanzheng;
    @Bind(R.id.regist)
    Button regist;
    @Bind(R.id.regist_phone_delete)
    ImageView regist_phone_delete;
    @Bind(R.id.regist_pwd_delete)
    ImageView regist_pwd_delete;
    @Bind(R.id.regist_choice)
    CheckBox regist_choice;
    @Bind(R.id.regist_agreement)
    TextView regist_agreement;
    private Handler handler;
    private String tellphone;
    private String pwd;
    private int count = 60;//30秒倒计时

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.regist_layout);
        setImmerseLayout(findViewById(R.id.navigation));
        ButterKnife.bind(this);
        initView();
        initData();
        initCode();
        initEvent();
    }

    private void initCode() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    regist_yanzheng.setText("剩余" + msg.what + "秒");
                }
            }
        };
    }

    private void initEvent() {
        navigation_back.setOnClickListener(this);
        regist_yanzheng.setOnClickListener(this);
        regist.setOnClickListener(this);
        regist_phone_delete.setOnClickListener(this);
        regist_pwd_delete.setOnClickListener(this);
        regist_agreement.setOnClickListener(this);
    }

    private void initData() {
        registphonenumber.addTextChangedListener(new Watcher(registphonenumber, regist_phone_delete));
        regist_password.addTextChangedListener(new Watcher(regist_password, regist_pwd_delete));
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.regist_yanzheng:
                if (StringUtils.isBlank(registphonenumber.getText().toString())) {
                    Snackbar.make(container, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
                    registphonenumber.requestFocus();
                } else if (!MatcherUtil.isMobileNumber(registphonenumber.getText().toString())) {
                    Snackbar.make(container, "请输入正确的手机号", Snackbar.LENGTH_SHORT).show();
                    registphonenumber.requestFocus();
                } else {
                    sendMsg();
                    sendMsgtoPhone();
                }
                break;
            case R.id.regist:
                if (isRight()) {
                    regist();
                }

                break;
            case R.id.regist_phone_delete:
                registphonenumber.setText("");
                break;
            case R.id.regist_pwd_delete:
                regist_password.setText("");
                break;
            case R.id.regist_agreement:
                Intent intent_ment = new Intent(RegistActivity.this, AgreeMentActivity.class);
                startActivity(intent_ment);
        }
    }

    //发送手机验证码的方法
    private void sendMsgtoPhone() {
        RequestParams params = new RequestParams();
        params.add("service", "mob");
        params.add("phone", String.valueOf(registphonenumber.getText().toString()));
        AsyncHttpUtil.post(UrlInterface.SendMsg_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    Toast.makeText(RegistActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegistActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private boolean isRight() {
        String phonenumber = registphonenumber.getText().toString().trim();
        String password = regist_password.getText().toString().trim();
        String yanzheng = regist_tv_yanzheng.getText().toString().trim();

        if (StringUtils.isBlank(phonenumber)) {
            Snackbar.make(container, "手机号不能为空", Snackbar.LENGTH_SHORT).show();
            registphonenumber.requestFocus();
            return false;
        } else if (!MatcherUtil.isMobileNumber(phonenumber)) {
            Snackbar.make(container, "手机号不正确", Snackbar.LENGTH_SHORT).show();
            registphonenumber.requestFocus();
            return false;
        } else if (StringUtils.isBlank(password)) {
            Snackbar.make(container, "密码不能为空", Snackbar.LENGTH_SHORT).show();
            regist_password.requestFocus();
            return false;
        } else if (!MatcherUtil.isPWD(password)) {
            Snackbar.make(container, "请输入6-12位的密码", Snackbar.LENGTH_SHORT).show();
            regist_password.requestFocus();
            return false;
        } else if (StringUtils.isBlank(yanzheng)) {
            Snackbar.make(container, "请输入验证码", Snackbar.LENGTH_SHORT).show();
            regist_tv_yanzheng.requestFocus();
            return false;
        }

        return true;
    }


    //用户注册方法
    private void regist() {
        if (regist_choice.isChecked()) {
            RequestParams params = new RequestParams();
            params.add("origin", String.valueOf("AndroidApp"));
            params.add("userLoginName", String.valueOf(registphonenumber.getText().toString()));
            params.add("userLoginPwd", String.valueOf(regist_password.getText().toString()));
            params.add("userLoginType", String.valueOf(Integer.valueOf(2)));
            params.add("mcode", String.valueOf(regist_tv_yanzheng.getText().toString()));
            AsyncHttpUtil.post(UrlInterface.Regist_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        registerEM(registphonenumber.getText().toString(), registphonenumber.getText().toString());
                        Intent intent_login = new Intent(RegistActivity.this, LoginActivity.class);
                        startActivity(intent_login);
                        finish();
                    } else if (apiMessage.Code == 1002) {
                        dialog(RegistActivity.this, "该用户已注册，请直接登录", "取消", "登录", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                finish();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(RegistActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Snackbar.make(container, "验证码错误", Snackbar.LENGTH_SHORT).show();
                        regist_tv_yanzheng.requestFocus();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        } else Toast.makeText(RegistActivity.this, "请同意芭乐健康软件许可协议", Toast.LENGTH_LONG).show();

    }

    private void registerEM(final String s, final String s1) {

        //注册失败会抛出HyphenateException
//        EMClient.getInstance().createAccount(username, pwd);//同步方法 服务器返回
        new Thread(new Runnable() {
            public void run() {
                try {
                    EMClient.getInstance().createAccount(s, s1);//同步方法 测试

                    runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(RegistActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (final HyphenateException e) {
                    runOnUiThread(new Runnable() {
                        public void run() {
                            int errorCode = e.getErrorCode();
                            if (errorCode == EMError.NETWORK_ERROR) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.network_anomalies), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ALREADY_EXIST) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.User_already_exists), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_AUTHENTICATION_FAILED) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.registration_failed_without_permission), Toast.LENGTH_SHORT).show();
                            } else if (errorCode == EMError.USER_ILLEGAL_ARGUMENT) {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.illegal_user_name), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), getResources().getString(R.string.Registration_failed), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        }).start();

    }


    private void sendMsg() {
        regist_yanzheng.setEnabled(false);
        regist_yanzheng.setText("获取验证码");
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
        if (tellphone != null && tellphone.equals(registphonenumber.getText().toString().trim())) {
            regist_yanzheng.setText("重新获取");
        } else {
            regist_yanzheng.setText("获取验证码");
        }
        regist_yanzheng.setEnabled(true);
        count = 30;
    }
}
