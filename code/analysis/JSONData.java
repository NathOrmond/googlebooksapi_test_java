package code.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class JSONData {

	static String rawData;
	static JSONObject json, volumeInfo;
	static JSONArray items;

	/**
	 * Constructor sets rawData (to be formatted) creates JSONObjects & JSONArrays
	 * for rawDataFormat (see jsonsimple1.1.1 documentation).
	 * 
	 * @param rawData
	 * @throws ParseException
	 */
	public JSONData(String rawData) throws ParseException {
		JSONData.rawData = rawData;
		JSONParser parser = new JSONParser();
		JSONData.json = (JSONObject) parser.parse(rawData);
		JSONData.items = (JSONArray) json.get("items");
		System.out.println("static vars set :");
	}

	/**********************************************************************
	 * 		GETTERS
	 **********************************************************************/
	
	/**
	 * Returns the raw results from querying the URL
	 * @return JSON
	 */
	public static JSONObject getEntireJsonObject() {
		return JSONData.json;
	}
	
	/**
	 * Returns the entire items array list from Data
	 * @return items
	 */
	public static JSONArray getItems() {
		return JSONData.items;
	}
	
	
	public static boolean publishersContainsPublisher(JSONObject publishers, String publisher) {
		return publishers.containsKey("publisher");
	}
	
	public static double getCostPerPage(double amount, long pageCount) {
		return (amount / (double) pageCount);
	}

	public static double getAmount(JSONObject listPrice) {
		return listPrice.containsKey("amount") ? (double) listPrice.get("amount") : (double) 0;
	}

	public static long getPageCount(JSONObject volumeInfo) {
		return volumeInfo.containsKey("pageCount") ? (long) volumeInfo.get("pageCount") : (long) 0;
	}

	public static boolean containsPublisher(JSONObject item) {
		return getVolumeInfo(item).containsKey("publisher");
	}

	public static double getAverageRating(JSONObject item) {
		return getVolumeInfo(item).containsKey("averageRating") ? (double) getVolumeInfo(item).get("averageRating") : (double) 0;
	}

	public static String getPublishedDate(JSONObject item) {
		return getVolumeInfo(item).containsKey("publishedDate") ? (String) getVolumeInfo(item).get("publishedDate") : "";
	}

	public static String getTitle(JSONObject item) {
		return getVolumeInfo(item).containsKey("title") ? (String) getVolumeInfo(item).get("title") : "";
	}

	public static String getPublisher(JSONObject item) {
		return getVolumeInfo(item).containsKey("publisher") ? (String) getVolumeInfo(item).get("publisher") : "";
	}

	public static JSONObject getVolumeInfo(JSONObject item) {
		return (JSONObject) item.get("volumeInfo");
	}

	public static JSONObject getSaleInfo(JSONObject item) {
		return (JSONObject) item.get("saleInfo");
	}

	public static JSONObject getListPrice(JSONObject saleInfo) {
		return (JSONObject) saleInfo.get("listPrice");
	}
	
}
