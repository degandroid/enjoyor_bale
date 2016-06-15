package com.enjoyor.healthhouse.ui;

import android.content.Intent;
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
import com.enjoyor.healthhouse.bean.ThirdLoginInfo;
import com.enjoyor.healthhouse.bean.UserInfo;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/6/13.
 */
public class BindActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.myphone_et_bindphone)
    EditText myphone_et_bindphone;
    @Bind(R.id.myphone_et_bindnum)
    EditText myphone_et_bindnum;
    @Bind(R.id.myphone_bindacces)
    TextView myphone_bindacces;
    @Bind(R.id.myphone_bind)
    Button myphone_bind;
    Long accId;
    private Handler handler;
    private int count = 60;//60秒倒计时
    private String tellphone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bind_ac_layout);
//        setImmerseLayout(findViewById(R.id.navigation));
        ButterKnife.bind(this);
        initCode();
        initView();
        initEvent();
    }

    private void initCode() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                // TODO Auto-generated method stub
                super.handleMessage(msg);
                if (msg.what > 0) {
                    myphone_bindacces.setText("剩余" + msg.what + "秒");
                }
            }
        };
    }

    private void initEvent() {
        myphone_bindacces.setOnClickListener(this);
        myphone_bind.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("绑定手机");
        accId = getIntent().getLongExtra("accountid", 0);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.myphone_bind://绑定手机
                bindphone();
                break;
            case R.id.myphone_bindacces://发送验证码
                sendMsg();
                sendMsgtoPhone();
                break;
        }
    }

    //绑定手机的方法
    private void bindphone() {
        RequestParams params = new RequestParams();
        if (myphone_et_bindphone.getText().toString().trim() != null) {
            params.add("phone", myphone_et_bindphone.getText().toString().trim());
            if (myphone_et_bindnum.getText().toString().trim() != null) {
                params.add("mcode", myphone_et_bindnum.getText().toString().trim());
                params.add("accId", accId + "");
                params.add("origin", "androidApp");
                AsyncHttpUtil.post(UrlInterface.Bind_Phone_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String json = new String(bytes);
                        ApiMessage apiMessage = ApiMessage.FromJson(json);
                        if (apiMessage.Code == 1001) {
                            Toast.makeText(BindActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                            accessInfo();
                        } else {
                            Toast.makeText(BindActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });
            } else {
                Toast.makeText(BindActivity.this, "验证码不能 为空", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(BindActivity.this, "手机号码不能 为空", Toast.LENGTH_LONG).show();
        }
    }

    //获取第三方绑定用户信息
    private void accessInfo() {
        RequestParams params = new RequestParams();
        params.add("accId", accId + "");
        AsyncHttpUtil.get(UrlInterface.Get_Thhird_LoginInfo_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    ThirdLoginInfo thirdlogininfo = JsonHelper.getJson(apimessage.Data, ThirdLoginInfo.class);
                    BaseDate.setSessionId(BindActivity.this, thirdlogininfo.getUserInfo().getUserId());
                    MyApplication.getInstance().getDBHelper().saveUser(thirdlogininfo.getUserInfo());
                    Intent intent_main = new Intent(BindActivity.this, MainTabActivity.class);
                    startActivity(intent_main);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void sendMsgtoPhone() {
        RequestParams params = new RequestParams();
        params.add("service", "mob");
        params.add("phone", String.valueOf(myphone_et_bindphone.getText().toString()));
        AsyncHttpUtil.post(UrlInterface.SendMsg_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    Toast.makeText(BindActivity.this, "验证码发送成功", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(BindActivity.this, "数据解析错误", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    private void sendMsg() {
        myphone_bindacces.setEnabled(false);
        myphone_bindacces.setText("获取验证码");
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
        tellphone = myphone_et_bindnum.getText().toString().trim();
        if (tellphone != null && tellphone.equals(myphone_et_bindnum.getText().toString().trim())) {
            myphone_bindacces.setText("重新获取");
        } else {
            myphone_bindacces.setText("获取验证码");
        }
        myphone_bindacces.setEnabled(true);
        count = 60;
    }
}
