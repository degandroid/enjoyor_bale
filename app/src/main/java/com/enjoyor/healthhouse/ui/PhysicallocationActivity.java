package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.PhysicallAdapter;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.custom.XListView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ToastUtil;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/12.
 * 体检点
 */

public class PhysicallocationActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    @Bind(R.id.physicall_lv)
    XListView physicall_lv;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    private double latitude;
    private double longitude;
    private static final double LATITUDE = 50.34324;
    private static final double LONGITUDE = 131.12455;
    List<PhsicallLocation.MachineModelsEntity> phsicallLocations = new ArrayList<PhsicallLocation.MachineModelsEntity>();
    private int pageNum = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physicallocation_ac_layout);
        ButterKnife.bind(this);
        progress();
        navigation_name.setText("芭乐健康机在哪");
        initListView();
        initView();
        initData(pageNum);
        initEvent();

    }

    private void initEvent() {
        re_back.setOnClickListener(this);
    }

    private void initListView() {
        physicall_lv.setPullRefreshEnable(false);
        physicall_lv.setPullLoadEnable(true);
        physicall_lv.setXListViewListener(this);
    }

    private void initView() {
        latitude = getIntent().getDoubleExtra("latitude", LATITUDE);
        longitude = getIntent().getDoubleExtra("longitude", LONGITUDE);
        Log.d("aaaaaa", latitude + "");
        Log.d("cccccccccc", longitude + "");
    }

    private void initData(final int pageNum) {
        RequestParams params = new RequestParams();
        params.add("lat", latitude + "");
        params.add("lng", longitude + "");
        params.add("pageNum", pageNum + "");
        params.add("pageCount", 8 + "");
        AsyncHttpUtil.get(UrlInterface.PhysicallAddr_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stops();
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    cancel();
                    PhsicallLocation temp = JsonHelper.getJson(apiMessage.Data, PhsicallLocation.class);
                    if (temp.getMachineModels() != null && temp.getMachineModels().size() > 0) {
                        phsicallLocations.addAll(temp.getMachineModels());
                        PhysicallAdapter adapter = new PhysicallAdapter(PhysicallocationActivity.this, phsicallLocations, R.layout.physicall_ac_item);
                        physicall_lv.setAdapter(adapter);
                        physicall_lv.setSelection((pageNum - 1) * 6);
                        physicall_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent_map = new Intent(PhysicallocationActivity.this, MapActivity.class);
                                intent_map.putExtra("addr",phsicallLocations.get(position-1).getAddressName());
                                intent_map.putExtra("latitude", "" + phsicallLocations.get(position-1).getMachineLat());
                                intent_map.putExtra("longitude", "" + phsicallLocations.get(position-1).getMachineLong());
                                startActivity(intent_map);
                            }
                        });
                    } else {
                        ToastUtil.showToast("暂无新数据");
                    }

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        ++pageNum;
        initData(pageNum);
//        physicall_lv.scrollToPosition(pageNum * 6);
//        physicall_lv.setSelection(pageNum);
    }

    public void stops() {
        if (physicall_lv != null) {
//            physicall_lv.stopRefresh();
            physicall_lv.stopLoadMore();
        }
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back:
                finish();
                break;
        }
    }
}