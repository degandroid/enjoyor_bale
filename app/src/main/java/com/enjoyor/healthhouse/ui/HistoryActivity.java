package com.enjoyor.healthhouse.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.HistoryInfoList;
import com.enjoyor.healthhouse.bean.HistoryInfoModel;
import com.enjoyor.healthhouse.bean.Page;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.BottomScrollView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

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

    @Bind(R.id.ll_hascircle)
    LinearLayout ll_hascircle;
    @Bind(R.id.ll_nocircle)
    LinearLayout ll_nocircle;
    @Bind(R.id.tv_nocircle_info)
    TextView tv_nocircle_info;

    @Bind(R.id.ll_history_shengao)
    LinearLayout ll_history_shengao;
    @Bind(R.id.tv_twotittle_one)
    TextView tv_twotittle_one;
    @Bind(R.id.tv_twotittle_two)
    TextView tv_twotittle_two;


    @Bind(R.id.ll_history_xueya)
    LinearLayout ll_history_xueya;
    @Bind(R.id.tv_threetittle_one)
    TextView tv_threetittle_one;
    @Bind(R.id.tv_threetittle_two)
    TextView tv_threetittle_two;
    @Bind(R.id.tv_threetittle_three)
    TextView tv_threetittle_three;


    @Bind(R.id.ll_history_xuetang)
    LinearLayout ll_history_xuetang;
    @Bind(R.id.bsv_bottom)
    BottomScrollView bsv_bottom;

    //    @Bind(R.id.sv_refresh)
//    BottomScrollView sv_refreshw;
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
    private String BMI_URL = "app/bmilist.do";//身高体重历史
    private String BS_URL = "app/bslist.do";//血糖历史
    private String BO_URL = "app/bolist.do";//血氧历史
    private String WL_URL = "app/whrlist.do";//腰围历史
    private String TL_URL = "app/temperlist.do";//体温历史
    private String ECG_URL = "app/ecglist.do";//心电历史

//    private float x1, x2, y1, y2;

    private List<HistoryInfoList> historyInfoLists = new ArrayList<>();
    private Page page;
    private int count = 0;

//    private View view_xueya;
//    private TextView tv_three_one;
//    private TextView tv_three_two;
//    private TextView tv_three_three;

    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);
        context = this;
        initHead();
        bsv_bottom.setOnScrollToBottomLintener(new BottomScrollView.OnScrollToBottomListener() {
            @Override
            public void onScrollBottomListener(boolean isBottom) {
                if (isBottom) {
                    getDate(count++);
                }
            }
        });
        if (getIntent().hasExtra("fromWhere")) {
            fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            getDate(1);
        }
    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        //继承了Activity的onTouchEvent方法，直接监听点击事件
//        if (event.getAction() == MotionEvent.ACTION_DOWN) {
//            //当手指按下的时候
//            x1 = event.getX();
//            y1 = event.getY();
//        }
//        if (event.getAction() == MotionEvent.ACTION_UP) {
//            //当手指离开的时候
//            x2 = event.getX();
//            y2 = event.getY();
//            if (y1 - y2 > 50) {
////                Toast.makeText(HistoryActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
//                getDate(count++);
//            } else if (y2 - y1 > 50) {
////                Toast.makeText(HistoryActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
//                if (count > 0) {
//                    getDate(count--);
//                } else {
//                    Toast.makeText(HistoryActivity.this, "已经是第一页了", Toast.LENGTH_SHORT).show();
//                }
//
//            } else if (x1 - x2 > 50) {
////                Toast.makeText(HistoryActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
//            } else if (x2 - x1 > 50) {
////                Toast.makeText(HistoryActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
//            }
//        }
//        return super.onTouchEvent(event);
//    }

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
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                navigation_name.setText("血压历史");
                navigation_name.setText("血压历史");
                ll_hascircle.setVisibility(View.VISIBLE);
                ll_history_xueya.setVisibility(View.VISIBLE);
                tv_threetittle_one.setText("日期");
                tv_threetittle_two.setText("收缩压");
                tv_threetittle_three.setText("舒张压");
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_xueya, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_three_one = (TextView) view_xueya.findViewById(R.id.tv_three_one);
                    TextView tv_three_two = (TextView) view_xueya.findViewById(R.id.tv_three_two);
                    TextView tv_three_three = (TextView) view_xueya.findViewById(R.id.tv_three_three);
                    tv_three_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_three_two.setText(historyInfoLists.get(i).getSystolicPressure() + "");

                    if (historyInfoLists.get(i).getSystolicPressure() > 140) {
                        tv_three_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                    }
                    if(historyInfoLists.get(i).getSystolicPressure() < 90){
                        tv_three_two.setTextColor(getResources().getColor(R.color.color_normal));
                    }
                    tv_three_three.setText(historyInfoLists.get(i).getDiastolicPressure() + "");
                    if (historyInfoLists.get(i).getDiastolicPressure() > 90) {
                        tv_three_three.setTextColor(getResources().getColor(R.color.color_abnormal));
                    }
                    if(historyInfoLists.get(i).getDiastolicPressure() < 60){
                        tv_three_three.setTextColor(getResources().getColor(R.color.color_normal));
                    }
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_SHENGAO:
                navigation_name.setText("身高历史");
                navigation_name.setText("身高历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("身高");
                ll_history_shengao.setVisibility(View.VISIBLE);
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getHeight() + "");
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_XUETANG:
                navigation_name.setText("血糖历史");
                navigation_name.setText("血糖历史");
                ll_history_xuetang.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText(Html.fromHtml(
                        "空腹血糖标准范围（4.4-7.0mmol/L）"
                                + "\n非空腹血糖标准范围（4.4-10.0mmol/L）"
                                + "\n注:偏高为"
                                + "<font color='#FF4081'>红色</font>"
                                + "字体，偏低为"
                                + "<font color='#55d6ea'>蓝色</font>"
                                + "字体"));
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xuetang = LayoutInflater.from(this).inflate(R.layout.item_history_xuetang, null);
                    view_xuetang.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_eight_one = (TextView) view_xuetang.findViewById(R.id.tv_eight_one);
                    TextView tv_eight_two = (TextView) view_xuetang.findViewById(R.id.tv_eight_two);
                    TextView tv_eight_three = (TextView) view_xuetang.findViewById(R.id.tv_eight_three);
                    TextView tv_eight_four = (TextView) view_xuetang.findViewById(R.id.tv_eight_four);
                    TextView tv_eight_five = (TextView) view_xuetang.findViewById(R.id.tv_eight_five);
                    TextView tv_eight_six = (TextView) view_xuetang.findViewById(R.id.tv_eight_six);
                    TextView tv_eight_seven = (TextView) view_xuetang.findViewById(R.id.tv_eight_seven);
                    TextView tv_eight_eight = (TextView) view_xuetang.findViewById(R.id.tv_eight_eight);
                    tv_eight_one.setText(historyInfoLists.get(0).getRecordTime() + "");
                    tv_eight_two.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_three.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_four.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_five.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_six.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_seven.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    tv_eight_eight.setText(historyInfoLists.get(0).getBloodSugar() + "");
                    ll_addchild.addView(view_xuetang);
                }
                break;
            case Constant.FROM_XUEYANG:
                navigation_name.setText("血氧历史");
                navigation_name.setText("血氧历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("血氧");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText(
                        Html.fromHtml(
                                "血氧正常范围大于95"
                                        +"\n注:小于为"
                                        + "<font color='#FF4081'>红色</font>"
                                        + "字体，大于等于为"
                                        + "<font color='#55d6ea'>蓝色</font>"
                                        + "字体"));
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getBo() + "");
                    if (historyInfoLists.get(0).getBo() < 95) {
                        tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                    }
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_YAOWEI:
                navigation_name.setText("腰围历史");
                navigation_name.setText("腰围历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("腰围");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("标准腰围计算方法" + "\n" + "男性：身高（cm）/2-11（cm），" + "\n" +
                        "女性：身高（cm）/2-14（cm），" + "\n" + "±5%为正常范围");
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getWaistLine() + "");
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_TIZHONG:
                navigation_name.setText("体重历史");
                navigation_name.setText("体重历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("体重");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("男性：（身高cm-80）*70%=标准体重" + "\n" + "nv性：（身高cm-70）*60%=标准体重");
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getWeight() + "");
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_TIWEN:
                navigation_name.setText("体温历史");
                navigation_name.setText("体温历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("体温");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("人体正常体温平均在36-37℃之间（腋窝）");
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getTemperature() + "");
                    if(Float.parseFloat(historyInfoLists.get(0).getTemperature())>37){
                        tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                    }
                    if(Float.parseFloat(historyInfoLists.get(0).getTemperature())<36){
                        tv_two_two.setTextColor(getResources().getColor(R.color.color_normal));
                    }
                    ll_addchild.addView(view_xueya);
                }
                break;
            case Constant.FROM_XINDIAN:
                navigation_name.setText("心电历史");
                navigation_name.setText("心电历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("心率");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText(
                        Html.fromHtml(
                                "安静时心率正常值"
                                        + "\n标准范围（60-100bpm）"
                                        + "\n注:偏高为"
                                        + "<font color='#FF4081'>红色</font>"
                                        + "字体，偏低为"
                                        + "<font color='#55d6ea'>蓝色</font>"
                                        + "字体"));
//                ll_addchild.removeAllViews();
                for (int i = 0; i < historyInfoLists.size(); i++) {
                    View view_xueya = LayoutInflater.from(this).inflate(R.layout.item_history_shengao, null);
                    view_xueya.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
                    TextView tv_two_one = (TextView) view_xueya.findViewById(R.id.tv_two_one);
                    TextView tv_two_two = (TextView) view_xueya.findViewById(R.id.tv_two_two);
                    tv_two_one.setText(historyInfoLists.get(i).getRecordTime() + "");
                    tv_two_two.setText(historyInfoLists.get(i).getEcg() + "");
                    if(historyInfoLists.get(0).getEcg()>100){
                        tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                    }
                    if(historyInfoLists.get(0).getEcg()<60){
                        tv_two_two.setTextColor(getResources().getColor(R.color.color_normal));
                    }
                    ll_addchild.addView(view_xueya);
                }
                break;
        }
    }

    private void getDate(int _count) {
        long _userId = MyApplication.getInstance().getDBHelper().getUser().getUserId();
        if (!StringUtils.isBlank(_userId + "")) {
            userId = MyApplication.getInstance().getDBHelper().getUser().getUserId() + "";
        }
        RequestParams params_xueya = new RequestParams();
        params_xueya.add("userId", userId);
        params_xueya.add("pageMethod", "2");
        params_xueya.add("pageNum", _count + "");
        params_xueya.add("pageCount", "8");
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                selectHealthInfo(BP_URL, params_xueya);
                break;
            case Constant.FROM_SHENGAO:
                selectHealthInfo(BMI_URL, params_xueya);
                break;
            case Constant.FROM_XUETANG:
                selectHealthInfo(BS_URL, params_xueya);
                break;
            case Constant.FROM_XUEYANG:
                selectHealthInfo(BO_URL, params_xueya);
                break;
            case Constant.FROM_YAOWEI:
                selectHealthInfo(WL_URL, params_xueya);
                break;
            case Constant.FROM_TIZHONG:
                selectHealthInfo(BMI_URL, params_xueya);
                break;
            case Constant.FROM_TIWEN:
                selectHealthInfo(TL_URL, params_xueya);
                break;
            case Constant.FROM_XINDIAN:
                selectHealthInfo(ECG_URL, params_xueya);
                break;
        }

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

    private boolean selectHealthInfo(String url, RequestParams params) {
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    HistoryInfoModel historyInfoModel = JsonHelper.getJson(apiMessage.Data, HistoryInfoModel.class);
                    historyInfoLists.clear();

                    switch (fromWhere) {
                        case Constant.FROM_XUEYA:
                            List<HistoryInfoList> _list_xueya = historyInfoModel.getBplist();
                            historyInfoLists.addAll(_list_xueya);
                            break;
                        case Constant.FROM_SHENGAO:
                            List<HistoryInfoList> _list_shengao = historyInfoModel.getBmilist();
                            historyInfoLists.addAll(_list_shengao);
                            break;
                        case Constant.FROM_XUETANG:
                            List<HistoryInfoList> _list_xuetang = historyInfoModel.getBslist();
                            historyInfoLists.addAll(_list_xuetang);
                            break;
                        case Constant.FROM_XUEYANG:
                            List<HistoryInfoList> _list_xueyang = historyInfoModel.getBolist();
                            historyInfoLists.addAll(_list_xueyang);
                            break;
                        case Constant.FROM_YAOWEI:
                            List<HistoryInfoList> _list_yaowei = historyInfoModel.getWhrlist();
                            historyInfoLists.addAll(_list_yaowei);
                            break;
                        case Constant.FROM_TIZHONG:
                            List<HistoryInfoList> _list_tizhong = historyInfoModel.getBmilist();
                            historyInfoLists.addAll(_list_tizhong);
                            break;
                        case Constant.FROM_TIWEN:
                            List<HistoryInfoList> _list_tiwen = historyInfoModel.getTemperlist();
                            historyInfoLists.addAll(_list_tiwen);
                            break;
                        case Constant.FROM_XINDIAN:
                            List<HistoryInfoList> _list_xindian = historyInfoModel.getEcglist();
                            historyInfoLists.addAll(_list_xindian);
                            break;
                    }
                    if (historyInfoLists.size() > 0) {
                        initListView();
                    }
                    page = historyInfoModel.getPage();

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
