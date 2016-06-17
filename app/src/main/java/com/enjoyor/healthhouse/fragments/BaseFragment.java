package com.enjoyor.healthhouse.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.ui.LoginActivity;

/**
 * Created by YuanYuan on 2016/4/25.
 */
public class BaseFragment extends Fragment {

    protected Dialog mDialog;
    protected Dialog dialog;

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
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
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
