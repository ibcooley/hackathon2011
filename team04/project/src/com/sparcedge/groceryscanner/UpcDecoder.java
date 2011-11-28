package com.sparcedge.groceryscanner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public class UpcDecoder {
	String upc = "";
	String urla = "http://www.amazon.com/s/ref=nb_sb_noss?url=search-alias%3Daps&field-keywords=";
	String urlb = "&x=0&y=0";
	String url = null;
	String html = null;
	String productName = "unknown";
	String productImage = "http://beeradvocate.com/im/beers/no_beer_pic.jpg";
	ArrayList<String> suggestions = new ArrayList<String>();
		
	UpcDecoder(String _upc) {
		if(_upc != null) {
			upc = _upc;
			url = urla + upc + urlb;
		}
	}
	
	public String getURL() { return url; }
	
	public String getProductName() { return productName.trim(); }

	//TODO: recomended items based on list items
	
	public String getImageURL() { return productImage; }
	
	public String get() {
		html = null;
		GroceryScanner.LOG("GET: " + url);
		long bytes = 0;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
            StringBuffer sb = new StringBuffer("");
            BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
		    	String line = "";
	            String NL = System.getProperty("line.separator");
		    	while ((line = in.readLine()) != null) {
		    		//GroceryScanner.LOG(line);
		    		if(line.contains("class=\"productTitle\"")) GroceryScanner.LOG(line);
		    		Pattern pp = Pattern.compile("class=\"productTitle\"><.*>(.+)</a");
		    		if(line.contains("class=\"productImage\"")) {
		    			GroceryScanner.LOG(line);
		    		}
					Matcher m  = pp.matcher(line);
					try {
					    if(m.find()) {
					        productName = m.group(1);
					    }
					}
					catch (Exception e) {
						GroceryScanner.LOG("OH SHIT");
					}
					
					// image
					pp = Pattern.compile("src=\"(.*)\" class.*alt=\"Product Details");
					m = pp.matcher(line);
					try {
					    if(m.find()) {
					        productImage = m.group(1);
					    }
					}
					catch (Exception e) {
						GroceryScanner.LOG("image: crapped out: " + e.toString());
					}
										
	                sb.append(line);
	                bytes += line.length();
	            }
		    	html = sb.toString();
				getSuggestions(productName.trim());

		    	GroceryScanner.LOG("bytes read: " + bytes);
		    	GroceryScanner.LOG("html is (bytes): " + html.length());
		    	GroceryScanner.LOG("product name: " + productName);
		    	GroceryScanner.LOG("product img: " + productImage);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html;	
	}

	public String getSuggestions(String name) {
		String html = null;
		String newname = name.replaceAll("\\s+", "+");
		GroceryScanner.LOG("New prod name: " + newname);
		String url2 = urla + newname + urlb;
		long bytes = 0;
		HttpClient httpclient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(url2);
		GroceryScanner.LOG("GET: " + url2);
		HttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		HttpEntity entity = response.getEntity();
		if (entity != null) {
            StringBuffer sb = new StringBuffer("");
            BufferedReader in = null;
			try {
				in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			} catch (IllegalStateException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		    try {
		    	String line = "";
	            String NL = System.getProperty("line.separator");
		    	while ((line = in.readLine()) != null) {
		    		//GroceryScanner.LOG("sug: " + line);
		    		if(line.contains("/product/")) GroceryScanner.LOG("sug prod: " + line);
					// suggestions
					//<div id="result_0" class="result firstRow product" name="1402208049"> 
					if(line.contains("firstRow")) GroceryScanner.LOG(line);
					Pattern pp = Pattern.compile("class=\"result firstRow product\" name=\"(.*)\">");
					Matcher m = pp.matcher(line);
					try {
					    if(m.find()) {
					    	GroceryScanner.LOG("Suggestion: " + m.group(1));
					        suggestions.add(m.group(1));
					    }
					}
					catch (Exception e) {
						GroceryScanner.LOG("suggestions: craped out: " + e.toString());
					}
					
	                sb.append(line);
	                bytes += line.length();
	            }
		    	html = sb.toString();
		    	GroceryScanner.LOG("bytes read: " + bytes);
		    	GroceryScanner.LOG("html is (bytes): " + html.length());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html;	
	}

}
