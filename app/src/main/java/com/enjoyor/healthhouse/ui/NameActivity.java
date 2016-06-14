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

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.UserInfo;
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
public class NameActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.name_et)
    EditText name_et;
    @Bind(R.id.name_delete)
    ImageView name_delete;
    @Bind(R.id.tv_right)
    TextView tv_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_ac_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        re_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        name_delete.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("姓名");
        if(!StringUtils.isBlank(MyApplication.getInstance().getDBHelper().getUser().getUserName())){
            name_et.setText(MyApplication.getInstance().getDBHelper().getUser().getUserName());
        }else{
            name_et.setHint("请输入姓名");
        }
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    name_delete.setVisibility(View.VISIBLE);
                } else
                    name_delete.setVisibility(View.INVISIBLE);

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
                saveName();
                break;
            case R.id.name_delete:
                name_et.setText("");
                break;
        }
    }

    private void saveName() {
//        name_et.setInputType();
        if (!StringUtils.isBlank(name_et.getText().toString().trim())) {
            RequestParams params = new RequestParams();
            params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
            params.add("userName", name_et.getText().toString().trim());
            AsyncHttpUtil.post(UrlInterface.ModifyInfo_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    Log.d("wyy---", json);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        Intent intent = getIntent();
                        setResult(100, intent);
                        intent.putExtra("name", name_et.getText().toString().trim());
                        Toast.makeText(NameActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                        UserInfo userInfo = MyApplication.getInstance().getDBHelper().getUser();
                        userInfo.setUserName(name_et.getText().toString().trim());
                        MyApplication.getInstance().getDBHelper().saveUser(userInfo);
                        MyApplication.setUserName(name_et.getText().toString().trim());
                        finish();
                    } else {
                        Toast.makeText(NameActivity.this, "姓名修改失败", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        } else {
            Toast.makeText(NameActivity.this, "姓名不能为空", Toast.LENGTH_LONG).show();
        }
    }
}
