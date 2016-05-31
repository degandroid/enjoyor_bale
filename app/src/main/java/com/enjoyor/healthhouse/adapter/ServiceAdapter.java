package com.enjoyor.healthhouse.adapter;

/**
 * Created by Administrator on 2016/5/28.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.ServiceInfo;
import com.enjoyor.healthhouse.ui.ItemServiceActivity;

import java.util.List;

/**
 * Created by dongjunkun on 2015/12/1.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {
    private boolean isFirst = true;
    private List<ServiceInfo> mList;

    private int item_normal_height;
    private int item_max_height;
    private float item_normal_alpha;
    private float item_max_alpha;
    private float item_normal_font_size;
    private float item_max_font_size;
    private float second_item_normal_font_size;
    private float second_item_max_font_size;
    private Context mContext;

    public ServiceAdapter(Context context, List<ServiceInfo> list) {
        this.mList = list;
        mContext = context;
        item_max_height = (int) context.getResources().getDimension(R.dimen.item_max_height);
        item_normal_height = (int) context.getResources().getDimension(R.dimen.item_normal_height);
        item_normal_font_size = context.getResources().getDimension(R.dimen.item_normal_font_size);
        item_max_font_size = context.getResources().getDimension(R.dimen.item_max_font_size);
        second_item_normal_font_size = context.getResources().getDimension(R.dimen.second_item_normal_font_size);
        second_item_max_font_size = context.getResources().getDimension(R.dimen.second_item_max_font_size);
        item_normal_alpha = context.getResources().getFraction(R.fraction.item_normal_mask_alpha, 1, 1);
        item_max_alpha = context.getResources().getFraction(R.fraction.item_max_mask_alpha, 1, 1);

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_service, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if (isFirst && position == 0) {
            isFirst = false;
            holder.mark.setAlpha(item_max_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_max_font_size);
            holder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_max_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_max_height));
        } else {
            holder.mark.setAlpha(item_normal_alpha);
            holder.text.setTextSize(TypedValue.COMPLEX_UNIT_PX, item_normal_font_size);
            holder.second_text.setTextSize(TypedValue.COMPLEX_UNIT_PX, second_item_normal_font_size);
            holder.itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, item_normal_height));
        }
        holder.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        holder.imageView.setImageResource(mList.get(position).getPic());
        holder.text.setText(mList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 2){
                    Toast.makeText(mContext, "暂未开放。。。", Toast.LENGTH_LONG).show();
                }else {
                    Intent intent = new Intent(mContext, ItemServiceActivity.class);
                    intent.putExtra("from", position);
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public View mark;
        public TextView text,second_text;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image);
            mark = itemView.findViewById(R.id.mark);
            text = (TextView) itemView.findViewById(R.id.text);
            second_text = (TextView) itemView.findViewById(R.id.sencond_text);
        }

    }


    public void sendNotifyDataSetChanged()
    {
        isFirst = true;
        this.notifyDataSetChanged();
    }
}
