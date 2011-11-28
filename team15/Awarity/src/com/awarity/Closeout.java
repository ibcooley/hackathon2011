package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Closeout extends Activity implements OnClickListener {
	/**
	 * @see android.app.Activity#onCreate(Bundle)
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// TODO Put your code here
		setContentView(R.layout.closeout);
		setTitle("Completion");
		Button exitBtn = (Button) findViewById(R.id.closeout_exit_btn);
		exitBtn.setOnClickListener(this);
		Button viewRptBtn = (Button) findViewById(R.id.closeout_view_report_btn);
		viewRptBtn.setOnClickListener(this);
		// existingBtn.setBackgroundResource(0);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.closeout_exit_btn:
			setResult(Globals.RC_CLOSEOUT);
			finish();
			break;
		case R.id.closeout_view_report_btn:
			Intent i = new Intent(this, Milestone04.class);
			startActivity(i);
			break;
		}
	}

}
