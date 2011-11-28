package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView.SavedState;

public class NewDetails extends Activity implements OnClickListener
{

	private EditText timeAllottedEt;
	private AutoCompleteTextView workTypeActv;
	private String taskName = "MyTask";
	private DbAdapter mDb;
	private Task task = new Task();
	private long mRowId;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.new_details);
		setTitle("Testing");

		mDb = new DbAdapter(this);
		mDb.open();

		// if (savedInstanceState == null)
		// {
		// Bundle extras = getIntent().getExtras();
		// taskName = extras.getString(Globals.TASK_NAME);
		// }
		// else
		// {
		// taskName = (String)
		// savedInstanceState.getSerializable(Globals.TASK_NAME);
		// }

		Bundle extras = getIntent().getExtras();
		taskName = extras.getString(Globals.TASK_NAME);

		setTitle(taskName);

		timeAllottedEt = (EditText) findViewById(R.id.new_details_time_allotted_et);
		workTypeActv = (AutoCompleteTextView) findViewById(R.id.new_details_work_type_actv);

		Button exitBtn = (Button) findViewById(R.id.new_details_exit_btn);
		exitBtn.setOnClickListener(this);
		// newBtn.setBackgroundResource(0);

		Button continueBtn = (Button) findViewById(R.id.new_details_continue_btn);
		continueBtn.setOnClickListener(this);
		// existingBtn.setBackgroundResource(0);
		Button dependenciesBtn = (Button) findViewById(R.id.new_details_dependencies_btn);
		dependenciesBtn.setOnClickListener(this);
		// existingBtn.setBackgroundResource(0);

		mDb.close();

	}

	@Override
	protected void onPause()
	{
		mDb.close();
		super.onPause();
	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.new_details_exit_btn:
			setResult(Globals.RC_NEWDETAILS);
			finish();
			break;
		case R.id.new_details_continue_btn:
			// Check for null entry
			if (workTypeActv.getText().length() == 0)
				popToast("Must enter work type");
			else if (timeAllottedEt.getText().length() == 0)
				popToast("Must enter time allotted");
			else
				goToNew();
			break;
		case R.id.new_details_dependencies_btn:
			// allow dependency entry
			if (workTypeActv.getText().length() == 0)
				popToast("Must enter work type");
			else if (timeAllottedEt.getText().length() == 0)
				popToast("Must enter time allotted");
			else
				goToDependency();
			break;
		}
	}

	private void goToDependency()
	{
		Intent i = new Intent(this, DependencyActivity.class);
		saveTask();
		i.putExtra(Globals.ROW_ID, mRowId);
		startActivityForResult(i, Globals.RC_DEPENDENCY);
	}

	private void goToNew()
	{
		saveTask();
		Intent i = new Intent(this, Progress.class);
		i.putExtra(Globals.ROW_ID, mRowId);
		startActivityForResult(i, Globals.RC_PROGRESS);
	}

	private void saveTask()
	{
		// save data to database
		task.setETA(timeAllottedEt.getText().toString());
		task.setName(taskName);
		task.setTaskComplete(false);
		mDb.open();
		mRowId = mDb.createTask(task);
		mDb.close();
	}

	private void popToast(String toastString)
	{
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode)
		{
		case Globals.RC_NEWDETAILS:
			finish();
			break;
		case Globals.RC_CHOOSE_EXISTING:
			break;
		default:
			finish();
			break;
		}
	}
}
