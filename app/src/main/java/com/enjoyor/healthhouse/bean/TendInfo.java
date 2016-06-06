package com.enjoyor.healthhouse.bean;

/**
 * Created by YuanYuan on 2016/6/3.
 * 健康趋势实体类
 */
public class TendInfo {
    /**
     * times : 2016-06-01 00:00:00
     * value : {"systolic":"0","diastolic":"0"}
     */

    private String times;
    /**
     * systolic : 0
     * diastolic : 0
     */

    private ValueEntity value;

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public ValueEntity getValue() {
        return value;
    }

    public void setValue(ValueEntity value) {
        this.value = value;
    }

    public static class ValueEntity {
        private String systolic;
        private String diastolic;

        public String getSystolic() {
            return systolic;
        }

        public void setSystolic(String systolic) {
            this.systolic = systolic;
        }

        public String getDiastolic() {
            return diastolic;
        }

        public void setDiastolic(String diastolic) {
            this.diastolic = diastolic;
        }
    }
//
//    /**
//     * times : 2016-05-29 00:00:00
//     * value : {"ecg":"0"}
//     */
//
//    private List<EcgtdcEntity> ecgtdc;
//    /**
//     * times : 2016-05-29 00:00:00
//     * value : {"bo":"0"}
//     */
//
//    private List<BotdcEntity> botdc;
//    /**
//     * times : 2016-05-29 00:00:00
//     * value : {"bmi":"0"}
//     */
//
//    private List<BmitdcEntity> bmitdc;
//    /**
//     * times : 2016-05-29 00:00:00
//     * value : {"systolic":"0","diastolic":"0"}
//     */
//
//    private List<BptdcEntity> bptdc;
//    /**
//     * times : 2016-05-29 00:00:00
//     * value : {"after":"0","befor":"0"}
//     */
//
//    private List<BstdcEntity> bstdc;
//
//    public List<EcgtdcEntity> getEcgtdc() {
//        return ecgtdc;
//    }
//
//    public void setEcgtdc(List<EcgtdcEntity> ecgtdc) {
//        this.ecgtdc = ecgtdc;
//    }
//
//    public List<BotdcEntity> getBotdc() {
//        return botdc;
//    }
//
//    public void setBotdc(List<BotdcEntity> botdc) {
//        this.botdc = botdc;
//    }
//
//    public List<BmitdcEntity> getBmitdc() {
//        return bmitdc;
//    }
//
//    public void setBmitdc(List<BmitdcEntity> bmitdc) {
//        this.bmitdc = bmitdc;
//    }
//
//    public List<BptdcEntity> getBptdc() {
//        return bptdc;
//    }
//
//    public void setBptdc(List<BptdcEntity> bptdc) {
//        this.bptdc = bptdc;
//    }
//
//    public List<BstdcEntity> getBstdc() {
//        return bstdc;
//    }
//
//    public void setBstdc(List<BstdcEntity> bstdc) {
//        this.bstdc = bstdc;
//    }
//
//    public static class EcgtdcEntity {
//        private String times;
//        /**
//         * ecg : 0
//         */
//
//        private ValueEntity value;
//
//        public String getTimes() {
//            return times;
//        }
//
//        public void setTimes(String times) {
//            this.times = times;
//        }
//
//        public ValueEntity getValue() {
//            return value;
//        }
//
//        public void setValue(ValueEntity value) {
//            this.value = value;
//        }
//
//        public static class ValueEntity {
//            private String ecg;
//
//            public String getEcg() {
//                return ecg;
//            }
//
//            public void setEcg(String ecg) {
//                this.ecg = ecg;
//            }
//        }
//    }
//
//    public static class BotdcEntity {
//        private String times;
//        /**
//         * bo : 0
//         */
//
//        private ValueEntity value;
//
//        public String getTimes() {
//            return times;
//        }
//
//        public void setTimes(String times) {
//            this.times = times;
//        }
//
//        public ValueEntity getValue() {
//            return value;
//        }
//
//        public void setValue(ValueEntity value) {
//            this.value = value;
//        }
//
//        public static class ValueEntity {
//            private String bo;
//
//            public String getBo() {
//                return bo;
//            }
//
//            public void setBo(String bo) {
//                this.bo = bo;
//            }
//        }
//    }
//
//    public static class BmitdcEntity {
//        private String times;
//        /**
//         * bmi : 0
//         */
//
//        private ValueEntity value;
//
//        public String getTimes() {
//            return times;
//        }
//
//        public void setTimes(String times) {
//            this.times = times;
//        }
//
//        public ValueEntity getValue() {
//            return value;
//        }
//
//        public void setValue(ValueEntity value) {
//            this.value = value;
//        }
//
//        public static class ValueEntity {
//            private String bmi;
//
//            public String getBmi() {
//                return bmi;
//            }
//
//            public void setBmi(String bmi) {
//                this.bmi = bmi;
//            }
//        }
//    }
//
//    public static class BptdcEntity {
//        private String times;
//        /**
//         * systolic : 0
//         * diastolic : 0
//         */
//
//        private ValueEntity value;
//
//        public String getTimes() {
//            return times;
//        }
//
//        public void setTimes(String times) {
//            this.times = times;
//        }
//
//        public ValueEntity getValue() {
//            return value;
//        }
//
//        public void setValue(ValueEntity value) {
//            this.value = value;
//        }
//
//        public static class ValueEntity {
//            private String systolic;
//            private String diastolic;
//
//            public String getSystolic() {
//                return systolic;
//            }
//
//            public void setSystolic(String systolic) {
//                this.systolic = systolic;
//            }
//
//            public String getDiastolic() {
//                return diastolic;
//            }
//
//            public void setDiastolic(String diastolic) {
//                this.diastolic = diastolic;
//            }
//        }
//    }
//
//    public static class BstdcEntity {
//        private String times;
//        /**
//         * after : 0
//         * befor : 0
//         */
//
//        private ValueEntity value;
//
//        public String getTimes() {
//            return times;
//        }
//
//        public void setTimes(String times) {
//            this.times = times;
//        }
//
//        public ValueEntity getValue() {
//            return value;
//        }
//
//        public void setValue(ValueEntity value) {
//            this.value = value;
//        }
//
//        public static class ValueEntity {
//            private String after;
//            private String befor;
//
//            public String getAfter() {
//                return after;
//            }
//
//            public void setAfter(String after) {
//                this.after = after;
//            }
//
//            public String getBefor() {
//                return befor;
//            }
//
//            public void setBefor(String befor) {
//                this.befor = befor;
//            }
//        }
//    }

}
