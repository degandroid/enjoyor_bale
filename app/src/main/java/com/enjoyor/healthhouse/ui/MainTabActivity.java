package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.fragments.CommunityFragment;
import com.enjoyor.healthhouse.fragments.HealthFragment;
import com.enjoyor.healthhouse.fragments.HomeFragment;
import com.enjoyor.healthhouse.fragments.MineFragment;
import com.enjoyor.healthhouse.fragments.ServiceFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class MainTabActivity extends BaseActivity implements View.OnClickListener{
    private long preTime;
    @Bind(R.id.main_tab1)LinearLayout main_tab1;
    @Bind(R.id.main_tab2)LinearLayout main_tab2;
    @Bind(R.id.main_tab3)LinearLayout main_tab3;
    @Bind(R.id.main_tab4)LinearLayout main_tab4;
    @Bind(R.id.main_tab5)LinearLayout main_tab5;

    @Bind(R.id.iv_tab1)ImageView iv_tab1;
    @Bind(R.id.iv_tab2)ImageView iv_tab2;
    @Bind(R.id.iv_tab3)ImageView iv_tab3;
    @Bind(R.id.iv_tab4)ImageView iv_tab4;
    @Bind(R.id.iv_tab5)ImageView iv_tab5;

    @Bind(R.id.tv_tab1)TextView tv_tab1;
    @Bind(R.id.tv_tab2)TextView tv_tab2;
    @Bind(R.id.tv_tab3)TextView tv_tab3;
    @Bind(R.id.tv_tab4)TextView tv_tab4;
    @Bind(R.id.tv_tab5)TextView tv_tab5;

    @Bind(R.id.ll_content)LinearLayout ll_content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tab);
        ButterKnife.bind(this);
        //初始化tabbar图标
        initImageTab();
        //初始化tabbar文字
        initTextTab();
        //设置首页为tabbar的默认
        initLayoutTab();
        //设置第一页为默认的fragment
        initDefault();
        selectTab(1);

    }
    private void selectTab(int key) {
        initImageTab();
        initTextTab();
        switch (key){
            case 1:
                iv_tab1.setBackgroundResource(R.mipmap.shouye2);
                tv_tab1.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                break;

            case 2:
                iv_tab2.setBackgroundResource(R.mipmap.shequ2);
                tv_tab2.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                break;

            case 3:
                iv_tab3.setBackgroundResource(R.mipmap.jiankang2);
                tv_tab3.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                break;

            case 4:
                iv_tab4.setBackgroundResource(R.mipmap.fuwu2);
                tv_tab4.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                break;

            case 5:
                iv_tab5.setBackgroundResource(R.mipmap.geren2);
                tv_tab5.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                break;
        }
    }
    private void initTextTab() {
        tv_tab1.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
        tv_tab2.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
        tv_tab3.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
        tv_tab4.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
        tv_tab5.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
    }
    private void initImageTab() {
        iv_tab1.setBackgroundResource(R.mipmap.shouye);
        iv_tab2.setBackgroundResource(R.mipmap.shequ);
        iv_tab3.setBackgroundResource(R.mipmap.jiankang);
        iv_tab4.setBackgroundResource(R.mipmap.fuwu);
        iv_tab5.setBackgroundResource(R.mipmap.geren);
    }

    private void initDefault() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.ll_content, HomeFragment.getInstance(1));
//        transaction.show(HomeFragment.getInstance(1));
        transaction.commit();
    }
    private void initLayoutTab() {
        main_tab1.setOnClickListener(this);
        main_tab2.setOnClickListener(this);
        main_tab3.setOnClickListener(this);
        main_tab4.setOnClickListener(this);
        main_tab5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();

        switch (key){
            case R.id.main_tab1:
                selectTab(1);
                transaction.replace(R.id.ll_content, HomeFragment.getInstance(1));
                break;

            case R.id.main_tab2:
                selectTab(2);
                transaction.replace(R.id.ll_content, CommunityFragment.getInstance(2));
                break;

            case R.id.main_tab3:
                selectTab(3);
                transaction.replace(R.id.ll_content, HealthFragment.getInstance(3));
                break;

            case R.id.main_tab4:
                selectTab(4);
                transaction.replace(R.id.ll_content, ServiceFragment.getInstance(4));
                break;

            case R.id.main_tab5:
                selectTab(5);
                transaction.replace(R.id.ll_content, MineFragment.getInstance(5));
                break;
        }
        transaction.commit();
    }


    /*----------------------------------------双击退出程序----------------------------------------*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if (System.currentTimeMillis() - preTime > 2000) {
                Toast.makeText(this, "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            } else {
                this.finish();
                System.exit(0);
            }
        }
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
            preTime = System.currentTimeMillis();
        }
        return true;
    }
}
