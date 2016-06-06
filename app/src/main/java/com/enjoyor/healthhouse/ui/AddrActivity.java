package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 */
public class AddrActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.addr_img)
    ImageView addr_img;
    @Bind(R.id.addr_et)
    EditText addr_et;
    @Bind(R.id.addr_tv)
    TextView addr_tv;
    @Bind(R.id.addr_detail)
    TextView addr_detail;
    int account;
    MyLocationListenner myListener;

    //定位
    LocationClient mLocClient;
    //    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;// 定位模式
    boolean isFirstLoc = true;// 是否首次定位

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addr_ac_layout);
        ButterKnife.bind(this);
        if (isFirstLoc) {
            myListener = new MyLocationListenner();
            isFirstLoc = false;
        }
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;// 设置定位模式为普通
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);// 注册监听函数：
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(1000);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocClient.setLocOption(option);
        mLocClient.start();
        initView();
    }

    private void initView() {
        navigation_name.setText("地址");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        re_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        addr_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("wyy---beforeTextChanged", s.length() + "");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("wyy---onTextChanged", s.length() + "");
                addr_tv.setText(s.length() + "/50");
                if (s.length() > 50) {
                    Toast.makeText(AddrActivity.this, "输入字符长度超过限制", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.tv_right:
                if (!StringUtils.isBlank(addr_et.getText().toString().trim())) {
                    RequestParams params = new RequestParams();
                    params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
                    params.add("address", "" + addr_et.getText().toString().trim());
                    AsyncHttpUtil.post(UrlInterface.ModifyInfo_URL, params, new AsyncHttpResponseHandler() {
                        @Override
                        public void onSuccess(int i, Header[] headers, byte[] bytes) {
                            String json = new String(bytes);
                            ApiMessage apiMessage = ApiMessage.FromJson(json);
                            if (apiMessage.Code == 1001) {
                                Toast.makeText(AddrActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                                Intent intent = getIntent();
                                intent.putExtra("addr", addr_et.getText().toString().trim());
                                setResult(400, intent);
                                finish();
                            }
                        }

                        @Override
                        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                        }
                    });

                } else {
                    Toast.makeText(AddrActivity.this, "地址不能为空", Toast.LENGTH_LONG).show();
                    addr_et.setError("地址不能为空");
                }

                break;
        }
    }

    /**
     * 定位sdk监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            } else {
                addr_detail.setText(bdLocation.getProvince() + bdLocation.getCity() + bdLocation.getDistrict());
            }
        }
    }
}
