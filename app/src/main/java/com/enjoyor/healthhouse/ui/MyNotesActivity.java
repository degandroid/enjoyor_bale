package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.ListItemAdapter;
import com.enjoyor.healthhouse.bean.NoteInfo;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.ListUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MyNotesActivity extends BaseActivity {

    private int voice;

    /**
     * Item数据实体集合
     */
    private ArrayList<NoteInfo> noteInfo_list = new ArrayList<>();
    private ArrayList<String> image_list;
    /**
     * ListView对象
     */
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;
    private String address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        ButterKnife.bind(this);
        if(getIntent().hasExtra("address")){
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
                        for(int j = 0;j<_list.size();j++){
                            NoteInfo noteInfo = new NoteInfo();
                            NoteInfo _noteInfo = _list.get(j);

                            noteInfo.setContent(_noteInfo.getContent());
                            noteInfo.setCreatetime(_noteInfo.getCreatetime());
                            noteInfo.setVoice(_noteInfo.getVoice());

                            image_list = new ArrayList<>();
                            if(!ListUtils.isEmpty(_noteInfo.getImgs())){
                                image_list.addAll(_noteInfo.getImgs());
                                if(_noteInfo.getVoice()>1){
                                    image_list.add(Constant.VALUE_VOICE);
                                }
                            }else{
                                if(_noteInfo.getVoice()>1){
                                    image_list.add(Constant.VALUE_VOICE);
                                }
                            }


                            noteInfo.setImgs(image_list);
                            noteInfo_list.add(noteInfo);
                        }
                    }
                    if (!ListUtils.isEmpty(noteInfo_list)) {
                        for(int k=0;k<noteInfo_list.size();k++){
                            Log.i("k",noteInfo_list.get(k).toString());
                        }
                        listview.setAdapter(new ListItemAdapter(MyNotesActivity.this, noteInfo_list,address));
                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }
}