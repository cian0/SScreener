package com.cicasiano.sscreener.dao;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cicasiano.sscreener.model.SQLite4jWrapper;
import com.cicasiano.sscreener.model.SecurityStats;
import com.cicasiano.sscreener.vos.Stat;

public class SecurityStatsDAO {
	SQLite4jWrapper db = null;

	public int insertStatsForSecurity(Stat stat){
		db = SQLite4jWrapper.getInstance();
		int id = -1;
		if (db.open()){
			try {
				String query = "INSERT OR REPLACE INTO " + SecurityStats.TABLE_NAME + " (" + 
						SecurityStats.C_FINANCIALS_ID + ", " +
						SecurityStats.C_SECURITY_SYMBOL + ", " +
						SecurityStats.C_VALUE_COMPANY + ", " +
						SecurityStats.C_VALUE_SECTOR + ", " +
						SecurityStats.C_VALUE_INDUSTRY + 
						") VALUES (?, ?, ?, ?, ?)";
				
				SQLiteStatement stmt = db.prepare(query);
				stmt.bind(1, stat.getStatID());
				stmt.bind(2, stat.getSymbol());
				stmt.bind(3, stat.getValCompany());
				stmt.bind(4, stat.getValSector());
				stmt.bind(5, stat.getValIndustry());
				stmt.step();
				
			} catch (SQLiteException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public void getSymbol() {
		
	}
}
