package com.cicasiano.sscreener.vos;

public class Stat {
	private String valCompany, valSector, valIndustry;
	private int statID;
	private String symbol;
	public String getValCompany() {
		return valCompany;
	}
	public void setValCompany(String valCompany) {
		this.valCompany = valCompany;
	}
	public String getValSector() {
		return valSector;
	}
	public void setValSector(String valSector) {
		this.valSector = valSector;
	}
	public String getValIndustry() {
		return valIndustry;
	}
	public void setValIndustry(String valIndustry) {
		this.valIndustry = valIndustry;
	}
	public int getStatID() {
		return statID;
	}
	public void setStatID(int statID) {
		this.statID = statID;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
