package code.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataPrices {

	/***
	 * Returns a list of the top cheapest books within the raw data in the format
	 * <JSONArray listLength> [ n* <JSONObject book>{ title:"" , costPerPage:"" }n ]
	 * the length of the cheapestBooks list to be created is a parameter for the method.
	 * 
	 * @param length
	 * @return topCheapestBooks
	 */
	public static JSONArray topCheapestBooks(int length) {
		JSONArray topCheapestBooks = new JSONArray();
		JSONObject item;

		for (int i = 0; i < JSONData.getItems().size(); i++) {
			item = (JSONObject) JSONData.getItems().get(i);
			if (JSONData.getSaleInfo(item).containsKey("listPrice") && JSONData.getVolumeInfo(item).containsKey("pageCount")) {
				if (topCheapestBooks.size() < length) {
					topCheapestBooks = addNewToTopCheapestBooks(item, topCheapestBooks);
				} else {
					topCheapestBooks = evaluateTopCheapestBooks(item, topCheapestBooks);
				}
			}
		}

		return topCheapestBooks;
	}

	
	/**
	 * 
	 * Evaluates whether the cost per page of a new JSONObject would put it 
	 * in a JSONArray of the topCheapestBooks. 
	 * If it does deletes the lowest value JSONObject and adds the new JSONObject returns the JSONArray 
	 * else returns the list as it was, no changes.
	 * 
	 * @param item
	 * @param topCheapestBooks
	 * @returntopCheapestBooks
	 */
	@SuppressWarnings("unchecked")
	private static JSONArray evaluateTopCheapestBooks(JSONObject item, JSONArray topCheapestBooks) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));
		newObj.put("costPerPage", JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));

		JSONObject arrayObj;
		for (int i = 0; i < topCheapestBooks.size(); i++) {
			arrayObj = (JSONObject) topCheapestBooks.get(i);
			if (((double) newObj.get("costPerPage")) < ((double) arrayObj.get("costPerPage"))) {
				topCheapestBooks.remove(i);
				topCheapestBooks.add(newObj);
				return topCheapestBooks;
			}
		}

		return topCheapestBooks;
	}

	/**
	 * Adds a new JSONObject to a JSONArray
	 * 
	 * @param item
	 * @param array
	 * @return array (with item appended)
	 */
	@SuppressWarnings("unchecked")
	private static JSONArray addNewToTopCheapestBooks(JSONObject item, JSONArray array) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put("costPerPage", JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));
		array.add(newObj);
		return array;
	}
	
}
