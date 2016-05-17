package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/16.
 */
public class NotesActivity extends BaseActivity {
    @Bind(R.id.tv)TextView tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        if(isLogin(this)){
            tv.setText("Login...");
        }
    }
}
