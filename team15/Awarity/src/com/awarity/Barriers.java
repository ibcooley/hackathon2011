package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Barriers extends Activity implements OnClickListener {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.barriers);
		Button existingBtn = (Button) findViewById(R.id.barriers_done_btn);
		existingBtn.setOnClickListener(this);
		//existingBtn.setBackgroundResource(0);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.barriers_done_btn:
			goToReturn();
			break;
		}		
	}

	private void goToReturn()
	{
		//save all data return to calling activity
		//return resultcode for success failure? Number of obstacles set?
		Intent i = new Intent();
		//i.putExtra(Globals.FILE_NAME, file.getName());
		setResult(Globals.RC_BARRIERS, i);
		finish();
	}
}
