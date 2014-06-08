package com.example.dreameater;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class WriteActivity extends Activity {

	DatabaseHandler mDatabase;
	public static final String EXTRA_DATE = "EXTRA_DATE";
//	public static final int EXTRA_ENTRY_ID = "EXTRA_ENTRY_ID";
	
	private String mDate = "";
	
	TextView textCurrentDate;
	TextView editContents;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_write);
		
		textCurrentDate = (TextView) findViewById(R.id.textCurrentDate);
		editContents = (TextView) findViewById(R.id.editContents);
		
		initDate();
		textCurrentDate.setText(mDate);
		
		mDatabase = new DatabaseHandler(this);
		loadContents();
	}
		
	private void loadContents() {
		LogEntry existing = mDatabase.getEntry(mDate);
		
		if(existing != null)
			editContents.setText(existing.getContents());
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		
		
		LogEntry current = new LogEntry();
		current.setDate(mDate);
		current.setContents(editContents.getText().toString());
		
		LogEntry existing = mDatabase.getEntry(mDate);
		
		if(existing == null)
			mDatabase.addEntry(current);
		else
			mDatabase.updateEntry(current);
	
	}
	
	private void initDate()
	{
		Intent intent = getIntent();
		String intentDate = intent.getStringExtra(EXTRA_DATE);
		
		if(intentDate != null) {
			mDate = intentDate;
		} else {
			mDate = currentDateAsString();
		}
	}
	
	private static String currentDateAsString() {
		Date date = new Date();
		return new SimpleDateFormat("MM-dd-yyyy").format(date);
	}
}
