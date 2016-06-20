package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.HealthFileInfo;
import com.enjoyor.healthhouse.bean.UserInfo;
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
 * Created by Administrator on 2016/5/26.
 */
public class HealthFileActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {

    @Bind(R.id.ll_back)
    LinearLayout ll_back;
    @Bind(R.id.tv_select)
    TextView tv_select;
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.tv_username)
    TextView tv_username;
    @Bind(R.id.xlv_healthfile)
    XListView xlv_healthfile;
    @Bind(R.id.ll_display)
    LinearLayout ll_display;
    @Bind(R.id.iv_userhead)
    ImageView iv_userhead;
    private List<HealthFileInfo.HealthFileList> healthFileList = new ArrayList<>();

    private int count = 1;

    private String userId;

    private PopupWindow pw;

    private String TYPE_ALL = "";
    private String TYPE_ZICE = "2";
    private String TYPE_TIJIAN = "1";
    private String TYPE_SUISHOUJI = "3";
    private String select_type = "";
    private String select_edit = "";


    private TextView tv_zice;
    private TextView tv_tijian;
    private TextView tv_suishouji;
    private TextView tv_all;

    private String address;
    private UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthfile);
        ButterKnife.bind(this);

        userInfo = MyApplication.getInstance().getDBHelper().getUser();
        if (getIntent().hasExtra("address")) {
            address = getIntent().getStringExtra("address");
        }
        initHead();

        if (userInfo != null) {
            if (!StringUtils.isBlank(userInfo.getUserName())) {
                tv_username.setText(userInfo.getUserName());
            } else {
                tv_username.setText(userInfo.getLoginName());
            }
            if (!StringUtils.isBlank(userInfo.getHeadImg())) {
                String path = MyApplication.getInstance().getDBHelper().getUser().getHeadImg();
                if (path != null && !path.startsWith("http://") && !path.startsWith("https://")) {
                    path = UrlInterface.FILE_URL + path;
                }
                Log.i("path",path);
                Glide.with(HealthFileActivity.this).load(path).into(iv_userhead);
            }
        }

        getDate(count, select_edit, select_type);
    }

    private void initHead() {
        ll_back.setOnClickListener(this);
        tv_select.setOnClickListener(this);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (StringUtils.isBlank(s.toString())) {
                    ll_display.setVisibility(View.VISIBLE);
                } else {
                    ll_display.setVisibility(View.GONE);
                }
                healthFileList.clear();
                select_edit = s.toString();
                count = 1;
                getDate(count, select_edit, select_type);
            }
        });
    }

    private void getDate(int count, String compName, String type) {
        if (!StringUtils.isBlank(userInfo.getUserId() + "")) {
            userId = MyApplication.getInstance().getDBHelper().getUser().getUserId() + "";
        }
        RequestParams params = new RequestParams();
        params.add("userId", userId);
        params.add("pageMethod", "2");
        params.add("pageNum", count + "");
        params.add("pageCount", 6 + "");
        params.add("compName", compName);
        params.add("type", type);
        Log.i("count", userId + "-----" + count + "-----" + select_edit + "-----" + select_type);
        AsyncHttpUtil.get(UrlInterface.HEALTHFILE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stops();
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    xlv_healthfile.setVisibility(View.VISIBLE);
                    HealthFileInfo healthFileInfo = JsonHelper.getJson(apiMessage.Data, HealthFileInfo.class);
                    List<HealthFileInfo.HealthFileList> _list = healthFileInfo.getRecordList();
//                    healthFileList.clear();
                    if (_list != null && _list.size() > 0) {
                        healthFileList.addAll(_list);
                        if (healthFileList.size() > 0) {
                            initListView();
                        } else {
                            xlv_healthfile.setVisibility(View.GONE);
                        }
                    }
                } else {
                    if (healthFileList.size() > 0) {
                        xlv_healthfile.setVisibility(View.VISIBLE);
                    } else {
                        xlv_healthfile.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                if (healthFileList.size() > 0) {
                    xlv_healthfile.setVisibility(View.VISIBLE);
                } else {
                    xlv_healthfile.setVisibility(View.GONE);
                }
            }
        });
    }

    private void initListView() {
        xlv_healthfile.setPullRefreshEnable(false);
        xlv_healthfile.setPullLoadEnable(true);
        xlv_healthfile.setXListViewListener(this);
        xlv_healthfile.setAdapter(new HealthFileAdapter());
        xlv_healthfile.setSelection((count - 1) * 6);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        getDate(++count, select_edit, select_type);
    }

    public void stops() {
        if (xlv_healthfile != null) {
            xlv_healthfile.stopLoadMore();
        }
    }

    private void selectType() {
        final View popView = LayoutInflater.from(this).inflate(R.layout.popup_healthfile_selecttype, null);
        pw = new PopupWindow(popView, 500, 200, true);
        pw.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        pw.setBackgroundDrawable(new BitmapDrawable());
        pw.setOutsideTouchable(true);
        pw.showAsDropDown(tv_select);

        tv_zice = (TextView) popView.findViewById(R.id.tv_zice);
        tv_tijian = (TextView) popView.findViewById(R.id.tv_tijian);
        tv_suishouji = (TextView) popView.findViewById(R.id.tv_suishouji);
        tv_all = (TextView) popView.findViewById(R.id.tv_all);
        tv_zice.setOnClickListener(this);
        tv_tijian.setOnClickListener(this);
        tv_suishouji.setOnClickListener(this);
        tv_all.setOnClickListener(this);
        setDefault();
        getDefaultColor();
    }

    private void getDefaultColor() {
        if (select_type == TYPE_ZICE) {
            tv_zice.setTextColor(getResources().getColor(R.color.colorGreenYellow));
            Drawable img_off = getResources().getDrawable(R.mipmap.bl_icon_small_zijian);
            img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
            tv_zice.setCompoundDrawables(img_off, null, null, null);
        } else if (select_type == TYPE_TIJIAN) {
            tv_tijian.setTextColor(getResources().getColor(R.color.form_feipang));
            Drawable img_off = getResources().getDrawable(R.mipmap.bl_icon_small_dingwei);
            img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
            tv_tijian.setCompoundDrawables(img_off, null, null, null);
        } else if (select_type == TYPE_SUISHOUJI) {
            tv_suishouji.setTextColor(getResources().getColor(R.color.color_normal));
            Drawable img_off = getResources().getDrawable(R.mipmap.bl_icon_small_xiangji);
            img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
            tv_suishouji.setCompoundDrawables(img_off, null, null, null);
        } else {
            setDefault();
        }
    }

    private void setDefault() {
        tv_zice.setTextColor(getResources().getColor(R.color.textcolor_body));
        Drawable img_off = getResources().getDrawable(R.mipmap.bl_icon_zijian);
        img_off.setBounds(0, 0, img_off.getMinimumWidth(), img_off.getMinimumHeight());
        tv_zice.setCompoundDrawables(img_off, null, null, null);

        tv_tijian.setTextColor(getResources().getColor(R.color.textcolor_body));
        Drawable img_off2 = getResources().getDrawable(R.mipmap.bl_icon_gray_dingwei);
        img_off2.setBounds(0, 0, img_off2.getMinimumWidth(), img_off2.getMinimumHeight());
        tv_tijian.setCompoundDrawables(img_off2, null, null, null);

        tv_suishouji.setTextColor(getResources().getColor(R.color.textcolor_body));
        Drawable img_off3 = getResources().getDrawable(R.mipmap.bl_icon_xiangji);
        img_off3.setBounds(0, 0, img_off3.getMinimumWidth(), img_off3.getMinimumHeight());
        tv_suishouji.setCompoundDrawables(img_off3, null, null, null);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.ll_back:
                finish();
                break;
            case R.id.tv_select:

                selectType();

                break;
            case R.id.tv_all:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_ALL;
                TextView tv0 = (TextView) v;
                tv_select.setText(tv0.getText());
                count = 1;
                getDate(count, select_edit, select_type);
                break;
            case R.id.tv_zice:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_ZICE;
                TextView tv = (TextView) v;
                tv_select.setText(tv.getText());
                count = 1;
                getDate(count, select_edit, select_type);
                break;
            case R.id.tv_tijian:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_TIJIAN;
                TextView tv1 = (TextView) v;
                count = 1;
                getDate(count, select_edit, select_type);
                break;
            case R.id.tv_suishouji:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_SUISHOUJI;
                TextView tv2 = (TextView) v;
                tv_select.setText(tv2.getText());
                count = 1;
                getDate(count, select_edit, select_type);
                break;
        }
    }

    class HealthFileAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return healthFileList.size();
        }

        @Override
        public Object getItem(int position) {
            return healthFileList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HealthFileActivity.this).inflate(R.layout.item_historyfile, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            String str = healthFileList.get(position).getRecordTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {
                java.util.Date dt = sdf.parse(str);
                String date = DateUtil.dateToString(dt, "MM/dd");
                holder.tv_date.setText(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            int type = healthFileList.get(position).getType();
            if (type == Constant.TYPE_TIJIAN) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_hongyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_tijian);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_dingwei);
                holder.tv_info.setText(healthFileList.get(position).getAddressName());
                holder.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HealthFileActivity.this, MyRecordActivity.class);
                        intent.putExtra("recordId", healthFileList.get(position).getRecordId());
                        startActivity(intent);
                    }
                });

            } else if (type == Constant.TYPE_ZICE) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_lvyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_zice);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_zijian);
                holder.tv_info.setText("个人自测");
                holder.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HealthFileActivity.this, MySelfCheckActivity.class);
                        intent.putExtra("recordId", healthFileList.get(position).getRecordId());
                        intent.putExtra("recordTime", healthFileList.get(position).getRecordTime());
                        startActivity(intent);
                    }
                });
            } else if (type == Constant.TYPE_SUISHOUJI) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_lanyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_suishouji);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_xiangji);
                holder.tv_info.setText(address);
                holder.ll_bg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(HealthFileActivity.this, MyNotesActivity.class);
                        intent.putExtra("address", address);
                        intent.putExtra("recordId", healthFileList.get(position).getRecordId());
                        startActivity(intent);
                    }
                });
            }


            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.tv_date)
            TextView tv_date;
            @Bind(R.id.iv_circle)
            ImageView iv_circle;
            @Bind(R.id.tv_info)
            TextView tv_info;
            @Bind(R.id.ll_bg)
            LinearLayout ll_bg;
            @Bind(R.id.iv_display)
            ImageView iv_display;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
