package com.sparcedge;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends Activity {

	private class DevicesButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			handleDevicesButtonClick();
		}
	}
	
	private class RoomsButtonHandler implements View.OnClickListener {
		public void onClick(View v) {
			handleRoomsButtonClick();
		}
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.menu);

		final Button devicesButton = (Button) findViewById(R.id.devicesButton);
		
		final Button roomsButton = (Button) findViewById(R.id.roomsButton);
		

		devicesButton.setOnClickListener(new DevicesButtonHandler());
		roomsButton.setOnClickListener(new RoomsButtonHandler());
	}

	public void handleDevicesButtonClick() {
		startActivity(new Intent(this, DeviceListActivity.class));
	}
	
	public void handleRoomsButtonClick() {
		startActivity(new Intent(this, RoomsListActivity.class));
	}

}
