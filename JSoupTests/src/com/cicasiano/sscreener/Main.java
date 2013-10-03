package com.cicasiano.sscreener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.SocketTimeoutException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.almworks.sqlite4java.SQLiteException;
import com.cicasiano.sscreener.dao.FinancialsDAO;
import com.cicasiano.sscreener.dao.SecurityDAO;
import com.cicasiano.sscreener.dao.SecurityStatsDAO;
import com.cicasiano.sscreener.model.SQLite4jWrapper;
import com.cicasiano.sscreener.utils.Tracer;
import com.cicasiano.sscreener.vos.Security;
import com.cicasiano.sscreener.vos.Stat;
public class Main {
	
	private static final String PSE_URL = "http://www.pse.com.ph/stockMarket/";
	private static final String ENCODING = "UTF-8";
	public static final String PROXY = "gateway.zscaler.net";
	public static final String PORT = "9400";
	private static final boolean USE_PROXIES = true;
	private static final String STOCK_LIST_SOURCE_FILE = "assets/markets.html";
	
	private static final String WRITE_LOGS_TO_FILE = "assets/logs.txt";
	private static Security security = new Security();
	private static SecurityDAO sDAO = new SecurityDAO();
	private static FinancialsDAO fDAO = new FinancialsDAO();
	private static SecurityStatsDAO ssDAO = new SecurityStatsDAO();
	private static NumberFormat nf = NumberFormat.getInstance(Locale.US);
	private static Stat stat = new Stat();
	private static final String PSE_REQ_URL = "http://www.pse.com.ph/stockMarket/marketInfo-marketActivity-indicesComposition.html?method=getCompositionIndices&ajax=true";
	private static final String USER_AGENT = "Mozilla/5.0";
	
	private static final String REUTERS_FINANCIAL_URL = "http://www.reuters.com/finance/stocks/financialHighlights?symbol=***.PS";
	public static void getFromDocAndPrint (Document doc, String id){
		System.out.println("getFromDocAndPrint : " + doc.getElementById(id));
		System.out.println("getFromDocAndPrint inner html : " + doc.getElementById(id).html());
		
	}
	// HTTP POST request
	private static void sendPost() {
		try {
			new PSEHTTPClient();
		} catch (IllegalStateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
 
	}
	public static void writeToFile(String element){
		try
		{
		    String filename= WRITE_LOGS_TO_FILE;
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
		    String filename= WRITE_LOGS_TO_FILE;
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
	private static void parseFinancialsFromDocument(Document doc){
		Elements el = doc.getElementsByTag("tr").select(".stripe");
		for (int j = 0 ; j < el.size(); j++){
			Element row = el.get(j);
			String statName = row.getAllElements().get(1).text().trim(); // Stat name
			
			int statID = fDAO.insertSecurityStatAndGetID(statName);

			stat.setStatID(statID);
			stat.setSymbol(security.getSymbolCode());
			if (row.getAllElements().size() >= 3)
				stat.setValCompany(row.getAllElements().get(2).text());
			if (row.getAllElements().size() >= 4)
				stat.setValIndustry(row.getAllElements().get(3).text());
			if (row.getAllElements().size() >= 5)
			stat.setValSector(row.getAllElements().get(4).text());
			ssDAO.insertStatsForSecurity(stat);
		}
	}
	public static void main (String [] args){
		setupProxies();
		sendPost();
		SQLite4jWrapper.getInstance().open();
		try {
			Document doc = parseFromFile();
			Elements tables = doc.getElementsByTag("table");
			System.out.print("List length : " + tables.size() + "\n");
			String reutersURL;
			try {
				SQLite4jWrapper.getInstance().exec("DELETE FROM SecurityStats");
			} catch (SQLiteException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			Element symbolElem;
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
				
				symbolElem = tr.child(2); // td > div > a
				
				if (symbolElem.text().trim().equals("") || symbolElem.text().trim().equals("Symbol"))
					continue;
				
				reutersURL = REUTERS_FINANCIAL_URL.replace("***", symbolElem.text());
				
				
				security.setFreeFloatLevel(Double.parseDouble(tr.child(6).text()));
				try {
					security.setFreeFloatMarketCap((nf.parse(tr.child(7).text()).longValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				security.setLastTradeDate(tr.child(3).text());
				try {
					security.setLastTradePrice(new BigDecimal((nf.parse(tr.child(4).text()).doubleValue())));
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
				try {
					security.setOutstandingShares((nf.parse(tr.child(5).text()).longValue()));
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				security.setPercentWeight(Double.parseDouble(tr.child(8).text()));
				security.setSecurityName((tr.child(1).text()));
				security.setSymbolCode((tr.child(2).text()));
				sDAO.saveSymbol(security);
				Tracer.trace("Parsing : " + reutersURL);
				try{
					Document reutersDoc = Jsoup.connect(reutersURL).timeout(20000).get();	
					parseFinancialsFromDocument(reutersDoc);
//					break;
					
				}catch (SocketTimeoutException e){
					Tracer.trace("TIMEOUT FOR : " + reutersURL);
					writeToFile(reutersURL);
					writeToFile(e.getStackTrace().toString());
					e.printStackTrace();
				}

			
			}
			

		} catch (IOException e) {
			e.printStackTrace();
		}
		SQLite4jWrapper.getInstance().close();
	}
}
