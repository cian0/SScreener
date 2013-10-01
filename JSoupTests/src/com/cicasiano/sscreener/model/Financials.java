package com.cicasiano.sscreener.model;

public abstract class Financials {
	
	public static final String TABLE_NAME = "Financials";
	public static final String C_KEY = "key";
	public static final String C_ID = "id";
	
	public static final String CREATE_STMT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
			C_KEY + " text UNIQUE NOT NULL, " +
			C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
			")";
}
