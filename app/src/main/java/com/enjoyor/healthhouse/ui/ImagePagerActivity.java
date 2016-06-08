package com.enjoyor.healthhouse.ui;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.widget.TextView;

import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.bean.VoiceDate;
import com.enjoyor.healthhouse.custom.HackyViewPager;
import com.enjoyor.healthhouse.fragments.ImageDetailFragment;
import com.enjoyor.healthhouse.net.ApiMessage;
import com.enjoyor.healthhouse.net.AsyncHttpUtil;
import com.enjoyor.healthhouse.net.JsonHelper;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;

import java.util.ArrayList;

/**
 * 图片查看器
 */
public class ImagePagerActivity extends FragmentActivity {
	private static final String STATE_POSITION = "STATE_POSITION";
	public static final String EXTRA_IMAGE_INDEX = "image_index"; 
	public static final String EXTRA_IMAGE_URLS = "image_urls";
	public static final String EXTRA_VOICE_ID = "voice_id";

	private HackyViewPager mPager;
	private int pagerPosition;
	private TextView indicator;
	private MediaPlayer player = new MediaPlayer();
	private int voice;
	@Override 
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_detail_pager);

		pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
		ArrayList<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);

		if(getIntent().hasExtra(EXTRA_VOICE_ID)){
			voice = getIntent().getIntExtra(EXTRA_VOICE_ID,0);
			if(voice>1){
				getVoicePath(voice);
			}
		}
		mPager = (HackyViewPager) findViewById(R.id.pager);
		ImagePagerAdapter mAdapter = new ImagePagerAdapter(getSupportFragmentManager(), urls);
		mPager.setAdapter(mAdapter);
		indicator = (TextView) findViewById(R.id.indicator);

		CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
		indicator.setText(text);
		// 更新下标
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageSelected(int arg0) {
				CharSequence text = getString(R.string.viewpager_indicator, arg0 + 1, mPager.getAdapter().getCount());
				indicator.setText(text);
			}

		});
		if (savedInstanceState != null) {
			pagerPosition = savedInstanceState.getInt(STATE_POSITION);
		}

		mPager.setCurrentItem(pagerPosition);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putInt(STATE_POSITION, mPager.getCurrentItem());
	}

	private class ImagePagerAdapter extends FragmentStatePagerAdapter {

		public ArrayList<String> fileList;

		public ImagePagerAdapter(FragmentManager fm, ArrayList<String> fileList) {
			super(fm);
			this.fileList = fileList;
		}

		@Override
		public int getCount() {
			return fileList == null ? 0 : fileList.size();
		}

		@Override
		public Fragment getItem(int position) {
			String url = fileList.get(position);
			return ImageDetailFragment.newInstance(UrlInterface.FILE_URL+"/"+url);
		}

	}

	public void getVoicePath(final int voice) {
		RequestParams params = new RequestParams();

		String url = UrlInterface.TEXT_URL + "record/self/" + voice + ".action";
		AsyncHttpUtil.get(url, params, new AsyncHttpResponseHandler() {
			@Override
			public void onSuccess(int i, Header[] headers, byte[] bytes) {
				String json = new String(bytes);
				ApiMessage apiMessage = ApiMessage.FromJson(json);
				if (apiMessage.Code == 1001) {
					VoiceDate voiceDate = JsonHelper.getJson(apiMessage.Data, VoiceDate.class);
					if(!StringUtils.isBlank(voiceDate.getFilePath())){
					playMusic(voiceDate.getFilePath());
//						playMusic("files/app/2016/06//7d5ea240-4f89-4b89-9202-8ea74daf1d79.AMR");
					}
				}
			}

			@Override
			public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {

			}
		});

	}

	private void playMusic(String filePath) {
		Log.i("voice", filePath);
		Uri uri  =  Uri.parse(filePath);
		player.create(this,Uri.parse(UrlInterface.FILE_URL+"/"+filePath));
		player.start();
	}

	@Override
	protected void onStop() {
		player.stop();
		super.onStop();

	}
}
