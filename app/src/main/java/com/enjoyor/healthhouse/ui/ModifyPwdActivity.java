package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 * 个人中心修改密码页面
 */
public class ModifyPwdActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.container)
    CoordinatorLayout container;

    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.modifypwd_et_access)
    EditText modifypwd_et_access;
    @Bind(R.id.modify_pwd)
    EditText modify_pwd;
    @Bind(R.id.modify_again_pwd)
    EditText modify_again_pwd;
    @Bind(R.id.modify_save)
    Button modify_save;
    private int count = 60;//30秒倒计时
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modify_pwd_ac_layout);
        ButterKnife.bind(this);
        initView();
        initEvent();
    }

    private void initEvent() {
        modify_save.setOnClickListener(this);
        re_back.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.modify_save:
                if(isCorrert()){
                    modifycommit();
                }

                break;
            case R.id.re_back:
                finish();
                break;
        }
    }

    private boolean isCorrert(){
        String oldPwd= modifypwd_et_access.getText().toString().trim();
        String newPwd = modify_pwd.getText().toString().trim();
        String againPwd = modify_again_pwd.getText().toString().trim();

        if(StringUtils.isBlank(oldPwd)){
            Snackbar.make(container, "原密码不能为空", Snackbar.LENGTH_SHORT).show();
            modifypwd_et_access.requestFocus();
            return false;
        }else if(StringUtils.isBlank(newPwd)){
            Snackbar.make(container, "新密码不能为空", Snackbar.LENGTH_SHORT).show();
            modify_pwd.requestFocus();
            return false;
        }else if(!MatcherUtil.isPWD(newPwd)){
            Snackbar.make(container, "请输入6-12位新密码", Snackbar.LENGTH_SHORT).show();
            modify_again_pwd.requestFocus();
            return false;
        }else if(StringUtils.isBlank(againPwd)){
            Snackbar.make(container, "请确认新密码", Snackbar.LENGTH_SHORT).show();
            modify_again_pwd.requestFocus();
            return false;
        }else if(!newPwd.equals(againPwd)){
            Snackbar.make(container, "请确认新密码", Snackbar.LENGTH_SHORT).show();
            modify_again_pwd.requestFocus();
            return false;
        }
        return true;
    }

    //保存修改密码的方法
    private void modifycommit() {
         RequestParams params = new RequestParams();
                params.add("id", "" + MyApplication.getInstance().getDBHelper().getUser().getAccountId());
                params.add("name", "" + MyApplication.getInstance().getDBHelper().getUser().getPhoneNumber());
                params.add("oldpwd", "" + modifypwd_et_access.getText().toString().trim());
                params.add("newpwd", "" + modify_pwd.getText().toString().trim());
                AsyncHttpUtil.post(UrlInterface.InfoModifyPwd_URL, params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        String json = new String(bytes);
                        Log.d("wyy----",json);
                        ApiMessage apiMessage = ApiMessage.FromJson(json);
                        if (apiMessage.Code == 1001) {
                            Toast.makeText(ModifyPwdActivity.this, "密码修改成功", Toast.LENGTH_LONG).show();
                            Intent intent_login = new Intent(ModifyPwdActivity.this, LoginActivity.class);
                            startActivity(intent_login);
                            finish();
                        } else {
                            Toast.makeText(ModifyPwdActivity.this, "" + apiMessage.Msg, Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                    }
                });

    }
}
