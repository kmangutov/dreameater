package com.example.dreameater;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

	private static final String BOOT_INTENT = "android.intent.action.BOOT_COMPLETED";
	private static final int MORNING_NOTIFICATION = 0;
	
	@Override
	public void onReceive(Context ctx, Intent intent) 
	{
		if(intent.getAction().equals(AlarmService.MORN_NOTIFICATION_INTENT))
			installMorningNotification(ctx);

		if(intent.getAction().equals(BOOT_INTENT))
			new AlarmService(ctx).enableAlarm();
	}

	public void installMorningNotification(Context ctx)
	{
		NotificationCompat.Builder builder;
		builder = new NotificationCompat.Builder(ctx)
			.setSmallIcon(R.drawable.ic_launcher)
			.setContentTitle("Dream Eater")
			.setContentText("Log your dream")
			.setAutoCancel(true);
		
		//Intent notificationIntent = new Intent("com.example.MORNING_ACTION");
		Intent notificationIntent = new Intent(ctx, WriteActivity.class);
		PendingIntent futureIntent = PendingIntent.getActivity(ctx, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(futureIntent);
		
		NotificationManager manager;
		manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		
		manager.notify(MORNING_NOTIFICATION, builder.build());
	}
	
	public void removeMorningNotification(Context ctx)
	{
		NotificationManager manager;
		manager = (NotificationManager) ctx.getSystemService(Context.NOTIFICATION_SERVICE);
		
		manager.cancel(MORNING_NOTIFICATION);
	}
}
