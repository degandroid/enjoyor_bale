package com.enjoyor.healthhouse.ui;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.Voice;
import com.enjoyor.healthhouse.custom.AudioRecorder;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/18.
 */
public class VoiceActivity extends BaseActivity implements View.OnClickListener {
    @Bind(R.id.voice_tv)
    TextView voice_tv;
    @Bind(R.id.voice_long)
    TextView voice_long;
    @Bind(R.id.voice_time)
    TextView voice_time;
    @Bind(R.id.voice_luyin)
    ImageView voice_luyin;
    @Bind(R.id.voice_cha)
    ImageView voice_cha;
    @Bind(R.id.voice_dui)
    ImageView voice_dui;
    private static int MAX_TIME = 60;    //最长录制时间，单位秒，0为无时间限制
    private static int MIX_TIME = 1;     //最短录制时间，单位秒，0为无时间限制，建议设为1
    private static int RECORD_NO = 0;  //不在录音
    private static int RECORD_ING = 1;   //正在录音
    private static int RECODE_ED = 2;   //完成录音
    private static int RECODE_STATE = 0;      //录音的状态

    private static float recodeTime = 0.0f;    //录音的时间
    private static double voiceValue = 0.0;    //麦克风获取的音量值

    private AudioRecorder mr;
    private Thread recordThread;

    private MediaPlayer mediaPlayer;
    private static boolean playState = false;  //播放状态
    private int count = 0;
    private Handler handler;
     Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.voice_ac_layout);
        ButterKnife.bind(this);
        initVoice();
        initTime();
        initEvent();

    }

    private void initEvent() {
        voice_dui.setOnClickListener(this);
    }

    /**
     * 录音的方法
     */
    private void initVoice() {
        voice_luyin.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        sendMsg();
                        voice_cha.setVisibility(View.GONE);
                        voice_dui.setVisibility(View.GONE);
                        if (RECODE_STATE != RECORD_ING) {
                            scanOldFile();
                            mr = new AudioRecorder("voice");
                            RECODE_STATE = RECORD_ING;
                            try {
                                mr.start();
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                            mythread();
                        }
                        break;
                    case MotionEvent.ACTION_UP:
                        timer.cancel();
//                        handler.getLooper().getThread().interrupt();
                        voice_luyin.setImageResource(R.mipmap.luyin_anniu);
                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE = RECODE_ED;
                            try {
                                mr.stop();
                                voiceValue = 0.0;
                                voice_cha.setVisibility(View.VISIBLE);
                                voice_dui.setVisibility(View.VISIBLE);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            if (recodeTime < MIX_TIME) {
                                showWarnToast();
                                RECODE_STATE = RECORD_NO;
                                voice_cha.setVisibility(View.GONE);
                                voice_dui.setVisibility(View.GONE);
                            } else {
                            }
                        }
                        break;
                }
                return true;
            }
        });
    }

    private void initTime() {
        handler = new
                Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        // TODO Auto-generated method stub
                        super.handleMessage(msg);
                        if (msg.what < 60) {
                            if (msg.what < 10) {
                                voice_time.setText("00:00:0" + msg.what);
                            } else {
                                voice_time.setText("00:00:" + msg.what);
                            }

                        } else {
                            voice_time.setText("00:01:00");
                        }
                    }
                }

        ;
    }

    /**
     * 录音计时线程
     */
    private void mythread() {
        recordThread = new Thread(ImgThread);
        recordThread.start();
    }

    //录音线程
    private Runnable ImgThread = new Runnable() {

        @Override
        public void run() {
            recodeTime = 0.0f;
            while (RECODE_STATE == RECORD_ING) {
                if (recodeTime >= MAX_TIME && MAX_TIME != 0) {
                    imgHandle.sendEmptyMessage(0);
                } else {
                    try {
                        Thread.sleep(200);
                        recodeTime += 0.2;
                        if (RECODE_STATE == RECORD_ING) {
                            voiceValue = mr.getAmplitude();
                            imgHandle.sendEmptyMessage(1);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        Handler imgHandle = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case 0:
                        //录音超过15秒自动停止
                        if (RECODE_STATE == RECORD_ING) {
                            RECODE_STATE = RECODE_ED;
                            try {
                                mr.stop();
                                voiceValue = 0.0;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            if (recodeTime < 1.0) {
                                showWarnToast();
                                RECODE_STATE = RECORD_NO;
                            }
                        }
                        break;
                    case 1:
                        setDialogImage();
                        break;
                    default:
                        break;
                }

            }
        };
    };

    /**
     * 录音时imageview随音量大小改变
     */
    private void setDialogImage() {
        if (voiceValue < 200.0) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu);
        } else if (voiceValue > 200.0 && voiceValue < 800) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu1);
        } else if (voiceValue > 800.0 && voiceValue < 1600) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu2);
        } else if (voiceValue > 1600.0 && voiceValue < 3200) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu3);
        } else if (voiceValue > 3200.0 && voiceValue < 10000) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu4);
        } else if (voiceValue > 10000.0) {
            voice_luyin.setImageResource(R.mipmap.luyin_anniu5);
        }
    }

    private void showWarnToast() {
        Toast toast = new Toast(VoiceActivity.this);
        LinearLayout linearLayout = new LinearLayout(VoiceActivity.this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        linearLayout.setPadding(20, 20, 20, 20);
        // 定义一个ImageView
        ImageView imageView = new ImageView(VoiceActivity.this);
        imageView.setImageResource(R.mipmap.voice_to_short); // 图标

        TextView mTv = new TextView(VoiceActivity.this);
        mTv.setText("时间太短   录音失败");
        mTv.setTextSize(14);
        mTv.setTextColor(Color.WHITE);//字体颜色
        //mTv.setPadding(0, 10, 0, 0);

        // 将ImageView和ToastView合并到Layout中
        linearLayout.addView(imageView);
        linearLayout.addView(mTv);
        linearLayout.setGravity(Gravity.CENTER);//内容居中
        linearLayout.setBackgroundResource(R.mipmap.record_bg);//设置自定义toast的背景

        toast.setView(linearLayout);
        toast.setGravity(Gravity.CENTER, 0, 0);//起点位置为中间     100为向下移100dp
        toast.show();
    }

    private void scanOldFile() {
        File file = new File(Environment
                .getExternalStorageDirectory(), "my/voice.amr");
        if (file.exists()) {
            file.delete();
        }
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.voice_cha:

                break;
            case R.id.voice_dui:
                String url = "/storage/sdcard0/my/voice.amr";
//                Intent intent = new Intent();
//                this.setResult(110,intent);
                saveVoice(url);
                break;
        }

    }

    private void saveVoice(String url) {
        RequestParams params = new RequestParams();
        params.add("type", 0 + "");
        params.add("origin", "ANDROIDAPP");
        File file = new File(url);
        try {
            params.put("file", file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        AsyncHttpUtil.post(UrlInterface.UpDateFile_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                Log.d("wyy+++++", json);
                Toast.makeText(VoiceActivity.this, "录音上传成功", Toast.LENGTH_LONG).show();
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<Voice> voice = JsonHelper.getArrayJson(apiMessage.Data, Voice.class);
                    Log.d("wyy-------", voice.get(0).getId() + "");
                    Intent intent = new Intent();
                    intent.putExtra("id", voice.get(0).getId());
                    VoiceActivity.this.setResult(110, intent);
                }
                finish();
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    private void sendMsg() {
        //倒记时
         timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() { // UI thread
                    @Override
                    public void run() {
                        count++;
                        handler.sendEmptyMessage(count);
                        if (count == 60) {
                            timer.cancel();
                            reSet();
                        }
                    }
                });
            }
        };

        timer.schedule(task, 1000, 1000);
    }

    private void reSet() {
        count = 0;
    }
}
