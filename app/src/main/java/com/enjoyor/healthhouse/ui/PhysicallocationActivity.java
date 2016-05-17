package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.PhysicallAdapter;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.custom.PullToRefeshListView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
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

public class PhysicallocationActivity extends BaseActivity implements PullToRefeshListView.OnRefreshListener {
    @Bind(R.id.physicall_lv)
    PullToRefeshListView physicall_lv;
    private double latitude;
    private double longitude;
    private static final double LATITUDE = 50.34324;
    private static final double LONGITUDE = 131.12455;
    List<PhsicallLocation.MachineModelsEntity> phsicallLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.physicallocation_ac_layout);
        ButterKnife.bind(this);
        initView();
        initData();

    }

    private void initListView() {
        physicall_lv.setAdapter(new PhysicallAdapter(this, phsicallLocations, R.layout.physicall_ac_item));
        physicall_lv.setonRefreshListener(this);

    }

    private void initView() {
        latitude = getIntent().getDoubleExtra("latitude", LATITUDE);
        longitude = getIntent().getDoubleExtra("longitude", LONGITUDE);
        Log.d("aaaaaa", latitude + "");
        Log.d("cccccccccc", longitude + "");
    }

    private void initData() {
        phsicallLocations = new ArrayList<PhsicallLocation.MachineModelsEntity>();
        RequestParams params = new RequestParams();
        params.add("lat", latitude + "");
        params.add("lng", longitude + "");
        params.add("pageNum", 0 + "");
        params.add("pageCount", 6 + "");
        AsyncHttpUtil.get(UrlInterface.PhysicallAddr_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (physicall_lv != null) {
                    physicall_lv.onRefreshComplete();
                }
                String json = new String(bytes);
                Log.d("wwwwwwwwwwwwwwwww", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    PhsicallLocation temp = JsonHelper.getJson(apiMessage.Data, PhsicallLocation.class);
                    phsicallLocations.clear();
                    phsicallLocations.addAll(temp.getMachineModels());
                    initListView();
//                    Log.d("ddddddddddddddddd", temp.getMachineModels().get(0).getCompName());
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    @Override
    public void onRefresh() {
        initData();
    }
}
