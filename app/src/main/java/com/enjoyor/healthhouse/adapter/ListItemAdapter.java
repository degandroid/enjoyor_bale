package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.NoteInfo;
import com.enjoyor.healthhouse.custom.NoScrollGridView;
import com.enjoyor.healthhouse.ui.ImagePagerActivity;

import java.util.ArrayList;

/**
 * 首页ListView的数据适配器
 * 
 * @author Administrator
 * 
 */
public class ListItemAdapter extends BaseAdapter {

	private int MusicVoice;
	private Context mContext;
	private ArrayList<NoteInfo> items;
	private String address;

	public ListItemAdapter(Context ctx, ArrayList<NoteInfo> items,String address) {
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
		holder.tv_where.setText(address);

		MusicVoice = itemEntity.getVoice();
		Log.i("_MusicVoice",MusicVoice+"");
		final ArrayList<String> imageUrls = itemEntity.getImgs();

		if (imageUrls == null || imageUrls.size() == 0) { // 没有图片资源就隐藏GridView
			holder.gridview.setVisibility(View.GONE);
		} else {
			holder.gridview.setVisibility(View.VISIBLE);
			Log.i("gridview",imageUrls.toString());
			holder.gridview.setAdapter(new NoScrollGridAdapter(mContext, imageUrls));
		}
		// 点击回帖九宫格，查看大图
		holder.gridview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				imageBrower(position, imageUrls,itemEntity.getVoice());
			}
		});
		return convertView;
	}

	/**
	 * 打开图片查看器
	 * 
	 * @param position
	 * @param urls2
	 */
	protected void imageBrower(int position, ArrayList<String> urls2,int voice) {
		Intent intent = new Intent(mContext, ImagePagerActivity.class);
		// 图片url,为了演示这里使用常量，一般从数据库中或网络中获取
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, urls2);

		intent.putExtra(ImagePagerActivity.EXTRA_VOICE_ID, voice);
		Log.i("MusicVoice",MusicVoice+"");
		intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, position);
		mContext.startActivity(intent);

	}

	/**
	 * listview组件复用，防止“卡顿”
	 * 
	 * @author Administrator
	 * 
	 */
	class ViewHolder {
		private TextView tv_title;
		private TextView tv_content;
		private TextView tv_where;
		private NoScrollGridView gridview;
	}
}
