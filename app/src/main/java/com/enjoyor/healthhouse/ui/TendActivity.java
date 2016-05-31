package com.enjoyor.healthhouse.ui;

import android.os.Bundle;

import com.enjoyor.healthhouse.R;

import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/31.
 */
public class TendActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tend_ac_layout);
        ButterKnife.bind(this);
    }
}
