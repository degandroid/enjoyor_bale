package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.BCAFragmentbean;
import com.enjoyor.healthhouse.bean.BodyReport;
import com.enjoyor.healthhouse.bean.RecordBMI;
import com.enjoyor.healthhouse.bean.RecordFat;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.JavaScriptInterface;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/29.
 * 人体成分评估
 */
public class BodyFragment extends BaseFragment {
    private View view;
    private String[] healthKey = {"体脂肪率", "体脂量", "肌肉量", "体水分率"};
    private String[] healthValue = {"11-<22%", "7.3-<11.6", "44-<52.6", ""};

    private String[] healthKey2 = {"体脂肪量", "体水分量", "细胞外液", "骨骼量"};
    private String[] healthKey3 = {"肌肉量", "细胞外液", "蛋白质", "体脂肪量"};
    @Bind(R.id.layout1)
    LinearLayout layout1;
    @Bind(R.id.layout2)
    LinearLayout layout2;
    @Bind(R.id.left_zuobi_zhifang)
    TextView left_zuobi_zhifang;
    @Bind(R.id.right_youbi_zhifang)
    TextView right_youbi_zhifang;
    @Bind(R.id.right_youbi_jirou)
    TextView right_youbi_jirou;
    @Bind(R.id.left_zuotui_zhifang)
    TextView left_zuotui_zhifang;
    @Bind(R.id.left_zuotui_jirou)
    TextView left_zuotui_jirou;
    @Bind(R.id.right_youtui_zhifang)
    TextView right_youtui_zhifang;
    @Bind(R.id.right_youtui_jirou)
    TextView right_youtui_jirou;
    @Bind(R.id.qugan_jirou)
    TextView qugan_jirou;
    @Bind(R.id.qugan_zhifang)
    TextView qugan_zhifang;
    @Bind(R.id.healthvalue1)
    TextView healthvalue1;
    @Bind(R.id.left_zuobi_jirou)
    TextView left_zuobi_jirou;
    BodyReport bodyReport;
    @Bind(R.id.bca_result)
    TextView bca_result;
    TextView tv_healthvalue = null;
    @Bind(R.id.bca_zhifang_result)
    TextView bca_zhifang_result;
    @Bind(R.id.bca_jirou_result)
    TextView bca_jirou_result;
    @Bind(R.id.bca_pinggu_result)
    TextView bca_pinggu_result;
    @Bind(R.id.bca_tizhong_result)
    TextView bca_tizhong_result;
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
    @Bind(R.id.body_bo__web)
    WebView body_bo__web;
    private String url = "http://115.29.15.59/Content/statichtml/oxygen.html";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        progress();
        view = inflater.inflate(R.layout.body_fg_layout, null);
        ButterKnife.bind(this, view);
        initWebview();
        initView();
        return view;
    }

    private void initWebview() {
        body_bo__web.getSettings().setDefaultTextEncodingName("utf-8");
        body_bo__web.getSettings().setJavaScriptEnabled(true);
        synCookies();//格式化写入cookie，需写在setJavaScriptEnabled之后
        body_bo__web.setWebChromeClient(chromeClient);
        body_bo__web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
        JavaScriptInterface javaScriptInterface = new JavaScriptInterface();
        body_bo__web.addJavascriptInterface(javaScriptInterface, "wyy");
        body_bo__web.loadUrl(url);
    }

    private void synCookies() {
        body_bo__web.setWebChromeClient(chromeClient);
        body_bo__web.setWebViewClient(new WebViewClient() {// 让webView内的链接在当前页打开，不调用系统浏览器
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    protected WebChromeClient chromeClient = new WebChromeClient() {
        // js交互提示
        public boolean onJsAlert(WebView view, String url, String message, android.webkit.JsResult result) {
            return super.onJsAlert(view, url, message, result);
        }
    };

    private void drawpicture(final String json) {
        new Handler().postDelayed(new Runnable() {//异步传本地数据给网页
            @Override
            public void run() {
                transferDataToWeb(json);
            }
        }, 1000);
    }

    private void transferDataToWeb(String json) {
        if (body_bo__web != null) {
            String info = StringUtils.centerString(json, "fatyear");
            Log.i("==================+++", info);
            body_bo__web.loadUrl("javascript:show_fate('" + info + "')");   //web网页中已添加了function show(json)方法
        }
    }

    private void initView() {
        RequestParams params = new RequestParams();
        params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
        AsyncHttpUtil.get(UrlInterface.BodyReport_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("json-----body------", json);
                drawpicture(json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    BCAFragmentbean bcaFragmentbean = JsonHelper.getJson(apiMessage.Data, BCAFragmentbean.class);
                    cancel();
                    if (bcaFragmentbean != null) {
                        initView(bcaFragmentbean);
                        getInfo(bcaFragmentbean);
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("wyy-----throwable-----", throwable.toString());
            }
        });
    }

    //当前人体成分
    private void getInfo(BCAFragmentbean bcaFragmentbean) {
        if (bcaFragmentbean != null) {
            healthvalue1.setText(bcaFragmentbean.getBasicMetaBolismBest() + "");
            bp_fg_suggest.setText(bcaFragmentbean.getHealthSuggest() + "");
        }
        RecordFat rf = bcaFragmentbean.getRecordFat();//人体成分分析
        if (rf != null) {
            left_zuobi_jirou.setText("脂肪量" + rf.getLeftArmFat() + "KG");
            left_zuobi_jirou.setText("肌肉量" + rf.getLeftArmMuscle() + "KG");
            right_youbi_zhifang.setText("脂肪量" + rf.getRightArmFat() + "KG");
            right_youbi_jirou.setText("肌肉量" + rf.getRightArmMuscle() + "KG");
            left_zuotui_zhifang.setText("脂肪量" + rf.getLeftLegFat() + "KG");
            left_zuotui_jirou.setText("肌肉量" + rf.getLeftLegMuscle() + "KG");
            right_youtui_zhifang.setText("脂肪量" + rf.getRightLegFat() + "KG");
            right_youtui_jirou.setText("肌肉量" + rf.getRightLegMuscle() + "KG");
            qugan_jirou.setText("肌肉量" + rf.getBodyMuscle() + "KG  ");
            qugan_zhifang.setText("脂肪量" + rf.getBodyFat() + "KG");
            bca_result.setText(rf.getWeightAdjust() + "");
            bca_zhifang_result.setText(rf.getFatAdjust() + "");
            bca_jirou_result.setText(rf.getMuscleAdjust() + "");
            if (!StringUtils.isEmpty(rf.getResult())) {
                if (Integer.parseInt(rf.getResult()) == 0) {
                    bca_pinggu_result.setText("急瘦");
                } else if (Integer.parseInt(rf.getResult()) == 1) {
                    bca_pinggu_result.setText("偏瘦");
                } else if (Integer.parseInt(rf.getResult()) == 2) {
                    bca_pinggu_result.setText("标准");
                } else if (Integer.parseInt(rf.getResult()) == 3) {
                    bca_pinggu_result.setText("超重");
                } else if (Integer.parseInt(rf.getResult()) == 4) {
                    bca_pinggu_result.setText("肥胖");
                } else if (Integer.parseInt(rf.getResult()) == 5) {
                    bca_pinggu_result.setText("急胖");
                } else {
                    bca_pinggu_result.setText("");
                }
                //bca_pinggu_result.setText(rf.getResult());
            }
        }
        RecordBMI rm = bcaFragmentbean.getRecordBMI();
        if (rm != null) {
            bca_tizhong_result.setText(rm.getIdealWeight() + "");
        }
    }


    private void initView(BCAFragmentbean bcaFragmentbean) {

        for (int i = 0; i < healthKey.length; i++) {//肥胖分析
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_one_layout, null);
            TextView tv_healthyname = (TextView) view.findViewById(R.id.tv_healthyname);
            tv_healthyname.setTextColor(getResources().getColor(R.color.white));
            TextView tv_healthyinfo = (TextView) view.findViewById(R.id.tv_healthyinfo);
            tv_healthvalue = (TextView) view.findViewById(R.id.healthvalue);
            tv_healthyname.setText(healthKey[i]);
            tv_healthyinfo.setText(healthValue[i]);
            if (setData(bcaFragmentbean, 1) != null) {
                ArrayList<String> temp = setData(bcaFragmentbean, 1);
                if (temp != null) {
                    tv_healthvalue.setText(temp.get(i));
                }
            }
            layout1.addView(view);
        }
        for (int j = 0; j < healthKey2.length; j++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.bca_fg_layout_item1, null);
            TextView bca_fg_item_tvname = (TextView) view.findViewById(R.id.bca_fg_item_tvname);
            TextView bca_fg_layout_tv_namevalue = (TextView) view.findViewById(R.id.bca_fg_layout_tv_namevalue);
            TextView bca_fg_item_tvres = (TextView) view.findViewById(R.id.bca_fg_item_tvres);
            TextView bca_fg_item_tvresvalue = (TextView) view.findViewById(R.id.bca_fg_item_tvresvalue);
            bca_fg_item_tvname.setText(healthKey2[j]);
            bca_fg_item_tvres.setText(healthKey3[j]);
            if (setData(bcaFragmentbean, 2) != null) {
                ArrayList<String> temp = setData(bcaFragmentbean, 2);
                if (temp != null) {
                    bca_fg_layout_tv_namevalue.setText(temp.get(j));
                }
            }
            if (setData(bcaFragmentbean, 3) != null) {
                ArrayList<String> temp = setData(bcaFragmentbean, 3);
                if (temp != null) {
                    bca_fg_item_tvresvalue.setText(temp.get(j));
                }
            }
            layout2.addView(view);
        }
        bp_fg_suggest.setText(bcaFragmentbean.getHealthSuggest());

    }

    private void saveInfo(BodyReport bodyReport) {

    }

    public ArrayList<String> setData(BCAFragmentbean bcaFragmentbean, int a) {
//        if (bcaFragmentbean == null) {
//            return null;
//        }
//        BCAFragmentbean.DataEntity.RecordFatEntity recordFat = bcaFragmentbean.getData().getRecordFat();
        RecordFat recordFat = bcaFragmentbean.getRecordFat();
        if (recordFat != null) {
            ArrayList<String> arr = new ArrayList<String>();
            switch (a) {
                case 1:
                    arr.add(recordFat.getFatRate() + "%");
                    arr.add(recordFat.getFat() + "kg");
                    arr.add(recordFat.getMuscle() + "kg");
                    arr.add(recordFat.getWaterRate() + "kg");
                    arr.add(recordFat.getViscera() + "");
                    break;
                case 2:
                    arr.add(recordFat.getExceptFat() + "kg");
                    arr.add(recordFat.getWater() + "kg");
                    arr.add(recordFat.getFoc() + "kg");
                    arr.add(recordFat.getBmc() + "kg");
                    break;
                case 3:
                    arr.add(recordFat.getMuscle() + "kg");
                    arr.add(recordFat.getFic() + "kg");
                    arr.add(recordFat.getProtein() + "kg");
                    arr.add(recordFat.getFat() + "kg");
                    break;
            }

            return arr;
        } else {
            return null;
        }
    }
}
