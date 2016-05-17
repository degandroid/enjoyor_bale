package com.enjoyor.healthhouse.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class AppInfo {

    private static Context context;
    public static long uiTid;

    public static int version;
    public static String packageName;

    public static String clientType;

    public static String versionName;


    /**
     * 推广渠道Id，后期推广使用
     */
//	private String channelId;
    public static void init(Context context) {
        AppInfo.setContext(context);
        uiTid = Thread.currentThread().getId();
    }


    public static void initAppInfo(Context context) {
        packageName = context.getPackageName();
        try {
            //获取版本号与版本名称
            PackageInfo packInfo =
                    context.getPackageManager().getPackageInfo(packageName, 0);
            version = packInfo.versionCode;
            versionName = packInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Context getContext() {
        return context;
    }

    public static void setContext(Context context) {
        AppInfo.context = context;
    }

    public static long getUiTid() {
        return uiTid;
    }

    public static int getVersion() {
        return version;
    }

    public static String getVersionName() {
        return versionName;
    }

    public static String getPackageName() {
        return packageName;
    }
}