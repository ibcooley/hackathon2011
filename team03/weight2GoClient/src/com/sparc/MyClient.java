package com.sparc;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MyClient extends WebViewClient {
	public boolean shouldOverrideUrlLoading(WebView view, String url) {  
	      view.loadUrl(url);  
	      return true;  
	    } 

}
