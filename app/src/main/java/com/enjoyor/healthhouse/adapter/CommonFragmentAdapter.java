package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.InfoClassSelect;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/31.
 */
public class CommonFragmentAdapter extends BaseAdapter {


    private List<InfoClassSelect> list;
    private Context context;
    LayoutInflater from;

    public CommonFragmentAdapter(Context context, List<InfoClassSelect> list) {
        this.list = list;
        this.context = context;
        from = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getImages().size() == 1 || list.get(position).getImages().size() == 0) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        Log.d("wyy---", type + "");
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bale)
                .showImageOnFail(R.mipmap.bale)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        switch (type) {
            case 0:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = from.inflate(R.layout.common_fg_layout_item1, null);
                    holder.common_fg_item_title = (TextView) convertView.findViewById(R.id.common_fg_item_title);
                    holder.common_fg_item_time = (TextView) convertView.findViewById(R.id.common_fg_item_time);
                    holder.common_fg_item_num = (TextView) convertView.findViewById(R.id.common_fg_item_num);
                    holder.common_fg_item_img1 = (ImageView) convertView.findViewById(R.id.common_fg_item_img1);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.common_fg_item_title.setText(list.get(position).getTitle());
                holder.common_fg_item_time.setText(list.get(position).getInterval());
                if ((list.get(position).getPageViews() + "").equals(null)) {
                    holder.common_fg_item_num.setText("阅读量：0");
                } else {
                    holder.common_fg_item_num.setText("阅读量:" + list.get(position).getPageViews());
                }
                if (list.get(position).getImages().size() == 0) {
                    holder.common_fg_item_img1.setImageResource(R.mipmap.bale);
                } else {
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(0).getPath(), holder.common_fg_item_img1, options);
                }
                break;
            case 1:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = from.inflate(R.layout.common_fg_layout_item3, null);
                    holder.communtity_fg_item3_title = (TextView) convertView.findViewById(R.id.communtity_fg_item3_title);
                    holder.communtity_fg_item3_time = (TextView) convertView.findViewById(R.id.communtity_fg_item3_time);
                    holder.communtity_fg_item3_num = (TextView) convertView.findViewById(R.id.communtity_fg_item3_num);
                    holder.communtity_fg_item3_img1 = (ImageView) convertView.findViewById(R.id.communtity_fg_item3_img1);
                    holder.communtity_fg_item3_img2 = (ImageView) convertView.findViewById(R.id.communtity_fg_item3_img2);
                    holder.communtity_fg_item3_img3 = (ImageView) convertView.findViewById(R.id.communtity_fg_item3_img3);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.communtity_fg_item3_title.setText(list.get(position).getTitle());
                holder.communtity_fg_item3_time.setText(list.get(position).getInterval());
                if ((list.get(position).getPageViews() + "").equals(null)) {
                    holder.communtity_fg_item3_num.setText("阅读量：0");
                } else {
                    holder.communtity_fg_item3_num.setText("阅读量:" + list.get(position).getPageViews());
                }
                holder.communtity_fg_item3_num.setText(list.get(position).getPageViews() + "");
                if (list.get(position).getImages().size() == 2) {
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(0).getPath(), holder.communtity_fg_item3_img1, options);
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(1).getPath(), holder.communtity_fg_item3_img2, options);
                    holder.communtity_fg_item3_img3
                            .setVisibility(View.INVISIBLE);
//                    holder.communtity_fg_item3_img3.setMaxHeight(180);
                } else if (list.get(position).getImages().size() == 3) {
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(0).getPath(), holder.communtity_fg_item3_img1, options);
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(1).getPath(), holder.communtity_fg_item3_img2, options);
                    ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + list.get(position).getImages().get(2).getPath(), holder.communtity_fg_item3_img3, options);
                }
                break;
        }
        return convertView;
    }

    class ViewHolder {
        TextView common_fg_item_title, common_fg_item_time, common_fg_item_num, communtity_fg_item3_title, communtity_fg_item3_time, communtity_fg_item3_num;
        ImageView common_fg_item_img1, communtity_fg_item3_img1, communtity_fg_item3_img2, communtity_fg_item3_img3;
    }
}
