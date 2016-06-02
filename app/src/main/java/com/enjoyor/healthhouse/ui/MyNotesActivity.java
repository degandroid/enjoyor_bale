package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.NoteInfo;
import com.enjoyor.healthhouse.bean.UserInfo;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/23.
 */
public class MyNotesActivity extends BaseActivity {
    @Bind(R.id.lv_mynote)ListView lv_mynote;
    private List<NoteInfo> noteInfo_list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mynotes_ac_layout);
        ButterKnife.bind(this);
        if(getIntent().hasExtra("recordId")){
            Long recordId = getIntent().getLongExtra("recordId",0L);
            getDate(recordId);
        }
    }

    private void initListView() {

        lv_mynote.setAdapter(new MyNoteAdapter());
    }

    public void getDate(Long recordId) {
        RequestParams params = new RequestParams();

        AsyncHttpUtil.post(UrlInterface.TEXT_URL + "record/notes/"+recordId+".action", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    UserInfo user = JsonHelper.getJson(apiMessage.Data, UserInfo.class);
                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });

    }

    class MyNoteAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}
