package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.utils.ImageLoader;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/18.
 */
public class CameraAdapter extends CommAdapter<String> {
    int width, height;
    List<String> datas;

    public CameraAdapter(Context context, List<String> datas, int layoutId, int width, int height) {
        super(context, datas, layoutId);
        this.width = width;
        this.height = height;
        this.datas = datas;
    }

    @Override
    public void convert(ViewHolder holder, String s) {

        if (s != null) {
            Log.d("wyy+++++++", s);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
            ImageView img = holder.getView(R.id.imageView);
            ImageView img1 = holder.getView(R.id.imageView1);
            img.setLayoutParams(params);
            img1.setLayoutParams(params);
            if (s.equals("tag")){
                Log.d("wyy+++++++", "--------------");
                img1.setVisibility(View.VISIBLE);
                img.setVisibility(View.GONE);
                img1.setImageResource(R.mipmap.zanting);
            }else {
                img.setVisibility(View.VISIBLE);
                img1.setVisibility(View.GONE);
            ImageLoader.getInstance().display(s, img, width, height);
        }}

    }
}
