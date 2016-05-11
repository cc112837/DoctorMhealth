package com.cc.doctormhealth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.cc.doctormhealth.MyApplication;
import com.cc.doctormhealth.constant.Constants;
import com.cc.doctormhealth.utils.DateUtil;

import java.util.ArrayList;
import java.util.List;


public class PatientDbHelper {
private static PatientDbHelper instance = null;
	
	private SqlLiteHelper helper;
	private SQLiteDatabase db;  // 邀请我的
	
	public PatientDbHelper(Context context) {
		helper = new SqlLiteHelper(context);
		db = helper.getWritableDatabase();
	}

	public void closeDb(){
		db.close();
		helper.close();
	}
	public static PatientDbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new PatientDbHelper(context);
		}
		return instance;
	}
	
	private class SqlLiteHelper extends SQLiteOpenHelper {

		private static final int DB_VERSION = 1;
		private static final String DB_NAME = "Patient";

		public SqlLiteHelper(Context context) {
			super(context, DB_NAME, null, DB_VERSION);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE  IF NOT EXISTS " + DB_NAME
						+ "( id INTEGER PRIMARY KEY AUTOINCREMENT,"+
						"username text ,userAge text,userSex text,date text" +
						"whos text,i_filed INTEGER,t_field text)";
			db.execSQL(sql);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			dropTable(db);
			onCreate(db);
		}

		private void dropTable(SQLiteDatabase db) {
			String sql = "DROP TABLE IF EXISTS "+DB_NAME;
			db.execSQL(sql);
		}

	}

	public void savePatient(String username){
		int nowCount = getCount(username);
		ContentValues values = new ContentValues();
		if (nowCount == 0) {
			values.put("username", username);
			values.put("sendDate", DateUtil.now_yyyy_MM_dd_HH_mm_ss());
			values.put("whos", Constants.USER_NAME);
			db.insert(helper.DB_NAME, "id", values);
		}
		else{
			values.put("sendDate", DateUtil.now_yyyy_MM_dd_HH_mm_ss());
			values.put("isDeal", 0);
			db.update(helper.DB_NAME, values, " username=? and whos=?", 
					new String[]{username,Constants.USER_NAME});
		}
		PatientDbHelper.getInstance(MyApplication.getInstance()).savePatient (""+0);
	}
	
	public void delFriend(String username){
		ContentValues values = new ContentValues();
		values.put("isDeal", 1);
		db.update(helper.DB_NAME, values, " username=? and whos=?", 
				new String[]{username,Constants.USER_NAME});
	}

	/**
	 * 取邀请我的
	 */
	public List<String> getNewFriend(){
		List<String> friends = new ArrayList<String>();
		String sql = "select username from " +helper.DB_NAME +
				" where whos = ? order by sendDate desc";
		Cursor cursor = db.rawQuery(sql, new String[]{Constants.USER_NAME});
		while(cursor.moveToNext()){
			friends.add(cursor.getString(0));
		}
		cursor.close();
		return friends;
	}
	
	//某个人
	public int getCount(String username){   
		int count = 0 ;
		String sql ="select count(0) from "+helper.DB_NAME+" where username=? and whos=?";
		Cursor cursor = db.rawQuery(sql, new String[]{username,Constants.USER_NAME});
		while(cursor.moveToNext()){
			count = cursor.getInt(0);
		}
		cursor.close();
		return count;
	}
	
	public void clear(){
		db.delete(helper.DB_NAME, "id>?", new String[]{"0"}); 
	}
}
