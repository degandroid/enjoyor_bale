package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class HealthFragment extends Fragment {
    @Bind(R.id.tv_info)TextView tv_info;

    private static HealthFragment mineFragment;

    public static HealthFragment getInstance(int info){
        mineFragment = new HealthFragment();

        Bundle bundle = new Bundle();
        bundle.putString("info",info+"MineFragment");
        mineFragment.setArguments(bundle);
        return mineFragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine,null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        tv_info.setText(bundle.getString("info"));
        return view;
    }



}

