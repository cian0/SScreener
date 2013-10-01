package com.cicasiano.sscreener.model;

public abstract class Securities {
	//1 Security Name
		//2 Symbol
		//3 Last Trade Date
		//4 Last Trade Price
		//5 Outstanding Shares
		//6 Full Market Capitalization
		//7 % Weight
		
	public static final String TABLE_NAME = "Securities";
	public static final String C_SECURITY_NAME = "security_name";
	public static final String C_SYMBOL = "symbol";
	public static final String C_LAST_TRADE_DATE = "last_trade_date";
	public static final String C_LAST_TRADE_PRICE = "last_trade_price";
	public static final String C_OUTSTANDING_SHARES = "outstanding_shares";
	public static final String C_FULL_MARKET_CAP = "full_market_cap";
	public static final String C_PERCENT_WEIGHT = "percent_weight";
	
	public static final String CREATE_STMT = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
			C_SECURITY_NAME + " text, " +
			C_SYMBOL + " text PRIMARY KEY, " +
			C_LAST_TRADE_DATE + " text, " +
			C_LAST_TRADE_PRICE + " text, " +
			C_OUTSTANDING_SHARES + " text, " +
			C_FULL_MARKET_CAP + " text, " +
			C_PERCENT_WEIGHT + " text " +
			")";
	
	
}
