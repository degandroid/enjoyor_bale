package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.Constant;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
public class HistoryActivity extends BaseActivity {
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.re_back)RelativeLayout re_back;

    @Bind(R.id.ll_history_xueya)LinearLayout ll_history_xueya;
    @Bind(R.id.ll_history_xuetang)LinearLayout ll_history_xuetang;

    @Bind(R.id.ll_addchild)LinearLayout ll_addchild;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("fromWhere")) {
            int fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initHead(fromWhere);
        }
    }
    private void initHead(int fromWhere) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("录入");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (fromWhere){
            case Constant.FROM_XUEYA:
                navigation_name.setText("血压历史");
                initView(fromWhere);
                break;
            case Constant.FROM_XUETANG:
                navigation_name.setText("血糖历史");
                initView(fromWhere);
                break;
        }
    }

    private void initView(int fromWhere) {
        switch (fromWhere){
            case Constant.FROM_XUEYA:
                ll_history_xueya.setVisibility(View.VISIBLE);
                for(int i=0;i<5;i++){
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_xueya,null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
                    ll_addchild.addView(view_xueya);
                }

                break;

            case Constant.FROM_XUETANG:
                ll_history_xuetang.setVisibility(View.VISIBLE);
                for(int i=0;i<5;i++) {
                    View view_xuetang = LayoutInflater.from(this).inflate(R.layout.item_history_xuetang, null);
                    view_xuetang.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,100));
                    ll_addchild.addView(view_xuetang);
                }
                break;
        }
    }
}
