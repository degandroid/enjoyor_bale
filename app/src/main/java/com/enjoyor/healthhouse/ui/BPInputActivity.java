package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.WheelView;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.xk.sanjay.rulberview.RulerWheel;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class BPInputActivity extends BaseActivity implements View.OnClickListener {
    private Context context;
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
//    private String
//    @Bind(R.id.tv_info)
//    TextView tv_info;
    private ArrayAdapter<String> whatAdapter;
    @Bind(R.id.ll_first)
    LinearLayout ll_first;
    @Bind(R.id.ll_second)
    LinearLayout ll_second;

    @Bind(R.id.rl_choicewhat)
    RelativeLayout rl_choicewhat;
    @Bind(R.id.tv_choicewhat)TextView tv_choicewhat;
    @Bind(R.id.tv_time)
    TextView tv_time;
    @Bind(R.id.tv_date)
    TextView tv_date;
    @Bind(R.id.rl_choicetime)
    RelativeLayout rl_choicetime;
    @Bind(R.id.rl_choicedate)
    RelativeLayout rl_choicedate;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
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
    @Bind(R.id.bpinput_arrow)
    ImageView bpinput_arrow;
    @Bind(R.id.bpinput_arrows)
    ImageView bpinput_arrows;
    @Bind(R.id.bpinput_save)
    TextView bpinput_save;
    @Bind(R.id.bpinput_up)
    RulerWheel bpinput_up;
    @Bind(R.id.bpinput_low)
    RulerWheel bpinput_low;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bpinput_ac_layout);
        ButterKnife.bind(this);
        context = this;
        if (getIntent().hasExtra("fromWhere")) {
            int fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initHead(fromWhere);
        }
        Log.d("tag", "create");
        initEvent();
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
                navigation_name.setText("血压录入");
                initFirstView(0, 100, 10, 70);
                initSecondView(0, 200, 10, 120);
                break;
            case Constant.FROM_XUETANG:
                rl_choicewhat.setVisibility(View.VISIBLE);
                initFirstView(0, 100, 10, 60);
                navigation_name.setText("血糖录入");
                selWhate();
                break;
        }
        tv_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (fromWhere) {
                    case Constant.FROM_XUEYA:
                        Intent intent_xueya = new Intent(context, HistoryActivity.class);
                        intent_xueya.putExtra("fromWhere", Constant.FROM_XUEYA);
                        startActivity(intent_xueya);
                        break;
                    case Constant.FROM_XUETANG:
                        Intent intent_xuetang = new Intent(context, HistoryActivity.class);
                        intent_xuetang.putExtra("fromWhere", Constant.FROM_XUETANG);
                        startActivity(intent_xuetang);
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
    private void initFirstView(int from, int to, int span, int value) {
        List<String> list = new ArrayList<>();
        for (int i = from; i <= to; i += span) {
            list.add(i + "");
            for (int j = 1; j < 10; j++) {
                list.add((i + j) + "");
            }
        }
        bpinput_bp_tv.setText(value + "");
        bpinput_up.setData(list);
        bpinput_up.setSelectedValue(value + "");
        bpinput_up.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {

            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                bpinput_bp_tv.setText(newValue + "");
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
        switch (key) {
            case R.id.bpinput_img_up_jian:
                int s = Integer.parseInt(bpinput_bp_tv.getText().toString());
                updateData(1, s, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_up_jia:
                int a = Integer.parseInt(bpinput_bp_tv.getText().toString());
                updateData(2, a, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_low_jian:
                int b = Integer.parseInt(bpinput_bp_tv_low.getText().toString());
                updateData(1, b, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bpiinput_img_up_jia:
                int d = Integer.parseInt(bpinput_bp_tv_low.getText().toString());
                updateData(2, d, bpinput_bp_tv_low, bpinput_low);
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

    private void updateData(int i, int str, TextView id, RulerWheel view) {
        if (i == 1) {
            str--;
        } else {
            str++;
        }
        id.setText(str + "");
        view.setSelectedValue(str + "");
        view.invalidate();
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
        } else if(CHOICE_WHAT == which){
            initWheelViewWhat(popupWindow_view);
            popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        }else {
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
                    list.add(year + "年");
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
                    list.add(mouth + "月");
                }
                break;
            case 3:
                for (int i = 0; i < 30; i++) {
                    date = DateUtil.getDaysAfter(i);
                    String str_day = DateUtil.longToDateString(date, "dd");
                    list.add(str_day + "日");
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
