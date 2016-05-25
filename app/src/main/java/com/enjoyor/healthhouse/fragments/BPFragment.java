package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/24.
 * 血压报告
 */
public class BPFragment extends BaseFragment {
    View view;
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
    @Bind(R.id.bp_fg_history)
    RelativeLayout bp_fg_history;
    @Bind(R.id.bp_fg_tend)
    RelativeLayout bp_fg_tend;
    @Bind(R.id.bp_fg_web)
    WebView bp_fg_web;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.bp_fg_layout, null);
        ButterKnife.bind(this,view);
        return view;
    }
}
