package code.main;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import code.analysis.JSONData;
import code.networking.SrvConnect;

public class Composer {

	private String query = "Programming";
	private String index = "0";
	private String maxResults = "40";
	private SrvConnect srvConn;
	private  JSONData format;

	public Composer() throws IOException, ParseException {
			srvConn = new SrvConnect(query,maxResults, index);
			format= new JSONData(new SrvConnect(query, maxResults,index).getCompleteDataSet());
	}
	
	
	public SrvConnect getSrvConn() {
		return srvConn;
	}
	
	public JSONData getFormat() throws ParseException, IOException {
		return srvConn.isConnected() ? format : new JSONData(new SrvConnect(query, maxResults,index).getCompleteDataSet());
	}
	
	
	
}
