package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.common.BaseDate;
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
 * 个人中心意见反馈页面
 */
public class SuggestActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.suggest_et)
    EditText suggest_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suggest_av_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navigation_name.setText("意见反馈");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("发送");
        tv_right.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.tv_right:
                suggest();
                break;
        }
    }

    private void suggest() {
        if (!StringUtils.isBlank(suggest_et.getText().toString().trim())) {
            RequestParams param = new RequestParams();
            param.add("content", "" + suggest_et.getText().toString().trim());
            param.add("origin", "ANDROIDAPP");
            if (BaseDate.getSessionId(this)!=null){
                param.add("id", "" +MyApplication.getInstance().getDBHelper().getUser().getAccountId() );
            }
            AsyncHttpUtil.get(UrlInterface.Suggest_URL, param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apimessage = ApiMessage.FromJson(json);
                    if (apimessage.Code == 1001) {
                        Toast.makeText(SuggestActivity.this, "" + apimessage.Msg, Toast.LENGTH_LONG).show();
                        finish();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });

        } else {
            Toast.makeText(SuggestActivity.this, "您的意见不能为空！", Toast.LENGTH_LONG).show();
        }
    }
}
