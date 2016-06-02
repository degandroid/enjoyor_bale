package com.enjoyor.healthhouse.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.ui.DataActivity;
import com.enjoyor.healthhouse.ui.InfoActivity;
import com.enjoyor.healthhouse.ui.LoginActivity;
import com.enjoyor.healthhouse.ui.ModifyPwdActivity;
import com.enjoyor.healthhouse.ui.MyPhoneActivity;
import com.enjoyor.healthhouse.ui.RegistActivity;
import com.enjoyor.healthhouse.ui.SettingActivity;
import com.enjoyor.healthhouse.ui.SuggestActivity;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/5/3.
 * 个人中心
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    View view;
    @Bind(R.id.mine_fg_logo)
    ImageView mine_fg_logo;
    @Bind(R.id.mine_fg_login)
    TextView mine_fg_login;
    @Bind(R.id.mine_fg_regist)
    TextView mine_fg_regist;
    @Bind(R.id.mine_fg_info)
    RelativeLayout mine_fg_info;
    @Bind(R.id.mine_fg_book)
    RelativeLayout mine_fg_book;
    @Bind(R.id.mine_fg_phone)
    RelativeLayout mine_fg_phone;
    @Bind(R.id.mine_fg_pwd)
    RelativeLayout mine_fg_pwd;
    @Bind(R.id.mine_fg_setting)
    RelativeLayout mine_fg_setting;
    @Bind(R.id.mine_fg_introduct)
    RelativeLayout mine_fg_introduct;
    @Bind(R.id.mine_fg_suggest)
    RelativeLayout mine_fg_suggest;
    Button picture;
    Button camera;
    Button cancle;
    private static final int PHOTO_REQUEST_CAREMA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果
    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    String path;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bale)
                .showImageOnFail(R.mipmap.bale)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        view = inflater.inflate(R.layout.mine_fg_layout, null);
        ButterKnife.bind(this, view);
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        View view1 = inflater1.inflate(R.layout.photo_choose_dialog, null);
        picture = (Button) view1.findViewById(R.id.picture);
        camera = (Button) view1.findViewById(R.id.camera);
        cancle = (Button) view1.findViewById(R.id.cancle);

        ImageLoader.getInstance().displayImage(UrlInterface.TEXT_URL
                + MyApplication.getInstance().getDBHelper().getUser().getHeadImg(), mine_fg_logo, options);
        Log.d("wyy---path---====",MyApplication.getInstance().getDBHelper().getUser().getHeadImg());
        initEvent();
        return view;
    }

    private void initEvent() {
        mine_fg_logo.setOnClickListener(this);
        mine_fg_login.setOnClickListener(this);
        mine_fg_regist.setOnClickListener(this);
        mine_fg_info.setOnClickListener(this);
        mine_fg_book.setOnClickListener(this);
        mine_fg_phone.setOnClickListener(this);
        mine_fg_pwd.setOnClickListener(this);
        mine_fg_setting.setOnClickListener(this);
        mine_fg_introduct.setOnClickListener(this);
        mine_fg_suggest.setOnClickListener(this);
        picture.setOnClickListener(this);
        camera.setOnClickListener(this);
        cancle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.mine_fg_logo:
                if (isLogin(getActivity())) {
                    showDialog();
                }
                isLogin(getActivity());
                break;
            case R.id.mine_fg_login://登录
                if (BaseDate.getSessionId(getActivity()) != null) {
                    Toast.makeText(getActivity(), "您已经成功登陆", Toast.LENGTH_LONG).show();
                    showDialog();
                } else {
                    Intent intent_login = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent_login);
                }
                break;
            case R.id.mine_fg_regist://注册
                Intent intent_regist = new Intent(getActivity(), RegistActivity.class);
                startActivity(intent_regist);
                break;
            case R.id.mine_fg_info://我的消息
                Intent intent_info = new Intent(getActivity(), InfoActivity.class);
                startActivity(intent_info);
                break;
            case R.id.mine_fg_book://我的资料
                if (isLogin(getActivity())) {
                    Intent intent_data = new Intent(getActivity(), DataActivity.class);
                    startActivity(intent_data);
                }
                isLogin(getActivity());
                break;
            case R.id.mine_fg_phone://我的手机
                Intent intent_phone = new Intent(getActivity(), MyPhoneActivity.class);
                startActivity(intent_phone);
                break;
            case R.id.mine_fg_pwd://修改密码
                if (isLogin(getActivity())) {
                    Intent intent_ped = new Intent(getActivity(), ModifyPwdActivity.class);
                    startActivity(intent_ped);
                }
                isLogin(getActivity());
                break;
            case R.id.mine_fg_setting://设置
                Intent intent_setting = new Intent(getActivity(), SettingActivity.class);
                startActivity(intent_setting);
                break;
            case R.id.mine_fg_introduct://推荐给好友
                break;
            case R.id.mine_fg_suggest://意见反馈
                Intent intent_suggest = new Intent(getActivity(), SuggestActivity.class);
                startActivity(intent_suggest);
                break;
        }
    }

    private void showDialog() {
        View view = getActivity().getLayoutInflater().inflate(R.layout.photo_choose_dialog,
                null);
        final Dialog dialog = new Dialog(getActivity(), R.style.transparentFrameWindowStyle);
        dialog.setContentView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        Window window = dialog.getWindow();
        // 设置显示动画
        window.setWindowAnimations(R.style.main_menu_animstyle);
        WindowManager.LayoutParams wl = window.getAttributes();
        wl.x = 0;
        wl.y = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        // 以下这两句是为了保证按钮可以水平满屏
        wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
        wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        // 设置显示位置
        dialog.onWindowAttributesChanged(wl);
        // 设置点击外围解散
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();

        view.findViewById(R.id.picture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 激活系统图库，选择一张图片
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_GALLERY
                startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.camera).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 激活相机
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                // 判断存储卡是否可以用，可用进行存储
                if (hasSdcard()) {
                    tempFile = new File(Environment.getExternalStorageDirectory(),
                            PHOTO_FILE_NAME);
                    // 从文件中创建uri
                    Uri uri = Uri.fromFile(tempFile);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                }
                // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
                startActivityForResult(intent, PHOTO_REQUEST_CAREMA);
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //相册跟拍照回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            // 从相册返回的数据
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                String[] proj = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().managedQuery(uri, proj, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);//获取用户选择图片的索引值
                cursor.moveToFirst();
                path = cursor.getString(column_index);
                File file = new File(path);
                savePicture(file, 1);
                Log.d("wyy---path-", path);
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAREMA) {
            // 从相机返回的数据
            if (hasSdcard()) {
                crop(Uri.fromFile(tempFile));
                savePicture(tempFile, 2);
//                Log.d("wyy---crop(Uri.fromFile(tempFile));-", Uri.fromFile(tempFile).toString());
            } else {
                Toast.makeText(getActivity(), "未找到存储卡，无法存储照片！", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            // 从剪切图片返回的数据
            if (data != null) {
                Bitmap bitmap = data.getParcelableExtra("data");
                this.mine_fg_logo.setImageBitmap(bitmap);
//                savePicture(tempFile, 2);
            }
            try {
                // 将临时文件删除
                tempFile.delete();
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 数据库上传相册返回的头像数据
     *
     * @param file
     */
    private void savePicture(File file, int code) {
        RequestParams param = new RequestParams();
        param.add("accountId", MyApplication.getInstance().getDBHelper().getUser().getAccountId() + "");
        param.add("origin", "ANDROIDAPP");
        try {
            param.put("picture", file);
            AsyncHttpUtil.get(UrlInterface.UpLogo_URL, param, new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                    String json = new String(bytes);
                    ApiMessage apimessage = ApiMessage.FromJson(json);
                    if (apimessage.Code == 1001) {
//                        Toast.makeText(getActivity(), "头像修改成功", Toast.LENGTH_LONG).show();
                    } else {
//                        Toast.makeText(getActivity(), "" + apimessage.Msg, Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

                }
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /*
    * 判断sdcard是否被挂载
     */
    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

    /*
    * 剪切图片
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CUT
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }
}

