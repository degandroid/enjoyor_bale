package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.JsonRecordInfo;
import com.enjoyor.healthhouse.bean.MyZijianInfo;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.DateUtil;
import com.enjoyor.healthhouse.utils.ListUtils;
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
 * Created by YuanYuan on 2016/5/23.
 */
public class MySelfCheckActivity extends BaseActivity {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    private Context context;

    @Bind(R.id.tv_ab_text)
    TextView tv_ab_text;
    @Bind(R.id.ll_shengao)
    LinearLayout ll_shengao;
    @Bind(R.id.ll_tizhong)
    LinearLayout ll_tizhong;
    @Bind(R.id.ll_yaowei)
    LinearLayout ll_yaowei;
    @Bind(R.id.ll_tiwen)
    LinearLayout ll_tiwen;
    @Bind(R.id.ll_xinlv)
    LinearLayout ll_xinlv;
    @Bind(R.id.ll_xueyang)
    LinearLayout ll_xueyang;
    @Bind(R.id.ll_xueya)
    LinearLayout ll_xueya;
    @Bind(R.id.ll_xuetang)
    LinearLayout ll_xuetang;
    private List<JsonRecordInfo> JsonList = new ArrayList<>();

    List<MyZijianInfo.BOS> bos = new ArrayList<>();
    List<MyZijianInfo.BPS> bps = new ArrayList<>();
    List<MyZijianInfo.BSES> bses = new ArrayList<>();
    List<MyZijianInfo.ECGS> ecgs = new ArrayList<>();
    List<MyZijianInfo.HEIGHTS> heights = new ArrayList<>();
    List<MyZijianInfo.TEMPERATURES> temperatures = new ArrayList<>();
    List<MyZijianInfo.WAISTLINES> waistlines = new ArrayList<>();
    List<MyZijianInfo.WEIGHTS> weights = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myselfcheck_layout);
        ButterKnife.bind(this);
        context = this;
        initHead();
//        initView();

        if (getIntent().hasExtra("recordId")) {
            getDate(getIntent().getLongExtra("recordId", 0l));
        }
        if (getIntent().hasExtra("recordTime")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                java.util.Date dt = sdf.parse(getIntent().getStringExtra("recordTime"));
                String date = DateUtil.dateToString(dt, "yyyy-MM-dd");
                tv_ab_text.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
    }

    private void initHead() {
        navigation_name.setText("我的自检");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {
        for (int i = 0; i < bos.size(); i++) {
            MyZijianInfo.BOS mBOS = bos.get(i);

            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            iv_info.setVisibility(View.GONE);
            tv_name.setText(mBOS.getCheckTime() + "");
            tv_result.setText(mBOS.getBo() + "");
            tv_range.setText(Constant.BO_BEST_MIN + "-" + Constant.BO_BEST_MAX);

            if (mBOS.getBo() < Constant.BO_BEST_MIN) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_lan);
            }

            if (mBOS.getBo() > Constant.BO_BEST_MAX) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_hong);
            }
            ll_xueyang.addView(view);
        }

        for (int i = 0; i < bps.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_xueya, null);
            MyZijianInfo.BPS mBPS = bps.get(i);
            TextView tv_time = (TextView) view.findViewById(R.id.tv_time);
            TextView tv_up_name = (TextView) view.findViewById(R.id.tv_up_name);
            TextView tv_up_result = (TextView) view.findViewById(R.id.tv_up_result);
            TextView tv_up_range = (TextView) view.findViewById(R.id.tv_up_range);
            ImageView iv_up_info = (ImageView) view.findViewById(R.id.iv_up_info);
            iv_up_info.setVisibility(View.GONE);
            TextView tv_down_name = (TextView) view.findViewById(R.id.tv_down_name);
            TextView tv_down_result = (TextView) view.findViewById(R.id.tv_down_result);
            TextView tv_down_range = (TextView) view.findViewById(R.id.tv_down_range);
            ImageView iv_down_info = (ImageView) view.findViewById(R.id.iv_down_info);
            iv_down_info.setVisibility(View.GONE);
            tv_time.setText(mBPS.getCheckTime() + "点");
            tv_up_name.setText("舒张压");
            tv_up_result.setText(mBPS.getDiastolicPressure() + "");
            tv_up_range.setText(Constant.diastolicPressure_BEST_MIN + "-" + Constant.diastolicPressure_BEST_MAX);

            tv_down_name.setText("收缩压");
            tv_down_result.setText(mBPS.getSystolicPressure() + "");
            tv_down_range.setText(Constant.systolicPressure_BEST_MIN + "-" + Constant.systolicPressure_BEST_MAX);

            if (mBPS.getDiastolicPressure() < Constant.diastolicPressure_BEST_MIN) {
                iv_up_info.setVisibility(View.VISIBLE);
                iv_up_info.setImageResource(R.mipmap.bl_icon_lan);
            }
            if (mBPS.getDiastolicPressure() > Constant.diastolicPressure_BEST_MAX) {
                iv_up_info.setImageResource(R.mipmap.bl_icon_hong);
                iv_up_info.setVisibility(View.VISIBLE);
            }
            if (mBPS.getSystolicPressure() < Constant.systolicPressure_BEST_MIN) {
                iv_down_info.setVisibility(View.VISIBLE);
                iv_down_info.setImageResource(R.mipmap.bl_icon_lan);
            }
            if (mBPS.getSystolicPressure() > Constant.systolicPressure_BEST_MAX) {
                iv_down_info.setImageResource(R.mipmap.bl_icon_hong);
                iv_down_info.setVisibility(View.VISIBLE);
            }
            ll_xueya.addView(view);
        }

        /*-----------------------------------------血糖-----------------------------------------*/


        View view_xuetang = LayoutInflater.from(context).inflate(R.layout.item_myrecord_xuetang, ll_xuetang);
        TextView tv_up_result_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_up_result);
        ImageView iv_up_info_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_up_info);
        iv_up_info_xuetang.setVisibility(View.GONE);
        TextView tv_up_result_two_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_up_result_two);
        ImageView iv_up_info_two_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_up_info_two);
        iv_up_info_two_xuetang.setVisibility(View.GONE);
        TextView tv_up_result_three_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_up_result_three);
        ImageView iv_up_info_three_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_up_info_three);
        iv_up_info_three_xuetang.setVisibility(View.GONE);
        TextView tv_down_result_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_down_result);
        ImageView iv_down_info_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_down_info);
        iv_down_info_xuetang.setVisibility(View.GONE);
        TextView tv_down_result_two_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_down_result_two);
        ImageView iv_down_info_two_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_down_info_two);
        iv_down_info_two_xuetang.setVisibility(View.GONE);
        TextView tv_down_result_three_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_down_result_three);
        ImageView iv_down_info_three_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_down_info_three);
        iv_down_info_three_xuetang.setVisibility(View.GONE);
        TextView tv_result_xuetang = (TextView) view_xuetang.findViewById(R.id.tv_result);
        ImageView iv_info_xuetang = (ImageView) view_xuetang.findViewById(R.id.iv_info);
        iv_info_xuetang.setVisibility(View.GONE);
        for (int i = 0; i < bses.size(); i++) {
            MyZijianInfo.BSES mBSES = bses.get(i);
            int type = mBSES.getBloodSugarType();
            switch (type) {
                case Constant.TYPE_KONGFU:
                    tv_up_result_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_up_info_xuetang);
                    break;
                case Constant.TYPE_ZAOCANHOU:
                    tv_up_result_two_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_up_info_two_xuetang);
                    break;
                case Constant.TYPE_WUCANQIAN:
                    tv_up_result_three_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_up_info_three_xuetang);
                    break;
                case Constant.TYPE_WUCANHOU:
                    tv_down_result_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_down_info_xuetang);
                    break;
                case Constant.TYPE_WANCANQIAN:
                    tv_down_result_two_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_down_info_two_xuetang);
                    break;
                case Constant.TYPE_WANCANHOU:
                    tv_down_result_three_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_down_info_three_xuetang);
                    break;
                case Constant.TYPE_SHUIQIAN:
                    tv_result_xuetang.setText(mBSES.getBloodSugar() + "");
                    setXTivInfo(mBSES, type, iv_info_xuetang);
                    break;
            }
        }

        for (int i = 0; i < ecgs.size(); i++) {
            MyZijianInfo.ECGS mECGS = ecgs.get(i);
            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            iv_info.setVisibility(View.GONE);
            tv_name.setText(ecgs.get(i).getCheckTime() + "");
            tv_result.setText(ecgs.get(i).getEcg() + "");
            tv_range.setText(Constant.ECG_BEST_MIN + "-" + Constant.ECG_BEST_MAX);
            if (mECGS.getEcg() < Constant.ECG_BEST_MIN) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_lan);
            }

            if (mECGS.getEcg() > Constant.ECG_BEST_MAX) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_hong);
            }

            ll_xinlv.addView(view);
        }
        for (int i = 0; i < heights.size(); i++) {

            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            iv_info.setVisibility(View.GONE);
            tv_range.setVisibility(View.INVISIBLE);
            tv_name.setText(heights.get(i).getCheckTime() + "");
            tv_result.setText(heights.get(i).getHeight() + "");

            ll_shengao.addView(view);
        }
        for (int i = 0; i < temperatures.size(); i++) {
            MyZijianInfo.TEMPERATURES mTEMPERATURES = temperatures.get(i);
            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            tv_name.setText(temperatures.get(i).getCheckTime() + "");
            tv_result.setText(temperatures.get(i).getTemperature() + "");
            tv_range.setText(Constant.temperature_BEST_MIN + "-" + Constant.temperature_BEST_MAX);
            if (mTEMPERATURES.getTemperature() < Constant.temperature_BEST_MIN) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_lan);
            }

            if (mTEMPERATURES.getTemperature() > Constant.temperature_BEST_MAX) {
                iv_info.setVisibility(View.VISIBLE);
                iv_info.setImageResource(R.mipmap.bl_icon_hong);
            }
            ll_tiwen.addView(view);
        }

        for (int i = 0; i < waistlines.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            iv_info.setVisibility(View.GONE);
            tv_range.setVisibility(View.INVISIBLE);
            tv_name.setText(waistlines.get(i).getCheckTime() + "");
            tv_result.setText(waistlines.get(i).getWaistLine() + "");
            ll_yaowei.addView(view);
        }
        for (int i = 0; i < weights.size(); i++) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord_shengao, null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
            TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
            ImageView iv_info = (ImageView) view.findViewById(R.id.iv_info);
            iv_info.setVisibility(View.GONE);
            tv_range.setVisibility(View.INVISIBLE);
            tv_name.setText(weights.get(i).getCheckTime() + "");
            tv_result.setText(weights.get(i).getWeight() + "");
            ll_tizhong.addView(view);
        }
    }

    private void setXTivInfo(MyZijianInfo.BSES mBSES, int type, ImageView view) {
        if (mBSES.getBloodSugar() < Constant.getBloodSugarMin(type)) {
            view.setVisibility(View.VISIBLE);
            view.setImageResource(R.mipmap.bl_icon_lan);
        }
        if (mBSES.getBloodSugar() > Constant.getBloodSugarMax(type)) {
            view.setImageResource(R.mipmap.bl_icon_hong);
            view.setVisibility(View.VISIBLE);
        }
    }

    private void getDate(Long recordId) {

        RequestParams params = new RequestParams();

        AsyncHttpUtil.get(UrlInterface.TEXT_URL + "record/self/" + recordId + ".action", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    MyZijianInfo info = JsonHelper.getJson(apiMessage.Data, MyZijianInfo.class);
                    List<MyZijianInfo.BOS> _bos = info.getBos();
                    if (!ListUtils.isEmpty(_bos)) {
                        bos.addAll(_bos);
                    }
                    List<MyZijianInfo.BPS> _bps = info.getBps();
                    if (!ListUtils.isEmpty(_bps)) {
                        bps.addAll(_bps);
                    }

                    List<MyZijianInfo.BSES> _bses = info.getBses();
                    if (!ListUtils.isEmpty(_bses)) {
                        bses.addAll(_bses);
                        Log.i("base", bses.size() + "");
                    }

                    List<MyZijianInfo.ECGS> _ecgs = info.getEcgs();
                    if (!ListUtils.isEmpty(_ecgs)) {
                        ecgs.addAll(_ecgs);
                    }
                    List<MyZijianInfo.HEIGHTS> _heights = info.getHeights();
                    if (!ListUtils.isEmpty(_heights)) {
                        heights.addAll(_heights);
                    }

                    List<MyZijianInfo.TEMPERATURES> _temperatures = info.getTemperatures();
                    if (!ListUtils.isEmpty(_temperatures)) {
                        temperatures.addAll(_temperatures);
                    }

                    List<MyZijianInfo.WAISTLINES> _waistlines = info.getWaistlines();
                    if (!ListUtils.isEmpty(_waistlines)) {
                        waistlines.addAll(_waistlines);
                    }

                    List<MyZijianInfo.WEIGHTS> _weights = info.getWeights();
                    if (!ListUtils.isEmpty(_weights)) {
                        weights.addAll(_weights);
                    }
                    initView();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }
}
