package com.enjoyor.healthhouse.fragments;

import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.VoiceDate;
import com.enjoyor.healthhouse.common.Constant;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.apache.http.Header;

import java.io.IOException;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 单张图片显示Fragment
 */
public class ImageDetailFragment extends Fragment {
	private String mImageUrl;
	private ImageView mImageView;
	private ProgressBar progressBar;
	private PhotoViewAttacher mAttacher;
	private int voice;
	private MediaPlayer mediaPlayer = new MediaPlayer();
	private boolean playState = false;
	public static ImageDetailFragment newInstance(String imageUrl,int voice) {
		final ImageDetailFragment f = new ImageDetailFragment();

		final Bundle args = new Bundle();
		args.putString("url", imageUrl);
		args.putInt("voice", voice);
		f.setArguments(args);

		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

		voice = getArguments().getInt("voice");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View v = inflater.inflate(R.layout.image_detail_fragment, container, false);
		mImageView = (ImageView) v.findViewById(R.id.image);
		mAttacher = new PhotoViewAttacher(mImageView);

		mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				getActivity().finish();
			}
		});

		progressBar = (ProgressBar) v.findViewById(R.id.loading);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		ImageLoader.getInstance().displayImage(mImageUrl, mImageView, new SimpleImageLoadingListener() {
			@Override
			public void onLoadingStarted(String imageUri, View view) {
				progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
				String message = "";
				switch (failReason.getType()) {
					case IO_ERROR:
						message = "下载错误";
						break;
					case DECODING_ERROR:
						message = "图片无法显示";
						break;
					case NETWORK_DENIED:
						message = "网络有问题，无法下载";
						break;
					case OUT_OF_MEMORY:
						message = "图片太大无法显示";
						break;
					case UNKNOWN:
						message = "未知的错误";
						break;
				}
//				Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
				progressBar.setVisibility(View.GONE);
			}

			@Override
			public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
				progressBar.setVisibility(View.GONE);
				mAttacher.update();
			}
		});

		Log.i("mImageUrl",mImageUrl);
		if(mImageUrl.equals( UrlInterface.FILE_URL+"/"+Constant.VALUE_VOICE)){
			mImageView.setImageResource(R.mipmap.bl_logo);
			if(voice>1){
				getVoicePath(voice);
			}
		}
	}
	public void getVoicePath(final int voice) {
		RequestParams params = new RequestParams();

		String url = UrlInterface.TEXT_URL + "resources/file/" + voice + ".action";
		AsyncHttpUtil.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String json = new String(bytes);
				ApiMessage apiMessage = ApiMessage.FromJson(json);
				if (apiMessage.Code == 1001) {
					VoiceDate voiceDate = JsonHelper.getJson(apiMessage.Data, VoiceDate.class);
					if (!StringUtils.isEmpty(voiceDate.getFilePath())) {
					playMusic(voiceDate.getFilePath());
//						playMusic("http://115.28.37.145:9008/healthstationserver/files/app/2016/06//7d5ea240-4f89-4b89-9202-8ea74daf1d79.AMR");
					}
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

			}
		});

	}

	private void playMusic(String filePath) {
		if (!playState) {
			String url = UrlInterface.FILE_URL+"/"+filePath;
			try {
				mediaPlayer.setDataSource(url);
				mediaPlayer.prepare();
				mediaPlayer.start();
				playState = true;
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
	@Override
	public void onPause() {
		mediaPlayer.stop();
		mImageView.destroyDrawingCache();
		mAttacher.cleanup();
		super.onPause();

	}

	@Override
	public void onDestroyView() {
		mImageView.destroyDrawingCache();
		mAttacher.cleanup();
		super.onDestroyView();

	}
}
