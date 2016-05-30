package com.enjoyor.healthhouse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.CommuntityAdapter;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/5/3.
 */
public class CommunityFragment extends BaseFragment implements ViewPager.OnPageChangeListener {
    @Bind(R.id.communtity_bz)
    LinearLayout communtity_bz;
    @Bind(R.id.communtity_bp)
    LinearLayout communtity_bp;
    @Bind(R.id.communtity_lose)
    LinearLayout communtity_lose;
    @Bind(R.id.communtity_bo)
    LinearLayout communtity_bo;
    @Bind(R.id.communtity_type)
    LinearLayout communtity_type;
    @Bind(R.id.communtity_bz_tv)
    TextView communtity_bz_tv;
    @Bind(R.id.communtity_bp_tv)
    TextView communtity_bp_tv;
    @Bind(R.id.communtity_lose_tv)
    TextView communtity_lose_tv;
    @Bind(R.id.communtity_bo_tv)
    TextView communtity_bo_tv;
    @Bind(R.id.communtity_type_tv)
    TextView communtity_type_tv;
    @Bind(R.id.communtity_bz_img)
    ImageView communtity_bz_img;
    @Bind(R.id.communtity_bp_img)
    ImageView communtity_bp_img;
    @Bind(R.id.communtity_lose_img)
    ImageView communtity_lose_img;
    @Bind(R.id.communtity_bo_img)
    ImageView communtity_bo_img;
    @Bind(R.id.communtity_type_img)
    ImageView communtity_type_img;
    @Bind(R.id.communtity_viewpager)
    ViewPager communtity_viewpager;
    private ArrayList<Fragment> fragmentList;
    private BOFragment bofragment = null;
    private BPFragment bpfragment = null;
    private ECGFragment ecgfragment = null;
    private HomeFragment homefragment = null;
    private HealthFragment healthfragment = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.communtity_fg_layout, null);
        ButterKnife.bind(this, view);
        initTextColor();
        initImage();
        initViewPager();
        initDefaultFg();
        initEvent();
        return view;
    }

    private void initEvent() {
        communtity_bz.setOnClickListener(new txListener(0));
        communtity_bp.setOnClickListener(new txListener(1));
        communtity_lose.setOnClickListener(new txListener(2));
        communtity_bo.setOnClickListener(new txListener(3));
        communtity_type.setOnClickListener(new txListener(4));
    }

    private void initViewPager() {
        fragmentList = new ArrayList<Fragment>();
        bofragment = new BOFragment();
        bpfragment = new BPFragment();
        ecgfragment = new ECGFragment();
        homefragment = new HomeFragment();
        healthfragment = new HealthFragment();
        fragmentList.add(bofragment);
        fragmentList.add(bpfragment);
        fragmentList.add(ecgfragment);
        fragmentList.add(homefragment);
        fragmentList.add(healthfragment);
        communtity_viewpager.setAdapter(new CommuntityAdapter(getActivity().getSupportFragmentManager(), fragmentList));
        communtity_viewpager.setOnPageChangeListener(this);
    }

    private void initDefaultFg() {
        communtity_lose_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
        communtity_lose_img.setVisibility(View.VISIBLE);
    }

    private void initImage() {
        communtity_bz_img.setVisibility(View.INVISIBLE);
        communtity_bp_img.setVisibility(View.INVISIBLE);
        communtity_lose_img.setVisibility(View.INVISIBLE);
        communtity_bo_img.setVisibility(View.INVISIBLE);
        communtity_type_img.setVisibility(View.INVISIBLE);
    }

    private void initTextColor() {
        communtity_bz_tv.setTextColor(getResources().getColor(R.color.textcolor_body));
        communtity_bp_tv.setTextColor(getResources().getColor(R.color.textcolor_body));
        communtity_lose_tv.setTextColor(getResources().getColor(R.color.textcolor_body));
        communtity_bo_tv.setTextColor(getResources().getColor(R.color.textcolor_body));
        communtity_type_tv.setTextColor(getResources().getColor(R.color.textcolor_body));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        changebutton(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void changebutton(int index) {
        switch (index) {
            case 0:
                initImage();
                initTextColor();
                communtity_bz_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
                communtity_bz_img.setVisibility(View.VISIBLE);
                break;
            case 1:
                initImage();
                initTextColor();
                communtity_bp_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
                communtity_bp_img.setVisibility(View.VISIBLE);
                break;
            case 2:
                initImage();
                initTextColor();
                communtity_lose_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
                communtity_lose_img.setVisibility(View.VISIBLE);
                break;
            case 3:
                initImage();
                initTextColor();
                communtity_bo_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
                communtity_bo_img.setVisibility(View.VISIBLE);
                break;
            case 4:
                initImage();
                initTextColor();
                communtity_type_tv.setTextColor(getResources().getColor(R.color.color_normal_second));
                communtity_type_img.setVisibility(View.VISIBLE);
                break;
        }
    }

    public class txListener implements View.OnClickListener {
        private int index = 2;

        public txListener(int i) {
            index = i;
        }

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            communtity_viewpager.setCurrentItem(index);
        }
    }
}

