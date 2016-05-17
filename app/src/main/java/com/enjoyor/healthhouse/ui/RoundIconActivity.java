package com.enjoyor.healthhouse.ui;

import android.os.Bundle;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.Constant;

/**
 * Created by Administrator on 2016/5/13.
 */
public class RoundIconActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roundicon);

        if(getIntent().hasExtra("fromWhere")){
            int fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initIconXML(fromWhere);
        }
    }

    private void initIconXML(int fromWhere) {
        switch (fromWhere){
            case Constant.FROM_XUEYA:
                break;
        }
    }
}
