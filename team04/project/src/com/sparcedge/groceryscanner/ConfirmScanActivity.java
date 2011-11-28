package com.sparcedge.groceryscanner;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class ConfirmScanActivity extends Activity {
	
	private String listName;
	private String product;
	private String urlName;
	private String scan;

//quantity confirm
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.viewitemdetails);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		urlName = ConfirmScanActivity.this.getIntent().getStringExtra("url");
		product = ConfirmScanActivity.this.getIntent().getStringExtra("product");
		listName = ConfirmScanActivity.this.getIntent().getStringExtra("list");
		scan = ConfirmScanActivity.this.getIntent().getStringExtra("scan");

		
		TextView p =(TextView)findViewById(R.id.product);
		p.setText(product);
		
		//display the image
		ImageView image = (ImageView)findViewById(R.id.itempic);
		URL url;
		InputStream content = null;
		try {
			url = new URL(urlName);
			content = (InputStream)url.getContent();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Drawable d = Drawable.createFromStream(content ,urlName );
		image.setImageDrawable(d);
		
		Button confirm = (Button)findViewById(R.id.confirm);
		confirm.setOnClickListener(confirmListener);
	}
	
	 private OnClickListener confirmListener = new OnClickListener() {
	        public void onClick(View v) {
	        	String quantity = ((EditText)findViewById(R.id.quantity)).getText().toString().trim();
	            ItemInfo iteminfo = new ItemInfo(listName,product,scan,urlName,quantity);
	            GroceryScanner.LOG(listName+product+scan+urlName);
	            GroceryScanner.db.open();
	            GroceryScanner.db.insertEntry(iteminfo);
	            GroceryScanner.db.close();
	            finish();
	        }
	    };

}
