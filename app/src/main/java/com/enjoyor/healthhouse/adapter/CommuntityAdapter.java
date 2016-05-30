package com.enjoyor.healthhouse.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by YuanYuan on 2016/5/27.
 */
public class CommuntityAdapter extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    public CommuntityAdapter(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list = list;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
