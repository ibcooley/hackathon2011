package com.sparcedge.groceryscanner;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
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
		
	UpcDecoder(String _upc) {
		if(_upc != null) {
			upc = _upc;
			url = urla + upc + urlb;
		}
	}
	
	public String getURL() { return url; }
	
	public String get(String urlToRead) {
		html = null;
		try {
            URL yahoo = new URL(urlToRead);
            URLConnection yahooConnection = yahoo.openConnection();
            DataInputStream dis = new DataInputStream(yahooConnection.getInputStream());
            String inputLine;

            while ((inputLine = dis.readLine()) != null) {
            	html = html + inputLine;
                //System.out.println(inputLine);
            }
            dis.close();
        } catch (MalformedURLException me) {
            System.out.println("MalformedURLException: " + me);
        } catch (IOException ioe) {
            System.out.println("IOException: " + ioe);
        }
        return html;
	}
	
	public String get2(String urlToRead) {
        String inputLine = null;

		try {
			GroceryScanner.LOG("get " + urlToRead + " ...");
			URL o = new URL(urlToRead);
	        URLConnection yc = o.openConnection();
	        BufferedReader in = new BufferedReader(
	                                new InputStreamReader(
	                                yc.getInputStream()));
	
	        while ((inputLine = in.readLine()) != null) {
	            System.out.println(inputLine);
	            html = html + inputLine;
	        }
	        in.close();
		} catch (Exception e) { } 
		
		return inputLine;
	}
	
	/*
	 * 
<a class="title" href="http://www.amazon.com/JAGERMEISTER-Jagermeister-Liqueur-70/dp
/B005J0PCCC/ref=sr_1_1?ie=UTF8&qid=1314468178&sr=8-1">Jagermeister Liqueur 70@</a> 
    <span class="ptBrand">by JAGERMEISTER</span> 
</div> 

	 */
	
	public String getProuctName() {
		String name = "unknown";
		if(html != null) {
		    Pattern pp = Pattern.compile("<a class=\"title\".*>(.*)<");
			Matcher m  = pp.matcher(html);
			try {
			    if(m.find()) {
			        name = m.group(1);
			    }
			}
			catch (Exception e) {
			}

		} 
		else {
			GroceryScanner.LOG("getProductName(): html is null?");
		}
		
		return name;
	}
	
	//TODO: recomended items based on list items
	
	public String getImageURL() {
		String url = "http://beeradvocate.com/im/beers/no_beer_pic.jpg";
		
		return url;
	}
	
	public String get3() {
		html = null;
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
		    InputStream instream = null;
			try {
				instream = entity.getContent();
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    int l;
		    byte[] tmp = new byte[2048];
		    try {
				while ((l = instream.read(tmp)) != -1) {
					html = html + new String(tmp);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return html;	
	}
	
}

