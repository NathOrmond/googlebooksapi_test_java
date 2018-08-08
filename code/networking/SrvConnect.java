package code.networking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class SrvConnect {

	private URL url;
	private URLConnection conn;
	private BufferedReader br;
	private String rawData;
	private String http;
	private boolean connected = false;
	
	/**
	 * Default constructor
	 */
	public SrvConnect() {
	} 
	
	/**
	 * sets URL, connects to URL and reads content from constructor
	 * @param query
	 * @param index
	 * @throws IOException
	 */
	public SrvConnect(String query,String maxResults, String index) throws IOException{ 
		setHttp(query,maxResults, index);
		connect(); 
		readContent();
	}
	
	/**
	 * Connects to URL
	 * @throws IOException
	 */
	public void connect() throws IOException { 
		url = new URL(http);
		System.out.println("Attempting Connection : " + http);
		conn = url.openConnection();
		connected = true;
		System.out.println("### succesful connection ###");
	}
	
	/**
	 * reads input at URL as input stream
	 * @throws IOException
	 */
	public void readContent() throws IOException { 
		br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		rawData = "";
		
		String line; 
		while((line = br.readLine()) != null) { 
			System.out.println(line);
			rawData = rawData + line;
		}
		
		System.out.println("### END OF INPUT STREAM ###");
		
	}
	
	/**
	 * 
	 * @return rawData (from a query)
	 */
	public String getCompleteDataSet() {
		return rawData;
	}
	
	/**
	 * sets URL to custom parameter
	 * (Google Books API says highest maxResults param = 40)
	 * @param query
	 * @param index
	 */
	public void setHttp(String query,String maxResults, String index) { 
		this.http = "https://www.googleapis.com/books/v1/volumes?q="+query+ "&startindex=" + index + "&maxResults=" + maxResults ;
	}
	
	public boolean isConnected() {
		return connected;
	}
	
}
