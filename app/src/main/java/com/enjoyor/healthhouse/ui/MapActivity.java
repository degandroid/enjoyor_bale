package com.enjoyor.healthhouse.ui;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.enjoyor.healthhouse.R;
import com.enjoyor.healthhouse.url.JavaScriptInterface;
import com.enjoyor.healthhouse.url.UrlInterface;
import com.enjoyor.healthhouse.utils.StringUtils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by YuanYuan on 2016/5/17.
 */
public class MapActivity extends BaseActivity {
    private String positionLong;
    private String positionLat;
    double jindu;
    double weidu;
    String address;
    String context;
    @Bind(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_ac_layout);
        ButterKnife.bind(this);
        initData();
        initEvent();
    }

    private void initEvent() {
        if (!StringUtils.isEmpty(positionLong) && !StringUtils.isEmpty(positionLat)) {
            webView.setWebViewClient(new WebViewClient() {
                public boolean shouldOverrideUrlLoading(WebView view, String url) { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                    view.loadUrl(url);
                    return true;
                }
            });
            jindu = Double.parseDouble(positionLong);
            weidu = Double.parseDouble(positionLat);
            Log.i("----", jindu + "===========" + weidu);
            if (StringUtils.isEmpty(address)) {
                address = "未知检测点";
            }
            Log.i("----", jindu + "");
            Log.i("----", weidu + "");
            webView.loadUrl(UrlInterface.Web_URL + "location=" + weidu + "," + jindu + "&title=" + address + "&content=&output=html&src=appName");
            WebSettings mWebSettings = webView.getSettings();
            mWebSettings.setJavaScriptEnabled(true);
            mWebSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
            mWebSettings.setSupportZoom(true);// 用于设置webview放大
            mWebSettings.setBuiltInZoomControls(true);
            mWebSettings.setDomStorageEnabled(true);
            mWebSettings.setUseWideViewPort(true);
            mWebSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        }
    }

    private void initData() {
        positionLat = getIntent().getStringExtra("latitude");
        positionLong = getIntent().getStringExtra("longitude");
        address = getIntent().getStringExtra("addr");
    }
}
