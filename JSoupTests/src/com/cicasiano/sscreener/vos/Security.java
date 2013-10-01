package com.cicasiano.sscreener.vos;

import java.math.BigDecimal;

public class Security {
	private String securityName, symbolCode, lastTradeDate;
	private BigDecimal lastTradePrice;
	private double freeFloatLevel, percentWeight;

	//0 is position num
	//1 Security Name
	//2 Symbol
	//3 Last Trade Date
	//4 Last Trade Price
	//5 Outstanding Shares
	//6 Full Market Capitalization
	//7 % Weight
	
	public String getSymbolCode() {
		return symbolCode;
	}
	public void setSymbolCode(String symbolCode) {
		this.symbolCode = symbolCode;
	}
	public String getLastTradeDate() {
		return lastTradeDate;
	}
	public void setLastTradeDate(String lastTradeDate) {
		this.lastTradeDate = lastTradeDate;
	}
	public BigDecimal getLastTradePrice() {
		return lastTradePrice;
	}
	public void setLastTradePrice(BigDecimal lastTradePrice) {
		this.lastTradePrice = lastTradePrice;
	}
	public double getFreeFloatLevel() {
		return freeFloatLevel;
	}
	public void setFreeFloatLevel(double freeFloatLevel) {
		this.freeFloatLevel = freeFloatLevel;
	}
	public double getPercentWeight() {
		return percentWeight;
	}
	public void setPercentWeight(double percentWeight) {
		this.percentWeight = percentWeight;
	}
	public long getOutstandingShares() {
		return outstandingShares;
	}
	public void setOutstandingShares(long outstandingShares) {
		this.outstandingShares = outstandingShares;
	}
	private long outstandingShares, freeFloatMarketCap;
	public long getFreeFloatMarketCap() {
		return freeFloatMarketCap;
	}
	public void setFreeFloatMarketCap(long freeFloatMarketCap) {
		this.freeFloatMarketCap = freeFloatMarketCap;
	}
	public String getSecurityName() {
		return securityName;
	}
	public void setSecurityName(String securityName) {
		this.securityName = securityName;
	}
}
