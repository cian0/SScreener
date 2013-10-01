package com.cicasiano.sscreener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.almworks.sqlite4java.SQLiteConnection;
import com.almworks.sqlite4java.SQLiteException;
import com.cicasiano.sscreener.dao.SecurityDAO;
import com.cicasiano.sscreener.model.SQLite4jWrapper;
import com.cicasiano.sscreener.vos.Security;
public class Main {
	
	private static final String PSE_URL = "http://www.pse.com.ph/stockMarket/";
	private static final String ENCODING = "UTF-8";
	private static final String PROXY = "gateway.zscaler.net";
	private static final String PORT = "9400";
	private static final boolean USE_PROXIES = true;
	private static final String STOCK_LIST_SOURCE_FILE = "assets/markets.html";
	
	private static final String REUTERS_FINANCIAL_URL = "http://www.reuters.com/finance/stocks/financialHighlights?symbol=***.PS";
	public static void getFromDocAndPrint (Document doc, String id){
		System.out.println("getFromDocAndPrint : " + doc.getElementById(id));
		System.out.println("getFromDocAndPrint inner html : " + doc.getElementById(id).html());
		
	}
	
	public static void writeToFile(String element){
		try
		{
		    String filename= "assets/MyFile.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(element +"\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	public static void writeToFile(Element element){
		try
		{
		    String filename= "assets/MyFile2.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(element +"\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	
	private static Document parseFromFile () throws IOException{
		File input = new File(STOCK_LIST_SOURCE_FILE);
		Document doc = Jsoup.parse(input, ENCODING, PSE_URL);
		return doc;
	}
	private static void setupProxies (){
		if (!USE_PROXIES)
			return;
		System.setProperty("http.proxyHost", PROXY);
		System.setProperty("http.proxyPort", PORT);
	}
	public static void main (String [] args){
		setupProxies();
		SQLite4jWrapper.getInstance().open();
		try {
			Document doc = parseFromFile();
			String link;
			Elements tables = doc.getElementsByTag("table");
			System.out.print("List length : " + tables.size() + "\n");
			String reutersURL;
			Security security = new Security();
			SecurityDAO sDAO = new SecurityDAO();
			NumberFormat nf = NumberFormat.getInstance(Locale.US);
			
			for (int i =0 ; i < tables.size(); i++){
				// from table > tbody > tr 	
				
				Element tr = tables.get(i).child(0).child(0);
//				System.out.print(tr);
				//0 is position num
				//1 Security Name
				//2 Symbol
				//3 Last Trade Date
				//4 Last Trade Price
				//5 Outstanding Shares
				//6 Free float level
				//7 Full Market Capitalization
				//8 % Weight
				
				Element secNameElem = tr.child(1);
				Element symbolElem = tr.child(2); // td > div > a
				Element symbolLinkElem = symbolElem.child(0).child(0);
				link = symbolLinkElem.attr("href");
				Element fullMarketCap = tr.child(6);
				
				if (symbolElem.text().trim().equals("") || symbolElem.text().trim().equals("Symbol"))
					continue;
				
				
				System.out.print("Security Name : " + secNameElem.text() + " ::: ");
				System.out.print("Full Market Capitalization : " + fullMarketCap.text() + " ::: ");
				System.out.print("Sec Link : " + link + " ::: ");
				System.out.print("Security Symbol : " + symbolElem.text() + "\n");
				reutersURL = REUTERS_FINANCIAL_URL.replace("***", symbolElem.text());
				System.out.print("reutersURL : " + reutersURL + "\n");
				
				security.setFreeFloatLevel(Double.parseDouble(tr.child(6).text()));
				try {
					security.setFreeFloatMarketCap((nf.parse(tr.child(7).text()).longValue()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				security.setLastTradeDate(tr.child(3).text());
				security.setLastTradePrice(new BigDecimal(tr.child(4).text()));
				
				try {
					security.setOutstandingShares((nf.parse(tr.child(5).text()).longValue()));
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				security.setPercentWeight(Double.parseDouble(tr.child(8).text()));
				security.setSecurityName((tr.child(1).text()));
				security.setSymbolCode((tr.child(2).text()));
				sDAO.saveSymbol(security);
				
				Document reutersDoc = Jsoup.connect(reutersURL).get();
				Elements el = reutersDoc.getElementsByTag("tr").select(".stripe");
				System.out.print(" : " + el.toString() + "\n");
				
				break;
				
//				writeToFile(divs.get(i));
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
