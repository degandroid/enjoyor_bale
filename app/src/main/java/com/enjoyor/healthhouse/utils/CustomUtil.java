package com.enjoyor.healthhouse.utils;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

/**
 * Created by Administrator on 2016/5/19.
 */
public class CustomUtil {
    public static Dialog dialog;

    public static boolean saveHealthInfo(final Context context,String url,RequestParams params) {
        AsyncHttpUtil.post(UrlInterface.TEXT_URL + url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Log.i("zxw","------------onSuccess--------");
                    Toast.makeText(context,"数据保存成功",Toast.LENGTH_LONG).show();
//                    dialog(context, "数据保存成功", "取消", "确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            disappear();
//                        }
//                    }, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            disappear();
//                        }
//                    });
                } else {
                    Log.i("zxw", "-----------------else----");
                    Toast.makeText(context,"数据保存失败",Toast.LENGTH_LONG).show();
//                    dialog(context, "数据保存失败，请确定是否有遗漏的选项", "取消", "确定", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            disappear();
//                        }
//                    }, new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            disappear();
//                        }
//                    });
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                Log.i("zxw","----------------onFailure----");
            }
        });
        Log.i("zxw", "-----------------CustomUtil----");
        return true;
    }








    public static void dialog(Context context,String body,String left_info,String right_info,View.OnClickListener left,View.OnClickListener right){
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
    public static void disappear(){
        dialog.cancel();
    }
}
