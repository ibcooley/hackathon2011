package com.sparc;

import android.app.Activity;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class SampleWebView extends Activity {
    private WebView webview;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.main);
        webview = (WebView) findViewById(R.id.webview);
        webview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);//        webSettings.setUseWideViewPort(true);
        webview.addJavascriptInterface(new JavascriptInterface(this, lm), "Android");
        final Activity activity = this;
        webview.setWebViewClient(new MyClient());

        WebSettings webSettings = webview.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setLightTouchEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webview.loadUrl("http://ec2-184-72-184-176.compute-1.amazonaws.com:8080/weight2go/");
//        webview.loadUrl("localhost:8080/Beer30Web");
    }
}