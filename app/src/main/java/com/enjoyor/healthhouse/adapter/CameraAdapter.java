package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.ui.ImageViewActivity;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by YuanYuan on 2016/5/18.
 */
public class CameraAdapter extends BaseAdapter {
    private Context mcontext;
    private ArrayList<String> imgList;
    LayoutInflater from;
    private static boolean playState = false;  //播放状态
    private MediaPlayer mediaPlayer;

    public CameraAdapter(Context mcontext, ArrayList<String> imgList, int width, int height) {
        this.mcontext = mcontext;
        this.imgList = imgList;
        this.height = height;
        this.width = width;
        from = LayoutInflater.from(mcontext);
    }


    @Override
    public int getCount() {
        return imgList.size() > 0 ? imgList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return imgList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        if (imgList.get(position).equals("tag")) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        int type = getItemViewType(position);
        ViewHolder holder = null;
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, height);
        switch (type) {
            case 0:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = from.inflate(R.layout.camera_item1, null);
                    holder.imageView1 = (ImageView) convertView.findViewById(R.id.imageView1);
                    holder.imageView1.setLayoutParams(params);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.imageView1.setImageResource(R.mipmap.zanting);
                holder.imageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startvoice();
                    }
                });
                break;
            case 1:
                if (convertView == null) {
                    holder = new ViewHolder();
                    convertView = from.inflate(R.layout.camera_item, null);
                    holder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
                    holder.imageView.setLayoutParams(params);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                Log.d("wyy=====", imgList.get(position));
                ImageLoader.getInstance().displayImage("file://" + imgList.get(position), holder.imageView, MyApplication.options);
                holder.imageView.setTag(imgList.get(position));
                holder.imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (imgList.contains("tag")) {
                            imgList.remove("tag");
                        }
                        Intent intent_large = new Intent(mcontext, ImageViewActivity.class);
                        intent_large.putExtra(ImageViewActivity.EXTRA_IMAGE_URLS, imgList);
                        intent_large.putExtra(ImageViewActivity.EXTRA_VOICE_ID, position);
                        mcontext.startActivity(intent_large);
                    }
                });
                break;
        }
        return convertView;
    }


    int width, height;
    List<String> datas;

    class ViewHolder {
        ImageView imageView, imageView1;
    }

    private void startvoice() {
        if (!playState) {
            mediaPlayer = new MediaPlayer();
            String url = "file:///sdcard/my/voice.amr";
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                mediaPlayer.start();
                playState = true;
                //设置播放结束时监听
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (playState) {
                            playState = false;
                        }
                    }
                });
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }
}
