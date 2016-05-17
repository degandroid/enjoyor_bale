package com.enjoyor.healthhouse.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by YuanYuan on 2016/4/27.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        //网络状态发生变化时
        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            this.testConnectivityManager(context);
        }
    }

    /**
     * 测试网络
     *
     * @param context
     */
    private void testConnectivityManager(Context context) {
        //获取系统连接服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络连接情况
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

    }

    /**
     * 判断是否联网
     *
     * @param
     * @return
     */
    public static boolean isOnline(Context context) {
        //获取系统连接服务
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取网络连接情况
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                Toast.makeText(context, "WIFI网络", Toast.LENGTH_LONG).show();
                return true;
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                Toast.makeText(context, "GPRS网络", Toast.LENGTH_LONG).show();
                return false;
            }
        } else {
            Toast.makeText(context, "无网络", Toast.LENGTH_LONG).show();
        }
        return false;
    }


}
