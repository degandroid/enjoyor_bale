package com.enjoyor.healthhouse.ui;

import android.app.Instrumentation;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.MineData;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 * 个人中心资料页面
 */
public class DataActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.data_name)
    RelativeLayout data_name;
    @Bind(R.id.data_sex)
    RelativeLayout data_sex;
    @Bind(R.id.data_phone)
    RelativeLayout data_phone;
    @Bind(R.id.data_number_card)
    RelativeLayout data_number_card;
    @Bind(R.id.data_addr)
    RelativeLayout data_addr;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.data_name_tv)
    TextView data_name_tv;
    @Bind(R.id.name_sex_tv)
    TextView name_sex_tv;
    @Bind(R.id.name_phone_tv)
    TextView name_phone_tv;
    @Bind(R.id.name_card_tv)
    TextView name_card_tv;
    @Bind(R.id.name_addr_tv)
    TextView name_addr_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.data_ac_layout);
        ButterKnife.bind(this);
        progress();
        initView();
        initData();
        initEvent();
    }

    private void initData() {
        RequestParams params = new RequestParams();
        params.add("accountId", "" + MyApplication.getInstance().getDBHelper().getUser().getAccountId());
        AsyncHttpUtil.post(UrlInterface.Data_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("wyy-onSuccess-", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    cancel();
                    MineData mineData = JsonHelper.getJson(apiMessage.Data, MineData.class);
                    initInfo(mineData);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void initInfo(MineData mineData) {
        data_name_tv.setText(mineData.getUserName());
        name_sex_tv.setText(mineData.getSex());
        if (mineData.getPhoneNumber() != null) {
            String cellphone = mineData.getPhoneNumber();
            String maskNumber = cellphone.substring(0, 3) + "****" + cellphone.substring(7, cellphone.length());
            name_phone_tv.setText(maskNumber);
        }
        if (mineData.getIdCard() != null) {
            String cardnumber = mineData.getIdCard();
            String maskcard = cardnumber.substring(0, 6) + "********" + cardnumber.substring(14, cardnumber.length());
            name_card_tv.setText(maskcard);
        }
        name_addr_tv.setText(mineData.getAddress());
    }

    private void initEvent() {
        data_name.setOnClickListener(this);
        data_sex.setOnClickListener(this);
        data_number_card.setOnClickListener(this);
        data_addr.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("我的资料");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.data_name://姓名
                Intent intent_name = new Intent(this, NameActivity.class);
                startActivityForResult(intent_name, 100);
                break;
            case R.id.data_sex://性别
                Intent intent_sex = new Intent(this, SexActivity.class);
                startActivityForResult(intent_sex, 200);
                break;
            case R.id.data_number_card://身份证
                Intent intent_num = new Intent(this, NumCardActivity.class);
                startActivityForResult(intent_num, 300);
                break;
            case R.id.data_addr://地址
                Intent intent_addr = new Intent(this, AddrActivity.class);
                startActivityForResult(intent_addr, 400);
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 100:
                data_name_tv.setText(data.getStringExtra("name"));
                break;
            case 200:
                name_sex_tv.setText(data.getStringExtra("sex"));
                break;
            case 300:
                if (data.getStringExtra("idcard") != null) {
                    String cardnumber = data.getStringExtra("idcard");
                    String maskcard = cardnumber.substring(0, 6) + "********" + cardnumber.substring(14, cardnumber.length());
                    name_card_tv.setText(maskcard);
                    break;
                }
            case 400:
                name_addr_tv.setText(data.getStringExtra("addr"));
                break;
        }
    }
}
