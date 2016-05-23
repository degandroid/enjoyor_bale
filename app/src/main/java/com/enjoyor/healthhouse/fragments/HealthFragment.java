package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class HealthFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.health_ry_full)
    RelativeLayout health_ry_full;
    @Bind(R.id.health_ry_empty)
    RelativeLayout health_ry_empty;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    boolean isLogin = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_fg_layout, null);
        ButterKnife.bind(this, view);
        if (isLogin) {
            isData();
            initView();
            initEvent();
            initData();
        }

        return view;
    }

    /**
     * 用户登录之后判断是否有数据，根据是否有数据加载布局
     */
    private void isData() {


    }

    /**
     * 判断用户是否登录
     */

    private void initData() {

    }

    private void initEvent() {
//        re_back.setOnClickListener(this);
    }

    private void initView() {
        navigation_name.setText("健康评估");
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                this.getActivity().finish();
        }
    }
}

