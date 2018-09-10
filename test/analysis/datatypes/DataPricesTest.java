package test.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;
import code.analysis.JSONData;
import code.analysis.datatypes.DataPrices;
import resource.TextFileReader;

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

	String expectedData = "[{\"costPerPage\":0.009032258064516128,\"title\":\"COMPUTER PROGRAMMING IN C\"},{\"costPerPage\":0.013807692307692307,\"title\":\"Basic Computer Programming\"},{\"costPerPage\":0.026045487894350697,\"title\":\"The C++ Programming Language\"},{\"costPerPage\":0.02,\"title\":\"COMPUTER PROGRAMMING IN FORTRAN 90 AND 95\"},{\"costPerPage\":0.027199754901960783,\"title\":\"Programming Python\"}]";
	int testNum = 0;
	static JSONData jsonData;
	static DataPrices dp;
	
	public DataPricesTest() {
		//not used
	}

	@Before
	public void init() throws ParseException {
		TextFileReader.readText();
		jsonData = new JSONData(TextFileReader.getRawData());
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
		String returnedData = returnedArray.toJSONString();
		System.out.println(consoleOutput("EXPECTED" , expectedData, 8));
		System.out.println(consoleOutput("ACTUAL  ",returnedData,8));
		Assert.assertEquals(expectedData, returnedData);
		String status;
		status = expectedData.equals(returnedData) ? "PASS" : "FAIL";
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
	
	
		/** TESTS TO BE IMPLEMENTED **/
	
//	@Test
//	public void testIterationMethod() {
//		String testName = "ITERATION_METHOD_TEST :";
//		System.out.println(consoleOutput("NEW DATA PRICES TEST", testName));
//
//		System.out.println(consoleOutput(testName, "START"));
//
//		//ToDo 
//		//Contains ListPrice && CostCount (stim data +ve and -ve) 
//		//JSONArrayLen < ListLen {addNew} else {dontAddNew}
//		
//		// Not Yet Implemented
//		Assert.assertTrue(false);
//
//		String status = null;
////		status = expectedData.equals(someCondition) ? "PASS" : "FAIL";
//		System.out.println(consoleOutput(testName, status));
//	}
//
//	@Test
//	public void testEvaluateListForRanking() {
//		String testName = "EVALUATE_LIST_FOR_RANKING_TEST :";
//		System.out.println(consoleOutput("NEW DATA PRICES TEST", testName));
//
//		System.out.println(consoleOutput(testName, "START"));
//
//		// Not Yet Implemented
//		Assert.assertTrue(false);
//
//		String status = null;
////		status = expectedData.equals(someCondition) ? "PASS" : "FAIL";
//		System.out.println(consoleOutput(testName, status));
//	}

}
