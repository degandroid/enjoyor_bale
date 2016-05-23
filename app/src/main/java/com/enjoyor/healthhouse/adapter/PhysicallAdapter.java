package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.ui.MapActivity;
import com.enjoyor.healthhouse.url.UrlInterface;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class PhysicallAdapter extends CommAdapter<PhsicallLocation.MachineModelsEntity> {
    Context context;

    public PhysicallAdapter(Context context, List<PhsicallLocation.MachineModelsEntity> datas, int layoutId) {
        super(context, datas, layoutId);
        this.context = context;
    }

    @Override
    public void convert(ViewHolder holder, final PhsicallLocation.MachineModelsEntity models) {
        if (models.getLogopath() == null) {
            holder.setImageResource(R.id.physicall_item_img, R.mipmap.bale);
        }
        Log.d("wyy============", models.getLogopath());
        holder.setImageURL(R.id.physicall_item_img, UrlInterface.TEXT_URL + models.getLogopath());
        holder.setText(R.id.physicall_addr, models.getAddressName());
        holder.setText(R.id.physicall_kilo, models.getDistance() + "");
        holder.getView(R.id.gotophysicall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_map = new Intent(context, MapActivity.class);
                intent_map.putExtra("latitude", models.getMachineLat()+"");
                intent_map.putExtra("longitude", models.getMachineLong()+"");
                Log.d("wyy---", models.getMachineLat());
                Log.d("wyy===",models.getMachineLong());
                context.startActivity(intent_map);
            }
        });
    }
}
