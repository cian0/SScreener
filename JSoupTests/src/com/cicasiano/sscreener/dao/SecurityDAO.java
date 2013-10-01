package com.cicasiano.sscreener.dao;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cicasiano.sscreener.model.SQLite4jWrapper;
import com.cicasiano.sscreener.model.Securities;
import com.cicasiano.sscreener.vos.Security;

public class SecurityDAO {
	SQLite4jWrapper db = null;

	public void saveSymbol(Security security){
		db = SQLite4jWrapper.getInstance();
		if (db.open()){
			try {
				String query = "INSERT INTO " + Securities.TABLE_NAME + " (" + 
						Securities.C_FULL_MARKET_CAP + ", " +
						Securities.C_LAST_TRADE_DATE + ", " + 
						Securities.C_LAST_TRADE_PRICE + ", " + 
						Securities.C_OUTSTANDING_SHARES + ", " + 
						Securities.C_PERCENT_WEIGHT + ", " +
						Securities.C_SYMBOL + ", " +
						Securities.C_SECURITY_NAME +  
						") VALUES (?, ?, ?, ?, ? , ? , ?)";
				
				SQLiteStatement stmt = db.prepare(query);
				
				stmt.bind(1, security.getFreeFloatMarketCap());
				stmt.bind(2, security.getLastTradeDate());
				stmt.bind(3, security.getLastTradePrice().toString());
				stmt.bind(4, security.getOutstandingShares());
				stmt.bind(5, security.getPercentWeight());
				stmt.bind(6, security.getSymbolCode());
				stmt.bind(7, security.getSecurityName());
				
				stmt.step();
				
			} catch (SQLiteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public void getSymbol() {
		
	}
}
