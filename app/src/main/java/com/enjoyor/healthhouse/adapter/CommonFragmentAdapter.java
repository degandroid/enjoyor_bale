package com.enjoyor.healthhouse.adapter;

import android.content.Context;

import com.enjoyor.healthhouse.bean.InfoClassSelect;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/31.
 */
public class CommonFragmentAdapter extends CommAdapter<InfoClassSelect> {

    public CommonFragmentAdapter(Context context, List<InfoClassSelect> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, InfoClassSelect infoClassSelect) {
        if (infoClassSelect.getImages().size() == 1) {

        } else {

        }

    }
}
