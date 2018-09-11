package test.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import code.analysis.JSONData;
import code.analysis.datatypes.DataPrices;
import resource.TextFileReader;
import resource.TextFileWriter;

import org.junit.Assert;
/**
 * 
 * @author Nathan Ormond
 *
 * Run as jUnit 4 test (jUnit 5 will cause to crash/fail)
 * Test runs on stimulated data within file.
 *
 */
public class DataPricesTest {

	JSONArray expectedReturnArray;
	int testNum = 0;
	static JSONData jsonData;
	static DataPrices dp;
	TextFileReader reader;
	TextFileWriter writer;
	
	public DataPricesTest() {
		//not used
	}

	@Before
	public void init() throws ParseException {
		reader = new TextFileReader();
		writer = new TextFileWriter();
		
		reader.setInputFileName("testdata.txt");
		jsonData = new JSONData(reader.getRawData());
		
		JSONParser parser = new JSONParser();
		reader.setInputFileName("DataPricesTestExpectedOutPut");
		expectedReturnArray = (JSONArray) parser.parse(reader.getRawData());
		
		dp = new DataPrices();
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCreateDesiredObject() {
		String testName = "CREATE_DESIRED_OBJECT_TEST :";
		System.out.println(consoleOutput("NEW DATA PRICES TEST", testName,0));
		System.out.println(consoleOutput(testName, "START",4));
		JSONObject expectedObj = new JSONObject();
		expectedObj.put("costPerPage", 0.009032258064516128);
		expectedObj.put("title", "COMPUTER PROGRAMMING IN C");
		JSONObject item = null;
		for (int i = 0; i < JSONData.getItems().size(); i++) {
			item = (JSONObject) JSONData.getItems().get(i);
				if (JSONData.getSaleInfo(item).containsKey("listPrice") && JSONData.getVolumeInfo(item).containsKey("pageCount")) {
						i = JSONData.getItems().size();
				}
		}
		JSONObject returnedObj = dp.createDesiredObject(item);
		System.out.println(consoleOutput("EXPECTED" , expectedObj.toJSONString(), 8));
		System.out.println(consoleOutput("ACTUAL  ",returnedObj.toJSONString(),8));
		Assert.assertEquals(expectedObj.get("costPerPage"), returnedObj.get("costPerPage"));
		Assert.assertEquals(expectedObj.get("title"), returnedObj.get("title"));
		String status = null;
		status = ((double) expectedObj.get("costPerPage") == (double) returnedObj.get("costPerPage")) && ((expectedObj.get("title").equals(returnedObj.get("title")))) ? "PASS" : "FAIL";
		System.out.println(consoleOutput(testName, status,4));
		System.out.println(consoleOutput("END DATA PRICES TEST", testName,0));
	}

	@Test
	public void testGetRankedArray() throws ParseException {
		String testName = "GET_RANKED_ARRAY_TEST :";
		System.out.println(consoleOutput("NEW DATA PRICES TEST", testName,0));
		System.out.println(consoleOutput(testName , "START",4));
		JSONArray returnedArray = dp.getRankedArray(5);	
		writer.setOutPutFile("DataPricesTestActualOutPut");
		writer.writeFile(returnedArray.toJSONString());
		String returnedData = returnedArray.toJSONString();
		System.out.println(consoleOutput("EXPECTED" , expectedReturnArray.toJSONString(), 8));
		System.out.println(consoleOutput("ACTUAL  ",returnedArray.toJSONString(),8));	
		Assert.assertEquals(expectedReturnArray.toJSONString(), returnedData);
		String status;
		status = expectedReturnArray.toJSONString().equals(returnedData) ? "PASS" : "FAIL";
		System.out.println(consoleOutput(testName, status,4));
		System.out.println(consoleOutput("END DATA PRICES TEST", testName,0));
	}

	private String consoleOutput(String content, String suffix, int indentation) {
		String indnt = "";
		for(int i = 0; i < indentation; i++) { 
			indnt = indnt + " ";
		}
		return "\n" + indnt +"#### " + content + " #### [STATUS: " + suffix + "]";
	}
	

}
