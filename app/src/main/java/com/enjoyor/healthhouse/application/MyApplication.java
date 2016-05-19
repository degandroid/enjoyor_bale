package com.enjoyor.healthhouse.application;

import android.app.Application;
import android.content.Context;

import com.baidu.mapapi.SDKInitializer;
import com.enjoyor.healthhouse.db.DBHelper;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class MyApplication extends Application {
    private static MyApplication instance;
    private DBHelper mDBHelper;
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
        instance = this;
        context = this.getApplicationContext();
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
