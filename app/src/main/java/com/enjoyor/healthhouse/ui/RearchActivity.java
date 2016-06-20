package com.enjoyor.healthhouse.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.Food;
import com.enjoyor.healthhouse.custom.XListView;
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
 * Created by Administrator on 2016/6/12.
 */
public class RearchActivity extends BaseActivity implements XListView.IXListViewListener, View.OnClickListener {
    @Bind(R.id.container)
    CoordinatorLayout container;
    private int count = 1;
    private String searchName = "";

    @Bind(R.id.tv_cancel)
    TextView tv_cancel;//取消
    @Bind(R.id.et_search)
    EditText et_search;
    @Bind(R.id.xlv_food)
    XListView xlv_food;
    @Bind(R.id.ll_clean)
    LinearLayout ll_clean;
    private List<Food.FoodList> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rearch);
        ButterKnife.bind(this);
        tv_cancel.setOnClickListener(this);
        ll_clean.setOnClickListener(this);
        getDate(searchName, count);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                foodList.clear();
                searchName = s.toString();
                count = 1;
                getDate(searchName, count);
            }
        });

    }
    private void initListView() {
        xlv_food.setPullRefreshEnable(false);
        xlv_food.setPullLoadEnable(true);
        xlv_food.setXListViewListener(this);
        xlv_food.setAdapter(new FoodAdapater());
        xlv_food.setSelection((count - 1) * 6);
    }
    private void getDate(final String name, int count) {

        RequestParams params = new RequestParams();
        params.add("name", name);
        params.add("pageNum", 1+"");
        params.add("pageCount", (10*count)+"");

        Log.i("searchName", name + count);
        AsyncHttpUtil.get(UrlInterface.FOOD_URL, params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                String json = new String(bytes);
                ApiMessage apiMessage = ApiMessage.FromJson(json);
                if (apiMessage.Code == 1001) {
                    Food food = JsonHelper.getJson(apiMessage.Data, Food.class);
                    List<Food.FoodList> _list = food.getList();
                    foodList.clear();
                    foodList.addAll(_list);
                    if (foodList.size() > 0) {
                        xlv_food.setVisibility(View.VISIBLE);
                        initListView();
                    } else {
                        xlv_food.setVisibility(View.GONE);
                        if (name != null) {
                            /*软件盘显示则隐藏，隐藏则显示*/
                            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            Snackbar.make(container, "该食物暂时未录入", Snackbar.LENGTH_SHORT).show();
//                            Toast.makeText(ItemServiceActivity.this, "该食物暂时未录入！！", Toast.LENGTH_LONG).show();
                        }
                    }

                }
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        int key = v.getId();
        switch (key) {
            case R.id.ll_clean:
                et_search.setText("");
                break;
            case R.id.tv_cancel:
                finish();
                break;

        }
    }

    class FoodAdapater extends BaseAdapter {

        @Override
        public int getCount() {
            return foodList.size();
        }

        @Override
        public Object getItem(int position) {
            return foodList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(RearchActivity.this).inflate(R.layout.item_food, null);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_food.setText(foodList.get(position).getName());
            holder.tv_kaluli.setText(foodList.get(position).getCalories());
            holder.tv_info.setText(foodList.get(position).getUnit());
            return convertView;
        }

        class ViewHolder {

            @Bind(R.id.tv_food)
            TextView tv_food;
            @Bind(R.id.tv_kaluli)
            TextView tv_kaluli;
            @Bind(R.id.tv_info)
            TextView tv_info;

            ViewHolder(View view) {
                ButterKnife.bind(this, view);
            }
        }
    }
    @Override
    public void onRefresh() {

    }

    @Override
    public void onLoadMore() {
        getDate(searchName, count++);
    }
}
