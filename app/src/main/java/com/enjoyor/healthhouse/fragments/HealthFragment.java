package com.enjoyor.healthhouse.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.HealthRecord;
import com.enjoyor.healthhouse.bean.HealthSuggest;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.ui.HealthFileActivity;
import com.enjoyor.healthhouse.ui.PhysicallocationActivity;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class HealthFragment extends BaseFragment implements View.OnClickListener {
    @Bind(R.id.health_ry_full)
    RelativeLayout health_ry_full;
    @Bind(R.id.health_ry_empty)
    RelativeLayout health_ry_empty;
    @Bind(R.id.button)
    Button button;
    @Bind(R.id.health_full_record)
    TextView health_full_record;
    @Bind(R.id.health_full_evalute)
    TextView health_full_evalute;
    @Bind(R.id.health_full_eat_sug)
    TextView health_full_eat_sug;
    @Bind(R.id.health_full_sport_sug)
    TextView health_full_sport_sug;
    @Bind(R.id.health_full_scr)
    LinearLayout health_full_scr;
    @Bind(R.id.health_full_ununsual_bt)
    TextView health_full_ununsual_bt;
    @Bind(R.id.health_full_ununsual_bp)
    TextView health_full_ununsual_bp;
    @Bind(R.id.health_full_ununsual_ecg)
    TextView health_full_ununsual_ecg;
    @Bind(R.id.health_full_ununsual_bi)
    TextView health_full_ununsual_bi;
    @Bind(R.id.health_full_ununsual_bo)
    TextView health_full_ununsual_bo;
    @Bind(R.id.health_full_bp_img)
    ImageView health_full_bp_img;
    @Bind(R.id.health_full_bp_tv)
    TextView health_full_bp_tv;
    @Bind(R.id.health_full_bs_img)
    ImageView health_full_bs_img;
    @Bind(R.id.health_full_bs_tv)
    TextView health_full_bs_tv;
    @Bind(R.id.health_full_bi_img)
    ImageView health_full_bi_img;
    @Bind(R.id.health_full_bi_tv)
    TextView health_full_bi_tv;
    @Bind(R.id.health_full_bo_img)
    ImageView health_full_bo_img;
    @Bind(R.id.health_full_bo_tv)
    TextView health_full_bo_tv;
    @Bind(R.id.health_full_ecg_img)
    ImageView health_full_ecg_img;
    @Bind(R.id.health_full_ecg_tv)
    TextView health_full_ecg_tv;
    @Bind(R.id.health_full_tab1)
    LinearLayout health_full_tab1;
    @Bind(R.id.health_full_tab2)
    LinearLayout health_full_tab2;
    @Bind(R.id.health_full_tab3)
    LinearLayout health_full_tab3;
    @Bind(R.id.health_full_tab4)
    LinearLayout health_full_tab4;
    @Bind(R.id.health_full_tab5)
    LinearLayout health_full_tab5;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.img_right)
    ImageView img_right;
    @Bind(R.id.health_lin)
    LinearLayout health_lin;
    FrameLayout main_tab;
    HealthRecord healthRecord;
    StringBuffer stringcom;
    StringBuffer stringrec;
    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.health_fg_layout, null);
        ButterKnife.bind(this, view);
        re_back.setOnClickListener(this);
        button.setOnClickListener(this);
        dialog = createLoadingDialog(getActivity(), "正在加载数据...");
        main_tab = (FrameLayout) getActivity().findViewById(R.id.main_tab);
        re_back.setVisibility(View.INVISIBLE);
        navigation_name.setText("健康评估");
        img_right.setVisibility(View.VISIBLE);
        initText();
        initImageTab();
        if (isLogin(getActivity())) {
            isData();
            initEvent();
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_tohealth = new Intent(getActivity(), PhysicallocationActivity.class);
                startActivity(intent_tohealth);
            }
        });
        return view;
    }

    /**
     * 初始化tab页文字
     */
    private void initText() {
        health_full_bp_tv.setTextColor(getResources().getColor(R.color.white));
        health_full_bs_tv.setTextColor(getResources().getColor(R.color.white));
        health_full_bi_tv.setTextColor(getResources().getColor(R.color.white));
        health_full_bo_tv.setTextColor(getResources().getColor(R.color.white));
        health_full_ecg_tv.setTextColor(getResources().getColor(R.color.white));

    }

    /**
     * 初始化tab页图片
     */
    private void initImageTab() {
        health_full_tab1.setBackgroundColor(getResources().getColor(R.color.colorGreenYellow));
        health_full_tab2.setBackgroundColor(getResources().getColor(R.color.colorGreenYellow));
        health_full_tab3.setBackgroundColor(getResources().getColor(R.color.colorGreenYellow));
        health_full_tab4.setBackgroundColor(getResources().getColor(R.color.colorGreenYellow));
        health_full_tab5.setBackgroundColor(getResources().getColor(R.color.colorGreenYellow));
    }

    /**
     * 用户登录之后判断是否有数据，根据是否有数据加载布局
     */
    private void isData() {
        dialog.show();
        RequestParams params = new RequestParams();
        if (MyApplication.getInstance().getDBHelper().getUser() != null) {
            params.add("userId", MyApplication.getInstance().getDBHelper().getUser().getUserId() + "");
            AsyncHttpUtil.get(UrlInterface.AccessHealInfo_URL, params, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    Log.d("wyy---", json);
                    ApiMessage apiMessage = ApiMessage.FromJson(json);
                    if (apiMessage.Code == 1001) {
                        dialog.dismiss();
                        health_ry_empty.setVisibility(View.GONE);
                        health_ry_full.setVisibility(View.VISIBLE);
                        healthRecord = JsonHelper.getJson(apiMessage.Data, HealthRecord.class);
                        if (saveHealthReport(healthRecord)) {
                            Log.d("wyy---", "baocun");
                        }
                        initInfo(healthRecord);
                        initHealthSug(healthRecord);
                        dialog.dismiss();
                    } else if (apiMessage.Code == -204) {
                        health_ry_empty.setVisibility(View.VISIBLE);
                        health_ry_full.setVisibility(View.GONE);
                        dialog.dismiss();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                    dialog.dismiss();
                }
            });
        }
        dialog.dismiss();
    }

    /**
     * 获取健康建议的方法
     *
     * @param healthRecord
     */
    private void initHealthSug(HealthRecord healthRecord) {
        String HEALTH_URL = UrlInterface.TEXT_URL;
        RequestParams param = new RequestParams();
        Log.d("wyy----", healthRecord.getRecordId() + "");
        String id = healthRecord.getRecordId() + "".trim();
        AsyncHttpUtil.get("http://115.28.37.145:9008/healthstationserver/" + "advice/record/" + id + ".action", param, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("wyy-----json----", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<HealthSuggest> healthSuggests = JsonHelper.getArrayJson(apiMessage.Data, HealthSuggest.class);
                    Log.d("wyy-----text----", json);
                    if (healthSuggests != null) {
                        for (HealthSuggest healthlist : healthSuggests) {
                            if (healthlist.getRecommendFoods() != null) {
                                stringcom = new StringBuffer();
                                for (HealthSuggest.RecommendFoodsEntity recommend : healthlist.getRecommendFoods()) {
                                    stringcom.append(recommend.getName() + ",");
                                }
                            }
                            if (healthlist.getRecommendExercises() != null) {
                                stringrec = new StringBuffer();
                                for (HealthSuggest.RecommendExercisesEntity recmondexe : healthlist.getRecommendExercises()) {
                                    stringrec.append(recmondexe.getName() + ",");
                                }
                            }
                        }
                    }
                    if (stringcom == null) {
                        health_full_eat_sug.setText("暂无饮食建议");
                    } else {
                        health_full_eat_sug.setText(stringcom.toString());
                    }
                    if (stringrec == null) {
                        health_full_sport_sug.setText("暂无运动建议");
                    } else {
                        health_full_sport_sug.setText(stringrec.toString());
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.d("wyy-----json----", throwable.toString());
            }
        });
    }

    /**
     * 初始化数据
     *
     * @param healthRecord
     */
    private void initInfo(HealthRecord healthRecord) {
        if (healthRecord.getUnusuals().size() == 0) {
            health_full_record.setText("当前体检数据正常，无异常项目，身体状况良好！");
            health_full_evalute.setText("本次体检显示身体状况良好");
        } else {
            health_full_record.setVisibility(View.GONE);
            health_full_scr.setVisibility(View.VISIBLE);
            StringBuffer stringhuilder = new StringBuffer();
            List<HealthRecord.UnusualsEntity> unusualList = healthRecord.getUnusuals();
            for (HealthRecord.UnusualsEntity unusual : unusualList) {
                if (unusual.getDisease().equals("hypertension")) {
                    health_full_ununsual_bp.setVisibility(View.VISIBLE);
                } else if (unusual.getDisease().equals("diabetesmellitus")) {
                    health_full_ununsual_bt.setVisibility(View.VISIBLE);
                } else if (unusual.getDisease().equals("SpO2")) {
                    health_full_ununsual_bo.setVisibility(View.VISIBLE);
                } else if (unusual.getDisease().equals("electrocardio")) {
                    health_full_ununsual_ecg.setVisibility(View.VISIBLE);
                }
                stringhuilder.append(unusual.getAdvice() + "\n");
            }
            health_full_evalute.setText(stringhuilder.toString());
            health_full_evalute.invalidate();
        }

    }


    private void initEvent() {

        health_full_tab1.setOnClickListener(this);
        health_full_tab2.setOnClickListener(this);
        health_full_tab3.setOnClickListener(this);
        health_full_tab4.setOnClickListener(this);
        health_full_tab5.setOnClickListener(this);
        img_right.setOnClickListener(this);//历史
    }


    @Override
    public void onClick(View v) {
        int key = v.getId();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
//        Log.d("wyy======",healthRecord.toString());
        if (healthRecord != null) {
            switch (key) {
                case R.id.health_full_tab1:
                    hideView();
                    navigation_name.setText("血压评估");
                    health_full_tab1.setBackgroundColor(getResources().getColor(R.color.green));
                    transaction.replace(R.id.health_lin, new BPFragment());
                    transaction.commit();
                    break;
                case R.id.health_full_tab2:
                    hideView();
                    health_full_tab2.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("血糖评估");
                    transaction.replace(R.id.health_lin, new BiFragment());
                    transaction.commit();
                    break;
                case R.id.health_full_tab3:
                    hideView();
                    health_full_tab3.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("人体成分评估");
                    transaction.replace(R.id.health_lin, new BodyFragment());
                    transaction.commit();
                    break;
                case R.id.health_full_tab4:
                    hideView();
                    health_full_tab4.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("血氧评估");
                    transaction.replace(R.id.health_lin, new BOFragment());
                    transaction.commit();
                    break;
                case R.id.health_full_tab5:
                    hideView();
                    health_full_tab5.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("心电评估");
                    transaction.replace(R.id.health_lin, new ECGFragment());
                    transaction.commit();
                    break;
                case R.id.re_back:
                    main_tab.setVisibility(View.VISIBLE);
                    health_lin.setVisibility(View.GONE);
                    isData();
                    initImageTab();
                    initText();
                    re_back.setVisibility(View.INVISIBLE);
                    navigation_name.setText("健康评估");
                    break;
                case R.id.img_right:
                    Intent intent_health = new Intent(getActivity(), HealthFileActivity.class);
                    startActivity(intent_health);
                    break;
            }
        } else {
            initImageTab();
            initText();
            switch (key) {
                case R.id.health_full_tab1:
                    navigation_name.setText("血压评估");
                    health_full_tab1.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case R.id.health_full_tab2:
                    navigation_name.setText("血糖评估");
                    health_full_tab2.setBackgroundColor(getResources().getColor(R.color.green));
                    break;
                case R.id.health_full_tab3:
                    health_full_tab3.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("人体成分评估");
                    break;
                case R.id.health_full_tab4:
                    health_full_tab4.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("血氧评估");
                    break;
                case R.id.health_full_tab5:
                    health_full_tab5.setBackgroundColor(getResources().getColor(R.color.green));
                    navigation_name.setText("心电评估");
                    break;
                case R.id.img_right:
                    navigation_name.setText("健康评估");
                    break;
            }
            health_ry_full.setVisibility(View.GONE);
            health_ry_empty.setVisibility(View.VISIBLE);
            re_back.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 隐藏于显示控件
     */
    private void hideView() {
        initImageTab();
        initText();
        main_tab.setVisibility(View.GONE);
        health_lin.setVisibility(View.VISIBLE);
        re_back.setVisibility(View.VISIBLE);
        health_ry_full.setVisibility(View.GONE);
        health_ry_empty.setVisibility(View.GONE);
    }

    private boolean saveHealthReport(HealthRecord healthRecord) {
        if (healthRecord != null) {
            healthRecord.setId(1);
            return MyApplication.getInstance().getDBHelper().saveHealthReport(healthRecord);
        }
        return false;
    }
}

