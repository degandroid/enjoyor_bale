package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.BMI;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/31.
 */
public class BMIActivity extends BaseActivity implements View.OnClickListener {
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
    @Bind(R.id.tv_twotittle_one)
    TextView tv_twotittle_one;
    @Bind(R.id.tv_twotittle_two)
    TextView tv_twotittle_two;

    @Bind(R.id.ll_null)
    LinearLayout ll_null;
    @Bind(R.id.lv_bmi)
    ListView lv_bmi;
    @Bind(R.id.ll_addweb)
    LinearLayout ll_addweb;
    @Bind(R.id.webView)
    WebView webView;
    private List<BMI> bmi_list = new ArrayList<>();
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmi);
        ButterKnife.bind(this);
        initHead();
        if (MyApplication.getInstance().getDBHelper().getBMI() != null) {
            List<BMI> _list = MyApplication.getInstance().getDBHelper().getBMI();
            if(_list.size()>10){
                for(int i=0;i<10;i++){
                    bmi_list.add(_list.get(_list.size()-(i+1)));
                }
            }else{
                bmi_list.addAll(_list);
            }

            lv_bmi.setAdapter(new BMIAdapter());
            initWeb();
        }
    }


    private void initHead() {
        bt_list.setOnClickListener(this);
        bt_curve.setOnClickListener(this);
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        navigation_name.setText("BMI历史");
        tv_twotittle_one.setText("日期");
        tv_twotittle_two.setText("BMI");
        ll_history_shengao.setVisibility(View.VISIBLE);

    }

    private void initWeb() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setSavePassword(false);
        webSettings.setSaveFormData(false);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(false);
        url = "http://115.28.37.145/Content/statichtml/t-vChart.html";
        webView.loadUrl(url);
        webView.addJavascriptInterface(new JsInterface(this), "AndroidWebView");
        webView.setWebChromeClient(new WebChromeClient());
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

    private void selectDisplay(int which) {
        if (1 == which) {
            bt_list.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_list.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_curve.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_curve.setBackgroundResource(R.drawable.white_null);
            lv_bmi.setVisibility(View.VISIBLE);
            ll_addweb.setVisibility(View.GONE);
            ll_history_shengao.setVisibility(View.VISIBLE);
        } else {
            bt_curve.setBackgroundResource(R.mipmap.bl_bg_selected);
            bt_curve.setTextColor(getResources().getColor(R.color.colorWhite));
            bt_list.setTextColor(getResources().getColor(R.color.textcolor_body));
            bt_list.setBackgroundResource(R.drawable.white_null);
            lv_bmi.setVisibility(View.GONE);
            ll_addweb.setVisibility(View.VISIBLE);
            ll_history_shengao.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.bt_list:
                selectDisplay(1);
                if (bmi_list.size() > 0) {
                    lv_bmi.setVisibility(View.VISIBLE);
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.GONE);
                } else {
                    lv_bmi.setVisibility(View.GONE);
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                }

                break;
            case R.id.bt_curve:
                selectDisplay(2);
                if (bmi_list.size() > 0) {
                    ll_null.setVisibility(View.GONE);
                    ll_addweb.setVisibility(View.VISIBLE);
                    lv_bmi.setVisibility(View.GONE);
                } else {
                    ll_null.setVisibility(View.VISIBLE);
                    ll_addweb.setVisibility(View.GONE);
                    lv_bmi.setVisibility(View.GONE);
                }
                sendInfoToJs();
                break;
        }
    }

    class BMIAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return bmi_list.size();
        }

        @Override
        public Object getItem(int position) {
            return bmi_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                convertView = LayoutInflater.from(BMIActivity.this).inflate(R.layout.item_history_shengao, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_two_one.setText(bmi_list.get(position).getCreateTime() + "");
            holder.tv_two_two.setText(bmi_list.get(position).getBmi() + "");
            Double a = bmi_list.get(position).getBmi();
            if (a <= 16.4) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_pianshou_one));
            } else if (a <= 18.4) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_pianshou_two));
            } else if (a <= 24.9) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_zhenchang_three));
            } else if (a <= 29.9) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_chaozhong_four));
            } else if (a <= 34.9) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_feipang_five));
            } else if (a <= 39.0) {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_feipang_six));
            } else {
                holder.tv_two_two.setTextColor(getResources().getColor(R.color.color_feipang_seven));
            }
            return convertView;
        }

        class ViewHolder {
            @Bind(R.id.tv_two_one)
            TextView tv_two_one;
            @Bind(R.id.tv_two_two)
            TextView tv_two_two;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


    public void sendInfoToJs() {
        String date = JSON.toJSONString(bmi_list);
        webView.loadUrl("javascript:show_BMI('" + date + "')");
    }


}
