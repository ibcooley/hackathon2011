package com.awarity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;

public class Milestone04 extends Activity implements OnClickListener
{
	private DbAdapter mDb;
	private Long mRowId;
	private Button continueBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.milestone04);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

//		mDb = new DbAdapter(this);
//		mDb.open();
//
//		mRowId = (savedInstanceState == null) ? null
//				: (Long) savedInstanceState
//						.getSerializable(DbAdapter.KEY_PLAYER_ROWID);
//		if (mRowId == null)
//		{
//			Bundle extras = getIntent().getExtras();
//			mRowId = extras != null ? extras
//					.getLong(DbAdapter.KEY_PLAYER_ROWID) : null;
//		}
//
//		Cursor playerCursor = mDb.fetchSave(mRowId);
//		startManagingCursor(playerCursor);
//
//		String name = playerCursor.getString(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_NAME));
//		int salary = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_SALARY));
//		int stress = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_STRESS));
//		int health = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_HEALTH));
//		int wealth = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_WEALTH));
//		int education = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_EDUCATION));
//		int vacation = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_VACATION));
//		int milestone = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_MILESTONE));
//		int progress = playerCursor.getInt(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_PROGRESS));
//		Long career = playerCursor.getLong(playerCursor
//				.getColumnIndex(DbAdapter.KEY_PLAYER_CAREER));
//
//		player = new Player(career, name, education, wealth, vacation, salary,
//				stress, health, milestone, progress);

		continueBtn = (Button) findViewById(R.id.milestone04_home_btn);
		continueBtn.setOnClickListener(this);
		continueBtn.setBackgroundResource(0);
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	public void onClick(View v)
	{
		Intent i = new Intent(this, MainActivity.class);
//		i.putExtra(DbAdapter.KEY_PLAYER_ROWID, mRowId);
		startActivity(i);
	}
}
