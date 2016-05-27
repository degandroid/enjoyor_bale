package com.enjoyor.healthhouse.ui;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.HealthBsInfoList;
import com.enjoyor.healthhouse.bean.HistoryInfoList;
import com.enjoyor.healthhouse.bean.HistoryInfoModel;
import com.enjoyor.healthhouse.bean.Page;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.XListView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/17.
 */
public class HistoryActivity extends BaseActivity implements View.OnClickListener, XListView.IXListViewListener {
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

    @Bind(R.id.physicall_lv)
    XListView physicall_lv;
    @Bind(R.id.ll_null)
    LinearLayout ll_null;
    @Bind(R.id.ll_addweb)
    LinearLayout ll_addweb;
    @Bind(R.id.webView)
    WebView webView;

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

    private String _json_xueya;
    private String _json_shengao;
    private String _json_xuetang;
    private String _json_xueyang;
    private String _json_yaowei;
    private String _json_tizhong;
    private String _json_tiwen;
    private String _json_xindian;

    private List<HistoryInfoList> historyInfoLists = new ArrayList<>();
    private Page page;
    private int count = 1;
    private String url;
    String date;
    String date2;
    @TargetApi(23)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        ButterKnife.bind(this);


        context = this;
        if (getIntent().hasExtra("fromWhere")) {
            fromWhere = getIntent().getIntExtra("fromWhere", Constant.FROM_XUEYA);
            initHead();
            initWeb();
            getDate(count);
        }
    }

    private void initWeb() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                url = "http://115.28.37.145/Content/statichtml/bloodpressure.html";
                break;
            case Constant.FROM_SHENGAO:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
            case Constant.FROM_XUETANG:
                url = "http://115.28.37.145/Content/statichtml/bloodSugar.html";
                break;
            case Constant.FROM_XUEYANG:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
            case Constant.FROM_YAOWEI:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
            case Constant.FROM_TIZHONG:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
            case Constant.FROM_TIWEN:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
            case Constant.FROM_XINDIAN:
                url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
                break;
        }
        webView.loadUrl(url);
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        webView.setWebChromeClient(new WebChromeClient());
    }

    //    myWebView.loadUrl("file:///android_asset/abc.html");
    private void initHead() {
        bt_list.setOnClickListener(this);
        bt_curve.setOnClickListener(this);
        tv_right.setVisibility(View.INVISIBLE);
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                navigation_name.setText("血压历史");
                ll_hascircle.setVisibility(View.VISIBLE);
                ll_history_xueya.setVisibility(View.VISIBLE);
                tv_threetittle_one.setText("日期");
                tv_threetittle_two.setText("收缩压");
                tv_threetittle_three.setText("舒张压");
                break;
            case Constant.FROM_SHENGAO:
                navigation_name.setText("身高历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("身高");
                ll_history_shengao.setVisibility(View.VISIBLE);
                break;
            case Constant.FROM_XUETANG:
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
                break;
            case Constant.FROM_XUEYANG:
                navigation_name.setText("血氧历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("血氧");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText(
                        Html.fromHtml(
                                "血氧正常范围大于95"
                                        + "\n注:小于为"
                                        + "<font color='#FF4081'>红色</font>"
                                        + "字体，大于等于为"
                                        + "<font color='#55d6ea'>蓝色</font>"
                                        + "字体"));
                break;
            case Constant.FROM_YAOWEI:
                navigation_name.setText("腰围历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("腰围");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("标准腰围计算方法" + "\n" + "男性：身高（cm）/2-11（cm），" + "\n" +
                        "女性：身高（cm）/2-14（cm），" + "\n" + "±5%为正常范围");
                break;
            case Constant.FROM_TIZHONG:
                navigation_name.setText("体重历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("体重");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("男性：（身高cm-80）*70%=标准体重" + "\n" + "nv性：（身高cm-70）*60%=标准体重");
                break;
            case Constant.FROM_TIWEN:
                navigation_name.setText("体温历史");
                tv_twotittle_one.setText("日期");
                tv_twotittle_two.setText("体温");
                ll_history_shengao.setVisibility(View.VISIBLE);
                ll_nocircle.setVisibility(View.VISIBLE);
                tv_nocircle_info.setText("人体正常体温平均在36-37℃之间（腋窝）");
                break;
            case Constant.FROM_XINDIAN:
                navigation_name.setText("心率历史");
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
        params_xueya.add("pageCount", (6*_count)+"");
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
                if (historyInfoLists.size() > 0) {
                    physicall_lv.setVisibility(View.VISIBLE);
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.GONE);
                } else {
                    physicall_lv.setVisibility(View.GONE);
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                }

                break;
            case R.id.bt_curve:
                selectDisplay(2);
                if (historyInfoLists.size() > 0) {
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.VISIBLE);
                    physicall_lv.setVisibility(View.GONE);
                } else {
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                    physicall_lv.setVisibility(View.GONE);
                }
                sendInfoToJs();
                break;
        }
    }

    private void selectDisplay(int which) {
        if (1 == which) {
            bt_list.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_list.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_curve.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_curve.setBackgroundResource(R.drawable.white_null);
            physicall_lv.setVisibility(View.VISIBLE);
            ll_addweb.setVisibility(View.GONE);
            if (fromWhere == Constant.FROM_XUEYA) {
                ll_history_xueya.setVisibility(View.VISIBLE);
            } else if (fromWhere == Constant.FROM_XUETANG) {
                ll_history_xuetang.setVisibility(View.VISIBLE);
            } else {
                ll_history_shengao.setVisibility(View.VISIBLE);
            }
        } else {
            bt_curve.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_curve.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_list.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_list.setBackgroundResource(R.drawable.white_null);
            physicall_lv.setVisibility(View.GONE);
            ll_addweb.setVisibility(View.VISIBLE);
            ll_history_xueya.setVisibility(View.GONE);
            ll_history_xuetang.setVisibility(View.GONE);
            ll_history_shengao.setVisibility(View.GONE);
        }
    }

    private boolean selectHealthInfo(String url, RequestParams params) {
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stops();
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    HistoryInfoModel historyInfoModel = JsonHelper.getJson(apiMessage.Data, HistoryInfoModel.class);
                    historyInfoLists.clear();
                    switch (fromWhere) {

                        case Constant.FROM_XUEYA:
                            List<HistoryInfoList> _list_xueya = historyInfoModel.getBplist();
                            _json_xueya = JSON.toJSONString(_list_xueya);
                            Log.i("json",_json_xueya);
                            historyInfoLists.addAll(_list_xueya);
                            break;
                        case Constant.FROM_SHENGAO:
                            List<HistoryInfoList> _list_shengao = historyInfoModel.getBmilist();
                            _json_shengao = JSON.toJSONString(_list_shengao);
                            Log.i("_json_shengao",_json_shengao);
                            historyInfoLists.addAll(_list_shengao);
                            Log.i("_json_shengao", historyInfoLists.toString());
                            break;
                        case Constant.FROM_XUETANG:
                            List<HistoryInfoList> _list_xuetang = historyInfoModel.getBslist();
                            _json_xuetang = JSON.toJSONString(_list_xuetang);
                            Log.i("_json_shengao",_json_xuetang);
                            historyInfoLists.addAll(_list_xuetang);
                            break;
                        case Constant.FROM_XUEYANG:
                            List<HistoryInfoList> _list_xueyang = historyInfoModel.getBolist();
                            _json_xueyang = JSON.toJSONString(_list_xueyang);
                            Log.i("_json_shengao",_json_xueyang);
                            historyInfoLists.addAll(_list_xueyang);
                            break;
                        case Constant.FROM_YAOWEI:
                            List<HistoryInfoList> _list_yaowei = historyInfoModel.getWhrlist();
                            _json_yaowei = JSON.toJSONString(_list_yaowei);
                            Log.i("_json_shengao",_json_yaowei);
                            historyInfoLists.addAll(_list_yaowei);
                            break;
                        case Constant.FROM_TIZHONG:
                            List<HistoryInfoList> _list_tizhong = historyInfoModel.getBmilist();
                            _json_tizhong = JSON.toJSONString(_list_tizhong);
                            Log.i("_json_shengao",_json_tizhong);
                            historyInfoLists.addAll(_list_tizhong);
                            break;
                        case Constant.FROM_TIWEN:
                            List<HistoryInfoList> _list_tiwen = historyInfoModel.getTemperlist();
                            _json_tiwen = JSON.toJSONString(_list_tiwen);
                            Log.i("_json_shengao",_json_tiwen);
                            historyInfoLists.addAll(_list_tiwen);
                            break;
                        case Constant.FROM_XINDIAN:
                            List<HistoryInfoList> _list_xindian = historyInfoModel.getEcglist();
                            _json_xindian = JSON.toJSONString(_list_xindian);
                            Log.i("_json_shengao",_json_xindian);
                            historyInfoLists.addAll(_list_xindian);
                            break;
                    }
                    if (historyInfoLists.size() > 0) {
                        initXList();
                    }
                    page = historyInfoModel.getPage();
                } else {
                    cancel();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                cancel();
            }
        });
        return true;
    }

    private void initXList() {
        physicall_lv.setPullRefreshEnable(false);
        physicall_lv.setPullLoadEnable(true);
        physicall_lv.setXListViewListener(this);
        PhysicallAdapter adapter = new PhysicallAdapter();
        physicall_lv.setAdapter(adapter);
        stops();
        physicall_lv.setSelection((count-1) * 6);
    }

    @Override
    public void onRefresh() {

    }
    public void stops() {
        if (physicall_lv != null) {
            physicall_lv.stopLoadMore();
        }
    }
    @Override
    public void onLoadMore() {
        getDate(++count);
    }

    //    public void stops() {
//        if (physicall_lv != null) {
//            physicall_lv.stopLoadMore();
//        }
//    }
    private void setBsColor(int i, String _value, TextView view) {
        float value = Float.parseFloat(_value);
        if (1 == i) {
            if (value < 4.4) {
                view.setTextColor(getResources().getColor(R.color.color_normal));
            }
            if (value > 6.9) {
                view.setTextColor(getResources().getColor(R.color.color_abnormal));
            }
        } else {
            if (value < 4.4) {
                view.setTextColor(getResources().getColor(R.color.color_normal));
            }
            if (value > 9.9) {
                view.setTextColor(getResources().getColor(R.color.color_abnormal));
            }
        }
    }

    class PhysicallAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return historyInfoLists.size();
        }

        @Override
        public Object getItem(int position) {
            return historyInfoLists.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder_Two holderTwo = null;
            ViewHolder_Three holderThree = null;
            ViewHolder_Eight holderEight = null;
            if (convertView == null) {
                if (fromWhere == Constant.FROM_XUEYA) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_history_xueya, null);
                    holderThree = new ViewHolder_Three(convertView);
                    convertView.setTag(holderThree);
                } else if (fromWhere == Constant.FROM_XUETANG) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_history_xuetang, null);
                    holderEight = new ViewHolder_Eight(convertView);
                    convertView.setTag(holderEight);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.item_history_shengao, null);
                    holderTwo = new ViewHolder_Two(convertView);
                    convertView.setTag(holderTwo);
                }
            } else {
                if (fromWhere == Constant.FROM_XUEYA) {
                    holderThree = (ViewHolder_Three) convertView.getTag();
                } else if (fromWhere == Constant.FROM_XUETANG) {
                    holderEight = (ViewHolder_Eight) convertView.getTag();
                } else {
                    holderTwo = (ViewHolder_Two) convertView.getTag();
                }
            }

            if (historyInfoLists.get(position).getRecordTime() != null) {
                String str = historyInfoLists.get(position).getRecordTime();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try {
                    java.util.Date dt = sdf.parse(str);
                    date = DateUtil.dateToString(dt,"MM/dd");
                    date2 = DateUtil.dateToString(dt,"yyyy/MM/dd");
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                switch (fromWhere) {
                    case Constant.FROM_XUEYA:
                        holderThree.tv_three_one.setText(date2 + "");
                        holderThree.tv_three_two.setText(historyInfoLists.get(position).getSystolicPressure() + "");
                        holderThree.tv_three_three.setText(historyInfoLists.get(position).getDiastolicPressure() + "");
                        if (historyInfoLists.get(position).getSystolicPressure() > 140) {
                            holderThree.tv_three_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                        }
                        if (historyInfoLists.get(position).getSystolicPressure() < 90) {
                            holderThree.tv_three_two.setTextColor(getResources().getColor(R.color.color_normal));
                        }
                        if (historyInfoLists.get(position).getDiastolicPressure() > 90) {
                            holderThree.tv_three_three.setTextColor(getResources().getColor(R.color.color_abnormal));
                        }
                        if (historyInfoLists.get(position).getDiastolicPressure() < 60) {
                            holderThree.tv_three_three.setTextColor(getResources().getColor(R.color.color_normal));
                        }
                        break;
                    case Constant.FROM_SHENGAO:

                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getHeight() + "");
                        break;
                    case Constant.FROM_XUETANG:
                        List<HealthBsInfoList> healthBsInfoListlist = historyInfoLists.get(position).getBeanList();
                        holderEight.tv_eight_one.setText(date + "");
                        for (HealthBsInfoList hb : healthBsInfoListlist) {
                            switch (hb.getBloodSugarType()) {
                                case HealthBsInfoList.TYPE_NULL:
                                    holderEight.tv_eight_two.setText(hb.getBloodSugar() + "");
                                    setBsColor(1, hb.getBloodSugar(), holderEight.tv_eight_two);
                                    break;
                                case HealthBsInfoList.TYPE_BREAKFAST:
                                    holderEight.tv_eight_three.setText(hb.getBloodSugar() + "");
                                    setBsColor(2, hb.getBloodSugar(), holderEight.tv_eight_three);
                                    break;
                                case HealthBsInfoList.TYPE_LUNCH_BEFORE:
                                    holderEight.tv_eight_four.setText(hb.getBloodSugar() + "");
                                    setBsColor(1, hb.getBloodSugar(), holderEight.tv_eight_four);
                                    break;
                                case HealthBsInfoList.TYPE_LUNCH_AFTER:
                                    holderEight.tv_eight_five.setText(hb.getBloodSugar() + "");
                                    setBsColor(2, hb.getBloodSugar(), holderEight.tv_eight_five);
                                    break;
                                case HealthBsInfoList.TYPE_DINNER_BEFOR:
                                    holderEight.tv_eight_six.setText(hb.getBloodSugar() + "");
                                    setBsColor(1, hb.getBloodSugar(), holderEight.tv_eight_six);
                                    break;
                                case HealthBsInfoList.TYPE_DINNER_AFTER:
                                    holderEight.tv_eight_seven.setText(hb.getBloodSugar() + "");
                                    setBsColor(2, hb.getBloodSugar(), holderEight.tv_eight_seven);
                                    break;
                                case HealthBsInfoList.TYPE_SLEEP:
                                    holderEight.tv_eight_eight.setText(hb.getBloodSugar() + "");
                                    setBsColor(2, hb.getBloodSugar(), holderEight.tv_eight_eight);
                                    break;
                            }
                        }
                        break;
                    case Constant.FROM_XUEYANG:
                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getBo() + "");
                        if (historyInfoLists.get(position).getBo() < 95) {
                            holderTwo.tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                        }
                        break;
                    case Constant.FROM_YAOWEI:
                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getWaistLine() + "");
                        break;
                    case Constant.FROM_TIZHONG:
                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getWeight() + "");
                        break;
                    case Constant.FROM_TIWEN:
                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getTemperature() + "");
                        if (Float.parseFloat(historyInfoLists.get(position).getTemperature()) > 37) {
                            holderTwo.tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                        }
                        if (Float.parseFloat(historyInfoLists.get(position).getTemperature()) < 36) {
                            holderTwo.tv_two_two.setTextColor(getResources().getColor(R.color.color_normal));
                        }
                        break;
                    case Constant.FROM_XINDIAN:
                        holderTwo.tv_two_one.setText(date2 + "");
                        holderTwo.tv_two_two.setText(historyInfoLists.get(position).getEcg() + "");
                        if (historyInfoLists.get(position).getEcg() > 100) {
                            holderTwo.tv_two_two.setTextColor(getResources().getColor(R.color.color_abnormal));
                        }
                        if (historyInfoLists.get(position).getEcg() < 60) {
                            holderTwo.tv_two_two.setTextColor(getResources().getColor(R.color.color_normal));
                        }
                        break;
                }
            }


            return convertView;
        }

        class ViewHolder_Two {
            @Bind(R.id.tv_two_one)
            TextView tv_two_one;
            @Bind(R.id.tv_two_two)
            TextView tv_two_two;

            ViewHolder_Two(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolder_Three {
            @Bind(R.id.tv_three_one)
            TextView tv_three_one;
            @Bind(R.id.tv_three_two)
            TextView tv_three_two;
            @Bind(R.id.tv_three_three)
            TextView tv_three_three;

            ViewHolder_Three(View view) {
                ButterKnife.bind(this, view);
            }
        }

        class ViewHolder_Eight {
            @Bind(R.id.tv_eight_one)
            TextView tv_eight_one;
            @Bind(R.id.tv_eight_two)
            TextView tv_eight_two;
            @Bind(R.id.tv_eight_three)
            TextView tv_eight_three;
            @Bind(R.id.tv_eight_four)
            TextView tv_eight_four;
            @Bind(R.id.tv_eight_five)
            TextView tv_eight_five;
            @Bind(R.id.tv_eight_six)
            TextView tv_eight_six;
            @Bind(R.id.tv_eight_seven)
            TextView tv_eight_seven;
            @Bind(R.id.tv_eight_eight)
            TextView tv_eight_eight;

            ViewHolder_Eight(View view) {
                ButterKnife.bind(this, view);
            }
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

    //在java中调用js代码
    public void sendInfoToJs() {
        switch (fromWhere) {
            case Constant.FROM_XUEYA:
                webView.loadUrl("javascript:show_ssy('" + _json_xueya + "')");
                webView.loadUrl("javascript:show_szy('" + _json_xueya + "')");
                break;
            case Constant.FROM_SHENGAO:
                webView.loadUrl("javascript:show_height('" + _json_shengao + "')");
                break;
            case Constant.FROM_XUETANG:
                webView.loadUrl("javascript:show_sugar('" + _json_xuetang + "')");
                break;
            case Constant.FROM_XUEYANG:
                webView.loadUrl("javascript:show_oxygen('" + _json_xueyang + "')");
                break;
            case Constant.FROM_YAOWEI:
                webView.loadUrl("javascript:show_waistline('" + _json_yaowei + "')");
                break;
            case Constant.FROM_TIZHONG:
                webView.loadUrl("javascript:show_weight('" + _json_tizhong + "')");
                break;
            case Constant.FROM_TIWEN:
                webView.loadUrl("javascript:show_temperature('" + _json_tiwen + "')");
                break;
            case Constant.FROM_XINDIAN:
                webView.loadUrl("javascript:show_ecg('" + _json_xindian + "')");
                break;
        }

    }
}
