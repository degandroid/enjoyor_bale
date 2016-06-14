package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.CameraAdapter;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.PhotoId;
import com.enjoyor.healthhouse.common.BaseDate;
import com.enjoyor.healthhouse.custom.PhotoPickerActivity;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.OtherUtils;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/5/16.
 */
public class NotesActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.img_right)
    ImageView img_right;
    @Bind(R.id.notes_et)
    EditText notes_et;
    @Bind(R.id.notes_photo)
    ImageView notes_photo;
    @Bind(R.id.notes_luyin)
    ImageView notes_luyin;
    @Bind(R.id.gridview)
    GridView notes_grid;
    @Bind(R.id.notes_address)
    TextView notes_address;
    @Bind(R.id.notes_commit)
    TextView notes_commit;
    @Bind(R.id.notes_top)
    RelativeLayout notes_top;
    //    选择照片
    private ArrayList<Uri> url = new ArrayList<>();
    CameraAdapter cameraAdapter;
    private int mColumnWidth;
    private static final int PICK_PHOTO = 1;
    private List<String> mResults;
    ArrayList<String> result = new ArrayList<>();//照片集合
    List<String> _result;
    List<String> notes = new ArrayList<>();
    public static List<String> list;
    String[] imageUriArray;
    int count = 0;
    int id;
    // 定位相关
    LocationClient mLocClient;
    public MyLocationListenner myListener = new MyLocationListenner();
    private MyLocationConfiguration.LocationMode mCurrentMode;// 定位模式
    boolean isFirstLoc = true;// 是否首次定位
    double lng;
    double lat;
    String address;
    String street = "";
    String streetNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);
        initView();
        initEvent();
        list = result;
    }

    private void initEvent() {
        notes_photo.setOnClickListener(this);
        re_back.setOnClickListener(this);
        notes_luyin.setOnClickListener(this);
        notes_commit.setOnClickListener(this);
        notes_top.setOnClickListener(this);

    }

    private void initView() {
        navigation_name.setText("随手记");
        img_right.setImageResource(R.mipmap.dangan);
        img_right.setVisibility(View.VISIBLE);
        img_right.setOnClickListener(this);
        int screenWidth = OtherUtils.getWidthInPx(getApplicationContext());
        mColumnWidth = (screenWidth - OtherUtils.dip2px(getApplicationContext(), 3)) / 5;
        mCurrentMode = MyLocationConfiguration.LocationMode.NORMAL;// 设置定位模式为普通
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);// 注册监听函数：
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(1000 * 60 * 60);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocClient.setLocOption(option);
        mLocClient.start();
    }


    /**
     * 定位sdk监听函数
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation == null) {
                return;
            } else {
                if (bdLocation.getStreet() != null) {
                    street = bdLocation.getStreet();
                }
                if (!StringUtils.isEmpty(bdLocation.getStreetNumber() + "")) {
                    streetNumber = bdLocation.getStreetNumber() + "号";
                } else {
                    streetNumber = "";
                }
            }
            address = street + streetNumber;
            notes_address.setText(address);
            lng = bdLocation.getLongitude();
            lat = bdLocation.getLatitude();
        }
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.img_right:
                if (BaseDate.getSessionId(this) != null) {
                    Intent intent = new Intent(NotesActivity.this, HealthFileActivity.class);
                    intent.putExtra("address", address);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(NotesActivity.this, LoginActivity.class);
                    intent.putExtra(LoginActivity.FROM_SUISHOUJI, true);
                    startActivity(intent);
                }
                break;
            case R.id.re_back://导航栏返回事件
                finish();
                break;
            case R.id.notes_photo://跳转本地图片库事件
                selectPhote();
                break;
            case R.id.notes_luyin://跳转录音事件
                Intent intent_voice = new Intent(this, VoiceActivity.class);
                startActivityForResult(intent_voice, 110);
                break;
            case R.id.notes_commit://点击提交按钮事件事件
                if (isLogin(this)) {
                    if (notes_et.getText().toString().trim().length() != 0 || !mFile.isEmpty()) {
                        if (mFile != null && !mFile.isEmpty()) {
                            Log.d("wyy-----json---", mFile.toString());
                            RequestParams param1 = new RequestParams();
                            for (int i = 0; i < mFile.size(); i++) {
                                try {
                                    param1.put("file" + i, mFile.get(i));
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                            param1.add("type", 0 + "");
                            param1.add("origin", "ANDROIDAPP");
                            AsyncHttpUtil.post(UrlInterface.UpDateFile_URL, param1, new AsyncHttpResponseHandler() {
                                @Override
                                public void onSuccess(int i, Header[] headers, byte[] bytes) {
                                    String json = new String(bytes);
                                    Log.d("wyy-----json1---", json);
                                    ApiMessage apimessage = ApiMessage.FromJson(json);
                                    if (apimessage.Code == 1001) {
                                        List<PhotoId> photoid = JsonHelper.getArrayJson(apimessage.Data, PhotoId.class);
                                        if (photoid != null && !photoid.isEmpty()) {
                                            for (PhotoId bean : photoid) {
                                                notes.add(bean.getId() + "");
                                            }
                                        }
                                        savenotes();
                                    }
                                }

                                @Override
                                public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                                }
                            });
                        } else {
                            savenotes();
                        }
                    } else {
                        Toast.makeText(NotesActivity.this, "请输入您的健康记录或者上传您的健康图片", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case R.id.notes_top:
                notes_et.setFocusable(true);
                //打开软键盘
                InputMethodManager imm = (InputMethodManager) NotesActivity.this.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    private void savenotes() {
        RequestParams params = new RequestParams();
        params.add("user", MyApplication.getInstance().getDBHelper().getUser().getUserId() + "");
        params.add("content", notes_et.getText().toString().trim());
        params.add("lng", lng + "");
        params.add("lat", lat + "");
        params.add("voice", id + "");
        if (notes.size() != 0) {
            String img = "";
            for (String s : notes) {
                img += s + ",";
            }
            params.add("images", img.substring(0, img.length() - 1));
            Log.d("wyy-----img---", img);
        }
        AsyncHttpUtil.post(UrlInterface.Notes_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                if (bytes != null) {
                    String json = new String(bytes);
                    ApiMessage api = ApiMessage.FromJson(json);
                    if (api.Data.equals(true + "")) {
                        Toast.makeText(NotesActivity.this, "文件上传成功", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(NotesActivity.this, "文件上传失败", Toast.LENGTH_LONG).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            }
        });
    }

    /**
     * 选择照片，模式设置
     */
    private void selectPhote() {
        int selectedMode = PhotoPickerActivity.MODE_MULTI;
        int maxNum = PhotoPickerActivity.DEFAULT_NUM;
        Intent intent = new Intent(NotesActivity.this, PhotoPickerActivity.class);
        intent.putExtra(PhotoPickerActivity.EXTRA_SHOW_CAMERA, true);
        intent.putExtra(PhotoPickerActivity.EXTRA_SELECT_MODE, selectedMode);
        intent.putExtra(PhotoPickerActivity.EXTRA_MAX_MUN, maxNum);
        startActivityForResult(intent, PICK_PHOTO);
    }

    List<File> mFile = new ArrayList<>();

    /**
     * 接受回传过来的数据
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            if (requestCode == PICK_PHOTO) {
                _result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                result.clear();
                result.addAll(_result);
                if (resultCode == RESULT_OK) {
                    for (String s : result) {
                        Log.d("wyy==============", s);
                        mFile.add(new File(s));
                    }
                }
            } else if (requestCode == 110) {
                if (count == 0) {
                    if (data != null) {
                        id = data.getIntExtra("id", 1);
                        result.add("tag");
                        ++count;
                    }
                } else {
                    result.remove("tag");
                    result.add("tag");
                    id = data.getIntExtra("id", 1);
                }
            }
            showResult(result);
        }
        Log.d("---------------", notes.size() + "");
    }

    private void showResult(ArrayList<String> result) {
        Log.d("--------result-------", result.get(0));
        if (mResults == null) {
            mResults = new ArrayList<String>();
        }
        mResults.clear();
        mResults.addAll(result);

        if (cameraAdapter == null) {
            imageUriArray = (String[]) mResults
                    .toArray(new String[mResults.size()]);
            cameraAdapter = new CameraAdapter(this, (ArrayList<String>) mResults, mColumnWidth, mColumnWidth);
            notes_grid.setAdapter(cameraAdapter);
        } else {
            cameraAdapter.notifyDataSetChanged();
        }
    }
}
