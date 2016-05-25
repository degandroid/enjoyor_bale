package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/25.
 */
public class InfoActivity extends BaseActivity {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_ac_layout);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        navigation_name.setText("我的消息");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
