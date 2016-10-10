package com.enjoyor.healthhouse.application;


import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.db.DBHelper;
import com.enjoyor.healthhouse.utils.AppManagerUtil;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.hyphenate.easeui.controller.EaseUI;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.util.Iterator;
import java.util.List;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class MyApplication extends MultiDexApplication {
    private static MyApplication instance;
    private DBHelper mDBHelper;
    private static Context context;
    public static boolean refrash = false;
    public static DisplayImageOptions options;
    public static DisplayImageOptions option;

    public static String userName;
    public static Double jindu;
    public static Double weidu;

    public static Double getJindu() {
        return jindu;
    }

    public static void setJindu(Double jindu) {
        MyApplication.jindu = jindu;
    }

    public static Double getWeidu() {
        return weidu;
    }

    public static void setWeidu(Double weidu) {
        MyApplication.weidu = weidu;
    }

    public static String getUserName() {
        return userName;
    }

    public static void setUserName(String userName) {
        MyApplication.userName = userName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        context = this.getApplicationContext();

        AppManagerUtil.getAppManager();
        SDKInitializer.initialize(context);
        ShareSDK.initSDK(context);
        //加载图片imageloader属性
        options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.bale)
                .showImageForEmptyUri(R.mipmap.jiazaishibai)
                .showImageOnFail(R.mipmap.jiazaishibai)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        //加载头像Imageloader属性
        option = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.touxiang)
                .showImageForEmptyUri(R.mipmap.touxiang)
                .showImageOnFail(R.mipmap.touxiang)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .displayer(new RoundedBitmapDisplayer(20))
                .build();
        //imageloader配置参数
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);
        ImageLoader.getInstance().init(configuration);


        SetEM();

    }

    private void SetEM() {
                  /*------------------------环信聊天--------------------*/
        EMOptions emOptions = new EMOptions();
        // 默认添加好友时，是不需要验证的，改成需要验证
        emOptions.setAcceptInvitationAlways(false);
                /*有其他第三方的情况下*/
        int pid = android.os.Process.myPid();
        String processAppName = getAppName(pid);
        // 如果APP启用了远程的service，此application:onCreate会被调用2次
        // 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
        // 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process name就立即返回

        if (processAppName == null || !processAppName.equalsIgnoreCase(context.getPackageName())) {
            Log.i("EM", "enter the service process!");

            // 则此application::onCreate 是被service 调用的，直接返回
            return;
        }
        //初始化
        EMClient.getInstance().init(context, emOptions);
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(true);
        EaseUI.getInstance().init(context, emOptions);
        /*----------------------------------------------------*/
    }

    public static Context getContext() {
        return context;
    }

    public DBHelper getDBHelper() {
        if (mDBHelper == null) {
            mDBHelper = new DBHelper(this);
        }
        return mDBHelper;
    }

    public static MyApplication getInstance() {
        return instance;
    }

    private String getAppName(int pID) {
        String processName = null;
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        List l = am.getRunningAppProcesses();
        Iterator i = l.iterator();
        PackageManager pm = this.getPackageManager();
        while (i.hasNext()) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i.next());
            try {
                if (info.pid == pID) {
                    processName = info.processName;
                    return processName;
                }
            } catch (Exception e) {
                // Log.d("Process", "Error>> :"+ e.toString());
            }
        }
        return processName;
    }

//    public int getDBVersion() {
//        if (getMetData() != null) {
//            Log.i("zxw", "dbVersion:" + getMetData().metaData.getInt("dbVersion"));
//            return Integer.valueOf(getMetData().metaData.getInt("dbVersion"));
//        }
//        return 1;
//    }
//    public ApplicationInfo getMetData() {
//        ApplicationInfo ai = null;
//        try {
//            PackageManager pm = this.getPackageManager();
//            ai = pm.getApplicationInfo(this.getPackageName(), PackageManager.GET_META_DATA);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ai;
//    }
}
