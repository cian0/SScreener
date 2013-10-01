package com.cicasiano.sscreener.model;

public abstract class SecurityStats {
	public static final String TABLE_NAME = "SecurityStats";
	public static final String C_FINANCIALS_ID = "financials_id";
	public static final String C_SECURITY_SYMBOL = "security_symbol";
	public static final String C_VALUE_COMPANY= "value_company";
	public static final String C_VALUE_INDUSTRY= "value_industry";
	public static final String C_VALUE_SECTOR= "value_sector";
	public static final String C_ID = "id";
	
	public static final String CREATE_STMT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
			C_FINANCIALS_ID + " INTEGER NOT NULL, " +
			C_SECURITY_SYMBOL + " text NOT NULL, " +
			C_VALUE_COMPANY + " text, " +
			C_VALUE_INDUSTRY + " text, " +
			C_VALUE_SECTOR + " text, " +
			C_ID + " INTEGER PRIMARY KEY AUTOINCREMENT " +
			")";
}
