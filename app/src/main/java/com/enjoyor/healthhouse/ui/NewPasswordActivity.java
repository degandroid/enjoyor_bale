package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
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
 * Created by Administrator on 2016/5/9.
 */
public class NewPasswordActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Bind(R.id.et_newpassword)
    EditText et_newpassword;
    @Bind(R.id.et_again_newpassword)
    EditText et_again_newpassword;
    @Bind(R.id.bt_commit)
    Button bt_commit;
    private String yanzhengma;
    private String phonenumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpassword);
        ButterKnife.bind(this);
        navigation_name.setText("新密码");
        yanzhengma = getIntent().getStringExtra("YanZheng");
        phonenumber = getIntent().getStringExtra("phone");
        initOnClick();
    }

    private void initOnClick() {
        et_newpassword.setOnClickListener(this);
        et_again_newpassword.setOnClickListener(this);
        bt_commit.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
            case R.id.bt_commit:
                if (isCorrect()) {
                    modify();
                }
                break;
        }
    }

    /**
     * 确认修改密码的方法
     */
    private void modify() {
        RequestParams params = new RequestParams();
        params.add("phone", String.valueOf(phonenumber));
        params.add("newpwd", et_newpassword.getText().toString().trim());
        params.add("mcode", String.valueOf(yanzhengma));
        AsyncHttpUtil.post(UrlInterface.ModifyPwd_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    Toast.makeText(NewPasswordActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(NewPasswordActivity.this, "" + apimessage.Msg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private boolean isCorrect() {
        String newPwd = et_newpassword.getText().toString().trim();
        String newAgainPwd = et_again_newpassword.getText().toString().trim();
        if (StringUtils.isBlank(newPwd)) {
            Toast.makeText(NewPasswordActivity.this, "新密码不能为空", Toast.LENGTH_LONG).show();
            et_newpassword.requestFocus();
            return false;
        } else if (StringUtils.isBlank(newAgainPwd)) {
            Toast.makeText(NewPasswordActivity.this, "两次密码输入不匹配", Toast.LENGTH_LONG).show();
            et_again_newpassword.requestFocus();
            return false;
        }
        if (newPwd.equals(newAgainPwd)) {
            return true;
        } else
            Toast.makeText(NewPasswordActivity.this, "两次密码输入不匹配", Toast.LENGTH_LONG).show();
        return false;
    }
}
