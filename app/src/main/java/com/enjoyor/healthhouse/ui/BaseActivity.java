package com.enjoyor.healthhouse.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean._FakeX509TrustManager;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.utils.AppManagerUtil;
import com.enjoyor.healthhouse.utils.ScreenUtil;
import com.enjoyor.healthhouse.utils.ToastUtil;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class BaseActivity extends AppCompatActivity {
    _FakeX509TrustManager fakeX509TrustManager;
    private View rootView;
    //进度框
    protected Dialog mDialog;
    // 屏幕方向
    private int mOrientation;
    private static long trigleCancel;
    private Dialog dialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppManagerUtil.addActivity(BaseActivity.this);
        fakeX509TrustManager.allowAllSSL();
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
    }

    /**
     * 隐藏软键盘的方法
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();
        if (null != currentFocus && imm.isActive()) {
            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 显示软键盘的方法
     */
    public void showKeyBoard(View focus) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = getCurrentFocus();

        if (null != currentFocus) {
            imm.showSoftInput(focus, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * /按后退键时
     */
    @Override
    public void onBackPressed() {

    }

    /**
     * 按下按键时触发事件的方法
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 按了后退键
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 退出Activity时动画
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 网络错误触发的方法
     *
     * @param content
     */
    public void showErrorDialog(String content) {
        if (TextUtils.isEmpty(content)) {
            new AlertDialog.Builder(this).setTitle(R.string.dialog_error)
                    .setMessage(R.string.error_network)
                    .setPositiveButton(R.string.dialog_close, null).show();
        } else {
            new AlertDialog.Builder(this).setTitle(R.string.dialog_error)
                    .setMessage(content)
                    .setPositiveButton(R.string.dialog_close, null).show();
        }
    }


    /**
     * 双击退出程序的方法
     */
    protected void toastExtrance() {
        final long uptimeMillis = SystemClock.uptimeMillis();
        if (uptimeMillis - trigleCancel > 2000) {
            trigleCancel = uptimeMillis;
            ToastUtil.showToast(getString(R.string.note_exit));
        } else {
            onExit();
        }
    }

    private void onExit() {
        ToastUtil.cancel();
        System.exit(0);
    }

    /**
     * 获取屏幕的方向
     */
    public int getOrientation() {
        return mOrientation;
    }

    /**
     * 沉浸式开发方法
     */
    protected void setImmerseLayout(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            int statusBarHeight = ScreenUtil.getStatusBarHeight(this.getBaseContext());
            view.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    protected void setImmerseLayout() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


    /**
     * 结束当前Activity
     */
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    /**
     * 设置导航栏文字
     *
     * @param view
     * @param id
     */
    public void setTitleBar(TextView view, int id) {
        view.setText(id);
    }

    public void dialog(Context context, String body) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_custom);
        TextView tv_body = (TextView) dialog.findViewById(R.id.tv_body);
        tv_body.setText(body);
        LinearLayout ll_button = (LinearLayout) dialog.findViewById(R.id.ll_button);
        ll_button.setVisibility(View.GONE);
    }

    public void dialog(Context context, String body, String left_info, String right_info, View.OnClickListener left, View.OnClickListener right) {
        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        dialog.getWindow().setContentView(R.layout.dialog_custom);

        TextView tv_body = (TextView) dialog.findViewById(R.id.tv_body);
        tv_body.setText(body);
        TextView tv_left = (TextView) dialog.findViewById(R.id.tv_left);
        tv_left.setOnClickListener(left);
        tv_left.setText(left_info);
        TextView tv_right = (TextView) dialog.findViewById(R.id.tv_right);
        tv_right.setOnClickListener(right);
        tv_right.setText(right_info);
    }

    public void disappear() {
        dialog.cancel();
    }

    public boolean isLogin(Context context) {
        if (BaseDate.getSessionId(context) == null) {
            dialog(context, "亲,您还未登录，是否立即登录", "取消", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disappear();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(BaseActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
            return false;
        }
        return true;
    }

    //动态加载数据对话框的方法
    public static Dialog createLoadingDialog(Context context, String msg) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        // main.xml中的ImageView
        ImageView spaceshipImage = (ImageView) v.findViewById(R.id.img);
        TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
        // 加载动画
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(
                context, R.anim.loading_animation);
        // 使用ImageView显示动画
        spaceshipImage.startAnimation(hyperspaceJumpAnimation);
        tipTextView.setText(msg);// 设置加载信息

        Dialog loadingDialog = new Dialog(context, R.style.loading_dialog);// 创建自定义样式dialog

        loadingDialog.setCancelable(true);// 不可以用“返回键”取消
        loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT));// 设置布局
        return loadingDialog;
    }
}
