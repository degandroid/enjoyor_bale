package com.enjoyor.healthhouse.ui;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.ThirdLoginInfo;
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
import com.mob.tools.utils.UIHandler;

import org.apache.http.Header;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qq.QQClientNotExistException;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.utils.WechatClientNotExistException;

/**
 * Created by Administrator on 2016/5/9.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, PlatformActionListener, Handler.Callback {
    @Bind(R.id.container)
    CoordinatorLayout container;
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
    @Bind(R.id.login_ipay)
    LinearLayout login_ipay;
    @Bind(R.id.login_qq)
    LinearLayout login_qq;
    @Bind(R.id.login_wx)
    LinearLayout login_wx;

    private String phoneNumber;
    private String password;

    private String LOGIN_URL = "account/applogin.action";
    public static final String FROM_BPINPUTACTIVITY = "FROM_BPINPUTACTIVITY";
    public static final String FROM_BPINPU_HISTORY = "FROM_BPINPU_HISTORY";
    public static final String FROM_SUISHOUJI = "FROM_SUISHOUJI";
    private boolean isFromBpInputActivity = false;
    Dialog dialog;
    Dialog dialog_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        setImmerseLayout(findViewById(R.id.navigation));
        ButterKnife.bind(this);
        dialog = createLoadingDialog(LoginActivity.this, "正在跳转...");
        dialog_login = createLoadingDialog(LoginActivity.this, "正在登陆...");
        if (getIntent().hasExtra(FROM_BPINPUTACTIVITY)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_BPINPUTACTIVITY, false);
        }
        if (getIntent().hasExtra(FROM_BPINPU_HISTORY)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_BPINPU_HISTORY, false);
        }
        if (getIntent().hasExtra(FROM_SUISHOUJI)) {
            isFromBpInputActivity = getIntent().getBooleanExtra(FROM_SUISHOUJI, true);
        }
        navigation_name.setText("登录");
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
        login_ipay.setOnClickListener(this);
        login_qq.setOnClickListener(this);
        login_wx.setOnClickListener(this);
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
                    dialog_login.show();
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
            case R.id.login_ipay://支付宝第三方登录
                Platform sino = ShareSDK.getPlatform(this, SinaWeibo.NAME);
                sino.SSOSetting(false);
                sino.setPlatformActionListener(this);
                sino.authorize();
                break;
            case R.id.login_qq://qq第三方登录
                Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
                qq.SSOSetting(false);
                qq.setPlatformActionListener(this);
                qq.authorize();
                break;
            case R.id.login_wx://微信第三方登录
                Platform wechat = ShareSDK.getPlatform(this, Wechat.NAME);
                wechat.showUser(null);
                wechat.SSOSetting(false);
                wechat.setPlatformActionListener(this);
//                wechat.authorize();
                if (!wechat.isValid()) {
                    wechat.authorize();
                }
                break;
        }
    }

    private boolean isCorrect() {
        phoneNumber = et_phonenumber.getText().toString().trim();
        password = et_password.getText().toString().trim();

        if (StringUtils.isBlank(phoneNumber)) {
            Snackbar.make(container, "手机号码不能为空", Snackbar.LENGTH_SHORT).show();
            et_phonenumber.requestFocus();
            return false;
        } else if (!MatcherUtil.isMobileNumber(phoneNumber)) {
            Snackbar.make(container, "请输入正确的手机号", Snackbar.LENGTH_SHORT).show();
            et_phonenumber.requestFocus();
            return false;
        } else if (StringUtils.isBlank(password)) {
            Snackbar.make(container, "密码不能为空", Snackbar.LENGTH_SHORT).show();
            et_password.requestFocus();
            return false;
        } else if (!MatcherUtil.isPWD(password)) {
            Snackbar.make(container, "请输入6-12位密码", Snackbar.LENGTH_SHORT).show();
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
                        dialog_login.dismiss();
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
                    dialog_login.dismiss();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private boolean saveUser(UserInfo user) {
        if (user != null) {
            user.setId(1);
            user.setPhoneNumber(phoneNumber);
            user.setUserLoginPwd(password);
            BaseDate.setSessionId(LoginActivity.this, user.getAccountId());
            return MyApplication.getInstance().getDBHelper().saveUser(user);
        }
        return false;
    }

    @Override
    public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.what = 2;
        msg.arg1 = 1;
        msg.arg2 = i;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Log.d("wyy------throwable----", throwable.toString());
        Message msg = new Message();
        msg.what = 1;
        msg.arg1 = i;
        msg.obj = throwable;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Log.d("wyy------i----", i + "");
        Message msg = new Message();
        msg.what = 0;
        UIHandler.sendMessage(msg, this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 登录接受消息
     */
    @Override
    public boolean handleMessage(Message msg) {
        dialog.show();
        int what = msg.what;
        switch (what) {
            case 0:
                Toast.makeText(LoginActivity.this, "登录已取消", Toast.LENGTH_LONG).show();
                dialog.dismiss();
                break;
            case 1:
                String failtext;
                if (msg.obj instanceof WechatClientNotExistException
                        || msg.obj instanceof QQClientNotExistException
                        || msg.obj instanceof WechatClientNotExistException) {
                    failtext = "版本过低或未安装客户端";
                } else if (msg.obj instanceof java.lang.Throwable
                        && msg.obj.toString() != null
                        && msg.obj.toString().contains(
                        "prevent duplicate publication")) {
                    failtext = "请稍后发送";
                } else if (msg.obj.toString().contains("error")) {
                    failtext = "登录失败";
                } else {
                    failtext = "登录失败";
                }
                dialog.dismiss();
                break;
            case 2:
                Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                String json = JSON.toJSONString(((Platform) msg.obj).getDb());
                ((Platform) msg.obj).removeAccount(true);
                Log.d("wyy------jn----", json);
                if (json != null) {
                    final RequestParams params = new RequestParams();
                    params.add("json", json);
                    AsyncHttpUtil.post(UrlInterface.Login_Third_URL, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String json1 = new String(bytes);
                            final ApiMessage apiMessage = ApiMessage.FromJson(json1);
                            if (apiMessage.Code == 1001) {
                                ThirdLoginInfo thirdLoginInfo = JsonHelper.getJson(apiMessage.Data, ThirdLoginInfo.class);
                                if (thirdLoginInfo.getUserInfo().getUserId() == null) {
                                    Intent intent_third = new Intent(LoginActivity.this, BindActivity.class);
                                    intent_third.putExtra("accountid", thirdLoginInfo.getUserInfo().getAccountId());
                                    startActivity(intent_third);
                                    dialog.dismiss();
                                } else {
                                    BaseDate.setSessionId(LoginActivity.this, thirdLoginInfo.getUserInfo().getAccountId());
                                    if (StringUtils.isEmpty(thirdLoginInfo.getUserInfo().getHeadImg())) {
                                        String path = thirdLoginInfo.getPlatUser().getUserIcon();
                                        thirdLoginInfo.getUserInfo().setHeadImg(path);
                                    }
                                    MyApplication.getInstance().getDBHelper().saveUser(thirdLoginInfo.getUserInfo());
                                    Intent intent_success = new Intent(LoginActivity.this, MainTabActivity.class);
                                    Log.d("wyy--database-", MyApplication.getInstance().getDBHelper().getUser().getUserId() + "");
                                    startActivity(intent_success);
                                    dialog.dismiss();
                                }
                            }
                            Log.d("wyy------json1----", json1);
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
                }

                break;
        }
        return false;
    }
}
