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
import android.view.View;
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
    protected ProgressDialog progress() {
        return progress("", "", null, false);
    }

    protected ProgressDialog progress(DialogInterface.OnCancelListener listener) {
        return progress("", "", listener, true);
    }

    protected ProgressDialog progress(String pTitle, String pMessage,
                                      DialogInterface.OnCancelListener pCancelClickListener, boolean outsideCancel) {
        if (getActivity().isFinishing())
            return null;
        ProgressDialog mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.show();
        mProgressDialog.setCanceledOnTouchOutside(outsideCancel);
        mProgressDialog.setCancelable(outsideCancel);
        mProgressDialog.setContentView(View.inflate(getActivity(),
                R.layout.dialog_process, null));
        mProgressDialog.getWindow().setBackgroundDrawable(new ColorDrawable());
        mProgressDialog.setOnCancelListener(pCancelClickListener);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog,
                                 int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss();
                    return true;
                } else {
                    return false;
                }
            }
        });
        mDialog = mProgressDialog;
        return mProgressDialog;
    }


    protected void cancel() {
        if (mDialog != null)
            mDialog.cancel();
    }
    public void dialog(Context context,String body,String left_info,String right_info,View.OnClickListener left,View.OnClickListener right){
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
    public void disappear(){
        dialog.cancel();
    }
    public boolean isLogin(Context context)
    {
        if(BaseDate.getSessionId(context)==null)
        {
            dialog(context, "亲,您还未登录，是否立即登录", "取消", "确定", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    disappear();
                }
            }, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(),LoginActivity.class);
                    startActivity(intent);
                }
            });
            return false;
        }
        return true;
    }
}
