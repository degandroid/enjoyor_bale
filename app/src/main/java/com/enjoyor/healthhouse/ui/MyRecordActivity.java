package com.enjoyor.healthhouse.ui;

import android.os.Bundle;

import com.enjoyor.healthhouse.R;

import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/23.
 */

/*我的报告*/
public class MyRecordActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrecord_ac_layout);
        ButterKnife.bind(this);
    }
}
