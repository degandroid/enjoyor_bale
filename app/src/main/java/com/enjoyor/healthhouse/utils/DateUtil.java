package com.enjoyor.healthhouse.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jinshaoling on 15/12/9.
 */
public class DateUtil {
    /**
     * 时间格式
     */
    public static final String PATTERN_DEFAULT       = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DEFAULT_SLASH = "yyyy/MM/dd HH:mm:ss";
    public static final String PATTERN_DAY           = "yyyy-MM-dd";
    public static final String PATTERN_DAY_SLASH     = "yyyy/MM/dd";



    /**
     * 判断当前日期是星期几<br>
     * <br>
     * @param pTime 修要判断的时间<br>
     * @return dayForWeek 判断结果<br>
     * @Exception 发生异常<br>
     */

    private static SimpleDateFormat format;
    public static int dayForWeek(long pTime) throws Exception {
        format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(pTime);
        int dayForWeek = 0;
        if(c.get(Calendar.DAY_OF_WEEK) == 1){
            dayForWeek = 7;
        }else{
            dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
        }
        return dayForWeek;
    }

    public static String dayForWeekStr(long pTime) throws Exception{
        int week = dayForWeek(pTime);
        String week_str = "";
        switch (week)
        {
            case 1:
                week_str = "周一";
                break;
            case 2:
                week_str =  "周二";
                break;
            case 3:
                week_str =  "周三";
                break;
            case 4:
                week_str =  "周四";
            break;
            case 5:
                week_str =  "周五";
            break;
            case 6:
                week_str =  "周六";
            break;
            case 7:
                week_str =  "周日";
            break;
        }
        return week_str;
    }

    /**
     * 获取当前时间戳(秒)
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:08:55
     */
    public static final int getCurrentTime(){
        return (int) (System.currentTimeMillis()/1000);
    }



    /**
     * 获取当前时间戳(秒)
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:08:55
     */
    public static final long getTimeSecond(long time){

        return (time/1000);
    }

    /**
     * 获取几天后的time(秒)
     * @param days
     * @return
     */

    public static final long getDaysAfter(int days)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE,days);

        return getTimeSecond(c.getTimeInMillis());
    }

    /**
     * 获取几天后的time(秒)
     * @param days
     * @return
     */

    public static final long getDaysAfter(Long timestamp,int days)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        c.add(Calendar.DATE,days);

        return getTimeSecond(c.getTimeInMillis());
    }

    public static final long getMinuteAfter(Long timestamp,int minute)
    {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timestamp);
        c.add(Calendar.MINUTE,minute);
        return getTimeSecond(c.getTimeInMillis());
    }

    public static final long getMinuteAfter(int minute)
    {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE,minute);
        return getTimeSecond(c.getTimeInMillis());
    }

    /**
     * 将long表示的时间转换为日期字符串<br/>
     * pattern : "yyyy-MM-dd" or "yyyy-MM-dd HH:mm:ss" or ...
     * @param dateTime
     * @param pattern
     * @return
     * @throws ParseException
     * @author xzy
     * @date 2015-1-28 下午2:16:58
     */
    public static final String longToDateString(long dateTime, String pattern){
        Date date = null;
        if (String.valueOf(dateTime).length() == 10) {
            date = new Date(dateTime*1000);
        }else{
            date = new Date(dateTime);
        }
        return dateToString(date, pattern);
    }

    /**
     * 将long表示的时间转换为日期字符串 : yyyy-MM-dd HH:mm:ss
     * @param dateTime
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:30:31
     */
    public static final String longToDateString(long dateTime){
        return longToDateString(dateTime, PATTERN_DEFAULT);
    }

    /**
     * 将日期转成字符串
     * @param date
     * @param pattern
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:55:31
     */
    public static final String dateToString(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    /**
     * 将日期转成字符串 : yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:55:51
     */
    public static final String dateToString(Date date){
        return new SimpleDateFormat(PATTERN_DEFAULT).format(date);
    }

    /**
     * 将字符串转成Date
     * @param dateInString
     * @param pattern
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:36:14
     */
    public static final Date stringToDate(String dateInString, String pattern){
        DateFormat df = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = df.parse(dateInString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将字符串转成Date : yyyy-MM-dd HH:mm:ss
     * @param dateInString
     * @return
     * @author xzy
     * @date 2015-1-28 下午2:41:42
     */
    public static final Date stringToDate(String dateInString){
        return stringToDate(dateInString, PATTERN_DEFAULT);
    }

    /**
     * 获取相隔天数
     * @Title: getDaysBetweenFromTimestamp
     * @param time
     * @return
     * @return int
     * @throws
     * @date 2015-3-2 下午2:26:43
     * @author xiezuoyuan
     */
    public static int getDaysBetweenFromTimestamp(long time){
//        if ((int) (getCurrentTime() - time) <= 0 || time <= 0) {
//            return 0;
//        }
        int days = (int) (Math.abs(getCurrentTime() - time)) / (60 * 60 * 24) ;

//        if(days==0)
//            days = -1;
        return days;
    }

    /**
     * 获取相隔天数
     * @Title: getDaysBetweenFromTimestamp
     * @param time1
     * @param time2
     * @return
     * @return int
     * @throws
     * @date 2015-3-2 下午2:26:43
     * @author xiezuoyuan
     */
    public static int getDaysBetweenFromTimestamp(long time1,long time2){
//        if ((int) (getCurrentTime() - time) <= 0 || time <= 0) {
//            return 0;
//        }
        int days = (int) (Math.abs(time2 - time1)) / (60 * 60 * 24) ;
//        if(days==0)
//            days = -1;
        return days;
    }


    /**
     * 获取相隔小时
     * @Title: getDaysBetweenFromTimestamp
     * @param time
     * @return
     * @return int
     * @throws
     * @date 2015-3-2 下午2:26:43
     * @author xiezuoyuan
     */
    public static int getHoursBetweenFromTimestamp(long time){
//        if ((int) (getCurrentTime() - time) <= 0 || time <= 0) {
//            return 0;
//        }
        int hours = (int) (Math.abs(getCurrentTime() - time)) / (60 * 60 ) ;
//        if(hours==0)
//            hours = -1;
        return hours;
    }


    /**
     * 获取相隔分钏
     * @Title: getDaysBetweenFromTimestamp
     * @param time
     * @return
     * @return int
     * @throws
     * @date 2015-3-2 下午2:26:43
     * @author xiezuoyuan
     */
    public static int getMinuteBetweenFromTimestamp(long time){
//        if ((int) (getCurrentTime() - time) <= 0 || time <= 0) {
//            return 0;
//        }
        int minute = (int) (Math.abs(getCurrentTime() - time)) / (60) ;
//        if(hours==0)
//            hours = -1;
        return minute;
    }


    /**
     * 获取相隔分钏
     * @Title: getDaysBetweenFromTimestamp
     * @param time
     * @return
     * @return int
     * @throws
     * @date 2015-3-2 下午2:26:43
     * @author xiezuoyuan
     */
    public static int getSecondBetweenFromTimestamp(long time){
        if ((int) (getCurrentTime() - time) <= 0 || time <= 0) {
            return 0;
        }
        int second = (int) (Math.abs(getCurrentTime() - time)) ;
//        if(hours==0)
//            hours = -1;
        return second;
    }


    public static String getBetweenFromTimestamp(long time) {
        if (String.valueOf(time).length() != 10)
            time = getTimeSecond(time);

        int between = getDaysBetweenFromTimestamp(time);
        if (between > 0)
            return between + "天";
        between = getHoursBetweenFromTimestamp(time);
        if (between > 0)
            return between + "小时";
        between = getMinuteBetweenFromTimestamp(time);
        if (between > 0)
            return between + "分钟";

        between = getSecondBetweenFromTimestamp(time);
        if (between >= 0)
            return between + "秒";
        else
            return "";


    }




    public static int getDaysLeftFromTimestamp(long time){
        if ((int) (time - getCurrentTime()) <= 0 || time <= 0) {
            return -1;
        }
        return (int) (time - getCurrentTime()) / (60 * 60 * 24) ;
    }

    /**
     * 判断指定时间戳是否是当天
     * @Title: isToday
     * @param time
     * @return
     * @return boolean
     * @throws
     * @date 2015-3-2 下午2:27:35
     * @author xiezuoyuan
     */
    public static boolean isToday(long time){
        Calendar cl = Calendar.getInstance();
        cl.setTimeInMillis(time * 1000);
        if (cl.get(Calendar.DATE) == Calendar.getInstance().get(Calendar.DATE)
                && cl.get(Calendar.MONTH) == Calendar.getInstance().get(Calendar.MONTH)
                && cl.get(Calendar.YEAR) == Calendar.getInstance().get(Calendar.YEAR)) {
            return true;
        }else{
            return false;
        }
    }

}
