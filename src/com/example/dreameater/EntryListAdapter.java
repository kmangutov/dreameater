package com.example.dreameater;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class EntryListAdapter extends BaseAdapter {

	private Context mCtx;
	protected List<LogEntry> mEntries;
	
	private final int SUMMARY_LENGTH = 25;
	
	public EntryListAdapter(Context ctx, List<LogEntry> entries) {
		mCtx = ctx;
		mEntries = entries;
	}
	
	@Override
	public int getCount() {
		return mEntries.size();
	}
	@Override
	public LogEntry getItem(int position) {
		return mEntries.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	public String sanitizeSummary(String summary) {
		
		if(summary.length() > SUMMARY_LENGTH)
			summary = summary.substring(0, SUMMARY_LENGTH) + "..."; 
		
		return summary.replaceAll("\n", " ");
	}
	
	@Override
	public View getView(int position, View view, ViewGroup parent) {

		LayoutInflater inflater =
				(LayoutInflater) mCtx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.listview_entry_item, null);
		
		LogEntry entry = mEntries.get(position);
		
		String date = entry.getDate();
		Date dateDate = entry.getDateAsDate();
		String[] dateSplit = date.split("-");
		String summary = sanitizeSummary(entry.getContents());
		
		
		TextView textEntryDate = 
				(TextView) view.findViewById(R.id.textEntryDate);
		TextView textEntrySnippet = 
				(TextView) view.findViewById(R.id.textEntrySnippet);
		
		if(dateDate == null)
			textEntryDate.setText(date);
		else
			textEntryDate.setText(Html.fromHtml("<font color='#A9A9A9'><small>" + dateSplit[0] + "</small>  " + dateSplit[1] + "</font>"));
		textEntrySnippet.setText(summary);
		view.setTag(date);
		
		return view;
	}
	
	
	
	
}
