package com.enjoyor.healthhouse.utils;

import com.enjoyor.healthhouse.bean.ItemMyRecord;
import com.enjoyor.healthhouse.bean.MyRecordInfo;
import com.enjoyor.healthhouse.common.Constant;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/6/3.
 */
public class RecordUtils {

    public static List<ItemMyRecord> _list = new ArrayList<>();
    public static List<ItemMyRecord> _list_ab = new ArrayList<>();

    public static List<ItemMyRecord> getRecordList(MyRecordInfo info){
        _list.clear();
        _list.add(new ItemMyRecord("身高(cm)", info.getHeight(), -1d, -1d));
        _list.add(new ItemMyRecord("体重(kg)",info.getWeight(),-1d, -1d));
        _list.add(new ItemMyRecord("腰围(cm)",info.getWaistLine(),-1d, -1d));
        _list.add(new ItemMyRecord("臀围(cm)",info.getHipLine(),-1d, -1d));
        _list.add(new ItemMyRecord("腰臀比(%)",info.getWhr(),85d,95d));

        _list.add(new ItemMyRecord("舒张压(mmHg)",info.getDiastolicPressure(),60d,90d));
        _list.add(new ItemMyRecord("收缩压(mmHg)",info.getSystolicPressure(),90d,140d));
        _list.add(new ItemMyRecord("血氧(%)",info.getBo(),95d,99d));
        _list.add(new ItemMyRecord("脉搏(次/分)",info.getPulse(),60d,100d));


        _list.add(new ItemMyRecord("血糖", info.getBloodSugar(), Constant.getBloodSugarMin(info.getBloodSugarType()), Constant.getBloodSugarMax(info.getBloodSugarType())));
                _list.add(new ItemMyRecord("尿酸(umol/L)", info.getUs(), 0.2d, 0.42d));
        _list.add(new ItemMyRecord("总胆固醇(mmol/L)",info.getChol(),0d,5.2d));

        _list.add(new ItemMyRecord("体温(℃)",info.getTemperature(),36d,37d));
        _list.add(new ItemMyRecord("心率(次/分钟)",info.getEcg(),60d,100d));

        _list.add(new ItemMyRecord("脂肪量(kg)",info.getFat(),6.7d,13.5d));
        _list.add(new ItemMyRecord("非脂肪量(kg)",info.getExceptFat(),-1d,-1d));
        _list.add(new ItemMyRecord("体脂比(%)",info.getFatRate(),-1d,-1d));
        _list.add(new ItemMyRecord("含水量(kg)",info.getWater(),-1d,-1d));
        _list.add(new ItemMyRecord("体水比(%)",info.getWaterRate(),-1d,-1d));
        _list.add(new ItemMyRecord("无机盐量(kg)",info.getMinerals(),-1d,-1d));
        _list.add(new ItemMyRecord("蛋白质量(kg)",info.getProtein(),-1d,-1d));
        _list.add(new ItemMyRecord("细胞内液(kg)",info.getFic(),-1d,-1d));

        _list.add(new ItemMyRecord("细胞外液(%)",info.getFoc(),-1d,-1d));
        _list.add(new ItemMyRecord("肌肉量(%)",info.getMuscle(),44d,52.4d));
        _list.add(new ItemMyRecord("骨骼量(%)",info.getBmc(),-1d,-1d));
        _list.add(new ItemMyRecord("肌肉率(%)",info.getMuscleRate(),-1d,-1d));
        _list.add(new ItemMyRecord("基础代谢(%)",info.getBasicMetaBolism(),1483d,-1d));
        _list.add(new ItemMyRecord("内脏脂肪等级(%)",info.getViscera(),1d,9d));
        return _list;
    }


    public static List<ItemMyRecord> getAbnormalList(MyRecordInfo info){
        _list_ab.clear();
       if(info.getUnusuals()!=null){
           List<MyRecordInfo.HealthAdviceModel> unusuals = info.getUnusuals();
           for(int i=0;i<unusuals.size();i++){
               String name = unusuals.get(i).getDisease();
               if(name.equals("hypertension")){
                   _list_ab.add(new ItemMyRecord("高血压",info.getSystolicPressure(),90d,140d));
               }else if(name.equals("diabetesmellitus")){
                   _list_ab.add(new ItemMyRecord("糖尿病", info.getBloodSugar(), Constant.getBloodSugarMin(info.getBloodSugarType()), Constant.getBloodSugarMax(info.getBloodSugarType())));
               }
               else if(name.equals("bodybmiscale")){
                   _list_ab.add(new ItemMyRecord("肥胖",info.getFat(),6.7d,13.5d));
               }
               else if(name.equals("SpO2")){
                   _list_ab.add(new ItemMyRecord("血氧饱和度",info.getBo(),95d,99d));
               }
               else if(name.equals("electorcardio")){
                   _list_ab.add(new ItemMyRecord("心电",info.getEcg(),60d,100d));
               }
               else if(name.equals("uricAcid")){
                   _list_ab.add(new ItemMyRecord("尿酸",info.getUs(),0.2d,0.42d));
               }
               else if(name.equals("dyslipidemia")){
                   _list_ab.add(new ItemMyRecord("血脂异常",info.getChol(),0d,0.52d));
               }
           }

       }

        return _list_ab;
    }

}
