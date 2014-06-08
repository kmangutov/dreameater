package com.example.dreameater;

import java.util.Calendar;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class WelcomeActivity extends Activity implements OnItemClickListener {

	Button buttonSetAlarm;
	ListView listViewEntries;
	CheckBox checkBoxEnabled;
	
	AlarmService mAlarmService;
	DatabaseHandler mDatabase;
	EntryListAdapter mEntryListAdapter;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        
        buttonSetAlarm = (Button) findViewById(R.id.buttonSetAlarm);
        listViewEntries = (ListView) findViewById(R.id.listViewEntries);
        checkBoxEnabled = (CheckBox) findViewById(R.id.checkBoxEnabled);
        
        mDatabase = new DatabaseHandler(this);
        mAlarmService = new AlarmService(this);
       
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
        if(mAlarmService.alarmExists())
        	checkBoxEnabled.setChecked(true);
        else
        	checkBoxEnabled.setChecked(false);
    
        //need to update list with new entries...
        mEntryListAdapter = new EntryListAdapter(this, mDatabase.getEntries());
        listViewEntries.setAdapter(mEntryListAdapter);
        listViewEntries.setOnItemClickListener(this);
    }
    
    public void onToggleEnabled	(View v) {
    	
    	boolean checked = checkBoxEnabled.isChecked();
    	
    	if(checked)
    		enableAlarm();
    	else
    		mAlarmService.cancelAlarm();
    }
    
    public Calendar testCalendar() {
		Calendar c = Calendar.getInstance();
		
		c.set(Calendar.HOUR_OF_DAY, 6);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		Calendar now = Calendar.getInstance();
		
		if(now.after(c))
			c.add(Calendar.DAY_OF_YEAR, 1);
		
		return c;
    }
    
    public void enableAlarm() {
    	
    	//if(alarmService.alarmExists())
    	//	return;
    	
    	mAlarmService.setAlarm(testCalendar());
    }
    
    
    public void NoteButtonClick(View v) {
    	Intent intent = new Intent(this, WriteActivity.class);
    	startActivity(intent);
    }
    
    public void SetAlarmButtonClick(View v) {
    	
    	boolean alarmEnabled = mAlarmService.alarmExists();
    	new AlertDialog.Builder(this)
    		.setTitle("Alarm exists?")
    		.setMessage(alarmEnabled + ".")
    		.show();
    	
    	//enableAlarm();
    }

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		//String selected = (String) ((TextView) view).getText();
		String selected = (String) view.getTag();
		
		//int selectedId = (Integer) view.getTag();
		
    	Intent intent = new Intent(this, WriteActivity.class);
    	intent.putExtra(WriteActivity.EXTRA_DATE, selected);
    	startActivity(intent);
		
	}

}
