package com.enjoyor.healthhouse.common;

/**
 * Created by Administrator on 2016/5/13.
 */
public class Constant {

    public static Long accountId;
    public static final String SP_TOKEN = "SP_TOKEN";

    public static final int FROM_XUEYA = 11;
    public static final int FROM_SHENGAO = 12;
    public static final int FROM_XUETANG = 13;
    public static final int FROM_XUEYANG = 14;
    public static final int FROM_YAOWEI = 21;
    public static final int FROM_TIZHONG = 22;
    public static final int FROM_TIWEN = 23;
    public static final int FROM_XINDIAN = 24;
    public static final int FROM_BMI = 33;

    public static final int TYPE_TIJIAN = 1;
    public static final int TYPE_ZICE = 2;
    public static final int TYPE_SUISHOUJI = 3;

    //类型：1:空腹，4：随机血糖，5早餐后，6午餐前，7午餐后，8晚餐前，9晚餐后，10睡前
    public static final int TYPE_KONGFU = 1;
    public static final int TYPE_SUIJI = 4;
    public static final int TYPE_ZAOCANHOU = 5;
    public static final int TYPE_WUCANQIAN = 6;
    public static final int TYPE_WUCANHOU = 7;
    public static final int TYPE_WANCANQIAN = 8;
    public static final int TYPE_WANCANHOU = 9;
    public static final int TYPE_SHUIQIAN = 10;

    /**
     * 收缩压-高压-理想最小值
     */
    public static final int systolicPressure_BEST_MIN=90;
    /**
     * 收缩压-高压-理想最大值
     */
    public static final int systolicPressure_BEST_MAX=140;
    /**
     * 舒张压-低压-理想最小值
     */
    public static final int diastolicPressure_BEST_MIN=60;
    /**
     * 舒张压-低压-理想最大值
     */
    public static final int diastolicPressure_BEST_MAX=90;
    /**
     * 心率最佳最小参考值
     */
    public static final double ECG_BEST_MIN=60;
    /**
     * 心率最佳最大参考值
     */
    public static final double ECG_BEST_MAX=100;
    /**
     * 血氧最小参考值
     */
    public static final double BO_BEST_MIN=95;
    /**
     * 血氧最大参考值
     */
    public static final double BO_BEST_MAX=99;
    /**
     * 脉搏最小参考值
     */
    public static final int PULSE_BEST_MIN=60;
    /**
     * 脉搏最大参考值
     */
    public static final int PULSE_BEST_MAX=100;
    /**
     * 体脂率最小参考值
     */
    public static final double FATRATE_BEST_MIN=11;
    /**
     * 体脂率最大参考值
     */
    public static final double FATRATE_BEST_MAX=22;
    /**
     * 体脂肪量最小参考值
     */
    public static final double FAT_BEST_MIN=6.7;
    /**
     * 体脂量最大参考值
     */
    public static final double FAT_BEST_MAX=13.5;
    /**
     * 肌肉量最小参考值
     */
    public static final double MUSCLE_BEST_MIN=44;
    /**
     * 肌肉量最大参考值
     */
    public static final double MUSCLE_BEST_MAX=52.4;
    /**
     * 基础代谢最佳参考值
     */
    public static final double BasicMetaBolism_BEST=1483;
    /**
     * 内脏脂肪等级最小参考值
     */
    public static final double viscera_BEST_MIN=1;
    /**
     * 内脏脂肪等级最大参考值
     */
    public static final double viscera_BEST_MAX=9;
    /**
     * 体温最小参考值
     */
    public static final double temperature_BEST_MIN=36.0;
    /**
     * 体温最大参考值
     */
    public static final double temperature_BEST_MAX=37.0;
    /**
     * 尿酸最大参考值
     */
    public static final double US_BEST_MAX=0.42;
    /**
     * 尿酸最小参考值
     */
    public static final double US_BEST_MIN=0.20;
    /**
     * 胆固醇最小参考值
     */
    public static final double CHOL_BEST_MIN=0;
    /**
     * 胆固醇最大参考值
     */
    public static final double CHOL_BEST_MAX=5.2;
    /**
     * 臀围比最大参考值
     */
    public static final double WHR_BEST_MAX=95;
    /**
     * 臀围比最小参考值
     */
    public static final double WHR_BEST_MIN=85;
    /**
     * 体重指数最小参考值
     */
    public static final double BMI_BEST_MIN=18.5;
    /**
     * 体重指数最大参考值
     */
    public static final double BMI_BEST_MAX=23.9;
    /**
     * 体水分率最小参考值
     */
    public static final double WATERRATE_BEST_MIN=55;
    /**
     * 体水分率最大参考值
     */
    public static final double WATERRATE_BEST_MAX=65;
    /**
     * 根据类型获取血糖最小参考值
     * @param type
     * @return
     */
    public static double getBloodSugarMin(Integer type){
        double min=0;
        if(type!=null){
            switch (type.intValue()) {
                case 1://空腹血糖
                    min=4.4;
                    break;
                case 2://餐后血糖
                    min=4.4;
                    break;
                case 3://尿酸血糖
                    min=4.4;
                    break;

                case 4://随机血糖
                    min=4.4;
                    break;
                case 5://5早餐后
                    min=4.4;
                    break;
                case 6://6午餐前
                    min=4.4;
                    break;
                case 7://7午餐后
                    min=4.4;
                    break;
                case 8://8晚餐前
                    min=4.4;
                    break;
                case 9://9晚餐后
                    min=4.4;
                    break;
                case 10://10睡前
                    min=4.4;
                    break;
                default:
                    min=4.4;
                    break;
            }
        }
        return min;
    }
    /**
     * 根据类型获取血糖最大参考值
     * @param type
     * @return
     */
    public static double getBloodSugarMax(Integer type){
        double min=0;
        if(type!=null){
            switch (type) {
                case 1://空腹血糖
                    min=7.0;
                    break;
                case 2://餐后血糖
                    min=10.0;
                    break;
                case 3://尿酸血糖
                    min=10.0;
                    break;
                case 4://随机血糖
                    min=10.0;
                    break;
                case 5://5早餐后
                    min=10.0;
                    break;
                case 6://6午餐前
                    min=10.0;
                    break;
                case 7://7午餐后
                    min=10.0;
                    break;
                case 8://8晚餐前
                    min=10.0;
                    break;
                case 9://9晚餐后
                    min=10.0;
                    break;
                case 10://10睡前
                    min=10.0;
                    break;
                default:
                    min=7.0;
                    break;
            }
        }
        return min;
    }

    public static String getBloodSugarType(Integer type){
        String min=null;
        if(type!=null){
            switch (type) {
                case 1://空腹血糖
                    min="空腹血糖";
                    break;
                case 2://餐后血糖
                    min="餐后血糖";
                    break;
                case 3://尿酸血糖
                    min="尿酸血糖";
                    break;
                case 4://随机血糖
                    min="随机血糖";
                    break;
                case 5://5早餐后
                    min="早餐后";
                    break;
                case 6://6午餐前
                    min="午餐前";
                    break;
                case 7://7午餐后
                    min="午餐后";
                    break;
                case 8://8晚餐前
                    min="晚餐前";
                    break;
                case 9://9晚餐后
                    min="晚餐后";
                    break;
                case 10://10睡前
                    min="睡前";
                    break;
                default:
                    min="空腹血糖";
                    break;
            }
        }
        return min;
    }




}
