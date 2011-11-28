package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Progress extends Activity implements OnClickListener,
		OnRatingBarChangeListener
{
	private EditText moreTimeEt, newEffortEt, timeSpentEt;
	private RatingBar satisfactionRbar;
	private long mRowId;

	private DbAdapter mDb;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.progress);
		setTitle("Awarity");

		mDb = new DbAdapter(this);

		Bundle extras = getIntent().getExtras();
		mRowId = extras.getLong(Globals.ROW_ID);

		moreTimeEt = (EditText) findViewById(R.id.progress_more_time_et);
		newEffortEt = (EditText) findViewById(R.id.progress_new_effort_et);
		timeSpentEt = (EditText) findViewById(R.id.progress_time_spent_et);

		Button addAnotherBtn = (Button) findViewById(R.id.progress_add_another_btn);
		addAnotherBtn.setOnClickListener(this);
		// newBtn.setBackgroundResource(0);

		Button obstaclesBtn = (Button) findViewById(R.id.progress_obstacles_btn);
		obstaclesBtn.setOnClickListener(this);
		Button saveCloseBtn = (Button) findViewById(R.id.progress_save_complete_btn);
		saveCloseBtn.setOnClickListener(this);
		Button saveExitBtn = (Button) findViewById(R.id.progress_save_exit_btn);
		saveExitBtn.setOnClickListener(this);
//		Button viewEffortsBtn = (Button) findViewById(R.id.progress_view_efforts_btn);
//		viewEffortsBtn.setOnClickListener(this);

		satisfactionRbar = (RatingBar) findViewById(R.id.progress_satisfaction_ratebar);
		satisfactionRbar.setOnRatingBarChangeListener(this);

	}

	@Override
	public void onClick(View v)
	{
		if (moreTimeEt.getText().length() == 0)
			moreTimeEt.setText("0");
		
		if (timeSpentEt.getText().length() == 0)
			popToast("Must enter time spent");
		else if (newEffortEt.getText().length() == 0)
			popToast("Must enter effort");
		else
		{
			switch (v.getId())
			{
			case R.id.progress_add_another_btn:
				saveEffort();
				goToAddAnother();
				break;
			case R.id.progress_obstacles_btn:
				goToObstacles();
				break;
			case R.id.progress_save_complete_btn:
				saveEffort();
				goToSaveComplete();
				break;
			case R.id.progress_save_exit_btn:
				saveEffort();
				goToSaveExit();
				break;
//			case R.id.progress_view_efforts_btn:
//				goToViewEfforts();
//				break;
			}
		}
	}

	private void saveEffort()
	{
		Effort effort = new Effort();
		effort.setElapsedTime(Integer
				.parseInt(timeSpentEt.getText().toString()));
		effort.setEstCompTime(Integer.parseInt(moreTimeEt.getText().toString()));
		effort.setProgressQuality(satisfactionRbar.getNumStars());
		effort.setDescription(newEffortEt.getText().toString());
		effort.setTaskNumber(mRowId);
		mDb.open();
		mDb.createEffort(effort);
		mDb.close();
	}

	private void goToViewEfforts()
	{
		// Create view page for currently added efforts
		Intent i = new Intent(this, ViewEfforts.class);
		startActivity(i);
	}

	private void popToast(String toastString)
	{
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}

	private void goToSaveExit()
	{
		// save all data
		finishActivity(Globals.RC_PROGRESS);
	}

	private void goToSaveComplete()
	{
		Intent i = new Intent(this, Closeout.class);
		startActivityForResult(i, Globals.RC_CLOSEOUT);
	}

	private void goToObstacles()
	{
		Intent i = new Intent(this, Barriers.class);
		startActivityForResult(i, Globals.RC_OBSTACLES);
	}

	private void goToAddAnother()
	{
		// Intent i = new Intent(this, Progress.class);
		// startActivityForResult(i, Globals.RC_PROGRESS);
		moreTimeEt.setText("");
		newEffortEt.setText("");
		timeSpentEt.setText("");
		satisfactionRbar.setRating(0);
	}

	@Override
	public void onRatingChanged(RatingBar arg0, float arg1, boolean arg2)
	{
		// TODO Auto-generated method stub

	}
}
