package com.cicasiano.sscreener.model;

import java.io.File;
import java.io.Serializable;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cicasiano.sscreener.utils.Tracer;

public class SQLite4jWrapper implements Serializable {
	private static final long serialVersionUID = 1L;
	private static final String DB_PATH = "assets/db.sqlite";
	private SQLiteConnection db= null;
	private SQLite4jWrapper() {
		// private constructor
	}

	private static class SQLite4jWrapperHolder {
		public static final SQLite4jWrapper INSTANCE = new SQLite4jWrapper();
		public static final SQLiteConnection db = new SQLiteConnection(new File(DB_PATH));
	}

	public static SQLite4jWrapper getInstance() {
		return SQLite4jWrapperHolder.INSTANCE;
	}
	
	public boolean open(){
		db = SQLite4jWrapperHolder.db;
		if (db == null)
			db = new SQLiteConnection(new File(DB_PATH));
		if (db.isOpen())
			return true;
		try {
			db.open(true);
		} catch (SQLiteException e) {
			db = null;
			e.printStackTrace();
		}
		if (db != null){
			initiateDBSchema(db);
			return true;
		}
		return false;
	}
	
	public int queryWithIntResult (String query) throws SQLiteException{
		open();
		int result = -1;
		SQLiteStatement stmt = db.prepare(query);
		while (stmt.step()){
			result = stmt.columnInt(0);
		}
		return result;
	}
	
	private void initiateDBSchema(SQLiteConnection db){
		int currentDBVersion = -1;
		try {
			currentDBVersion = queryWithIntResult("PRAGMA user_version");
			System.out.println("CURRENT DB VERSION : " + currentDBVersion);
			if (currentDBVersion < 1)
				v1Updates(db);
			
		} catch (SQLiteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	public void close(){
		if (db != null)
		{
			if (db.isOpen() && !db.isDisposed()){
				db.dispose();
			}
		}
	}
	public SQLiteStatement prepare (String sql) throws SQLiteException{
//		Tracer.trace("query : " + sql);
		return db.prepare(sql);
	}
	public int insertUniqueAndRetrieveID(){
		
		return -1;
	}
	public void exec (String sql) throws SQLiteException{
		db.exec(sql);
	}
	
	protected SQLite4jWrapper readResolve() {
		return getInstance();
	}
	
	private void v1Updates(SQLiteConnection db) throws SQLiteException{
		System.out.println("V1 UPDATE INITIATED");
		db.exec(Securities.CREATE_STMT);
		db.exec(Financials.CREATE_STMT);
		db.exec(SecurityStats.CREATE_STMT);
		db.exec("PRAGMA user_version = 1");
		System.out.println("V1 UPDATE FINISHED");
	}
}