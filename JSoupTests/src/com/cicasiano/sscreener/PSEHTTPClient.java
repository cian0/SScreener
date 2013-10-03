package com.cicasiano.sscreener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;


 
public class PSEHTTPClient {
	private final String USER_AGENT = "Mozilla/5.0";
	private final String URL = "http://www.pse.com.ph/stockMarket/marketInfo-marketActivity-indicesComposition.html?method=getCompositionIndices&ajax=true";
	private String result = null;
	public PSEHTTPClient() throws IllegalStateException, IOException{
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpHost proxy = new HttpHost(Main.PROXY, Integer.valueOf(Main.PORT));
		RequestConfig config = RequestConfig.custom()
                .setProxy(proxy)
                .build();
		
		
		
		HttpPost httpPost = new HttpPost(URL);
		httpPost.setConfig(config);
		httpPost.setHeader("User-agent", USER_AGENT);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
		
		// Request parameters and other properties.
		List<NameValuePair> params = new ArrayList<NameValuePair>(2);
		
		params.add(new BasicNameValuePair("sector", "ALL"));
		
		httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		//Execute and get the response.
		HttpResponse response = httpClient.execute(httpPost);
		
		int responseCode = response.getStatusLine().getStatusCode();
		if (responseCode == 200){
			System.out.println("\nSending 'POST' request to URL : " + URL);
			System.out.println("Post parameters : " + params);
			System.out.println("Response Code : " + responseCode);
		 
			BufferedReader rd = new BufferedReader(
		                new InputStreamReader(response.getEntity().getContent()));
		 
			StringBuffer result = new StringBuffer();
			String line = "";
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			this.result = result.toString();
			System.out.println(result.toString());
		}else{
			this.result = null;
		}
		
		
	}
	public String getResult() {
		return result;
	}
}
