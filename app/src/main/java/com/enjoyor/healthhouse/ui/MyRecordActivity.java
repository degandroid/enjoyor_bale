package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.ItemMyRecord;
import com.enjoyor.healthhouse.bean.MyRecordInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ListUtils;
import com.enjoyor.healthhouse.utils.RecordUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/23.
 */

/*我的报告*/
public class MyRecordActivity extends BaseActivity {
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Bind(R.id.fl_normal)
    FrameLayout fl_normal;
    @Bind(R.id.fl_abnormal)
    FrameLayout fl_abnormal;

    @Bind(R.id.sv_normal)
    ScrollView sv_normal;
    @Bind(R.id.lly_normal)
    LinearLayout lly_normal;
    @Bind(R.id.lly_abnormal)
    LinearLayout lly_abnormal;
    @Bind(R.id.sv_abnormal)
    ScrollView sv_abnormal;

    @Bind(R.id.tv_ab_text)
    TextView tv_ab_text;

    @Bind(R.id.ll_abnormal)
    LinearLayout ll_abnormal;
    @Bind(R.id.tv_normal)
    TextView tv_normal;
    @Bind(R.id.tv_abnormal)
    TextView tv_abnormal;
    @Bind(R.id.iv_info)
    ImageView iv_info;
    @Bind(R.id.ll_shengao)
    LinearLayout ll_shengao;
    private Context context;

    private final int STATE_NORMAL = 1;
    private final int STATE_ABNORMAL = 2;

    List<ItemMyRecord> list_abnormal = new ArrayList<>();
    List<ItemMyRecord> list_normal = new ArrayList<>();

//    private boolean tag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myrecord_ac_layout);
        ButterKnife.bind(this);
        context = this;
        initHead();

        if (getIntent().hasExtra("recordId")) {
            getDate(getIntent().getLongExtra("recordId", 0l));
        }
        initCheckState();
    }

    private void initHead() {
        navigation_name.setText("我的报告");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getDate(Long recordId) {

        RequestParams params = new RequestParams();
        params.add("recordId", recordId + "");
        AsyncHttpUtil.get(UrlInterface.RECORD_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    MyRecordInfo info = JsonHelper.getJson(apiMessage.Data, MyRecordInfo.class);
                    initView(info);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void initView(MyRecordInfo info) {
        List<ItemMyRecord> _list = RecordUtils.getRecordList(info);
        list_normal.clear();
        list_normal.addAll(_list);
        if (!ListUtils.isEmpty(list_normal)) {
            sv_normal.setVisibility(View.VISIBLE);
            for (int i = 0; i < list_normal.size(); i++) {
                ItemMyRecord itemMyRecord = list_normal.get(i);
                View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord, null);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
                TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
                ImageView iv_upanddown = (ImageView) view.findViewById(R.id.iv_upanddown);
                iv_upanddown.setVisibility(View.GONE);
                if (i == 5 || i == 8 || i == 11 || i == 13) {
                    TextView textView = new TextView(context);
                    textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 12));
                    textView.setBackgroundResource(R.color.color_line);
                    ll_shengao.addView(textView);
                }


                tv_name.setText(itemMyRecord.getName());
                if(itemMyRecord.getResult()!=null){
                    tv_result.setText(itemMyRecord.getResult() + "");
                }else{
                    tv_result.setText("");
                }

                tv_range.setText(itemMyRecord.getMin() + "-" + itemMyRecord.getMax());

                if (itemMyRecord.getMin() == -1) {
                    tv_range.setText("");
                } else if (itemMyRecord.getMax() == -1) {
                    tv_range.setText(itemMyRecord.getMin() + "");
                }

                if (itemMyRecord.getResult() != null && itemMyRecord.getMax() != -1 && itemMyRecord.getMin() != -1) {
                    if (itemMyRecord.getResult() < itemMyRecord.getMin()) {
                        iv_upanddown.setVisibility(View.VISIBLE);
                        iv_upanddown.setImageResource(R.mipmap.bl_icon_lan);
                        list_abnormal.add(itemMyRecord);
                    }
                    if (itemMyRecord.getResult() > itemMyRecord.getMax()) {
                        iv_upanddown.setVisibility(View.VISIBLE);
                        iv_upanddown.setImageResource(R.mipmap.bl_icon_hong);
                        list_abnormal.add(itemMyRecord);
                    }
                }
                ll_shengao.addView(view);
            }
        }else{
            lly_normal.setVisibility(View.VISIBLE);
        }
//        List<ItemMyRecord> list_ab = RecordUtils.getAbnormalList(info);
        if (!ListUtils.isEmpty(list_abnormal)) {
            sv_abnormal.setVisibility(View.VISIBLE);
            iv_info.setVisibility(View.VISIBLE);
                tv_ab_text.setText("共" + list_abnormal.size() + "项异常");
                for (int i = 0; i < list_abnormal.size(); i++) {
                    ItemMyRecord itemMyRecord = list_abnormal.get(i);
                    View view = LayoutInflater.from(context).inflate(R.layout.item_myrecord, null);
                TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
                TextView tv_result = (TextView) view.findViewById(R.id.tv_result);
                TextView tv_range = (TextView) view.findViewById(R.id.tv_range);
                ImageView iv_upanddown = (ImageView) view.findViewById(R.id.iv_upanddown);
                iv_upanddown.setVisibility(View.GONE);


                tv_name.setText(itemMyRecord.getName());
                tv_result.setText(itemMyRecord.getResult() + "");
                tv_range.setText(itemMyRecord.getMin() + "-" + itemMyRecord.getMax());

                if (itemMyRecord.getResult() != null && itemMyRecord.getMax() != -1 && itemMyRecord.getMin() != -1) {
                    if (itemMyRecord.getResult() < itemMyRecord.getMin()) {
                        iv_upanddown.setVisibility(View.VISIBLE);
                        iv_upanddown.setImageResource(R.mipmap.bl_icon_lan);
                    }
                    if (itemMyRecord.getResult() > itemMyRecord.getMax()) {
                        iv_upanddown.setVisibility(View.VISIBLE);
                        iv_upanddown.setImageResource(R.mipmap.bl_icon_hong);
                    }
                }
                ll_abnormal.addView(view);
            }
        } else {
            iv_info.setVisibility(View.GONE);
            lly_normal.setVisibility(View.VISIBLE);
            tv_ab_text.setText("暂无异常");
        }
    }


    private void initCheckState() {
        fl_normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(list_normal.size()>0){
                    sv_normal.setVisibility(View.VISIBLE);
                    sv_abnormal.setVisibility(View.GONE);
                    lly_normal.setVisibility(View.GONE);
                    lly_abnormal.setVisibility(View.GONE);
                    CheckBackground(STATE_NORMAL);
                }else{
                    lly_normal.setVisibility(View.VISIBLE);
                    sv_normal.setVisibility(View.GONE);
                    sv_abnormal.setVisibility(View.GONE);
                    lly_abnormal.setVisibility(View.GONE);
                }
            }
        });
        fl_abnormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                tag = false;
                iv_info.setVisibility(View.GONE);
                if(list_abnormal.size()>0){
                    sv_abnormal.setVisibility(View.VISIBLE);
                    sv_normal.setVisibility(View.GONE);
                    lly_normal.setVisibility(View.GONE);
                    lly_abnormal.setVisibility(View.GONE);
                    CheckBackground(STATE_ABNORMAL);
                }else{
                    lly_abnormal.setVisibility(View.VISIBLE);
                    sv_abnormal.setVisibility(View.GONE);
                    sv_normal.setVisibility(View.GONE);
                    lly_normal.setVisibility(View.GONE);
                }

            }
        });
    }

    private void CheckBackground(int i) {
        switch (i) {
            case STATE_NORMAL:
                fl_normal.setBackgroundResource(R.drawable.bl_bg_left_select);
                tv_normal.setTextColor(getResources().getColor(R.color.colorWhite));

                fl_abnormal.setBackgroundResource(R.drawable.white_null);
                tv_abnormal.setTextColor(getResources().getColor(R.color.textcolor_body));
                break;
            case STATE_ABNORMAL:
                fl_abnormal.setBackgroundResource(R.drawable.bl_bg_right_select);
                tv_abnormal.setTextColor(getResources().getColor(R.color.colorWhite));
                fl_normal.setBackgroundResource(R.drawable.white_null);
                tv_normal.setTextColor(getResources().getColor(R.color.textcolor_body));
                break;
        }
    }


}
