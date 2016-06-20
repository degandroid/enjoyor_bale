package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
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
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.WheelView;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.CustomUtil;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.RequestParams;
import com.xk.sanjay.rulberview.RulerWheel;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    @Bind(R.id.tv_second_bar)TextView tv_second_bar;
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

    @Bind(R.id.container)CoordinatorLayout container;

    private int fromWhere;
    private int CHOICE_DATE = 1;
    private int CHOICE_TIME = 2;
    private int CHOICE_WHAT = 3;
    private int DEFAULT_VALUE = 5;
    private String str_year;
    private String str_mouth;
    private String str_day;
    private String str_hour;
    private String str_mwhat;
    private PopupWindow popupWindow;
    private String TAG = this.getClass().getSimpleName();
    private static final String[] mWhat = {"空腹血糖", "早餐后血糖", "午餐前血糖", "午餐后血糖", "晚餐前血糖", "晚餐后血糖", "睡前血糖"};

    private String userId;//用户ID
    private String times;//时间
    private String hours;//小时
    private String systolicPressure = "120";//高压
    private String diastolicPressure = "70";//低压
    private String height = "160";//身高
    private String weight = "80";//体重
    private String bloodSugar = "6.0";//血糖
    private String type = "1";//类型：1:空腹，4：随机血糖，5早餐后，6午餐前，7午餐后，8晚餐前，9晚餐后，10睡前
    private int _type = 9;
    private String bo = "98.0";//血氧
    private String waistLine = "70";//腰围
    private String temperature = "36.7";//体温
    private String ecg = "80";//心率


    private int FIRST_RULE = 1;
    private int SECOND_RULE = 2;
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
        if (BaseDate.getSessionId(this)!=null) {
            if (getDate()) {
                switch (fromWhere) {
                    case Constant.FROM_XUEYA:
                        RequestParams params_xueya = new RequestParams();
                        params_xueya.add("userId", userId);
                        params_xueya.add("times", times);
                        params_xueya.add("hours", hours);
                        params_xueya.add("systolicPressure", systolicPressure);
                        params_xueya.add("diastolicPressure", diastolicPressure);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_BP_URL, params_xueya,container);
                        break;
                    case Constant.FROM_SHENGAO:
                        RequestParams params_shengao = new RequestParams();
                        params_shengao.add("userId", userId);
                        params_shengao.add("times", times);
                        params_shengao.add("height", height);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_BMI_URL, params_shengao,container);
                        break;
                    case Constant.FROM_XUETANG:
                        Log.i("final_stype","userId"+userId+"\n"+"times"+times+"\n"+"bloodSugar"+bloodSugar+"\n"+"type"+ type);
                        RequestParams params = new RequestParams();
                        params.add("userId", userId);
                        params.add("times", times);
                        params.add("bloodSugar", bloodSugar);
                        params.add("type", type);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_BS_URL, params,container);
                        break;
                    case Constant.FROM_XUEYANG:
                        RequestParams params_xueyang = new RequestParams();
                        params_xueyang.add("userId", userId);
                        params_xueyang.add("times", times);
                        params_xueyang.add("bo", bo);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_BO_URL, params_xueyang,container);
                        break;
                    case Constant.FROM_YAOWEI:
                        RequestParams params_yaowei = new RequestParams();
                        params_yaowei.add("userId", userId);
                        params_yaowei.add("times", times);
                        params_yaowei.add("waistLine", waistLine);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_WL_URL, params_yaowei,container);
                        break;
                    case Constant.FROM_TIZHONG:
                        RequestParams params_tizhong = new RequestParams();
                        params_tizhong.add("userId", userId);
                        params_tizhong.add("times", times);
                        params_tizhong.add("weight", weight);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_BMI_URL, params_tizhong,container);
                        break;
                    case Constant.FROM_TIWEN:
                        RequestParams params_tiwen = new RequestParams();
                        params_tiwen.add("userId", userId);
                        params_tiwen.add("times", times);
                        params_tiwen.add("temperature", temperature);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_TP_URL, params_tiwen,container);
                        break;
                    case Constant.FROM_XINDIAN:
                        RequestParams params_xindian = new RequestParams();
                        params_xindian.add("userId", userId);
                        params_xindian.add("times", times);
                        params_xindian.add("ecg", ecg);
                        CustomUtil.saveHealthInfo(context, UrlInterface.SAVE_ECG_URL, params_xindian,container);
                        break;
                }
            }
        }else{
            dialog(context, "亲,您还未登录，是否立即登录", "取消", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disappear();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BPInputActivity.this, LoginActivity.class);
                    intent.putExtra(LoginActivity.FROM_BPINPUTACTIVITY, true);
                    startActivity(intent);
                    disappear();
                }
            });
        }

    }

    private boolean getDate() {
        /*----------------------------判断userId是否为空----------------------------*/
        long _userId = MyApplication.getInstance().getDBHelper().getUser().getUserId();
        if (!StringUtils.isBlank(_userId + "")) {
            userId = MyApplication.getInstance().getDBHelper().getUser().getUserId() + "";
        } else {
            return false;
        }

        /*----------------------------判断日期是否为空----------------------------*/
        String _date = tv_date.getText().toString();
        if(StringUtils.isBlank(_date)||_date.equals("请选择")){
            Snackbar.make(container,"请选择日期",Snackbar.LENGTH_SHORT).show();
            return false;
        }else{
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
            Date date_time=null;
            try {
                date_time=sdf.parse(_date);//将String to Date类型
            } catch (ParseException e) {
                e.printStackTrace();
            }
            long long_time=date_time.getTime()/1000;
            int long_now = DateUtil.getCurrentTime();
            if(long_time>long_now){
                Snackbar.make(container,"选择时间大于当前时间",Snackbar.LENGTH_SHORT).show();
                return false;
            }else{
                times = _date;
            }
        }


        /*----------------------------获取第一个刻度尺的值（默认）----------------------------*/
        String up = bpinput_bp_tv.getText().toString();
        if (!StringUtils.isBlank(up)) {
            diastolicPressure = up;
            height = up;
            bloodSugar = up;
            bo = up;
            waistLine = up;
            weight = up;
            temperature = up;
            ecg = up;
        }


        /*----------------------------判断时间端的类型----------------------------*/
        if (_type == 4) {
            type = "1";
        }else {
            type = _type + "";
        }


        /*----------------------------如果为血压时，添加第二个刻度尺以及判断时间是否为空----------------------------*/
        if (fromWhere == Constant.FROM_XUEYA) {
            /*判断时间是否为空或者未选择*/
            String _times = tv_time.getText().toString();
            if(StringUtils.isBlank(_times) ||_times.equals("请选择")){
                Snackbar.make(container,"请选择时间",Snackbar.LENGTH_SHORT).show();
                return false;
            }else{
                SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH");
                Date date_time=null;
                try {
                    date_time=sdf.parse(_date+" "+_times);//将String to Date类型
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                long long_time=date_time.getTime()/1000;
                int long_now = DateUtil.getCurrentTime();
                if(long_time>long_now){
                    Snackbar.make(container,"选择时间大于当前时间",Snackbar.LENGTH_SHORT).show();
                    return false;
                }else{
                    hours = _times;
                }
            }


            String down = bpinput_bp_tv_low.getText().toString();
            if (!StringUtils.isBlank(down)) {
                systolicPressure = down;
            }
        }

//        Log.i("zxw", "userId：" + userId + "\n" + "times：" + times + "\n" + "hours：" + hours
//                        + "\n" + "systolicPressure：" + systolicPressure + "\n" + "diastolicPressure：" + diastolicPressure
//                        + "\n" + "height：" + height + "\n" + "weight：" + weight + "\n" + "bloodSugar：" + bloodSugar
//                        + "\n" + "type：" + type + "\n" + "bo：" + bo + "\n" + "waistLine：" + waistLine
//                        + "\n" + "temperature：" + temperature + "\n" + "ecg：" + ecg
//        );
        return true;
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
                tv_second_bar.setVisibility(View.VISIBLE);
                navigation_name.setText("血压录入");
                tv_numberinfo.setText("舒张压（mmol/L）");
                tv_numberinfo_second.setText("收缩压（mmol/L）");
                tv_display.setText("收缩压/舒张压  正常范围：90-140/60-90mmHg");
                initFirstView(0, 100, 10, "70");
                initSecondView(0, 200, 10, 120);
                break;
            case Constant.FROM_SHENGAO:
                navigation_name.setText("身高录入");
                tv_numberinfo.setText("身高（cm）");
                tv_display.setVisibility(View.GONE);
                initFirstView(100, 240, 10, "160");
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
                initFirstView(2, 14, 1, "6.0");
                break;
            case Constant.FROM_XUEYANG:
                navigation_name.setText("血氧录入");
                tv_numberinfo.setText("血氧（%）");
                tv_display.setText("血氧的正常范围：>95%");
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
                initFirstView(30, 150, 10, "80");
                break;
            case Constant.FROM_TIWEN:
                navigation_name.setText("体温录入");
                tv_numberinfo.setText("体温（℃）");
                tv_display.setText("人体正常体温平均在36-37℃之间（腋窝）");
                initFirstView(35, 41, 1, "36.7");
                break;
            case Constant.FROM_XINDIAN:
                navigation_name.setText("心率录入");
                tv_numberinfo.setText("心率（bpm）");
                tv_display.setText("安静时心率标准范围（60-100bpm）" + "\n" + "安静时心率>100为窦性心动过速" + "\n" +
                        "安静时心率<60为窦性心动过缓");
                initFirstView(40, 120, 10, "80");
                break;
        }

            tv_right.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (BaseDate.getSessionId(BPInputActivity.this) != null) {
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
                    } else {
                        dialog(context, "亲,您还未登录，是否立即登录", "取消", "确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                disappear();
                            }
                        }, new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(BPInputActivity.this, LoginActivity.class);
                                intent.putExtra(LoginActivity.FROM_BPINPU_HISTORY, true);
                                startActivity(intent);
                                disappear();
                            }
                        });
                    }
                }
            });


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
    private void initFirstView(final int from, final int to, int span, String value) {
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
                    DecimalFormat fnum = new DecimalFormat("##0.0");
                    String s = fnum.format(i + j);
                    list.add(s);
                }
            }
        } else {
        }
        bpinput_bp_tv.setText(value);
        bpinput_up.setData(list);
        bpinput_up.setSelectedValue(value);
        bpinput_up.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {

                int a = (int)Float.parseFloat(newValue);
                if(a<=to&&a>=from){
                    bpinput_bp_tv.setText(newValue);
                }
                if (a > to) {
                    bpinput_bp_tv.setText(to+"");
                    bpinput_up.setSelectedValue(to + "");
                }
                if (a < from) {
                    bpinput_bp_tv.setText(from+"");
                    bpinput_up.setSelectedValue(from + "");
                }
                if(fromWhere == Constant.FROM_XUETANG ||fromWhere == Constant.FROM_XUEYANG || fromWhere ==Constant.FROM_TIWEN){
                    changeColor(Float.parseFloat(newValue));
                }else{
                    changeColor(Integer.parseInt(newValue));
                }
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {
            }
        });

    }

    private void changeColor(float a) {
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                changeColorFunction(a, 60, 90);
                break;
            case Constant.FROM_SHENGAO:

                break;
            case Constant.FROM_XUETANG:
                if(_type==4||_type==6||_type==8){
                    changeColorFunction(a, 4.4f, 7.0f);
                }else{
                    changeColorFunction(a, 4.4f, 10.0f);
                }

                break;
            case Constant.FROM_XUEYANG:
                changeColorFunction(a, 95, 100);
                break;
            case Constant.FROM_YAOWEI:

                break;
            case Constant.FROM_TIZHONG:

                break;
            case Constant.FROM_TIWEN:
                changeColorFunction(a, 36.0f, 37.0f);
                break;
            case Constant.FROM_XINDIAN:
                changeColorFunction(a, 60, 100);
                break;
        }

    }

    private void changeColorFunction(float a, float min, float max) {
        if (a > min && a < max) {
            bpinput_bp_tv.setTextColor(getResources().getColor(R.color.color_normal));
        } else {
            bpinput_bp_tv.setTextColor(getResources().getColor(R.color.color_abnormal));
        }
    }

    private void initSecondView(final int from,final int to, int span, int value) {
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
//                systolicPressure = newValue;
                float a = Float.parseFloat(newValue);
                if (a > 90 && a < 140) {
                    bpinput_bp_tv_low.setText(newValue + "");
                    bpinput_bp_tv_low.setTextColor(getResources().getColor(R.color.color_normal_second));
                } else {
                    bpinput_bp_tv_low.setText(newValue + "");
                    bpinput_bp_tv_low.setTextColor(getResources().getColor(R.color.color_abnormal));
                }
                if (a > to){
                    bpinput_bp_tv_low.setText(to + "");
                    bpinput_low.setSelectedValue(to+"");
                }
                if(a<from){
                    bpinput_bp_tv_low.setText(from + "");
                    bpinput_low.setSelectedValue(from + "");
                }
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
                updateData(FIRST_RULE,1, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_up_jia:
                updateData(FIRST_RULE,2, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_low_jian:
                updateData(SECOND_RULE,1, _strng_second, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bpiinput_img_up_jia:

                updateData(SECOND_RULE,2, _strng_second, bpinput_bp_tv_low, bpinput_low);
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

    private void updateData(int which,int i, String str, TextView id, RulerWheel view) {
        if (fromWhere == Constant.FROM_XUETANG || fromWhere == Constant.FROM_TIWEN || fromWhere == Constant.FROM_XUEYANG) {
            float value_float = Float.parseFloat(str);
            if (i == 1) {
                value_float -= 0.1;
            } else {
                value_float += 0.1;
            }
            DecimalFormat fnum = new DecimalFormat("##0.0");
            String value = fnum.format(value_float);
            if(isFloatRight(value_float,which)){
                id.setText(value + "");
                view.setSelectedValue(value + "");

                view.invalidate();
            }
            else{
//                Toast.makeText(context, "超出范围", Toast.LENGTH_LONG).show();
                Snackbar.make(container, "超出范围", Snackbar.LENGTH_LONG).show();
                return;
            }
        } else {
            int value_int = Integer.parseInt(str);
            if (i == 1) {
                value_int--;
            } else {
                value_int++;
            }
            if (isFloatRight(value_int,which)) {
                id.setText(value_int + "");
                view.setSelectedValue(value_int + "");
                view.invalidate();
            }
            else{
//                Toast.makeText(context,"超出范围",Toast.LENGTH_LONG).show();
                Snackbar.make(container, "超出范围", Snackbar.LENGTH_LONG).show();
            }
        }

    }

    private boolean isFloatRight(float str,int which) {

        float a = str;
        switch (fromWhere){
            case Constant.FROM_XUEYA:
                if(which == FIRST_RULE){
                    if(a<0||a>100){
                        return false;
                    }
                }
                if(which == SECOND_RULE){
                    if(a<0||a>200){
                        return false;
                    }
                }
                break;
            case Constant.FROM_SHENGAO:
                if(a<100||a>240){
                    return false;
                }
                break;
            case Constant.FROM_XUETANG:
                if(a<2||a>14.9){
                    return false;
                }
                break;
            case Constant.FROM_XUEYANG:
                if(a<50||a>99.9){
                    return false;
                }
                break;
            case Constant.FROM_YAOWEI:
                if(a<50||a>100){
                    return false;
                }
                break;
            case Constant.FROM_TIZHONG:
                if(a<30||a>150){
                    return false;
                }
                break;
            case Constant.FROM_TIWEN:
                if(a<35||a>41.9){
                    return false;
                }
                break;
            case Constant.FROM_XINDIAN:
                if(a<40||a>110){
                    return false;
                }
                break;
        }
        return true;
    }

    protected void initPopuptWindow(View view, int which) {
        // TODO Auto-generated method stub

        View popupWindow_view = getLayoutInflater().inflate(R.layout.popwindow_wheelview, null, false);
        popupWindow = new PopupWindow(popupWindow_view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setAnimationStyle(R.style.Animation_AppCompat_DropDownUp);
        TextView tv_company = (TextView) popupWindow_view.findViewById(R.id.tv_company);
        if (CHOICE_DATE == which) {
            tv_company.setVisibility(View.VISIBLE);
            tv_company.setText("单位（年-月-日）");
            initWheelViewDate(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else if (CHOICE_TIME == which) {
            tv_company.setVisibility(View.VISIBLE);
            tv_company.setText("单位（点）");
            initWheelViewTime(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else if (CHOICE_WHAT == which) {
            tv_company.setVisibility(View.GONE);
            initWheelViewWhat(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        } else {
            if (popupWindow != null) {
                popupWindow.dismiss();
            }
        }

    }

    private void initWheelViewWhat(View popupWindow_view) {
        WheelView wv_year = (WheelView) popupWindow_view.findViewById(R.id.wv_year);
        WheelView wv_mouth = (WheelView) popupWindow_view.findViewById(R.id.wv_mouth);
        WheelView wv_day = (WheelView) popupWindow_view.findViewById(R.id.wv_day);
        wv_year.setVisibility(View.GONE);
        wv_mouth.setVisibility(View.GONE);
        wv_day.setData(getStartData(5));
        wv_day.setDefault(DEFAULT_VALUE);
        wv_day.setOnSelectListener(new WheelView.OnSelectListener() {
            @Override
            public void endSelect(int id, String text) {
                str_mwhat = text;
                Log.i("time_stype","------------------"+id);
                _type = 4 + id;
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
                if(fromWhere == Constant.FROM_XUETANG){
                    bpinput_bp_tv.setTextColor(getResources().getColor(R.color.color_normal));
//                    initFirstView(2, 14, 1, "6.0");
                }
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

        long date = DateUtil.getDaysAfter(0);
        String string_hour = DateUtil.longToDateString(date, "HH");
        int hour = Integer.parseInt(string_hour);
        wv_day.setDefault(hour);
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

        long date = DateUtil.getDaysAfter(0);
//        String string_year = DateUtil.longToDateString(date, "yyyy");
//        int year = Integer.parseInt(string_year);
        String string_mouth = DateUtil.longToDateString(date, "MM");
        int mouth = Integer.parseInt(string_mouth);
        String string_day = DateUtil.longToDateString(date, "dd");
        int day = Integer.parseInt(string_day);
        wv_year.setDefault(9);
        wv_mouth.setDefault(mouth-1);
        wv_day.setDefault(day-1);
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
                tv_date.setText(str_year + "-" + str_mouth + "-" + str_day);
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
                date = DateUtil.getDaysAfter(0);
                String str_year = DateUtil.longToDateString(date, "yyyy");
                int year = Integer.parseInt(str_year) - 9;
                for (int i = 0; i < 10; i++) {
                    list.add((year + i) + "");
                }
                break;
            case 2:
                for (int i = 1; i < 13; i++) {
                    list.add(String.format("%02d", i));
                }
                break;
            case 3:
                for (int i = 1; i < 32; i++) {
                    list.add(String.format("%02d", i));
                }
                break;
            case 4:
                for (int i = 0; i < 24; i++) {
                    list.add(i + "");
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
