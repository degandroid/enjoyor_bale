package com.enjoyor.healthhouse.bean;

import java.util.List;

/**
 * Created by YuanYuan on 2016/6/3.
 * 健康趋势实体类
 */
public class TendInfo {


    /**
     * value : {"after":"5","befor":"7.53"}
     * times : 2016-05-17 00:00:00
     */

    private List<BstdcEntity> bstdc;
    /**
     * value : {"bo":"95.74"}
     * times : 2016-05-17 00:00:00
     */

    private List<BotdcEntity> botdc;
    /**
     * value : {"ecg":"89.05"}
     * times : 2016-05-17 00:00:00
     */

    private List<EcgtdcEntity> ecgtdc;
    /**
     * value : {"bmi":"39.01"}
     * times : 2016-05-17 00:00:00
     */

    private List<BmitdcEntity> bmitdc;
    /**
     * value : {"diastolic":"62.49","systolic":"106.57"}
     * times : 2016-05-17 00:00:00
     */

    private List<BptdcEntity> bptdc;

    public List<BstdcEntity> getBstdc() {
        return bstdc;
    }

    public void setBstdc(List<BstdcEntity> bstdc) {
        this.bstdc = bstdc;
    }

    public List<BotdcEntity> getBotdc() {
        return botdc;
    }

    public void setBotdc(List<BotdcEntity> botdc) {
        this.botdc = botdc;
    }

    public List<EcgtdcEntity> getEcgtdc() {
        return ecgtdc;
    }

    public void setEcgtdc(List<EcgtdcEntity> ecgtdc) {
        this.ecgtdc = ecgtdc;
    }

    public List<BmitdcEntity> getBmitdc() {
        return bmitdc;
    }

    public void setBmitdc(List<BmitdcEntity> bmitdc) {
        this.bmitdc = bmitdc;
    }

    public List<BptdcEntity> getBptdc() {
        return bptdc;
    }

    public void setBptdc(List<BptdcEntity> bptdc) {
        this.bptdc = bptdc;
    }

    public static class BstdcEntity {
        /**
         * after : 5
         * befor : 7.53
         */

        private ValueEntity value;
        private String times;

        public ValueEntity getValue() {
            return value;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public static class ValueEntity {
            private String after;
            private String befor;

            public String getAfter() {
                return after;
            }

            public void setAfter(String after) {
                this.after = after;
            }

            public String getBefor() {
                return befor;
            }

            public void setBefor(String befor) {
                this.befor = befor;
            }
        }
    }

    public static class BotdcEntity {
        /**
         * bo : 95.74
         */

        private ValueEntity value;
        private String times;

        public ValueEntity getValue() {
            return value;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public static class ValueEntity {
            private String bo;

            public String getBo() {
                return bo;
            }

            public void setBo(String bo) {
                this.bo = bo;
            }
        }
    }

    public static class EcgtdcEntity {
        /**
         * ecg : 89.05
         */

        private ValueEntity value;
        private String times;

        public ValueEntity getValue() {
            return value;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public static class ValueEntity {
            private String ecg;

            public String getEcg() {
                return ecg;
            }

            public void setEcg(String ecg) {
                this.ecg = ecg;
            }
        }
    }

    public static class BmitdcEntity {
        /**
         * bmi : 39.01
         */

        private ValueEntity value;
        private String times;

        public ValueEntity getValue() {
            return value;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public static class ValueEntity {
            private String bmi;

            public String getBmi() {
                return bmi;
            }

            public void setBmi(String bmi) {
                this.bmi = bmi;
            }
        }
    }

    public static class BptdcEntity {
        /**
         * diastolic : 62.49
         * systolic : 106.57
         */

        private ValueEntity value;
        private String times;

        public ValueEntity getValue() {
            return value;
        }

        public void setValue(ValueEntity value) {
            this.value = value;
        }

        public String getTimes() {
            return times;
        }

        public void setTimes(String times) {
            this.times = times;
        }

        public static class ValueEntity {
            private String diastolic;
            private String systolic;

            public String getDiastolic() {
                return diastolic;
            }

            public void setDiastolic(String diastolic) {
                this.diastolic = diastolic;
            }

            public String getSystolic() {
                return systolic;
            }

            public void setSystolic(String systolic) {
                this.systolic = systolic;
            }
        }
    }
}
