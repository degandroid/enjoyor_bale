package com.enjoyor.healthhouse.ui;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.HealthFileInfo;
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
    @Bind(R.id.ll_display)LinearLayout ll_display;

    private List<HealthFileInfo.HealthFileList> healthFileList = new ArrayList<>();

    private int count = 1;

    private String HEALTHFILE_URL = "/app/getrecordlist.do";
    private String userId;

    private PopupWindow pw;

    private String TYPE_ALL = "";
    private String TYPE_ZICE = "2";
    private String TYPE_TIJIAN = "1";
    private String TYPE_SUISHOUJI = "3";
    private String select_type = "";
    private String select_edit = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_healthfile);
        ButterKnife.bind(this);
        initHead();
        if (MyApplication.getInstance().getDBHelper().getUser().getLoginName() != null) {
            tv_username.setText(MyApplication.getInstance().getDBHelper().getUser().getLoginName());
        }
        getDate(1, select_edit, select_type);
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
                if(StringUtils.isBlank(s.toString())){
                    ll_display.setVisibility(View.VISIBLE);
                }else{
                    ll_display.setVisibility(View.GONE);
                    healthFileList.clear();
                    select_edit = s.toString();
                    getDate(1, select_edit, select_type);
                }

            }
        });
    }

    private void getDate(int i, String compName, String type) {
        long _userId = MyApplication.getInstance().getDBHelper().getUser().getUserId();
        if (!StringUtils.isBlank(_userId + "")) {
            userId = MyApplication.getInstance().getDBHelper().getUser().getUserId() + "";
        }
        RequestParams params = new RequestParams();
        params.add("userId", userId);
        params.add("paging", "true");
        params.add("pageMethod", "2");
        params.add("pageNum", i + "");
        params.add("pageCount", "10");
        params.add("compName", compName);
        params.add("type", type);
        AsyncHttpUtil.get(UrlInterface.TEXT_URL + HEALTHFILE_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stops();
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    xlv_healthfile.setVisibility(View.VISIBLE);
                    HealthFileInfo healthFileInfo = JsonHelper.getJson(apiMessage.Data, HealthFileInfo.class);
                    List<HealthFileInfo.HealthFileList> _list = healthFileInfo.getRecordList();
                    healthFileList.addAll(_list);
                    if (healthFileList!=null&&healthFileList.size()>0) {
                        initListView();
                    }else{
                        xlv_healthfile.setVisibility(View.GONE);
                    }
                } else {
                    xlv_healthfile.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                xlv_healthfile.setVisibility(View.GONE);
            }
        });
    }

    private void initListView() {
        xlv_healthfile.setPullRefreshEnable(false);
        xlv_healthfile.setPullLoadEnable(true);
        xlv_healthfile.setXListViewListener(this);
        xlv_healthfile.setAdapter(new HealthFileAdapter());

        xlv_healthfile.setSelection((count - 1) * 10);
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        ++count;
        getDate(count, select_edit, select_type);
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

        TextView tv_zice = (TextView) popView.findViewById(R.id.tv_zice);
        TextView tv_tijian = (TextView) popView.findViewById(R.id.tv_tijian);
        TextView tv_suishouji = (TextView) popView.findViewById(R.id.tv_suishouji);
        tv_zice.setOnClickListener(this);
        tv_tijian.setOnClickListener(this);
        tv_suishouji.setOnClickListener(this);
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
            case R.id.tv_zice:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_ZICE;
                TextView tv = (TextView) v;
                tv_select.setText(tv.getText());
                getDate(1, select_edit, select_type);
                break;
            case R.id.tv_tijian:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_TIJIAN;
                TextView tv1 = (TextView) v;
                tv_select.setText(tv1.getText());
                getDate(1, select_edit, select_type);
                break;
            case R.id.tv_suishouji:
                healthFileList.clear();
                if (pw != null && pw.isShowing()) {
                    pw.dismiss();
                }
                select_type = TYPE_SUISHOUJI;
                TextView tv2 = (TextView) v;
                tv_select.setText(tv2.getText());
                getDate(1, select_edit, select_type);
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(HealthFileActivity.this).inflate(R.layout.item_historyfile, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            int type = healthFileList.get(position).getType();
            if (type == Constant.TYPE_TIJIAN) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_hongyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_tijian);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_dingwei);
            } else if (type == Constant.TYPE_ZICE) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_lvyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_zice);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_zijian);
            } else if (type == Constant.TYPE_SUISHOUJI) {
                holder.iv_circle.setImageResource(R.mipmap.bl_icon_lanyuan);
                holder.ll_bg.setBackgroundResource(R.mipmap.bl_bg_suishouji);
                holder.iv_display.setImageResource(R.mipmap.bl_icon_sel_xiangji);
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
            holder.tv_info.setText(healthFileList.get(position).getAddressName());
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
