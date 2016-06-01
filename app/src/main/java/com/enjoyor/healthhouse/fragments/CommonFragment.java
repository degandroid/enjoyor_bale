package com.enjoyor.healthhouse.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.CommonFragmentAdapter;
import com.enjoyor.healthhouse.bean.InfoClassSelect;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.ui.CommunitityCommonActivity;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/31.
 * 资讯fragment
 */
public class CommonFragment extends BaseFragment {
    private static CommonFragment commonFragment;

    public static CommonFragment getInstance(int a) {
        commonFragment = new CommonFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", a + "");
        commonFragment.setArguments(bundle);
        return commonFragment;
    }

    View view;
    List<InfoClassSelect> list;
    @Bind(R.id.common_fg_lv)
    ListView common_fg_lv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.common_fg_layout, null);
        ButterKnife.bind(this, view);
        progress();
        initData();
        return view;
    }

    private void initData() {
        Bundle bundle = getArguments();
        String a = bundle.getString("key");
        RequestParams params = new RequestParams();
        params.add("pageNum", "1");
        params.add("pageCount", "10");
        params.add("classify", a);
        AsyncHttpUtil.get(UrlInterface.InfoClassSelect_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("wyy=====", json);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    list = JsonHelper.getArrayJson(apiMessage.Data, InfoClassSelect.class);
                    CommonFragmentAdapter adapter = new CommonFragmentAdapter(getActivity(), list);
                    common_fg_lv.setAdapter(adapter);
                    cancel();
                    onClick();
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    private void onClick() {
        common_fg_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent_com = new Intent(getActivity(), CommunitityCommonActivity.class);
                int i = list.get(position).getId();
                Log.d("wyy====", i + "");
                intent_com.putExtra("id", i);
                CommonFragment.this.startActivity(intent_com);
            }
        });
    }
}
