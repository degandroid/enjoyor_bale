package com.enjoyor.healthhouse.ui;

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
 * Created by YuanYuan on 2016/5/25.
 * 个人中心我的手机页面
 */
public class MyPhoneActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.myphone_et_num)
    EditText myphone_et_num;
    @Bind(R.id.myphone_accesnum)
    TextView myphone_accesnum;
    @Bind(R.id.myphone_et_newphone)
    EditText myphone_et_newphone;
    @Bind(R.id.myphone_et_againnum)
    EditText myphone_et_againnum;
    @Bind(R.id.myphone_acces)
    TextView myphone_acces;
    @Bind(R.id.myphone_save)
    Button myphone_save;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    private int count = 60;//30秒倒计时
    private int countnew = 60;//30秒倒计时
    private Handler handler;
    private Handler handlernew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myphone_ac_layout);
        ButterKnife.bind(this);
        initView();
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
                    myphone_accesnum.setText("剩余" + msg.what + "秒");
                }
            }
        };
        handlernew = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    myphone_acces.setText("剩余" + msg.what + "秒");
                }
            }
        };
    }

    private void initEvent() {
        re_back.setOnClickListener(this);
        myphone_accesnum.setOnClickListener(this);
        myphone_acces.setOnClickListener(this);
        myphone_save.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("我的手机");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.myphone_accesnum:
                sendMsg();
                sendMsgtoPhone();
                break;
            case R.id.myphone_acces:
                sendNewMsg();
                sendMsgtoNewPhone();
                break;
            case R.id.myphone_save:
                savePhone();
                break;
        }
    }

    private void savePhone() {
        if (!StringUtils.isBlank(myphone_et_num.getText().toString().trim())) {
            if (!StringUtils.isBlank(myphone_et_newphone.getText().toString().trim()) && MatcherUtil.isMobileNumber(myphone_et_newphone.getText().toString().trim())) {
                if (!StringUtils.isBlank(myphone_et_againnum.getText().toString().trim())) {
                    RequestParams params2 = new RequestParams();
                    params2.add("oldphone", "" + MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber());
                    params2.add("newphone", "" + myphone_et_newphone.getText().toString().trim());
                    params2.add("oldcode", "" + myphone_et_num.getText().toString().trim());
                    params2.add("newcode", "" + myphone_et_againnum.getText().toString().trim());
                    AsyncHttpUtil.post(UrlInterface.Modify_Bind_Phone_URL, params2, new AsyncHttpResponseHandler() {

                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String json3 = new String(bytes);
                            ApiMessage apiMessage3 = ApiMessage.FromJson(json3);
                            if (apiMessage3.Code == 1001) {
                                Toast.makeText(MyPhoneActivity.this, "手机变更成功", Toast.LENGTH_LONG).show();
                                finish();
                            } else {
                                Toast.makeText(MyPhoneActivity.this, "" + apiMessage3.Msg, Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });
                } else {
                    Toast.makeText(MyPhoneActivity.this, "新手机验证码不能为空，请重新输入", Toast.LENGTH_LONG).show();
                }
            } else {
                Toast.makeText(MyPhoneActivity.this, "您说的手机号码不正确。请重新输入", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(MyPhoneActivity.this, "请输入原手机验证码", Toast.LENGTH_LONG).show();
        }
    }

    private void sendMsgtoNewPhone() {
        RequestParams params = new RequestParams();
        params.add("service", "mob");
        if (myphone_et_newphone.getText().toString().trim() != null) {
            if (MatcherUtil.isMobileNumber(myphone_et_newphone.getText().toString().trim())) {
                params.add("phone", String.valueOf(myphone_et_newphone.getText().toString()));
                AsyncHttpUtil.post(UrlInterface.SendMsg_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String json = new String(bytes);
                        ApiMessage apimessage = ApiMessage.FromJson(json);
                        if (apimessage.Code == 1001) {
                            Toast.makeText(MyPhoneActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(MyPhoneActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    }
                });
            } else {
                Toast.makeText(this, "电话号码格式不正确，请确认您的电话号码是否正确", Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(this, "电话号码不能为空", Toast.LENGTH_LONG).show();
        }

    }

    private void sendNewMsg() {
        myphone_acces.setEnabled(false);
        myphone_acces.setText("获取验证码");
        //倒记时
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        countnew--;
                        handlernew.sendEmptyMessage(countnew);
                        if (countnew == 0) {
                            timer.cancel();
                            reSet(2);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    private void sendMsgtoPhone() {
        RequestParams params = new RequestParams();
        params.add("service", "mob");
        params.add("phone", MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber() + "");
        AsyncHttpUtil.post(UrlInterface.SendMsg_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    Toast.makeText(MyPhoneActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MyPhoneActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private void sendMsg() {
        this.myphone_accesnum.setEnabled(false);
        this.myphone_accesnum.setText("获取验证码");
        //倒记时
        final Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        MyPhoneActivity.this.count--;
                        MyPhoneActivity.this.handler.sendEmptyMessage(MyPhoneActivity.this.count);
                        if (MyPhoneActivity.this.count == 0) {
                            timer.cancel();
                            reSet(1);
                        }
                    }
                });
            }
        };
        timer.schedule(task, 1000, 1000);
    }

    private void reSet(int i) {
        if (i == 1) {
            myphone_accesnum.setText("获取验证码");
            myphone_accesnum.setEnabled(true);
            count = 60;
        } else {
            myphone_acces.setText("获取验证码");
            myphone_acces.setEnabled(true);
            countnew = 60;
        }
    }
}
