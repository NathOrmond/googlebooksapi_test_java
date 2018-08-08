package code.main;

import java.io.IOException;
import org.json.simple.parser.ParseException;
import code.analysis.FormatData;
import code.networking.SrvConnect;

public class Composer {

	private String query = "Programming";
	private String index = "0";
	private String maxResults = "40";
	private SrvConnect srvConn;
	private  FormatData format;

	public Composer() throws IOException, ParseException {
			srvConn = new SrvConnect(query,maxResults, index);
			format= new FormatData(new SrvConnect(query, maxResults,index).getCompleteDataSet());
	}
	
	
	public SrvConnect getSrvConn() {
		return srvConn;
	}
	
	public FormatData getFormat() throws ParseException, IOException {
		return srvConn.isConnected() ? format : new FormatData(new SrvConnect(query, maxResults,index).getCompleteDataSet());
	}
	
	
	
}
