package com.example.dreameater;

import java.util.Calendar;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmService 
{
	private Context mCtx;
	private PendingIntent mPendingIntent;
	
	public static final int REQUEST_CODE = 1;

	public static final String MORN_NOTIFICATION_INTENT = "com.example.dreameater.NOTIFICATION_INTENT";
	
	public AlarmService(Context context) {
		mCtx = context;
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
    	
    	setAlarm(testCalendar());
    }

	public boolean alarmExists() {
		
		return (PendingIntent.getBroadcast(
				mCtx, 
				REQUEST_CODE, 
				new Intent(MORN_NOTIFICATION_INTENT), 
				PendingIntent.FLAG_NO_CREATE) != null);
	}
	
	public void setAlarm(Calendar time) {
		
		long timeMillis = time.getTimeInMillis();
		
		Intent intent = new Intent(MORN_NOTIFICATION_INTENT);
		mPendingIntent = PendingIntent.getBroadcast(mCtx, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager manager = (AlarmManager)mCtx.getSystemService(Context.ALARM_SERVICE);
		
		manager.setInexactRepeating(AlarmManager.RTC, timeMillis, AlarmManager.INTERVAL_DAY, mPendingIntent);
		//manager.set(AlarmManager.RTC, timeMillis, mPendingIntent);
	}
	
	public void cancelAlarm() {
		
		Intent intent = new Intent(MORN_NOTIFICATION_INTENT);
		mPendingIntent = PendingIntent.getBroadcast(mCtx, REQUEST_CODE, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		
		AlarmManager manager = (AlarmManager)mCtx.getSystemService(Context.ALARM_SERVICE);
		manager.cancel(mPendingIntent);
		mPendingIntent.cancel();
	}
}
