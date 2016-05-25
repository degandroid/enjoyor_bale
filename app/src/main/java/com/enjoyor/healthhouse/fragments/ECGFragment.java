package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/24.
 * 心电评估报告
 */
public class ECGFragment extends BaseFragment {
    View view;
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
    @Bind(R.id.bp_fg_history)
    RelativeLayout bp_fg_history;
    @Bind(R.id.bp_fg_tend)
    RelativeLayout bp_fg_tend;
    @Bind(R.id.bp_fg_web)
    WebView bp_fg_web;
    @Bind(R.id.bp_fg_img)
    ImageView bp_fg_img;
    @Bind(R.id.bp_fg_title)
    TextView bp_fg_title;
    @Bind(R.id.bp_fg_ecg_tv)
    TextView bp_fg_ecg_tv;
    @Bind(R.id.bp_fg_ecg_go)
    ImageView bp_fg_ecg_go;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bp_fg_layout, null);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        bp_fg_ecg_tv.setVisibility(View.VISIBLE);
        bp_fg_ecg_go.setVisibility(View.VISIBLE);
        bp_fg_title.setText("当前心率");
        bp_fg_img.setImageResource(R.mipmap.ecg_record);
    }
}
