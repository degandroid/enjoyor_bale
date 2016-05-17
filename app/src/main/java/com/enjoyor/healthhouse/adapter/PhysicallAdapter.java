package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.PhsicallLocation;
import com.enjoyor.healthhouse.ui.MapActivity;

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
        holder.setText(R.id.physicall_addr, models.getCompName());
        holder.setText(R.id.physicall_kilo, models.getDistance() + "");
        holder.getView(R.id.gotophysicall).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_map = new Intent(context, MapActivity.class);
                intent_map.putExtra("latitude", models.getMachineLat());
                intent_map.putExtra("longitude", models.getMachineLong());
                context.startActivity(intent_map);
            }
        });
    }
}
