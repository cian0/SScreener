package com.cicasiano.sscreener.dao;

import com.almworks.sqlite4java.SQLiteException;
import com.almworks.sqlite4java.SQLiteStatement;
import com.cicasiano.sscreener.model.Financials;
import com.cicasiano.sscreener.model.SQLite4jWrapper;

public class FinancialsDAO {
	SQLite4jWrapper db = null;

	public int insertSecurityStatAndGetID(String statName){
		db = SQLite4jWrapper.getInstance();
		int id = -1;
		if (db.open()){
			try {
				String query = "INSERT OR IGNORE INTO " + Financials.TABLE_NAME + " (" + 
						Financials.C_KEY +  
						") VALUES (?)";
				
				SQLiteStatement stmt = db.prepare(query);
				stmt.bind(1, statName);
				stmt.step();
				
				SQLiteStatement selStmt = db.prepare("SELECT " + Financials.C_ID + " FROM " + Financials.TABLE_NAME
						 + " WHERE " + 	Financials.C_KEY + " = ?");
				selStmt.bind(1, statName);
				
				while (selStmt.step()){
					id = selStmt.columnInt(0);
					break;
				}
			} catch (SQLiteException e) {
				e.printStackTrace();
			}
		}
		return id;
	}

	public void getSymbol() {
		
	}
}
