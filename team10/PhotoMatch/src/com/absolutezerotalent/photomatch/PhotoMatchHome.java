package com.absolutezerotalent.photomatch;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageButton;

public class PhotoMatchHome extends Activity {
	/** Called when the activity is first created. */

	ImageButton connectToPicasa, manage, memorize;
	WebView mWebView;
	SharedPreferences settings;
	ArrayList<ImageItem> imageUrlList;
	ImageAdapter mImageAdapter;
	Context c;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		c = getApplicationContext();
		// check to see if user has logged in before
		settings = getSharedPreferences(Constants.PREFERENCES_NAME, MODE_PRIVATE);
		Boolean isLoggedIn = settings.getBoolean("loggedIn", false);

		if(!isLoggedIn){
			// we're not logged in; show login button
			setContentView(R.layout.home_portriat);
			// get handle on button
			connectToPicasa = (ImageButton)findViewById(R.id.connectButton);
			// get handle on webview
			mWebView = (WebView)findViewById(R.id.oAuthWebView);

			// make the button do something
			connectToPicasa.setOnClickListener(new OnClickListener() {

				//@Override
				public void onClick(View v) {
					// We've clicked something; login to picasa
					Log.d("Log Notes", "Connecting to Picassa");
					connect();

				}
			});
		}else{
			// We've connected; show the alternative screen
			setHome();
			manage = (ImageButton)findViewById(R.id.manage);
			memorize = (ImageButton)findViewById(R.id.memorize);
			
			manage.setOnClickListener(new OnClickListener() {
				
				//@Override
				public void onClick(View v) {
					// fire the manage page
					Intent i = new Intent(PhotoMatchHome.this, Manage.class);
					startActivity(i);
				}
			});
			
			memorize.setOnClickListener(new OnClickListener() {
				
				//@Override
				public void onClick(View v) {
					// fire the activity page
					Intent i = new Intent(PhotoMatchHome.this, Memorize.class);
					startActivity(i);
				}
			});
		}


	}

	private void setHome() {
		// set proper background
		setContentView(R.layout.home_loggedin_portriat);
		
	}

	protected void connect() {
		// Make auth call
		// Construct the url
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("client_id", Constants.CLIENT_ID);
		parameters.put("redirect_uri", "http://localhost");
		parameters.put("scope", "https://picasaweb.google.com/data/");
		parameters.put("response_type", "code");

		String url = createURL(Constants.OAUTH_URL, parameters);
		// make the webview visible
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setVisibility(View.VISIBLE);
		mWebView.setWebViewClient(new WebViewClient(){
			// TODO: HANDLE IF THE USER DENIES US ACCESS
			@Override
			public void onPageFinished(WebView view, String url)  {
				if(url.startsWith(Constants.OAUTH_CALLBACK_URL)){
					if(url.indexOf("code=")!=-1){
						String code = extractCodeFromUrl(url);
						// set the preference that we've logged in and
						SharedPreferences.Editor prefsEditor = settings.edit();
						prefsEditor.putBoolean("loggedIn", true);
						prefsEditor.putString("code", code);
						prefsEditor.commit();
						// close the webView
						mWebView.setVisibility(View.GONE);
						
						// set alternative View
						//wizard();
						setHome();
					}
				}
			}

			private String extractCodeFromUrl(String url) {
				
				String code = url.split("=")[1];
				Log.d("THe code", code);
				return code;
			}
		});
		
		mWebView.requestFocus(View.FOCUS_DOWN);
		mWebView.setOnTouchListener(new View.OnTouchListener()
		{
		    //@Override
		    public boolean onTouch(View v, MotionEvent event)
		    {
		        switch (event.getAction())
		        {
		            case MotionEvent.ACTION_DOWN:
		            case MotionEvent.ACTION_UP:
		                if (!v.hasFocus())
		                {
		                    v.requestFocus();
		                }
		                break;
		        }
		        return false;
		    }
		});
		mWebView.loadUrl(url);
	}


	protected void wizard() {
		// Wizard for getting picassa pics
		setContentView(R.layout.picassagallery);
		
		// display loading images progress bar
		
		// get albums
		ArrayList<String> albums = getAlbums();
		
		GridView gridView = (GridView) findViewById(R.id.picassaGrid);
		
		
		gridView.setAdapter(new ImageAdapter(c));
	}

	private ArrayList<String> getAlbums() {
		ArrayList<String> albums = new ArrayList<String>();
		String url = "http://picasaweb.google.com/data/feed/api/user/triplemwebdesigns?access_token=" + settings.getString("code", null);
		
				Log.d("THE RULE", url);
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httppost = new HttpGet(URI.create(url));
		HttpResponse response;
		try {
		response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		InputStream stream = entity.getContent();
		Log.d("response", stream_reader(stream));
		} catch (ClientProtocolException e) {
		//Handle not connecting to client
		Log.d("ClientProtocolException Thrown", e.toString());
		} catch (IOException e) {
		//couldn't connect to host
		//TODO: HANDLE NOT CONNECTING TO CLIENT
		Log.d("IOException Thrown", e.toString());
		}
//		Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//
//		startActivity(myIntent);
		
		return albums;
	}

	public static String stream_reader(InputStream in) throws IOException {
		StringBuffer stream = new StringBuffer();
		byte[] b = new byte[4096];
		for (int n; (n = in.read(b)) != -1;) {
		stream.append(new String(b, 0, n));
		}
		return stream.toString();
		}

	public String createURL(String oauthUrl, HashMap<String, String> parameters) {
		// TODO Auto-generated method stub
		String url = oauthUrl;
		Iterator<String> keys = parameters.keySet().iterator();
		for(int i = 0; i < parameters.size(); i++){
			// append the parameter and value onto url
			if(i == 0){
				//add ?
				url += "?";
			}
			// append the parameter name
			String nextKey = keys.next();
			url += nextKey;
			// append the '='
			url += "=";
			// append the value
			url += parameters.get(nextKey);
			if(!(i == parameters.size() - 1)){
				url += "&";
			}
		}
		//TODO: UTF-8 encode the whole thing

		//
		Log.d("URL", url);
		return url;
	}
	
	 private class ImageAdapter extends ArrayAdapter<String>{
	    	private ArrayList<String> items;
	    	
	    	public ImageAdapter(Context context) {
				super(context, 0);
				//this.items = imageUrlList;
			}
	    	
	    }

}