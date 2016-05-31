package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.ServiceAdapter;
import com.enjoyor.healthhouse.bean.ServiceInfo;
import com.enjoyor.healthhouse.custom.CustomFirstItemRecyclerView;
import com.enjoyor.healthhouse.custom.CustomRecyclerView.DeviceUtils;
import com.enjoyor.healthhouse.custom.CustomRecyclerView.HeaderAndFooterRecyclerViewAdapter;
import com.enjoyor.healthhouse.custom.CustomRecyclerView.MyLinearLayoutManager;
import com.enjoyor.healthhouse.custom.CustomRecyclerView.RecyclerViewUtils;
import com.enjoyor.healthhouse.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class ServiceFragment extends BaseFragment {
    //    @Bind(R.id.tv_info)TextView tv_info;
    @Bind(R.id.rv_content)
    CustomFirstItemRecyclerView rv_content;

    private List<ServiceInfo> mList = new ArrayList<>();

    private static ServiceFragment mineFragment;

    private ServiceAdapter mAdapter;

    private int[] pic = {R.mipmap.bl_bg_bmi, R.mipmap.bl_bg_kaluli,R.mipmap.bl_bg_tianjiahong};
    private String[] str = {"BMI计算","卡路里换算","正在添加中"};

    public static ServiceFragment getInstance(int info) {
        mineFragment = new ServiceFragment();

        Bundle bundle = new Bundle();
        bundle.putString("info", info + "MineFragment");
        mineFragment.setArguments(bundle);
        return mineFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_service, null);
        ButterKnife.bind(this, view);
        Bundle bundle = getArguments();
        for (int i = 0; i < pic.length; i++) {
            ServiceInfo serviceInfo = new ServiceInfo();
            serviceInfo.setPic(pic[i]);
            serviceInfo.setName(str[i]);
            mList.add(serviceInfo);
        }
        rv_content.setLayoutManager(new MyLinearLayoutManager(getActivity()));
        rv_content.setHasFixedSize(false);
        mAdapter = new ServiceAdapter(getActivity(), mList);
        HeaderAndFooterRecyclerViewAdapter headerAndFooterRecyclerViewAdapter = new HeaderAndFooterRecyclerViewAdapter(mAdapter);
        rv_content.setAdapter(headerAndFooterRecyclerViewAdapter);
        View moreView = getActivity().getLayoutInflater().inflate(R.layout.footer_more, null);
        FrameLayout more = (FrameLayout) moreView.findViewById(R.id.more);
        int item_max_height = (int) getResources().getDimension(R.dimen.item_max_height);
//        int tabBarHeight = getResources().getDimensionPixelSize(R.dimen.tab_bar_height);
        //把高度设置得更大一些，增加柔韧性
        more.getLayoutParams().height = DeviceUtils.getScreenHeight(getActivity()) - item_max_height - ScreenUtils.returnBarHeight(getActivity());
        RecyclerViewUtils.setFooterView(rv_content, moreView);
        return view;
    }


}

