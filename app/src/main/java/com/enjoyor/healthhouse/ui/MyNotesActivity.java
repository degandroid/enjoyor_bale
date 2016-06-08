package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.adapter.ListItemAdapter;
import com.enjoyor.healthhouse.bean.NoteInfo;
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
    /**
     * ListView对象
     */
    @Bind(R.id.listview)
    ListView listview;
    @Bind(R.id.navigation_name)
    TextView navigation_name;
    @Bind(R.id.re_back)
    RelativeLayout re_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynote);
        ButterKnife.bind(this);
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

        String url = UrlInterface.TEXT_URL + "record/notes/" + recordId + ".action";
        AsyncHttpUtil.get(url, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    List<NoteInfo> _list = JsonHelper.getArrayJson(apiMessage.Data, NoteInfo.class);

                    if (!ListUtils.isEmpty(_list)) {
                        noteInfo_list.clear();
                        noteInfo_list.addAll(_list);
                        if (!ListUtils.isEmpty(noteInfo_list)) {
                            listview.setAdapter(new ListItemAdapter(MyNotesActivity.this, noteInfo_list));
                        }

                    }
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }
}