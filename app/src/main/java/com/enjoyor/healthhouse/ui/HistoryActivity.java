package com.enjoyor.healthhouse.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.BottomScrollView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
public class HistoryActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Bind(R.id.bt_list)
    Button bt_list;
    @Bind(R.id.bt_curve)
    Button bt_curve;

    @Bind(R.id.ll_history_shengao)
    LinearLayout ll_history_shengao;
    @Bind(R.id.ll_history_xueya)
    LinearLayout ll_history_xueya;
    @Bind(R.id.ll_history_xuetang)
    LinearLayout ll_history_xuetang;

    @Bind(R.id.sv_refresh)
    BottomScrollView sv_refreshw;
    @Bind(R.id.ll_null)
    LinearLayout ll_null;
    @Bind(R.id.ll_addchild)
    LinearLayout ll_addchild;
    @Bind(R.id.ll_addweb)
    LinearLayout ll_addweb;

    private int fromWhere;
    private Context context;

    private String userId;

    private String BP_URL = "app/bplist.do";//血压历史

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        context = this;
        initHead();
        sv_refreshw.setOnScrollToBottomLintener(new BottomScrollView.OnScrollToBottomListener() {

            @Override
            public void onScrollBottomListener(boolean isBottom) {
                // TODO Auto-generated method stub
                Log.e("SCROLLVIEW", isBottom + "");

            }
        });
        if (getIntent().hasExtra("fromWhere")) {
            fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initListView();
//            lv_history.setAdapter();
        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);

    }

    private void initHead() {
        bt_list.setOnClickListener(this);
        bt_curve.setOnClickListener(this);
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("录入");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initListView() {
        long _userId = MyApplication.getInstance().getDBHelper().getUser().getUserId();
        if (!StringUtils.isBlank(_userId + "")) {
            userId = MyApplication.getInstance().getDBHelper().getUser().getUserId() + "";
        }

        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                navigation_name.setText("血压历史");
                navigation_name.setText("血压历史");
                ll_history_xueya.setVisibility(View.VISIBLE);
                RequestParams params_xueya = new RequestParams();
//                params_xueya.add("userId", userId);
//                params_xueya.add("times", times);
//                params_xueya.add("waistLine", waistLine);
                selectHealthInfo(BP_URL, params_xueya);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_xueya, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_SHENGAO:
                navigation_name.setText("身高历史");
                navigation_name.setText("身高历史");
//                lv_history.setAdapter(new HistoryAdapter(2));
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_XUETANG:
                navigation_name.setText("血糖历史");
                navigation_name.setText("血糖历史");
//                lv_history.setAdapter(new HistoryAdapter(8));
                ll_history_xuetang.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xuetang = LayoutInflater.from(this).inflate(R.layout.item_history_xuetang, null);
                    view_xuetang.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xuetang);
                }
                break;
            case Constant.FROM_XUEYANG:
                navigation_name.setText("血氧历史");
                navigation_name.setText("血氧历史");
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_YAOWEI:
                navigation_name.setText("腰围历史");
                navigation_name.setText("腰围历史");
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_TIZHONG:
                navigation_name.setText("体重历史");
                navigation_name.setText("体重历史");
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_TIWEN:
                navigation_name.setText("体温历史");
                navigation_name.setText("体温历史");
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_XINDIAN:
                navigation_name.setText("心电历史");
                navigation_name.setText("心电历史");
                ll_history_shengao.setVisibility(View.VISIBLE);
                for (int i = 0; i < 5; i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    ll_addchild.addView(view_xueya);
                }
                break;
        }
    }

    private boolean getDate() {


        return true;
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.bt_list:
                selectDisplay(1);
                if (ll_addchild.getChildCount() > 0) {
                    ll_addchild.setVisibility(View.VISIBLE);
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.GONE);
                } else {
                    ll_addchild.setVisibility(View.GONE);
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                }

                break;
            case R.id.bt_curve:
                selectDisplay(2);
                if (ll_addweb.getChildCount() > 0) {
                    ll_addchild.setVisibility(View.GONE);
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.VISIBLE);
                } else {
                    ll_addchild.setVisibility(View.GONE);
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                }
                break;
        }
    }

    private void selectDisplay(int which) {
        if (1 == which) {
            bt_list.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_list.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_curve.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_curve.setBackgroundResource(R.drawable.white_null);
            ll_addchild.setVisibility(View.VISIBLE);
            ll_addweb.setVisibility(View.GONE);
        } else {
            bt_curve.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_curve.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_list.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_list.setBackgroundResource(R.drawable.white_null);
            ll_addchild.setVisibility(View.GONE);
            ll_addweb.setVisibility(View.VISIBLE);

        }
    }


//        class ViewHolderTwo {
//            @Bind(R.id.tv_two_one)
//            TextView tv_two_one;
//            @Bind(R.id.tv_two_two)
//            TextView tv_two_two;
//
//            ViewHolderTwo(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }
//
//        class ViewHolderThree {
//            @Bind(R.id.tv_three_one)
//            TextView tv_three_one;
//            @Bind(R.id.tv_three_two)
//            TextView tv_three_two;
//            @Bind(R.id.tv_three_three)
//            TextView tv_three_three;
//
//            ViewHolderThree(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }
//
//        class ViewHolderEight {
//            @Bind(R.id.tv_eight_one)
//            TextView tv_eight_one;
//            @Bind(R.id.tv_eight_two)
//            TextView tv_eight_two;
//            @Bind(R.id.tv_eight_three)
//            TextView tv_eight_three;
//            @Bind(R.id.tv_eight_four)
//            TextView tv_eight_four;
//            @Bind(R.id.tv_eight_five)
//            TextView tv_eight_five;
//            @Bind(R.id.tv_eight_six)
//            TextView tv_eight_six;
//            @Bind(R.id.tv_eight_seven)
//            TextView tv_eight_seven;
//            @Bind(R.id.tv_eight_eight)
//            TextView tv_eight_eight;
//            ViewHolderEight(View view) {
//                ButterKnife.bind(this, view);
//            }
//        }


    private boolean selectHealthInfo(String url, RequestParams params) {
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {

                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
        return true;
    }
}
