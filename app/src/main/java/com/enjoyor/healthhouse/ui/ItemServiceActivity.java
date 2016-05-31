package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.Food;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.XListView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nineoldandroids.animation.ObjectAnimator;
import com.xk.sanjay.rulberview.RulerWheel;

import org.apache.http.Header;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/30.
 */
public class ItemServiceActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
    private Context context;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.tv_right)
    TextView tv_right;
    /*-------------------------BMI换算--------------------------*/
    @Bind(R.id.ll_bmi)
    LinearLayout ll_bmi;
    @Bind(R.id.tv_select)
    TextView tv_select;
    @Bind(R.id.bpinput_bp_tv)
    TextView bpinput_bp_tv;
    @Bind(R.id.bpinput_img_up_jian)
    ImageView bpinput_img_up_jian;
    @Bind(R.id.bpinput_img_up_jia)
    ImageView bpinput_img_up_jia;
    @Bind(R.id.bpinput_up)
    RulerWheel bpinput_up;

    @Bind(R.id.bpinput_bp_tv_low)
    TextView bpinput_bp_tv_low;
    @Bind(R.id.bpinput_img_low_jian)
    ImageView bpinput_img_low_jian;
    @Bind(R.id.bpiinput_img_up_jia)
    ImageView bpiinput_img_up_jia;
    @Bind(R.id.bpinput_low)
    RulerWheel bpinput_low;

    @Bind(R.id.tv_infonumber)
    TextView tv_infonumber;

    private int FIRST_RULE = 1;
    private int SECOND_RULE = 2;


    /*-------------------------卡路里换算--------------------------*/
    @Bind(R.id.ll_kaluli)
    LinearLayout ll_kaluli;
    @Bind(R.id.ll_info)
    LinearLayout ll_info;
    @Bind(R.id.bt_calculation)
    Button bt_calculation;
    @Bind(R.id.tv_recalculation)
    TextView tv_recalculation;
    @Bind(R.id.xlv_food)
    XListView xlv_food;
    @Bind(R.id.re_search)
    RelativeLayout re_search;
    @Bind(R.id.ll_listinfo)
    LinearLayout ll_listinfo;
    @Bind(R.id.ll_head)LinearLayout ll_head;
    @Bind(R.id.ll_rightsearch)LinearLayout ll_rightsearch;

    private PopupWindow pw;
    private String FOOD_URL = "app/food/search.do";

    private int TO_BMI = 0;
    private int TO_KALULI = 1;

    private List<Food.FoodList> foodList = new ArrayList<>();
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemservice);
        context = this;
        ButterKnife.bind(this);

        re_back.setOnClickListener(this);
        tv_right.setText("历史");
        tv_right.setOnClickListener(this);
        if (getIntent().hasExtra("from")) {
            int from = getIntent().getIntExtra("from", 0);
            if (from == TO_BMI) {
                navigation_name.setText("BMI换算");
                ll_bmi.setVisibility(View.VISIBLE);
                ll_kaluli.setVisibility(View.GONE);
                initEvent();
                initFirstView(100, 240, 10, "160");
                initSecondView(30, 150, 10, "80");
            } else if (from == TO_KALULI) {
                navigation_name.setText("卡路里换算");
                re_search.setOnClickListener(this);
                ll_bmi.setVisibility(View.GONE);
                ll_kaluli.setVisibility(View.VISIBLE);
                getDate("", count + "");
            }
        }
    }

    private void initListView() {
        xlv_food.setPullRefreshEnable(false);
        xlv_food.setPullLoadEnable(true);
        xlv_food.setXListViewListener(this);

        xlv_food.setAdapter(new FoodAdapater());
        xlv_food.setSelection((count - 1) * 6);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        getDate("", (count++) + "");
    }

    class FoodAdapater extends BaseAdapter {

        @Override
        public int getCount() {
            return foodList.size();
        }

        @Override
        public Object getItem(int position) {
            return foodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(ItemServiceActivity.this).inflate(R.layout.item_food, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_food.setText(foodList.get(position).getName());
            holder.tv_kaluli.setText(foodList.get(position).getCalories());
            holder.tv_info.setText(foodList.get(position).getUnit());
            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.tv_food)
            TextView tv_food;
            @Bind(R.id.tv_kaluli)
            TextView tv_kaluli;
            @Bind(R.id.tv_info)
            TextView tv_info;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }

    private void initEvent() {
        bpinput_img_up_jian.setOnClickListener(this);
        bpinput_img_up_jia.setOnClickListener(this);
        bpinput_img_low_jian.setOnClickListener(this);
        bpiinput_img_up_jia.setOnClickListener(this);
        tv_recalculation.setOnClickListener(this);
        bt_calculation.setOnClickListener(this);
        tv_select.setOnClickListener(this);
    }

    private void selectType() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.popup_service_selectstandard, null);
        pw = new PopupWindow(popView, 500, 200, true);
        pw.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(tv_select);

        TextView tv_zhongguo = (TextView) popView.findViewById(R.id.tv_zhongguo);
        TextView tv_guoji = (TextView) popView.findViewById(R.id.tv_guoji);
        TextView tv_riben = (TextView) popView.findViewById(R.id.tv_riben);
        TextView tv_xinjiapo = (TextView) popView.findViewById(R.id.tv_xinjiapo);
        tv_zhongguo.setOnClickListener(this);
        tv_guoji.setOnClickListener(this);
        tv_riben.setOnClickListener(this);
        tv_xinjiapo.setOnClickListener(this);
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
        }
        bpinput_bp_tv.setText(value);
        bpinput_up.setData(list);
        bpinput_up.setSelectedValue(value);
        bpinput_up.setScrollingListener(new RulerWheel.OnWheelScrollListener<String>() {
            @Override
            public void onChanged(RulerWheel wheel, String oldValue, String newValue) {
                int a = (int) Float.parseFloat(newValue);
                if (a <= to && a >= from) {
                    bpinput_bp_tv.setText(newValue);
                }
                if (a > to) {
                    bpinput_bp_tv.setText(to + "");
                    bpinput_up.setSelectedValue(to + "");
                }
                if (a < from) {
                    bpinput_bp_tv.setText(from + "");
                    bpinput_up.setSelectedValue(from + "");
                }
                changeColor(Constant.FROM_SHENGAO, a);
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {
            }
        });

    }

    private void changeColor(int from, float a) {
        if (from == Constant.FROM_SHENGAO) {
            changeColorFunction(a, 60, 90);
        }

    }

    private void changeColorFunction(float a, float min, float max) {
        if (a > min && a < max) {
            bpinput_bp_tv.setTextColor(getResources().getColor(R.color.color_normal));
        } else {
            bpinput_bp_tv.setTextColor(getResources().getColor(R.color.color_abnormal));
        }
    }

    private void initSecondView(final int from, final int to, int span, String value) {
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
                float a = Float.parseFloat(newValue);
                if (a > 90 && a < 140) {
                    bpinput_bp_tv_low.setText(newValue + "");
                    bpinput_bp_tv_low.setTextColor(getResources().getColor(R.color.color_normal_second));
                } else {
                    bpinput_bp_tv_low.setText(newValue + "");
                    bpinput_bp_tv_low.setTextColor(getResources().getColor(R.color.color_abnormal));
                }
                if (a > to) {
                    bpinput_bp_tv_low.setText(to + "");
                    bpinput_low.setSelectedValue(to + "");
                }
                if (a < from) {
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

    private boolean isFloatRight(float str, int which) {
        float a = str;
        if (which == FIRST_RULE) {
            if (a < 100 || a > 240) {
                return false;
            }
        }
        if (which == SECOND_RULE) {
            if (a < 30 || a > 150) {
                return false;
            }
        }
        return true;
    }

    private void updateData(int which, int i, String str, TextView id, RulerWheel view) {

        int value_int = Integer.parseInt(str);
        if (i == 1) {
            value_int--;
        } else {
            value_int++;
        }
        if (isFloatRight(value_int, which)) {
            id.setText(value_int + "");
            view.setSelectedValue(value_int + "");
            view.invalidate();
        } else {
            Toast.makeText(context, "超出范围", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        String _strng = bpinput_bp_tv.getText().toString();
        String _strng_second = bpinput_bp_tv_low.getText().toString();
        switch (key) {
            case R.id.bpinput_img_up_jian:
                updateData(FIRST_RULE, 1, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_up_jia:
                updateData(FIRST_RULE, 2, _strng, bpinput_bp_tv, bpinput_up);
                break;
            case R.id.bpinput_img_low_jian:
                updateData(SECOND_RULE, 1, _strng_second, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bpiinput_img_up_jia:
                updateData(SECOND_RULE, 2, _strng_second, bpinput_bp_tv_low, bpinput_low);
                break;
            case R.id.bt_calculation:
                calculation(_strng, _strng_second);
                ll_info.setVisibility(View.VISIBLE);
                bt_calculation.setVisibility(View.GONE);
                break;
            case R.id.tv_recalculation:
                calculation(_strng, _strng_second);
                break;
            case R.id.re_back:
                finish();
                break;
            case R.id.tv_right:
                /*历史*/
                break;
            case R.id.tv_select:
                selectType();
                break;
            case R.id.tv_zhongguo:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv = (TextView) v;
                tv_select.setText(tv.getText());
                break;
            case R.id.tv_guoji:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv1 = (TextView) v;
                tv_select.setText(tv1.getText());
                break;
            case R.id.tv_riben:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv2 = (TextView) v;
                tv_select.setText(tv2.getText());
                break;
            case R.id.tv_xinjiapo:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv3 = (TextView) v;
                tv_select.setText(tv3.getText());
                break;
            case R.id.re_search:

                WindowManager wm = this.getWindowManager();

                int width = wm.getDefaultDisplay().getWidth();
                ObjectAnimator.ofFloat(ll_head,"alpha",0).setDuration(1000).start();
                ObjectAnimator.ofFloat(ll_listinfo,"translationY",-ll_listinfo.getHeight()).setDuration(1000).start();
                ObjectAnimator.ofFloat(ll_rightsearch,"alpha",100).setDuration(1000).start();
                ObjectAnimator.ofFloat(re_search,"translationX",-width).setDuration(1000).start();

                break;
        }
    }

    private void calculation(String _strng, String string_second) {
        Integer height = Integer.parseInt(_strng);
        Integer weight = Integer.parseInt(string_second);
        float a = weight / (height * height / 10000);
        DecimalFormat fnum = new DecimalFormat("##0.0");
        String value = fnum.format(a);
        tv_infonumber.setText(value);
    }


    private void getDate(String name, String count) {

        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("pageNum", count);
        params.add("pageCount", "10");
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + FOOD_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Food food = JsonHelper.getJson(apiMessage.Data, Food.class);
                    List<Food.FoodList> _list = food.getList();
                    foodList.addAll(_list);
                    initListView();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
