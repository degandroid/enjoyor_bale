package com.enjoyor.healthhouse.utils;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(String title) {
        showToast(title, Toast.LENGTH_SHORT);
    }

    public static void showToastShort(String title) {
        showToast(title, Toast.LENGTH_SHORT);
    }

    public static void showToast(final String title, int duration) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        if (AppUtil.isCurrentThreadUiThread()) {
            show(title);
        }

    }

    /**
     * Method: show
     *
     * @param title
     */
    private static void show(final String title) {
        if (mToast == null) {
            mToast =
                    Toast.makeText(AppInfo.getContext(), title,
                            Toast.LENGTH_SHORT);
        }
        mToast.setText(title);
        mToast.show();
    }

    private ToastUtil() {

    }

    /**
     * Method: showToast
     *
     * @param
     */
    public static void showToast(int resId) {
        showToast(AppInfo.getContext().getResources().getString(resId));
    }

    public static void cancel() {
        if (mToast != null) {
            //            mToast.cancel();
            mToast = null;
        }
    }
}
