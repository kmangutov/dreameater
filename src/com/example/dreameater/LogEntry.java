package com.example.dreameater;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LogEntry 
{
	private int mId;
	private String mDate;
	private String mContents;
	
	public LogEntry() {
		
	}
	
	public LogEntry(int id, String date, String contents) {
		this.mId = id;
		this.mDate = date;
		this.mContents = contents;
	}
	
	public Date getDateAsDate() {
		
		try {
			return new SimpleDateFormat("MM-dd-yyyy").parse(mDate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
			
	}
	
	public int getId() {
		return mId;
	}
	public void setId(int id) {
		this.mId = id;
	}
	public String getDate() {
		return mDate;
	}
	public void setDate(String date) {
		this.mDate = date;
	}
	public String getContents() {
		return mContents;
	}
	public void setContents(String contents) {
		this.mContents = contents;
	}
	
	public String toString() {
		return mDate;
	}
}
