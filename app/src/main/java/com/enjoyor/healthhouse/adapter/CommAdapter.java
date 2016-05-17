package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by YuanYuan on 2016/3/28.
 * 自定义适配器，
 */
public abstract class CommAdapter<T> extends BaseAdapter {
    protected Context mContext;
    protected List<T> mDatas;//数据集
    protected LayoutInflater inflater;//布局填充期
    protected int layoutId;//布局id

    public CommAdapter(Context context, List<T> datas, int layoutId) {
        this.mContext = context;
        this.mDatas = datas;
        this.layoutId = layoutId;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size() > 0 ? mDatas.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = ViewHolder.get(mContext, convertView, parent, layoutId, position);

        convert(holder, (T) getItem(position));

        return holder.getmConvertView();
    }

    public abstract void convert(ViewHolder holder, T t);
}
