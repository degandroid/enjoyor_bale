package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
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
import com.enjoyor.healthhouse.url.UrlInterface;
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
    @Bind(R.id.bp_fg_suggest)
    TextView bp_fg_suggest;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.body_fg_layout, null);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        RequestParams params = new RequestParams();
        params.add("userId", "" + MyApplication.getInstance().getDBHelper().getUser().getUserId());
        AsyncHttpUtil.get(UrlInterface.BodyReport_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    BCAFragmentbean bcaFragmentbean = JsonHelper.getJson(apiMessage.Data, BCAFragmentbean.class);
                    initView(bcaFragmentbean);
                    getInfo(bcaFragmentbean);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void getInfo(BCAFragmentbean bcaFragmentbean) {
//        RecordFat recordFat = bcaFragmentbean.getRecordFatEntity();
//        if (recordFat != null) {
//            ArrayList<String > arr=new ArrayList<String>();
//            arr.add(recordFat.getFatRate());
//            arr.add(recordFat.getFat());
//            arr.add(recordFat.getMuscle());
//            arr.add(recordFat.getWaterRate());
//            arr.add(recordFat.getViscera());
        if (bcaFragmentbean != null) {
            healthvalue1.setText(bcaFragmentbean.getBasicMetaBolismBest() + "");
            bp_fg_suggest.setText(bcaFragmentbean.getHealthSuggest() + "");
        }

        RecordFat rf = bcaFragmentbean.getRecordFat();
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
        RecordBMI rm = bcaFragmentbean.getRecordBMI();
        if (rm != null) {
            bca_tizhong_result.setText(rm.getIdealWeight() + "");
        }


    }


    private void initView(BCAFragmentbean bcaFragmentbean) {

        for (int i = 0; i < healthKey.length; i++) {
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

    }

    private void saveInfo(BodyReport bodyReport) {

    }

    public ArrayList<String> setData(BCAFragmentbean bcaFragmentbean, int a) {
        if (bcaFragmentbean == null) {
            return null;
        }
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
