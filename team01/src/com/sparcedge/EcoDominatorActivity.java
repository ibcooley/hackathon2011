package com.sparcedge;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sparcedge.database.*;
import com.sparcedge.model.api.Device;

public class EcoDominatorActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        
        
        final Button button = (Button) findViewById(R.id.devicesButton);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	setContentView(R.layout.device_list);
            }
        });

    }

	
}