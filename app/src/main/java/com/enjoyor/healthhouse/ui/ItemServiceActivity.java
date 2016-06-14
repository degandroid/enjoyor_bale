package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.BMI;
import com.enjoyor.healthhouse.bean.Food;
import com.enjoyor.healthhouse.custom.XListView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
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

    @Bind(R.id.ll_info)
    LinearLayout ll_info;
    //    @Bind(R.id.tv_infonumber)
//    TextView tv_infonumber;
//    @Bind(R.id.tv_infotext)TextView tv_infotext;
    @Bind(R.id.tv_recalculation)
    TextView tv_recalculation;

    @Bind(R.id.webView)
    WebView webView;


    private int FIRST_RULE = 1;
    private int SECOND_RULE = 2;


    /*-------------------------卡路里换算--------------------------*/
    @Bind(R.id.ll_kaluli)
    LinearLayout ll_kaluli;
    @Bind(R.id.ll_head)
    LinearLayout ll_head;
    @Bind(R.id.bt_calculation)
    Button bt_calculation;
    @Bind(R.id.xlv_food)
    XListView xlv_food;

    @Bind(R.id.et_kaluli)
    EditText et_kaluli;
    private String kaluli;
    private String jiaoer;
    @Bind(R.id.et_jiaoer)
    EditText et_jiaoer;

    @Bind(R.id.ll_listinfo)
    LinearLayout ll_listinfo;
    @Bind(R.id.re_tosearch)
    RelativeLayout re_tosearch;
    @Bind(R.id.ll_search)
    LinearLayout ll_search;
    @Bind(R.id.et_search)
    EditText et_search;

    @Bind(R.id.ll_clean)
    LinearLayout ll_clean;
    @Bind(R.id.tv_cancel)
    TextView tv_cancel;//取消
    @Bind(R.id.bt_jisuan)
    Button bt_jisuan;
    @Bind(R.id.container)
    CoordinatorLayout container;

    private PopupWindow pw;
    ObjectAnimator objectAnimator;

    private List<Food.FoodList> foodList = new ArrayList<>();
    private int count = 1;
    private String searchName = "";
    private int TO_BMI = 0;
    private int TO_KALULI = 1;
    private String FOOD_URL = "app/food/search.do";

    private float value;
    private String normal = "";
    private String CHINA_STANDARD = "";
    private String INTERNATIONAL_STANDARD = "INT";
    private String JAPAN_STANDARD = "JP";
    private String SINJAPORE_STANDARD = "SGP";

    private int from;

    private String url = "http://115.28.37.145/Content/statichtml/bmiIndex.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itemservice);
        context = this;
        ButterKnife.bind(this);
        initHead();
        if (getIntent().hasExtra("from")) {
            from = getIntent().getIntExtra("from", 0);
            if (from == TO_BMI) {
                ll_bmi.setVisibility(View.VISIBLE);
                ll_kaluli.setVisibility(View.GONE);
                initBMI();
                initWebView();
            } else if (from == TO_KALULI) {
                ll_bmi.setVisibility(View.GONE);
                ll_kaluli.setVisibility(View.VISIBLE);
                re_tosearch.setVisibility(View.VISIBLE);
                tv_right.setVisibility(View.INVISIBLE);
                initKaLuLi();
            }
        }
    }

    private void initKaLuLi() {
        navigation_name.setText("卡路里换算");
        objectAnimator = new ObjectAnimator();
        re_tosearch.setOnClickListener(this);
        et_search.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
        ll_clean.setOnClickListener(this);
        bt_jisuan.setOnClickListener(this);
        getDate(searchName, count + "");
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                foodList.clear();
                count = 1;
                searchName = s.toString();
                getDate(searchName, 1 + "");

            }
        });
    }

    private void initBMI() {
        navigation_name.setText("BMI换算");
        bpinput_img_up_jian.setOnClickListener(this);
        bpinput_img_up_jia.setOnClickListener(this);
        bpinput_img_low_jian.setOnClickListener(this);
        bpiinput_img_up_jia.setOnClickListener(this);
        bt_calculation.setOnClickListener(this);
        tv_recalculation.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        initFirstView(100, 240, 10, "160");
        initSecondView(30, 150, 10, "80");
    }

    private void initHead() {
        re_back.setOnClickListener(this);
        tv_right.setText("历史");
        tv_right.setVisibility(View.VISIBLE);
        tv_right.setOnClickListener(this);
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
        getDate(searchName, (count++) + "");
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
            }

            @Override
            public void onScrollingStarted(RulerWheel wheel) {

            }

            @Override
            public void onScrollingFinished(RulerWheel wheel) {
            }
        });

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
                bpinput_bp_tv_low.setText(newValue + "");
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
            Snackbar.make(container, "超出范围", Snackbar.LENGTH_SHORT).show();
//            Toast.makeText(context, "超出范围", Toast.LENGTH_LONG).show();
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
                Intent intent = new Intent(ItemServiceActivity.this, BMIActivity.class);
                startActivity(intent);
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
                normal = CHINA_STANDARD;
                calculation(_strng, _strng_second);
                break;
            case R.id.tv_guoji:

                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv1 = (TextView) v;
                tv_select.setText(tv1.getText());
                normal = INTERNATIONAL_STANDARD;
                calculation(_strng, _strng_second);
                break;
            case R.id.tv_riben:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv2 = (TextView) v;
                tv_select.setText(tv2.getText());
                normal = JAPAN_STANDARD;
                calculation(_strng, _strng_second);
                break;
            case R.id.tv_xinjiapo:
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                TextView tv3 = (TextView) v;
                tv_select.setText(tv3.getText());
                normal = SINJAPORE_STANDARD;
                calculation(_strng, _strng_second);
                break;
            case R.id.re_tosearch:
                Intent intent_torearch = new Intent(ItemServiceActivity.this,RearchActivity.class);
                startActivity(intent_torearch);

//                WindowManager wm = this.getWindowManager();
//                int width = wm.getDefaultDisplay().getWidth();
//                objectAnimator.ofFloat(ll_head, "alpha", 0).setDuration(1000).start();
//                objectAnimator.ofFloat(ll_listinfo, "translationY", -ll_listinfo.getHeight()).setDuration(1000).start();
//                objectAnimator.ofFloat(ll_search, "alpha", 10).setDuration(1000).start();
//                objectAnimator.ofFloat(re_tosearch, "translationX", -width).setDuration(1000).start();
            case R.id.et_search:
//                re_tosearch.setVisibility(View.GONE);
//                et_search.setAlpha(100);
                break;
            case R.id.ll_clean:
                et_search.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                Intent intent_cnacle = new Intent(ItemServiceActivity.this, ItemServiceActivity.class);
                intent_cnacle.putExtra("from", TO_KALULI);
                this.startActivity(intent_cnacle);
                break;
            case R.id.bt_jisuan:
                if (getNumber() == 1) {
                    float a = Float.parseFloat(kaluli);
                    DecimalFormat df = new DecimalFormat("0.000");
                    et_jiaoer.setText(df.format(a * 4.184));
                } else if (getNumber() == 2) {
                    float a = Float.parseFloat(jiaoer);
                    DecimalFormat df = new DecimalFormat("0.000");
                    et_kaluli.setText(df.format(a / 4.184));
                }
                break;
        }
    }

    private int getNumber() {
        if (!StringUtils.isBlank(et_kaluli.getText().toString().toString()) && et_kaluli.isFocused()) {
            kaluli = et_kaluli.getText().toString().toString();

            return 1;
        } else if (!StringUtils.isBlank(et_jiaoer.getText().toString().toString()) && et_jiaoer.isFocused()) {
            jiaoer = et_jiaoer.getText().toString().toString();
            return 2;
        }
        return -1;
    }

    private void calculation(String _strng, String string_second) {
        float height = Float.parseFloat(_strng);
        float weight = Float.parseFloat(string_second);
        float a = weight / (height * height / 10000);
        DecimalFormat fnum = new DecimalFormat("##0.0");
        String _value = fnum.format(a);
        value = Float.parseFloat(_value);

        getWebView();
        String time = DateUtil.longToDateString(DateUtil.getCurrentTime(), "yyyy-MM-dd hh:mm:ss");
        BMI _bmi = new BMI();
        _bmi.setRecordTime(time);
        _bmi.setCreateTime(time);
        _bmi.setBmi(Double.parseDouble(_value));
        MyApplication.getInstance().getDBHelper().saveBMI(_bmi);

    }

    private void initWebView() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        webView.loadUrl(url);
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        webView.setWebChromeClient(new WebChromeClient());

    }
    private void getWebView(){
        webView.clearCache(true);
        Log.i("date",value+"------------"+normal);
        if(StringUtils.isBlank(normal)){
            webView.loadUrl("javascript:show('" + value + "')");
        }else{
//            String info = value + ",‘" + normal+"'";
            webView.loadUrl("javascript:show(" + value+",'" +normal+ "')");
        }

    }

    private class JsInterface {
        private Context mContext;

        public JsInterface(Context context) {
            this.mContext = context;
        }

        //在js中调用window.AndroidWebView.showInfoFromJs(name)，便会触发此方法。
        @JavascriptInterface
        public void showInfoFromJs(String name) {
            Toast.makeText(mContext, name, Toast.LENGTH_SHORT).show();
        }
    }


    private void getDate(final String name, String count) {

        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("pageNum", count);
        params.add("pageCount", "10");

        Log.i("searchName", name + count);
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + FOOD_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Food food = JsonHelper.getJson(apiMessage.Data, Food.class);
                    List<Food.FoodList> _list = food.getList();
                    foodList.addAll(_list);
                    if (foodList.size() > 0) {
                        initListView();
                    } else {
                        xlv_food.setVisibility(View.GONE);
                        if (name != null) {
                            /*软件盘显示则隐藏，隐藏则显示*/
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            Snackbar.make(container, "该食物暂时未录入", Snackbar.LENGTH_SHORT).show();
//                            Toast.makeText(ItemServiceActivity.this, "该食物暂时未录入！！", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
}
