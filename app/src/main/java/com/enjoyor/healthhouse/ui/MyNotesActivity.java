package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.application.MyApplication;
import com.enjoyor.healthhouse.bean.NoteInfo;
import com.enjoyor.healthhouse.bean.VoiceDate;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.custom.NoScrollGridView;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ListUtils;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNotesActivity extends BaseActivity {
    private MediaPlayer mediaPlayer;
    private ArrayList<NoteInfo> noteInfo_list = new ArrayList<>();

//    private ArrayList<String> file_list = new ArrayList<>();
    private boolean playState = false;

    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    private String address;

    private boolean PLAY_MEDIA = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        ButterKnife.bind(this);
        if (getIntent().hasExtra("address")) {
            address = getIntent().getStringExtra("address");
        }

        initHead();

        if (getIntent().hasExtra("recordId")) {
            Long recordId = getIntent().getLongExtra("recordId", 0L);
            getDate(recordId);
        }
    }

    private void initHead() {
        navigation_name.setText("我的随手记");
        re_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer!=null){
                    mediaPlayer.stop();
                }

                finish();
            }
        });
    }

    public void getDate(Long recordId) {
        RequestParams params = new RequestParams();

        AsyncHttpUtil.get(UrlInterface.getNotes(recordId), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<NoteInfo> _list = JsonHelper.getArrayJson(apiMessage.Data, NoteInfo.class);
                    noteInfo_list.clear();
                    if (!ListUtils.isEmpty(_list)) {
                        noteInfo_list.addAll(_list);
                        listview.setAdapter(new ListItemAdapter(MyNotesActivity.this, noteInfo_list, address));
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    public class ListItemAdapter extends BaseAdapter {

        private Context mContext;
        private ArrayList<NoteInfo> items;
        private String address;

        public ListItemAdapter(Context ctx, ArrayList<NoteInfo> items, String address) {
            this.mContext = ctx;
            this.items = items;
            this.address = address;
        }

        @Override
        public int getCount() {
            return items == null ? 0 : items.size();
        }

        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(mContext, R.layout.item_list, null);

                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
                holder.tv_content = (TextView) convertView.findViewById(R.id.tv_content);
                holder.tv_where = (TextView) convertView.findViewById(R.id.tv_where);
                holder.gridview = (NoScrollGridView) convertView.findViewById(R.id.gridview);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            final NoteInfo itemEntity = items.get(position);
            holder.tv_title.setText(itemEntity.getCreatetime());
            holder.tv_content.setText(itemEntity.getContent());
            holder.tv_where.setText(itemEntity.getPosition());
            ArrayList<String> imageUrls = itemEntity.getImgs();
            ArrayList<String> imageUrls_voice = new ArrayList<>();
            if (itemEntity.getVoice() > 1) {
                if (!ListUtils.isEmpty(imageUrls)) {
                    imageUrls_voice.addAll(imageUrls);
                }
                imageUrls_voice.add(Constant.VALUE_VOICE);
            }else{
                if (!ListUtils.isEmpty(imageUrls)) {
                    imageUrls_voice.addAll(imageUrls);
                }
            }
            if (ListUtils.isEmpty(imageUrls) && ListUtils.isEmpty(imageUrls_voice)) { // 没有图片资源就隐藏GridView
                holder.gridview.setVisibility(View.GONE);
            } else {
                holder.gridview.setVisibility(View.VISIBLE);
                holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls_voice, imageUrls, itemEntity.getVoice()));
            }
            return convertView;
        }

        class ViewHolder {
            private TextView tv_title;
            private TextView tv_content;
            private TextView tv_where;
            private NoScrollGridView gridview;
        }
    }

    public class NoScrollGridAdapter extends BaseAdapter {
//        public DisplayImageOptions _option;
        private int voice;

        /**
         * 上下文
         */
        private Context ctx;
        /**
         * 图片Url集合
         */
        private ArrayList<String> imageUrls;
        private ArrayList<String> imageUrls_voice;

        public NoScrollGridAdapter(Context ctx, ArrayList<String> imageUrls_voice, ArrayList<String> imageUrls, int voice) {
            this.ctx = ctx;
            this.imageUrls = imageUrls;
            this.imageUrls_voice = imageUrls_voice;
            this.voice = voice;

//            _option = new DisplayImageOptions.Builder()
//                    .showImageOnLoading(R.mipmap.bale)
//                    .showImageForEmptyUri(R.mipmap.jiazaishibai)
//                    .showImageOnFail(R.mipmap.jiazaishibai)
//                    .cacheInMemory(true)
//                    .bitmapConfig(Bitmap.Config.RGB_565)
//                    .build();
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return imageUrls_voice == null ? 0 : imageUrls_voice.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return imageUrls_voice.get(position);
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {



            View view = View.inflate(ctx, R.layout.item_gridview, null);
            final ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
            if (imageUrls_voice.get(position).equals(Constant.VALUE_VOICE)) {
                imageView.setImageResource(R.mipmap.zanting);
                imageView.setScaleType(ImageView.ScaleType.CENTER);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!PLAY_MEDIA) {
                            getVoicePath(voice);
                            imageView.setImageResource(R.mipmap.bofangzhong);
                            PLAY_MEDIA = true;
                        } else {
                            mediaPlayer.pause();
                            imageView.setImageResource(R.mipmap.zanting);
                            PLAY_MEDIA = false;
                        }
                        Log.i("boolen", PLAY_MEDIA + "");
                    }
                });
            } else {
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL + "/" + imageUrls.get(position), imageView, MyApplication.options);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        imageBrower(position, imageUrls);
                    }
                });
            }
            return view;
        }

        /**
         * 打开图片查看器
         *
         * @param position
         * @param urls2
         */
        protected void imageBrower(int position, ArrayList<String> urls2) {
            Intent intent = new Intent(ctx, ImagePagerActivity.class);
            // 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);
            intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
            ctx.startActivity(intent);
        }
    }
        private void playMusic(String filePath) {
            Log.i("filepath",filePath);
                String url = UrlInterface.FILE_URL + "/" + filePath;
                try {
                        mediaPlayer = new MediaPlayer();
                        mediaPlayer.setDataSource(url);
                        mediaPlayer.prepare();
                        mediaPlayer.start();

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
//        }

    public void getVoicePath(final int voice) {
        RequestParams params = new RequestParams();
        AsyncHttpUtil.get(UrlInterface.getResources(voice), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    VoiceDate voiceDate = JsonHelper.getJson(apiMessage.Data, VoiceDate.class);
                    if (!StringUtils.isEmpty(voiceDate.getFilePath())) {
                        playMusic(voiceDate.getFilePath());
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        if(mediaPlayer!=null){
            mediaPlayer.stop();
        }

        super.onDestroy();
    }
}