package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.PhysicallAdapter;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.custom.PullToRefeshListView;
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

public class PhysicallocationActivity extends BaseActivity implements XListView.IXListViewListener {
    @Bind(R.id.physicall_lv)
    XListView physicall_lv;
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
        initListView();
        initView();
        initData(pageNum);

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
        params.add("pageCount", 6 + "");
        AsyncHttpUtil.get(UrlInterface.PhysicallAddr_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                stops();
                String json = new String(bytes);
//                Log.d("wwwwwwwwwwwwwwwww", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    PhsicallLocation temp = JsonHelper.getJson(apiMessage.Data, PhsicallLocation.class);
                    if (temp.getMachineModels() != null && temp.getMachineModels().size() > 0) {
//                        phsicallLocations.clear();
                        phsicallLocations.addAll(temp.getMachineModels());
                        Log.d("wwwwwwwwwwwwwwwww", phsicallLocations.size() + "           " + pageNum);
                        PhysicallAdapter adapter = new PhysicallAdapter(PhysicallocationActivity.this, phsicallLocations, R.layout.physicall_ac_item);
                        physicall_lv.setAdapter(adapter);
                        physicall_lv.setSelection((pageNum - 1) * 6);
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

}