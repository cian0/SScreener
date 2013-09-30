import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class Main {
	public static void getFromDocAndPrint (Document doc, String id){
		System.out.println("getFromDocAndPrint : " + doc.getElementById(id));
		System.out.println("getFromDocAndPrint inner html : " + doc.getElementById(id).html());
		
	}
	
	public static void writeToFile(String element){
		try
		{
		    String filename= "MyFile.txt";
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
		    String filename= "MyFile2.txt";
		    FileWriter fw = new FileWriter(filename,true); //the true will append the new data
		    fw.write(element +"\n");//appends the string to the file
		    fw.close();
		}
		catch(IOException ioe)
		{
		    System.err.println("IOException: " + ioe.getMessage());
		}
	}
	public static void main (String [] args){
		System.out.println("aaa");
		/*
		 * String url = "http://www.gudi.ch/armbanduhr-metall-wasserdicht-1280x960-megapixels-p-560.html";
InputStream input = new URL(url).openStream();
Document doc = Jsoup.parse(input, "CP1252", url);
String title = doc.select(".products_name").first().text();

<div class="x-grid3-cell-inner x-grid3-col-2" unselectable="on"><a href="companyInfo.html?id=114&amp;security=123&amp;tab=0">ABS</a></div>
		 * */
		try {
			System.setProperty("http.proxyHost", "gateway.zscaler.net");
			System.setProperty("http.proxyPort", "9400");
//			String url = "http://www.pse.com.ph/stockMarket/marketInfo-marketActivity.html?tab=1&indexName=All%20Shares";
//			InputStream input = new URL(url).openStream();
			File input = new File("MyFile.txt");
			Document doc = Jsoup.parse(input, "UTF-8", "http://www.pse.com.ph/stockMarket/");

			
//			Document doc = Jsoup.parse(input, "CP1252", url);
//			Document doc = Jsoup.parse(input, "ISO-8859-1", url);
//			Document doc = Jsoup.parse(input,"UTF-8", url);
//			 
//			Tidy tidy = new Tidy();
//			tidy.setQuiet(true);
//			tidy.setShowWarnings(false);
//			
//			org.w3c.dom.Document doc2= tidy.parseDOM(input, System.out);
//			org.w3c.dom.Element div1 = doc2.getElementById("middleColumnCom");
//			System.out.println("tidy title : " + doc2.get); 
//			System.out.println("tidy : middleColumnCom " + div1); 
			
//			String title = doc.select(".products_name").first().text();
			
			
//			Document doc = Jsoup.connect("http://www.pse.com.ph/stockMarket/marketInfo-marketActivity.html?tab=1&indexName=All%20Shares")
//					 .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:8.0.1) Gecko/20100101 Firefox/8.0.1")
//					 .get();
			 
			// get page title
//			String title = doc.title();
//			System.out.println("title : " + title);
//			System.out.println("ext-comp-1031 : " + doc.select("div#ext-comp-1031"));
//			writeToFile(doc.html());
//			Elements divs = doc.getElementsByClass("x-grid3-col x-grid3-cell x-grid3-td-4 ");

			Elements divs = doc.getElementsByTag("table");
//			Elements divs = doc.getElementsByAttribute("unselectable");
//			Elements divs = doc.getElementsByClass("x-grid3-row ");
			
			
			for (int i =0 ; i < divs.size(); i++){
				
//				if (!divs.get(i).tagName().equalsIgnoreCase("div")){
//					divs.remove(i);
//					continue;
//				}
				// from table > tbody > tr 	
				Element tr = divs.get(i).child(0).child(0);
				
				
				
				System.out.print("TR children : " + tr.children().size() + "\n");
//				writeToFile(divs.get(i));
			}
			
//			getFromDocAndPrint(doc, "middleColumnCom");
//			getFromDocAndPrint(doc, "marketActivityDetails");
//			getFromDocAndPrint(doc, "ext-comp-1028");
			
			//<div class="x-grid3-cell-inner x-grid3-col-2" unselectable="on"><a href="companyInfo.html?id=29&amp;security=146&amp;tab=0">2GO</a></div>
			// get all links
//			Element marketActivityDetails = doc.getElementById("marketActivityDetails");
//			System.out.println("marketActivityDetails : " + marketActivityDetails.outerHtml());
//			System.out.println("marketActivityDetails.getAllElements().get(0) : " + marketActivityDetails.getAllElements().get(0));
//			System.out.println("marketActivityDetails.getElementById('ext-comp-1031') : " + marketActivityDetails.getElementById("ext-comp-1031"));
//			
//			
//			
//			System.out.println("indicesCompositionGrid : " + marketActivityDetails.getElementById("indicesCompositionGrid"));
//			
//			
////			Element indicesCompositionGrid = doc.getElementById("indicesCompositionGrid");
////			System.out.println("indicesCompositionGrid : " + indicesCompositionGrid);
//			Element indicesCompositionGrid2 = doc.getElementById("indicesCompositionGrid2");
//			System.out.println("indicesCompositionGrid2 : " + indicesCompositionGrid2);
//			Elements divs = doc.getElementsByClass("x-grid3-row");
//			System.out.println( "Elements fetched : " + divs.size());
//			for (Element div : divs) {
//	 			System.out.println("text : " + div.text());	 
//			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
