package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.CameraAdapter;
import com.enjoyor.healthhouse.custom.PhotoPickerActivity;
import com.enjoyor.healthhouse.utils.OtherUtils;

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
    @Bind(R.id.notes_et)
    EditText notes_et;
    @Bind(R.id.notes_photo)
    ImageView notes_photo;
    @Bind(R.id.notes_camera)
    ImageView notes_camera;
    @Bind(R.id.notes_luyin)
    ImageView notes_luyin;
    @Bind(R.id.gridview)
    GridView notes_grid;
    @Bind(R.id.notes_address)
    TextView notes_address;
    @Bind(R.id.notes_commit)
    TextView notes_commit;
    //    选择照片
    private ArrayList<Uri> url = new ArrayList<>();
    CameraAdapter cameraAdapter;
    private int mColumnWidth;
    private static final int PICK_PHOTO = 1;
    private List<String> mResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        ButterKnife.bind(this);

        initView();
        initEvent();
    }

    private void initEvent() {
        notes_photo.setOnClickListener(this);
        re_back.setOnClickListener(this);
        notes_camera.setOnClickListener(this);
        notes_luyin.setOnClickListener(this);
        notes_commit.setOnClickListener(this);

    }

    private void initView() {
        navigation_name.setText("随手记");
        int screenWidth = OtherUtils.getWidthInPx(getApplicationContext());
        mColumnWidth = (screenWidth - OtherUtils.dip2px(getApplicationContext(), 4)) / 3;
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.re_back://导航栏返回事件
                finish();
                break;
            case R.id.notes_photo://跳转本地图片库事件
                selectPhote();
                break;
            case R.id.notes_camera://跳转拍照事件
                break;
            case R.id.notes_luyin://跳转录音事件
                Intent intent_voice = new Intent(this, VoiceActivity.class);
                startActivity(intent_voice);
                break;
            case R.id.notes_commit://点击提交按钮事件事件
                break;
        }
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_PHOTO) {
            if (resultCode == RESULT_OK) {
                ArrayList<String> result = data.getStringArrayListExtra(PhotoPickerActivity.KEY_RESULT);
                showResult(result);
            }
        }
    }

    private void showResult(ArrayList<String> result) {
        if (mResults == null) {
            mResults = new ArrayList<String>();
        }
        mResults.clear();
        mResults.addAll(result);

        if (cameraAdapter == null) {
            cameraAdapter = new CameraAdapter(this, mResults, R.layout.camera_item, mColumnWidth, mColumnWidth);
            notes_grid.setAdapter(cameraAdapter);
        } else {
//            cameraAdapter.setPathList(mResults);
            cameraAdapter.notifyDataSetChanged();
        }
    }
}
