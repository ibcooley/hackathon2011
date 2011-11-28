package com.sparcedge.groceryscanner;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends Activity {
	
	private String listName;
	
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additem);
        listName = AddItemActivity.this.getIntent().getStringExtra("myKey");
        TextView t=(TextView)findViewById(R.id.name); 
        t.setText(listName);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Button scanButton = (Button)findViewById(R.id.scan);
        scanButton.setOnClickListener(scanListener);
        
        Button addButton  = (Button)findViewById(R.id.addmanually);
        addButton.setOnClickListener(addListener);



    }
    
    private OnClickListener scanListener = new OnClickListener() {
        public void onClick(View v) {
        	Toast.makeText(AddItemActivity.this,"Scanning...", Toast.LENGTH_SHORT).show();        
            Intent intentScan = new Intent("com.google.zxing.client.android.SCAN");
        	intentScan.addCategory(Intent.CATEGORY_DEFAULT);
        	try {
        		startActivityForResult(intentScan, GroceryScanner.REQUEST_BARCODE_SCAN);               
	        } catch (ActivityNotFoundException e) {
	            AlertDialog.Builder downloadDialog = new AlertDialog.Builder(getApplicationContext());
	            downloadDialog.setTitle("Install Barcode Scanner?");
	            downloadDialog.setMessage("This application can use the camera for Barcode Scanning. Would you like to install the camera scanner?");
	            downloadDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialogInterface, int i) {
	                    Uri uri = Uri.parse("market://search?q=pname:com.google.zxing.client.android");
	                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
	                    startActivity(intent);
	                  }
	                });
	            downloadDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
	                  public void onClick(DialogInterface dialogInterface, int i) {}
	                });
	            downloadDialog.show();
	        }
        }
    };
    
    private OnClickListener addListener = new OnClickListener() {
        public void onClick(View v) {
        	Toast.makeText(AddItemActivity.this,"Add manually", Toast.LENGTH_SHORT).show();        
        	
        }
    };
    
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        GroceryScanner.LOG( "onActivityResult: " + requestCode + " " + resultCode);
        switch (requestCode) {
        case GroceryScanner.REQUEST_BARCODE_SCAN:
            if (resultCode == RESULT_OK) {
            	String scan = data.getStringExtra("SCAN_RESULT");
            	GroceryScanner.LOG("barcode: " + scan);
            	UpcDecoder bc = new UpcDecoder(scan);
            	bc.get();
            	String imageURL = bc.getImageURL();
            	String productName = bc.getProductName();
            	confirmScan(imageURL,productName,scan);
            } else if (resultCode == RESULT_CANCELED) {
            	//Debug("scan canceled");
            }
            break;
   
        }
    }
    
    public void confirmScan(String url, String product,String scan)
    {
    	Intent i = new Intent(AddItemActivity.this,ConfirmScanActivity.class);
    	i.putExtra("list", listName);
    	i.putExtra("url",url);
    	i.putExtra("product", product);
    	i.putExtra("scan", scan);
    	AddItemActivity.this.startActivity(i);

    }


}
