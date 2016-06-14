package com.enjoyor.healthhouse.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;

public class NoScrollGridAdapter extends BaseAdapter {

	/** 上下文 */
	private Context ctx;
	/** 图片Url集合 */
	private ArrayList<String> imageUrls;

	public NoScrollGridAdapter(Context ctx, ArrayList<String> imageUrls) {
		this.ctx = ctx;
		this.imageUrls = imageUrls;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return imageUrls == null ? 0 : imageUrls.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return imageUrls.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = View.inflate(ctx, R.layout.item_gridview, null);
		ImageView imageView = (ImageView) view.findViewById(R.id.iv_image);
		DisplayImageOptions options = new DisplayImageOptions.Builder()//
				.showImageForEmptyUri(R.mipmap.bl_logo)
				.cacheInMemory(true)//
				.cacheOnDisk(true)//
				.bitmapConfig(Bitmap.Config.RGB_565)//
				.build();
				Log.i("get", imageUrls.toString());
		if(imageUrls.get(position).equals(Constant.VALUE_VOICE)){
			imageView.setImageResource(R.mipmap.zanting);
		}else{
			ImageLoader.getInstance().displayImage(UrlInterface.FILE_URL+"/"+imageUrls.get(position), imageView,options);
		}
		return view;
	}

}
