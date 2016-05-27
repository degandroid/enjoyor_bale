package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 */
public class NumCardActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.name_et)
    EditText name_et;
    @Bind(R.id.name_delete)
    ImageView name_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.name_ac_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navigation_name.setText("身份证");
        tv_right.setText("保存");
        tv_right.setVisibility(View.VISIBLE);
        name_et.setHint("请输入正确的身份证号码");
        name_delete.setVisibility(View.INVISIBLE);
        name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (name_et.getText().toString().trim() != null) {
                    name_delete.setVisibility(View.VISIBLE);
                } else {
                    name_delete.setVisibility(View.INVISIBLE);
                }
            }
        });
        name_delete.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.tv_right:
                saveId();
                break;
            case R.id.name_delete:
                name_et.setText("");
                break;
            case R.id.re_back:
                finish();
                break;
        }
    }

    private void saveId() {
        if (name_et.getText().toString().trim() != null) {
            RequestParams params = new RequestParams();
            params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
            params.add("idCard", "" + name_et.getText().toString().trim());
            AsyncHttpUtil.post(UrlInterface.ModifyInfo_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        Toast.makeText(NumCardActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                        Intent intent = getIntent();
                        intent.putExtra("idcard", name_et.getText().toString().trim());
                        setResult(300, intent);
                        finish();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });

        } else {
            Toast.makeText(this, "", Toast.LENGTH_LONG).show();
        }
    }
}
