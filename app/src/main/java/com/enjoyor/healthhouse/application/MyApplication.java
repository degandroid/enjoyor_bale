package com.enjoyor.healthhouse.application;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;

import com.baidu.mapapi.SDKInitializer;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.custom.CircleBitmapDisplayer;
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

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        instance = this;
        context = this.getApplicationContext();
        AppManagerUtil.getAppManager();
        options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bale)
                .showImageOnFail(R.mipmap.bale)
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
