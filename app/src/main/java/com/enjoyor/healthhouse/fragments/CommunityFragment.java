package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.CommuntityAdapter;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.InfoClass;
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

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;

/**
 * Created by Administrator on 2016/5/3.
 */
public class CommunityFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.communtity_viewpager)
    ViewPager communtity_viewpager;
    @Bind(R.id.communtity_group)
    LinearLayout communtity_group;
    List<InfoClass> listInfo = new ArrayList<>();
    private ArrayList<Fragment> fragmentList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        progress();
        View view = inflater.inflate(R.layout.communtity_fg_layout, null);
        ButterKnife.bind(this, view);
        initData();//获取资讯文章分类
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (MyApplication.refrash) {
            initData();
            MyApplication.refrash = false;
        }
        cancel();
    }

    private void initDefaultFragment() {
        TextView textView = (TextView) communtity_group.getChildAt(0).findViewById(R.id.text);
        ImageView imageView = (ImageView) communtity_group.getChildAt(0).findViewById(R.id.img);
        textView.setTextColor(getResources().getColor(R.color.colorGreenYellow));
        imageView.setVisibility(View.VISIBLE);
        communtity_viewpager.setCurrentItem(0);
    }

    private void defaultGroup() {
        final int count = communtity_group.getChildCount();
        for (int k = 0; k < count; k++) {
            TextView textView = (TextView) communtity_group.getChildAt(k).findViewById(R.id.text);
            ImageView imageView = (ImageView) communtity_group.getChildAt(k).findViewById(R.id.img);
            textView.setTextColor(getResources().getColor(R.color.textcolor_smallittle));
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    private void addFragment() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < listInfo.size(); i++) {
            CommonFragment commonFragment = CommonFragment.getInstance(listInfo.get(i).getId());
            fragmentList.add(commonFragment);
        }
    }

    private void initClick() {
        final int count = communtity_group.getChildCount();
        for (int j = 0; j < count; j++) {
            final int finalJ = j;
            communtity_group.getChildAt(j).setClickable(true);
            communtity_group.getChildAt(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = (TextView) v.findViewById(R.id.text);
                    ImageView imageView = (ImageView) v.findViewById(R.id.img);
                    defaultGroup();
                    textView.setTextColor(getResources().getColor(R.color.colorGreenYellow));
                    imageView.setVisibility(View.VISIBLE);
                    communtity_viewpager.setCurrentItem(finalJ);
                    communtity_viewpager.invalidate();
                }
            });
        }

    }

    private void initTab() {
        communtity_group.removeAllViews();
        communtity_group.setLayoutParams(new RelativeLayout.LayoutParams(MATCH_PARENT, 110));
        for (int i = 0; i < listInfo.size(); i++) {
            Log.d("wyy-------", listInfo.size() + "");
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.communtity_tab, null);
            view.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1));
            TextView textView = (TextView) view.findViewById(R.id.text);
            textView.setText(listInfo.get(i).getName());
            textView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 100));
            ImageView imageView = (ImageView) view.findViewById(R.id.img);
            imageView.setVisibility(View.INVISIBLE);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, 10));
            communtity_group.addView(view);
        }
        cancel();
    }

    private void initData() {
//        progress();
        RequestParams pararm = new RequestParams();
        AsyncHttpUtil.get(UrlInterface.InfoClass_URL, pararm, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apimessage = ApiMessage.FromJson(json);
                if (apimessage.Code == 1001) {
                    cancel();
                    listInfo.clear();
                    List<InfoClass> _list = JsonHelper.getArrayJson(apimessage.Data, InfoClass.class);
                    listInfo.addAll(_list);
                    if (listInfo.size() > 0) {
                        initTab();
                        defaultGroup();
                        addFragment();
                        initViewPager();
                        initDefaultFragment();
                        initClick();
                        cancel();
                    } else {
                        Toast.makeText(getActivity(), "暂无更多数据", Toast.LENGTH_LONG).show();
                        cancel();
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    private void initViewPager() {
        communtity_viewpager.setAdapter(new CommuntityAdapter(getActivity().getSupportFragmentManager(), fragmentList));
        communtity_viewpager.setOnPageChangeListener(this);
        communtity_viewpager.invalidate();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        defaultGroup();
        TextView textView = (TextView) communtity_group.getChildAt(position).findViewById(R.id.text);
        ImageView imageView = (ImageView) communtity_group.getChildAt(position).findViewById(R.id.img);
        textView.setTextColor(getResources().getColor(R.color.colorGreenYellow));
        imageView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}

