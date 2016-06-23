package com.enjoyor.healthhouse.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.db.DBHelper;
import com.enjoyor.healthhouse.utils.AppManagerUtil;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import cn.sharesdk.framework.ShareSDK;


/**
 * Created by YuanYuan on 2016/4/25.
 */
public class MyApplication extends Application {
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
