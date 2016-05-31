package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.UserInfo;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.AppManagerUtil;
import com.enjoyor.healthhouse.utils.MatcherUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.navigation_back)
    ImageView navigation_back;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Bind(R.id.et_phonenumber)
    EditText et_phonenumber;
    @Bind(R.id.et_password)
    EditText et_password;
    @Bind(R.id.bt_login)
    Button bt_login;
    @Bind(R.id.tv_forget_password)
    TextView tv_forget_password;
    @Bind(R.id.tv_login_quick)
    TextView tv_login_quick;


    private String phoneNumber;
    private String password;

    private String LOGIN_URL = "account/applogin.action";
    public static final String FROM_BPINPUTACTIVITY = "FROM_BPINPUTACTIVITY";
    public static final String FROM_BPINPU_HISTORY = "FROM_BPINPU_HISTORY";
    public static final String FROM_SUISHOUJI = "FROM_SUISHOUJI";
    private boolean isFromBpInputActivity = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setImmerseLayout(findViewById(R.id.navigation));
        ButterKnife.bind(this);
        if (getIntent().hasExtra(FROM_BPINPUTACTIVITY)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_BPINPUTACTIVITY, false);
        }
        if (getIntent().hasExtra(FROM_BPINPU_HISTORY)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_BPINPU_HISTORY, false);
        }
        if (getIntent().hasExtra(FROM_SUISHOUJI)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_SUISHOUJI, false);
        }
        navigation_name.setText("登陆");
        navigation_back.setVisibility(View.INVISIBLE);
        initOnClick();
    }

    private void initOnClick() {
        et_phonenumber.setOnClickListener(this);
        et_password.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        tv_login_quick.setOnClickListener(this);
        navigation_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.et_phonenumber:
                break;

            case R.id.et_password:
                break;

            case R.id.bt_login:
                if (isCorrect()) {
                    initData();
                }
                break;

            case R.id.tv_forget_password:
                Intent intent_forget = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent_forget);
                break;

            case R.id.tv_login_quick:
                Intent intent_quick = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent_quick);
                break;
        }
    }

    private boolean isCorrect() {
        phoneNumber = et_phonenumber.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (StringUtils.isBlank(phoneNumber)) {
            Toast.makeText(LoginActivity.this, "手机号码不能为空", Toast.LENGTH_LONG).show();
            et_phonenumber.requestFocus();
            return false;
        } else if (!MatcherUtil.isMobileNumber(phoneNumber)) {
            Toast.makeText(LoginActivity.this, "请输入真确的手机号", Toast.LENGTH_LONG).show();
            et_phonenumber.requestFocus();
            return false;
        } else if (StringUtils.isBlank(password)) {
            Toast.makeText(LoginActivity.this, "密码不能为空", Toast.LENGTH_LONG).show();
            et_password.requestFocus();
            return false;
        }
        return true;
    }


    private void initData() {

        RequestParams params = new RequestParams();
        params.add("origin", String.valueOf("AndroidApp"));
        params.add("userLoginName", phoneNumber);
        params.add("userLoginPwd", password);
        params.add("userLoginType", String.valueOf("2"));
        AsyncHttpUtil.post(UrlInterface.TEXT_URL + LOGIN_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    UserInfo user = JsonHelper.getJson(apiMessage.Data, UserInfo.class);
                    if (saveUser(user)) {
                        Log.i("zxw", "用户信息已保存");

                    }
                    if (isFromBpInputActivity) {
                        finish();
                    } else {
                        AppManagerUtil.getAppManager().finishAllActivity();
                        Intent intent = new Intent(LoginActivity.this, MainTabActivity.class);
                        startActivity(intent);
                    }
                } else {
                    dialog(LoginActivity.this, "用户名或者密码错误", "取消", "重新输入", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            disappear();
                        }
                    }, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_password.setText("");
                            disappear();
                            et_password.requestFocus();
                        }
                    });
//                    Toast.makeText(LoginActivity.this, "用户名或者密码错误，请重新输入", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private boolean saveUser(UserInfo user) {
        if (user != null) {
//            UserInfo user2 = new UserInfo();
            user.setId(1);
//            user2.setLoginName("ABCD");
            user.setPhoneNumber(phoneNumber);
            user.setUserLoginPwd(password);
            BaseDate.setSessionId(LoginActivity.this, user.getAccountId());
            return MyApplication.getInstance().getDBHelper().saveUser(user);
        }
        return false;
    }
}
