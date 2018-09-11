package test.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import code.analysis.JSONData;
import code.analysis.datatypes.DataRatings;
import resource.TextFileReader;
import resource.TextFileWriter;

public class DataRatingsTest {

	JSONArray expectedReturnArray;
	static JSONData jsonData;
	static DataRatings dr;
	TextFileReader reader;
	TextFileWriter writer;
	
	public DataRatingsTest() {
	}
	
	@Before
	public void init() throws ParseException {
		reader = new TextFileReader();
		writer = new TextFileWriter();
		
		reader.setInputFileName("testdata.txt");
		jsonData = new JSONData(reader.getRawData());
		
		JSONParser parser = new JSONParser();
		reader.setInputFileName("DataRatingsTestActualOutPut");
		expectedReturnArray = (JSONArray) parser.parse(reader.getRawData());
		
		dr = new DataRatings();
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void testCreateDesiredObject() {
		String testName = "CREATE_DESIRED_OBJECT_TEST :";
		System.out.println(consoleOutput("NEW DATA RATINGS TEST", testName,0));
		System.out.println(consoleOutput(testName, "START",4));
		JSONObject expectedObj = new JSONObject();
		expectedObj.put("averageRating", 4.0);
		expectedObj.put("title", "COMPUTER PROGRAMMING IN C");
		JSONObject item = null;
		for (int i = 0; i < JSONData.getItems().size(); i++) {
			item = (JSONObject) JSONData.getItems().get(i);
				if (JSONData.getVolumeInfo(item).containsKey("averageRating")) {
						i = JSONData.getItems().size();
				}
		}
		JSONObject returnedObj = dr.createDesiredObject(item);
		System.out.println(consoleOutput("EXPECTED" , expectedObj.toJSONString(), 8));
		System.out.println(consoleOutput("ACTUAL  ",returnedObj.toJSONString(),8));
		Assert.assertEquals(expectedObj.get("averageRating"), returnedObj.get("averageRating"));
		Assert.assertEquals(expectedObj.get("title"), returnedObj.get("title"));
		String status = null;
		status = ((double) expectedObj.get("averageRating") == (double) returnedObj.get("averageRating")) && ((expectedObj.get("title").equals(returnedObj.get("title")))) ? "PASS" : "FAIL";
		System.out.println(consoleOutput(testName, status,4));
		System.out.println(consoleOutput("END DATA ratings TEST", testName,0));
	}

	@Test
	public void testGetRankedArray() {
		String testName = "GET_RANKED_ARRAY_TEST :";
		System.out.println(consoleOutput("NEW DATA RATINGS TEST", testName,0));
		System.out.println(consoleOutput(testName , "START",4));
		JSONArray returnedArray = dr.getRankedArray(5);	
		writer.setOutPutFile("DataRatingsTestActualOutPut");
		writer.writeFile(returnedArray.toJSONString());
		String returnedData = returnedArray.toJSONString();
		System.out.println(consoleOutput("EXPECTED" , expectedReturnArray.toJSONString(), 8));
		System.out.println(consoleOutput("ACTUAL  ",returnedArray.toJSONString(),8));	
		Assert.assertEquals(expectedReturnArray.toJSONString(), returnedData);
		String status;
		status = expectedReturnArray.toJSONString().equals(returnedData) ? "PASS" : "FAIL";
		System.out.println(consoleOutput(testName, status,4));
		System.out.println(consoleOutput("END DATA RATINGS TEST", testName,0));
		
	}
	
	
	private String consoleOutput(String content, String suffix, int indentation) {
		String indnt = "";
		for(int i = 0; i < indentation; i++) { 
			indnt = indnt + " ";
		}
		return "\n" + indnt +"#### " + content + " #### [STATUS: " + suffix + "]";
	}
	
}
