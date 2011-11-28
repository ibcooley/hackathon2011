package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener
{
	private EditText newTaskEt;
	private Button continueNewBtn;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		setTitle("Awarity");

		newTaskEt = (EditText) findViewById(R.id.main_new_task_et);
		newTaskEt.setVisibility(View.INVISIBLE);

		continueNewBtn = (Button) findViewById(R.id.main_continue_new_btn);
		continueNewBtn.setOnClickListener(this);
		continueNewBtn.setVisibility(View.INVISIBLE);

		Button newBtn = (Button) findViewById(R.id.main_new_btn);
		newBtn.setOnClickListener(this);
		// newBtn.setBackgroundResource(0);

		Button existingBtn = (Button) findViewById(R.id.main_existing_btn);
		existingBtn.setOnClickListener(this);
		// existingBtn.setBackgroundResource(0);

	}

	@Override
	public void onClick(View v)
	{
		switch (v.getId())
		{
		case R.id.main_existing_btn:
			goToExisting();
			break;
		case R.id.main_new_btn:
			newTaskEt.setVisibility(View.VISIBLE);
			newTaskEt.setText("");
			continueNewBtn.setVisibility(View.VISIBLE);
			break;
		case R.id.main_continue_new_btn:
			// Check for null entry
			if (newTaskEt.getText().length() == 0)
				popToast("Must enter task name");
			else
				goToNewDetails();
			break;
		}
	}

	private void goToNewDetails()
	{
		Intent i = new Intent(this, NewDetails.class);
		i.putExtra(Globals.TASK_NAME, newTaskEt.getText());
		startActivityForResult(i, Globals.RC_NEWDETAILS);
	}

	private void goToNew()
	{
		// insert into DB

		Intent i = new Intent(this, Progress.class);
		startActivityForResult(i, Globals.RC_PROGRESS);

	}

	private void popToast(String toastString)
	{
		Toast.makeText(this, toastString, Toast.LENGTH_LONG).show();
	} 

	private void goToExisting()
	{
		Intent i = new Intent(this, ChooseExisting.class);
		startActivityForResult(i, Globals.RC_CHOOSE_EXISTING);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO Auto-generated method stub
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