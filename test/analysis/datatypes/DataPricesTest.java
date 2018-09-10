package test.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import code.analysis.JSONData;
import code.analysis.datatypes.DataPrices;
import org.junit.Assert;

class DataPricesTest {

	String expectedData = "[{\"costPerPage\":0.009032258064516128,\"title\":\"COMPUTER PROGRAMMING IN C\"},{\"costPerPage\":0.013807692307692307,\"title\":\"Basic Computer Programming\"},{\"costPerPage\":0.026045487894350697,\"title\":\"The C++ Programming Language\"},{\"costPerPage\":0.02,\"title\":\"COMPUTER PROGRAMMING IN FORTRAN 90 AND 95\"},{\"costPerPage\":0.027199754901960783,\"title\":\"Programming Python\"}]";

	@Before
	private void init() throws ParseException {

	}

	@Test
	void testIterationMethod() {
		// Not Yet Implemented
		Assert.assertTrue(false);
	}

	@Test
	void testEvaluateListForRanking() {
		// Not Yet Implemented
		Assert.assertTrue(false);
	}

	@Test
	void testCreateDesiredObject() {
		// Not Yet Implemented
		Assert.assertTrue(false);
	}

	@Test
	void testGetRankedArray() throws ParseException {
		System.out.println("#### START GET RANKED ARRAY TEST ####");
		
		TextFileReader.readText();
		JSONData jsonData = new JSONData(TextFileReader.getRawData());
		DataPrices dp = new DataPrices();
		JSONArray returnedArray = dp.getRankedArray(5);
		String returnedData = returnedArray.toJSONString();

		System.out.println("\nExpected : ");
		System.out.println(expectedData);
		System.out.println("\nActual : ");
		System.out.println(returnedData);

		Assert.assertEquals(expectedData, returnedData);
		
		System.out.println("#### END GET RANKED ARRAY TEST ####");
	}

}
