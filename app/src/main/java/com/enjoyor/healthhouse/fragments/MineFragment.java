package com.enjoyor.healthhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.ui.DataActivity;
import com.enjoyor.healthhouse.ui.InfoActivity;
import com.enjoyor.healthhouse.ui.LoginActivity;
import com.enjoyor.healthhouse.ui.ModifyPwdActivity;
import com.enjoyor.healthhouse.ui.MyPhoneActivity;
import com.enjoyor.healthhouse.ui.NewPasswordActivity;
import com.enjoyor.healthhouse.ui.RegistActivity;
import com.enjoyor.healthhouse.ui.SettingActivity;
import com.enjoyor.healthhouse.ui.SuggestActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 * 个人中心
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    View view;
    @Bind(R.id.mine_fg_logo)
    ImageView mine_fg_logo;
    @Bind(R.id.mine_fg_login)
    TextView mine_fg_login;
    @Bind(R.id.mine_fg_regist)
    TextView mine_fg_regist;
    @Bind(R.id.mine_fg_info)
    RelativeLayout mine_fg_info;
    @Bind(R.id.mine_fg_book)
    RelativeLayout mine_fg_book;
    @Bind(R.id.mine_fg_phone)
    RelativeLayout mine_fg_phone;
    @Bind(R.id.mine_fg_pwd)
    RelativeLayout mine_fg_pwd;
    @Bind(R.id.mine_fg_setting)
    RelativeLayout mine_fg_setting;
    @Bind(R.id.mine_fg_introduct)
    RelativeLayout mine_fg_introduct;
    @Bind(R.id.mine_fg_suggest)
    RelativeLayout mine_fg_suggest;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fg_layout, null);
        ButterKnife.bind(this, view);
        initEvent();
        return view;
    }

    private void initEvent() {
        mine_fg_logo.setOnClickListener(this);
        mine_fg_login.setOnClickListener(this);
        mine_fg_regist.setOnClickListener(this);
        mine_fg_info.setOnClickListener(this);
        mine_fg_book.setOnClickListener(this);
        mine_fg_phone.setOnClickListener(this);
        mine_fg_pwd.setOnClickListener(this);
        mine_fg_setting.setOnClickListener(this);
        mine_fg_introduct.setOnClickListener(this);
        mine_fg_suggest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.mine_fg_logo:
                Toast.makeText(getActivity(), "您点击了上传头像按钮", Toast.LENGTH_LONG).show();
                break;
            case R.id.mine_fg_login://登录
                if (BaseDate.getSessionId(getActivity()) != null) {
                    Toast.makeText(getActivity(), "您已经成功登陆", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.mine_fg_regist://注册
                Intent intent_regist = new Intent(getActivity(), RegistActivity.class);
                startActivity(intent_regist);
                break;
            case R.id.mine_fg_info://我的消息
                Intent intent_info = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent_info);
                break;
            case R.id.mine_fg_book://我的资料
                if (isLogin(getActivity())){
                    Intent intent_data = new Intent(getActivity(), DataActivity.class);
                    startActivity(intent_data);
                }
                isLogin(getActivity());
                break;
            case R.id.mine_fg_phone://我的手机
                Intent intent_phone = new Intent(getActivity(), MyPhoneActivity.class);
                startActivity(intent_phone);
                break;
            case R.id.mine_fg_pwd://修改密码
                Intent intent_ped = new Intent(getActivity(), ModifyPwdActivity.class);
                startActivity(intent_ped);
                break;
            case R.id.mine_fg_setting://设置
                Intent intent_setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent_setting);
                break;
            case R.id.mine_fg_introduct://推荐给好友
                break;
            case R.id.mine_fg_suggest://意见反馈
                Intent intent_suggest = new Intent(getActivity(), SuggestActivity.class);
                startActivity(intent_suggest);
                break;
        }
    }
}

