package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.WheelView;
import com.enjoyor.healthhouse.utils.CustomUtil;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.loopj.android.http.RequestParams;
import com.xk.sanjay.rulberview.RulerWheel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class BPInputActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;

    @Bind(R.id.ll_first)
    LinearLayout ll_first;//第一个刻度尺（默认）
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.ll_second)
    LinearLayout ll_second;//第二个刻度尺
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.rl_choicedate)
    RelativeLayout rl_choicedate;//选择日期（默认）
    @Bind(R.id.rl_choicetime)
    RelativeLayout rl_choicetime;//选择时间
    @Bind(R.id.rl_choicewhat)
    RelativeLayout rl_choicewhat;//时间端
    @Bind(R.id.tv_choicewhat)
    TextView tv_choicewhat;

    @Bind(R.id.bpinput_bp_tv)
    TextView bpinput_bp_tv;
    @Bind(R.id.bpinput_img_up_jian)
    ImageView bpinput_img_up_jian;
    @Bind(R.id.bpinput_img_up_jia)
    ImageView bpinput_img_up_jia;
    @Bind(R.id.bpinput_bp_tv_low)
    TextView bpinput_bp_tv_low;
    @Bind(R.id.bpinput_img_low_jian)
    ImageView bpinput_img_low_jian;
    @Bind(R.id.bpiinput_img_up_jia)
    ImageView bpiinput_img_up_jia;
    @Bind(R.id.bpinput_up)
    RulerWheel bpinput_up;
    @Bind(R.id.bpinput_low)
    RulerWheel bpinput_low;

    @Bind(R.id.tv_numberinfo)
    TextView tv_numberinfo;//数字信息
    @Bind(R.id.tv_numberinfo_second)
    TextView tv_numberinfo_second;//数字信息
    @Bind(R.id.tv_display_more)
    TextView tv_display_more;
    @Bind(R.id.tv_display)
    TextView tv_display;//刻度尺数值描述
    @Bind(R.id.bpinput_save)
    TextView bpinput_save;

    private int fromWhere;
    private int CHOICE_DATE = 1;
    private int CHOICE_TIME = 2;
    private int CHOICE_WHAT = 3;
    private int DEFAULT_VALUE = 0;
    private String str_year;
    private String str_mouth;
    private String str_day;
    private String str_hour;
    private String str_mwhat;
    private PopupWindow popupWindow;
    private String TAG = this.getClass().getSimpleName();
    private static final String[] mWhat = {"空腹血糖", "早餐后血糖", "午餐前血糖", "午餐后血糖", "晚餐前血糖", "晚餐后血糖", "睡前血糖"};
    private static String BP_URL = "app/savebp.action";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpinput_ac_layout);
        ButterKnife.bind(this);
        context = this;
        if (getIntent().hasExtra("fromWhere")) {
            fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initHead(fromWhere);
        }
        initEvent();
    }

    @OnClick(R.id.bpinput_save)
    public void onSaveClick() {
        Log.i("zxw","asssssssssssssssssss");
        switch (fromWhere){
            case Constant.FROM_XUEYA:
                RequestParams params = new RequestParams();
                params.add("userId","195");
                params.add("times", "");
                params.add("systolicPressure", "");
                params.add("diastolicPressure", "");
                CustomUtil.saveHealthInfo(context,BP_URL, params);
                break;
        }
    }

    private void initHead(final int fromWhere) {
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setText("历史");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                ll_second.setVisibility(View.VISIBLE);
                rl_choicetime.setVisibility(View.VISIBLE);
                navigation_name.setText("血压录入");
                tv_numberinfo.setText("舒张压（mmol/L）");
                tv_numberinfo_second.setText("收缩压（mmol/L）");
                tv_display.setText("收缩压/舒张压  正常范围：90-140/60-190mmHg");
                initFirstView(0, 100, 10, "70");
                initSecondView(0, 200, 10, 120);
                break;
            case Constant.FROM_SHENGAO:
                navigation_name.setText("身高录入");
                tv_numberinfo.setText("身高（cm）");
                tv_display.setVisibility(View.GONE);
                initFirstView(100, 250, 10, "160");
                break;
            case Constant.FROM_XUETANG:
                rl_choicewhat.setVisibility(View.VISIBLE);
                navigation_name.setText("血糖录入");
                tv_numberinfo.setText("血糖录入");
                tv_display.setText(
                        Html.fromHtml(
                                "<font color='#009cd6'>注</font>" +
                                        "以上范围仅适用于尿病患者"));
                tv_display.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG); //下划线
                tv_display.getPaint().setAntiAlias(true);//抗锯齿
                tv_display_more.setVisibility(View.VISIBLE);
                tv_display_more.setText("空腹血糖标准范围（4.4-7.0mmol/L）" + "\n非空腹血糖标准范围（4.4-10.0mmol/L）");
                initFirstView(2, 15, 1, "6.0");
                break;
            case Constant.FROM_XUEYANG:
                navigation_name.setText("血氧录入");
                tv_numberinfo.setText("血氧（%）");
                tv_display.setText("血氧的正常范围：>95&");
                initFirstView(50, 99, 1, "98.0");
                break;
            case Constant.FROM_YAOWEI:
                navigation_name.setText("腰围录入");
                tv_numberinfo.setText("腰围（cm）");
                tv_display.setText("标准腰围计算方法" + "\n" + "男性：身高（cm）/2-11（cm），" + "\n" +
                        "女性：身高（cm）/2-14（cm），" + "\n" + "±5%为正常范围");
                initFirstView(50, 100, 10, "70");
                break;
            case Constant.FROM_TIZHONG:
                navigation_name.setText("体重录入");
                tv_numberinfo.setText("体重（kg）");
                tv_display.setVisibility(View.GONE);
                initFirstView(60, 250, 10, "80");
                break;
            case Constant.FROM_TIWEN:
                navigation_name.setText("体温录入");
                tv_numberinfo.setText("体温（℃）");
                tv_display.setText("人体正常体温平均在36-37℃之间（腋窝）");
                initFirstView(35, 42, 1, "36.7");
                break;
            case Constant.FROM_XINDIAN:
                navigation_name.setText("心电录入");
                tv_numberinfo.setText("心率（bpm）");
                tv_display.setText("安静时心率标准范围（60-100bpm）" + "\n" + "安静时心率>100为绞性心动过速" + "\n" +
                        "安静时心率<60为绞性心动过缓");
                initFirstView(40, 120, 10, "80");
                break;
        }
        selWhate();
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fromWhere) {
                    case Constant.FROM_XUEYA:
                        Intent intent_xueya = new Intent(context, HistoryActivity.class);
                        intent_xueya.putExtra("fromWhere", Constant.FROM_XUEYA);
                        startActivity(intent_xueya);
                        break;
                    case Constant.FROM_SHENGAO:
                        Intent intent_shengao = new Intent(context, HistoryActivity.class);
                        intent_shengao.putExtra("fromWhere", Constant.FROM_SHENGAO);
                        startActivity(intent_shengao);
                        break;
                    case Constant.FROM_XUETANG:
                        Intent intent_xuetang = new Intent(context, HistoryActivity.class);
                        intent_xuetang.putExtra("fromWhere", Constant.FROM_XUETANG);
                        startActivity(intent_xuetang);
                        break;
                    case Constant.FROM_XUEYANG:
                        Intent intent_xueyang = new Intent(context, HistoryActivity.class);
                        intent_xueyang.putExtra("fromWhere", Constant.FROM_XUEYANG);
                        startActivity(intent_xueyang);
                        break;
                    case Constant.FROM_YAOWEI:
                        Intent intent_yaowei = new Intent(context, HistoryActivity.class);
                        intent_yaowei.putExtra("fromWhere", Constant.FROM_YAOWEI);
                        startActivity(intent_yaowei);
                        break;
                    case Constant.FROM_TIZHONG:
                        Intent intent_tizhong = new Intent(context, HistoryActivity.class);
                        intent_tizhong.putExtra("fromWhere", Constant.FROM_TIZHONG);
                        startActivity(intent_tizhong);
                        break;
                    case Constant.FROM_TIWEN:
                        Intent intent_tiwen = new Intent(context, HistoryActivity.class);
                        intent_tiwen.putExtra("fromWhere", Constant.FROM_TIWEN);
                        startActivity(intent_tiwen);
                        break;
                    case Constant.FROM_XINDIAN:
                        Intent intent_xindian = new Intent(context, HistoryActivity.class);
                        intent_xindian.putExtra("fromWhere", Constant.FROM_XINDIAN);
                        startActivity(intent_xindian);
                        break;
                }
            }
        });

    }

    private void selWhate() {

    }

    private void initEvent() {
        rl_choicedate.setOnClickListener(this);
        rl_choicetime.setOnClickListener(this);
        bpinput_img_up_jian.setOnClickListener(this);
        bpinput_img_up_jia.setOnClickListener(this);
        bpinput_img_low_jian.setOnClickListener(this);
        bpiinput_img_up_jia.setOnClickListener(this);
        rl_choicewhat.setOnClickListener(this);
    }

    /**
     * @param from//最小值
     * @param to//最大值
     * @param span//数字跨度
     * @param value//默认值
     */
    private void initFirstView(int from, int to, int span, String value) {
        List<String> list = new ArrayList<>();
        if (10 == span) {
            for (int i = from; i <= to; i += span) {
                list.add(i + "");
                for (int j = 1; j < 10; j++) {
                    list.add((i + j) + "");
                }
            }
        } else if (1 == span) {
            for (float i = from; i <= to; i += span) {
                list.add(i + "");
                for (float j = 0.1f; j < 1.0f; j += 0.1) {
                    list.add((i + j) + "");
                }
            }
        } else {

        }

        bpinput_bp_tv.setText(value);
        bpinput_up.setData(list);
//        bpinput_up.setSelectedValue(value + "");
        bpinput_up.setSelectedValue(value);
        bpinput_up.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                bpinput_bp_tv.setText(newValue);
                float a = Float.parseFloat(newValue);
                Log.i("zxw", a + "");
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {
            }
        });

    }

    private void initSecondView(int from, int to, int span, int value) {
        List<String> list = new ArrayList<>();
        for (int i = from; i <= to; i += span) {
            list.add(i + "");
            for (int j = 1; j < 10; j++) {
                list.add((i + j) + "");
            }
        }
        bpinput_bp_tv_low.setText(value + "");
        bpinput_low.setData(list);
        bpinput_low.setSelectedValue(value + "");
        bpinput_low.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                bpinput_bp_tv_low.setText(newValue + "");
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        String _strng = bpinput_bp_tv.getText().toString();
        String _strng_second = bpinput_bp_tv_low.getText().toString();

        switch (key) {
            case R.id.bpinput_img_up_jian:
                updateData(1, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_up_jia:
                updateData(2, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_low_jian:
                updateData(1, _strng_second, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bpiinput_img_up_jia:

                updateData(2, _strng_second, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.rl_choicedate:
                initPopuptWindow(v, CHOICE_DATE);
                break;
            case R.id.rl_choicetime:
                initPopuptWindow(v, CHOICE_TIME);
                break;
            case R.id.rl_choicewhat:
                initPopuptWindow(v, CHOICE_WHAT);
                break;
        }
    }

    private void updateData(int i, String str, TextView id, RulerWheel view) {
        if (fromWhere == Constant.FROM_XUETANG || fromWhere == Constant.FROM_TIWEN || fromWhere == Constant.FROM_XUEYANG) {
            float value_float = Float.parseFloat(str);
            if (i == 1) {
                value_float -= 0.1;
            } else {
                value_float += 0.1;
            }
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String value = fnum.format(value_float);
            id.setText(value + "");
            view.setSelectedValue(value + "");
            view.invalidate();
        } else {
            int value_int = Integer.parseInt(str);
            if (i == 1) {
                value_int--;
            } else {
                value_int++;
            }
            id.setText(value_int + "");
            view.setSelectedValue(value_int + "");
            view.invalidate();
        }


    }

    protected void initPopuptWindow(View view, int which) {
        // TODO Auto-generated method stub

        View popupWindow_view = getLayoutInflater().inflate(R.layout.popwindow_wheelview, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
        if (CHOICE_DATE == which) {
            initWheelViewDate(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else if (CHOICE_TIME == which) {
            initWheelViewTime(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else if (CHOICE_WHAT == which) {
            initWheelViewWhat(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }

    }

    private void initWheelViewWhat(View popupWindow_view) {
        WheelView wv_day = (WheelView) popupWindow_view.findViewById(R.id.wv_day);
        wv_day.setData(getStartData(5));
        wv_day.setDefault(DEFAULT_VALUE);
        wv_day.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_mwhat = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        str_mwhat = wv_day.getSelectedText();
        popupWindow_view.findViewById(R.id.bt_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_choicewhat.setText(str_mwhat);
                popupWindow.dismiss();
            }
        });
        popupWindow_view.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void initWheelViewTime(View popupWindow_view) {
        WheelView wv_year = (WheelView) popupWindow_view.findViewById(R.id.wv_year);
        WheelView wv_mouth = (WheelView) popupWindow_view.findViewById(R.id.wv_mouth);
        WheelView wv_day = (WheelView) popupWindow_view.findViewById(R.id.wv_day);
        wv_year.setVisibility(View.GONE);
        wv_mouth.setVisibility(View.GONE);
        wv_day.setData(getStartData(4));
        wv_day.setDefault(DEFAULT_VALUE);
        wv_day.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_hour = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        str_hour = wv_day.getSelectedText();
        popupWindow_view.findViewById(R.id.bt_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_time.setText(str_hour);
                popupWindow.dismiss();
            }
        });
        popupWindow_view.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private void initWheelViewDate(View popupWindow_view) {
        WheelView wv_year = (WheelView) popupWindow_view.findViewById(R.id.wv_year);
        WheelView wv_mouth = (WheelView) popupWindow_view.findViewById(R.id.wv_mouth);
        WheelView wv_day = (WheelView) popupWindow_view.findViewById(R.id.wv_day);
        wv_year.setData(getStartData(1));
        wv_mouth.setData(getStartData(2));
        wv_day.setData(getStartData(3));
        wv_year.setDefault(DEFAULT_VALUE);
        wv_mouth.setDefault(DEFAULT_VALUE);
        wv_day.setDefault(DEFAULT_VALUE);
        wv_year.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_year = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        str_year = wv_year.getSelectedText();
        wv_mouth.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_mouth = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        str_mouth = wv_mouth.getSelectedText();
        wv_day.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_day = text;
            }

            @Override
            public void selecting(int id, String text) {
            }
        });
        str_day = wv_day.getSelectedText();
        popupWindow_view.findViewById(R.id.bt_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_date.setText(str_year + str_mouth + str_day);
                popupWindow.dismiss();
            }
        });
        popupWindow_view.findViewById(R.id.bt_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    private ArrayList<String> getStartData(int start) {
        ArrayList<String> list = new ArrayList<>();
        long date;
        switch (start) {
            case 1:
                for (int i = 0; i < 10; i++) {
                    date = DateUtil.getDaysAfter(i);
                    String str_year = DateUtil.longToDateString(date, "yyyy");
                    int year = Integer.parseInt(str_year) + i;
                    list.add(year + "-");
                }
                break;
            case 2:
                for (int i = 0; i < 12; i++) {
                    date = DateUtil.getDaysAfter(i);
                    String str_mouth = DateUtil.longToDateString(date, "MM");
                    int mouth = Integer.parseInt(str_mouth) + i;
                    if (mouth > 12) {
                        mouth -= 12;
                    }
                    list.add(mouth + "-");
                }
                break;
            case 3:
                for (int i = 0; i < 30; i++) {
                    date = DateUtil.getDaysAfter(i);
                    String str_day = DateUtil.longToDateString(date, "dd");
                    list.add(str_day + "");
                }
                break;
            case 4:
                for (int i = 0; i < 24; i++) {
                    date = DateUtil.getDaysAfter(i);
                    String str_mouth = DateUtil.longToDateString(date, "HH");
                    int hour = Integer.parseInt(str_mouth) + i;
                    if (hour > 24) {
                        hour -= 24;
                    }
                    list.add(hour + "点");
                }
                break;
            case 5:
                for (int i = 0; i < mWhat.length; i++) {
                    list.add(mWhat[i]);
                }
                break;
        }
        return list;
    }
}
