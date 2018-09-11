package test.analysis.datatypes;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import code.analysis.JSONData;
import code.analysis.datatypes.DataPublishers;
import org.junit.Assert;
import resource.TextFileReader;
import resource.TextFileWriter;

public class DataPublishersTest {
	
	static JSONData jsonData;
	static DataPublishers dp;
	TextFileReader reader;
	TextFileWriter writer;
	
	@Before
	public void init() throws ParseException {
		reader = new TextFileReader();
		writer = new TextFileWriter();
		
		reader.setInputFileName("testdata.txt");
		jsonData = new JSONData(reader.getRawData());
		
		writer.setOutPutFile("DataPublishersTestActualOutPut");
		
		dp = new DataPublishers();
	}
	
	@Test
	public void testBooksByPublisher() throws ParseException {
		String testName = "BOOKS_BY_PUBLISHER_TEST :";
		System.out.println(consoleOutput("NEW DATA PUBLISHERS TEST", testName,0));
		System.out.println(consoleOutput(testName, "START",4));
		
		JSONObject actualReturnedValues = dp.booksByPublisher();
		writer.writeFile(actualReturnedValues.toJSONString());
		
		reader.setInputFileName("DataPublishersTestExpectedOutPut");
		JSONParser parser = new JSONParser();
		JSONObject expectedReturnedValues = (JSONObject) parser.parse(reader.getRawData());
		
		Assert.assertEquals(expectedReturnedValues.size(), actualReturnedValues.size());
		Assert.assertEquals(expectedReturnedValues.toJSONString(), actualReturnedValues.toJSONString());
		
		String status = null;
		status = (actualReturnedValues.toJSONString().equals(expectedReturnedValues.toJSONString())) ? "PASS" : "FAIL";
		System.out.println(consoleOutput(testName, status,4));
		System.out.println(consoleOutput("END DATA PUBLISHER TEST", testName,0));
	}
	
	
	private String consoleOutput(String content, String suffix, int indentation) {
		String indnt = "";
		for(int i = 0; i < indentation; i++) { 
			indnt = indnt + " ";
		}
		return "\n" + indnt +"#### " + content + " #### [STATUS: " + suffix + "]";
	}
}
