package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.util.Log;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;

import java.util.List;

/**
 * Created by YuanYuan on 2016/5/13.
 */
public class PhysicallAdapter extends CommAdapter<PhsicallLocation.MachineModelsEntity> {

    public PhysicallAdapter(Context context, List<PhsicallLocation.MachineModelsEntity> datas, int layoutId) {
        super(context, datas, layoutId);
    }

    @Override
    public void convert(ViewHolder holder, final PhsicallLocation.MachineModelsEntity models) {
        if (models.getLogopath() == null) {
            holder.setImageResource(R.id.physicall_item_img, R.mipmap.bale);
        }
        Log.d("wyy============", models.getLogopath());
        holder.setImageURL(R.id.physicall_item_img, UrlInterface.TEXT_URL + models.getLogopath());
        holder.setText(R.id.physicall_addr, models.getAddressName());
        if (models.getDistance()!=null){
            if (models.getDistance() > 1000) {
                int kilo = models.getDistance() / 1000;
                int meter = models.getDistance() % 1000;
                if (meter > 100) {
                    int meters = (models.getDistance() - kilo * 1000) / 100;
                    holder.setText(R.id.physicall_kilo, kilo + "." + meters + "KM");
                } else {
                    holder.setText(R.id.physicall_kilo, kilo + "KM");
                }

            } else {
                holder.setText(R.id.physicall_kilo, models.getDistance() + "米");
            }
        }
    }
}
