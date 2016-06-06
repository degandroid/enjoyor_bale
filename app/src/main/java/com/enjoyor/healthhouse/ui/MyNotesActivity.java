package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.NoteInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ListUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.apache.http.Header;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNotesActivity extends BaseActivity {
    public static DisplayImageOptions mNormalImageOptions;
    public static final String SDCARD_PATH = Environment.getExternalStorageDirectory().toString();
    public static final String IMAGES_FOLDER = SDCARD_PATH + File.separator + "demo" + File.separator + "images" + File.separator;
    private List<NoteInfo> note_list = new ArrayList<>();
    private Context context;
    @Bind(R.id.lv_list)
    ListView lv_list;
    private List<String> pics = new ArrayList<>();
    private int mScreenHeight;
    private int mScreenWidth;

    private int VALUE_ONEPIC = 1;
    private int VALUE_TWOPIC = 2;
    private int VALUE_MOREPIC = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mynotes_ac_layout);
        context = this;
        ButterKnife.bind(this);
        Display display = getWindowManager().getDefaultDisplay();
        mScreenHeight = display.getHeight();
        mScreenWidth = display.getWidth();


        if (getIntent().hasExtra("recordId")) {
            Long recordId = getIntent().getLongExtra("recordId", 0L);
            getDate(recordId);
        }
    }

    public void getDate(Long recordId) {
        RequestParams params = new RequestParams();

        String url = UrlInterface.TEXT_URL + "record/notes/" + recordId + ".action";
        AsyncHttpUtil.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<NoteInfo> _list = JsonHelper.getArrayJson(apiMessage.Data, NoteInfo.class);

                    if (!ListUtils.isEmpty(_list)) {
                        note_list.clear();
                        note_list.addAll(_list);
                        lv_list.setAdapter(new ListAdapter(note_list));
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }
    public class ImagesInnerGridViewAdapter extends BaseAdapter {
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageForEmptyUri(R.mipmap.bl_banner1)
                .showImageOnFail(R.mipmap.bl_banner1)
                .cacheInMemory(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        private List<String> datas;
        private int height = 0;
        private int width = 0;
        private int type;

        public ImagesInnerGridViewAdapter(List<String> datas,int type) {
            this.datas = datas;
            this.type = type;
        }

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            int _type = getItemViewType(position);
            final ViewHolder holder;
            if (convertView == null) {
//                switch (_type){
//                    case VALUE_ONEPIC:
//                        break;
//                    case VALUE_TWOPIC:
//                        break;
//                    case VALUE_MOREPIC:
//                        break;
//                }
                convertView = LayoutInflater.from(context).inflate(R.layout.item_gridview, null);
                holder = new ViewHolder(convertView);
                holder.img_picture = (ImageView) convertView.findViewById(R.id.img_picture);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.img_picture.setScaleType(ImageView.ScaleType.CENTER_CROP);
            ImageLoader.getInstance().displayImage(datas.get(position), holder.img_picture, options);
            holder.img_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyNotesActivity.this, SpaceImageDetailActivity.class);
                    intent.putExtra("images", (ArrayList<String>) datas);
                    intent.putExtra("position", position);
                    int[] location = new int[2];
                    holder.img_picture.getLocationOnScreen(location);
                    intent.putExtra("locationX", location[0]);
                    intent.putExtra("locationY", location[1]);
                    intent.putExtra("width", holder.img_picture.getWidth());
                    intent.putExtra("height", holder.img_picture.getHeight());
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
            return convertView;
        }
        @Override
        public int getItemViewType(int position) {
            return type;
        }

        @Override
        public int getViewTypeCount() {
            return 3;
        }
        public class ViewHolder {
            ImageView img_picture;
            ViewHolder(View view) {
                ButterKnife.bind(this,view);
            }
        }
    }

    class ListAdapter extends BaseAdapter {

        private List<NoteInfo> note_list;

        ListAdapter(List<NoteInfo> note_list) {
            this.note_list = note_list;
        }

        @Override
        public int getCount() {
            return note_list.size();
        }

        @Override
        public Object getItem(int position) {
            return note_list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            NoteInfo info = note_list.get(position);
            List<String> _list = info.getImgs();
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(MyNotesActivity.this).inflate(R.layout.item_mynote, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }


            holder.tv_createtime.setText(info.getCreatetime());
            holder.tv_where.setText("");
            holder.tv_content.setText(info.getContent());
            if (!ListUtils.isEmpty(_list)) {
                pics.clear();
                for (int i = 0; i < _list.size(); i++) {
                    pics.add(UrlInterface.FILE_URL + _list.get(i));
                }
                getDefault(holder);
                if (pics.size() == 1) {
                    holder.ngv_oneline_onepic.setVisibility(View.VISIBLE);
                    holder.ngv_oneline_onepic.setAdapter(new ImagesInnerGridViewAdapter(pics,VALUE_ONEPIC));
                } else if (pics.size() == 2) {
                    holder.ngv_oneline_twopic.setVisibility(View.GONE);
                    holder.ngv_oneline_onepic.setAdapter(new ImagesInnerGridViewAdapter(pics,VALUE_TWOPIC));
                } else {
                    holder.ngv_moreline.setVisibility(View.GONE);
                    holder.ngv_oneline_onepic.setAdapter(new ImagesInnerGridViewAdapter(pics,VALUE_MOREPIC));
                }
            }
            return convertView;
        }

        public void getDefault(ViewHolder holder) {
            holder.ngv_oneline_onepic.setVisibility(View.GONE);
            holder.ngv_oneline_twopic.setVisibility(View.GONE);
            holder.ngv_moreline.setVisibility(View.GONE);
        }

        public class ViewHolder {
            @Bind(R.id.tv_createtime)
            TextView tv_createtime;
            @Bind(R.id.tv_where)
            TextView tv_where;
            @Bind(R.id.tv_content)
            TextView tv_content;

            @Bind(R.id.ngv_oneline_onepic)
            NoScrollGridView ngv_oneline_onepic;

            @Bind(R.id.ngv_oneline_twopic)
            NoScrollGridView ngv_oneline_twopic;

            @Bind(R.id.ngv_moreline)
            NoScrollGridView ngv_moreline;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }


}
