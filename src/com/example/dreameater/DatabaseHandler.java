package com.example.dreameater;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "dream_eater_db";
	private static final String TABLE_ENTRIES = "entries";
	
	private static final String KEY_ID = "id";
	private static final String KEY_DATE = "date";
	private static final String KEY_CONTENTS = "contents";
	
	private static final String QUERY_CREATE_ENTRIES = 
			"create table " + TABLE_ENTRIES + " ("
			+ KEY_ID + " integer primary key, "
			+ KEY_DATE + " text,"
			+ KEY_CONTENTS + " text)";
	private static final String QUERY_DROP_ENTRIES = 
			"drop table if exists " + TABLE_ENTRIES;
				
	
	public long addEntry(LogEntry e) {
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		
		values.put(KEY_DATE, e.getDate());
		values.put(KEY_CONTENTS, e.getContents());
		
		long id = db.insert(TABLE_ENTRIES, null, values);
		db.close();
		
		return id;
	}
	
	public LogEntry getEntry(String date) {
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor cursor = db.query(
				TABLE_ENTRIES,
				new String[] { KEY_ID, KEY_DATE, KEY_CONTENTS },
				KEY_DATE + "=?",
				new String[] { date },
				null, null, null, null);
		
		if(cursor != null && cursor.getCount() != 0)
			cursor.moveToFirst();
		else
			return null;
		
		LogEntry e = fromCursor(cursor);
		db.close();
		
		return e;
	}
	
	public LogEntry getEntry(int id) {
		SQLiteDatabase db = getWritableDatabase();
		
		Cursor cursor = db.query(
				TABLE_ENTRIES,
				new String[] { KEY_ID, KEY_DATE, KEY_CONTENTS },
				KEY_ID + "=?",
				new String[] { String.valueOf(id) },
				null, null, null, null);
		
		if(cursor != null)
			cursor.moveToFirst();
		
		LogEntry e = fromCursor(cursor);
		db.close();
		
		return e;
	}
	
	public List<LogEntry> getEntries() {
		List<LogEntry> entries = new ArrayList<LogEntry>();
		String selectQuery = "select * from " + TABLE_ENTRIES + " order by " + KEY_ID + " desc";
		
		SQLiteDatabase db = getWritableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);
		
		if(cursor.moveToFirst()) {
			do {
				LogEntry entry = fromCursor(cursor);
				entries.add(entry);
			} while (cursor.moveToNext());
		}
		db.close();
		
		return entries;
	}
	
	private LogEntry fromCursor(Cursor cursor) {
		return new LogEntry(
				cursor.getInt(0),
				cursor.getString(1),
				cursor.getString(2));
	}
	
	//update contents of entry with same date
	public int updateEntry(LogEntry e) {
		SQLiteDatabase db = getWritableDatabase();
		
		ContentValues values = new ContentValues();
		values.put(KEY_DATE, e.getDate());
		values.put(KEY_CONTENTS, e.getContents());
		
		int r = db.update(TABLE_ENTRIES, values, KEY_DATE + "=?", new String[]{e.getDate()+""});
		db.close();
		return r;
	}
	
	public DatabaseHandler(Context ctx) {
		super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	public void insertDummy() {
		
		LogEntry e = new LogEntry();
		e.setDate("1/2/3");
		e.setContents("test");
		
		addEntry(e);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(QUERY_CREATE_ENTRIES);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int v0, int v1) {
		db.execSQL(QUERY_DROP_ENTRIES);
		onCreate(db);
	}

}
