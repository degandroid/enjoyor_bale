package com.enjoyor.healthhouse.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.text.Html;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class AppUtil {
    private static Activity currentActivity;

    public static boolean isCurrentThreadUiThread() {
        return Thread.currentThread().getId() == AppInfo.getUiTid();
    }

    public static File getPreferredDataDir() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdCardDir = Environment.getExternalStorageDirectory();
            return sdCardDir;
        }
        return null;
    }

    public static boolean checkPhoneHavEnghStorage(long size) {
        return AppUtil.checkStorageRom(Environment.getDataDirectory(), size);
    }

    public static boolean checkSdcardHavEnghStorage(long size) {
        return AppUtil.checkStorageRom(getPreferredDataDir(), size);
    }

    @SuppressWarnings("deprecation")
    static synchronized boolean checkStorageRom(File file, long size) {
        if (file == null) {
            return false;
        }

        StatFs mStat = new StatFs(file.getAbsolutePath());
        long blockSize = mStat.getBlockSize();
        long avaleCout = mStat.getAvailableBlocks();
        long val = avaleCout * blockSize;
        if (size + 10 * 1024 * 1024 <= val) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @return the currentActivity
     */
    public static Activity getCurrentActivity() {
        return currentActivity;
    }

    /**
     * @param currentActivity the currentActivity to set
     */
    public static void setCurrentActivity(Activity currentActivity) {
        AppUtil.currentActivity = currentActivity;
    }

    public static String getString(int resId) {
        return AppInfo.getContext().getString(resId);
    }

    public static String getString(int resId, Object... formatArgs) {
        return AppInfo.getContext().getString(resId, formatArgs);
    }

    public static byte[] inputStreamToByte(InputStream in) {
        final int BUFFER_SIZE = 2046;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] data = new byte[BUFFER_SIZE];
        int count = -1;
        try {
            while ((count = in.read(data, 0, BUFFER_SIZE)) != -1) {
                outStream.write(data, 0, count);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            data = null;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return outStream.toByteArray();
    }





    public static void showEditorError(TextView editor, String msg) {
        if (editor == null || TextUtils.isEmpty(msg)) {
            return;
        }

        String content = "<font color=#FF0000>" + msg + "</font>";
        editor.setError(Html.fromHtml(content));
        editor.requestFocus();
    }

    public static void showEditorError(TextView editor, int resId) {
        Context context = AppInfo.getContext();
        String msg = context.getResources().getString(resId);

        showEditorError(editor, msg);
    }

    public static boolean patternPhoneNumber(String number) {

        Pattern pattern = Pattern.compile("^((13[0-9])|(14[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(number);
        if (matcher.matches()) {
            return true;
        }

        return false;
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return null;
        }
        return (String) DateFormat.format("yyyy年MM月dd日 HH:mm", date);
    }

    public static String simpleDateFormat(Date date) {
        if (date == null) {
            return null;
        }
        return (String) DateFormat.format("yyyy年MM月", date);
    }

    public static String replaceEnter(String source) {
        if (source == null) {
            return null;
        }
        String tmp = source.replace("\r\n", " ");
        tmp = tmp.replace("\n", " ");
        return tmp;
    }
}
