package code.analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FormatData {

	String rawData;
	JSONObject json, volumeInfo;
	JSONArray items;

	/**
	 * Constructor sets rawData (to be formatted) creates JSONObjects & JSONArrays
	 * for rawDataFormat (see jsonsimple1.1.1 documentation).
	 * 
	 * @param rawData
	 * @throws ParseException
	 */
	public FormatData(String rawData) throws ParseException {
		this.rawData = rawData;
		JSONParser parser = new JSONParser();
		json = (JSONObject) parser.parse(rawData);
		items = (JSONArray) json.get("items");
		System.out.println(json);
	}

	public JSONObject getJson() {
		return this.json;
	}

	/**
	 * 
	 * 
	 * @param listLength
	 * @return
	 */
	@SuppressWarnings("unchecked") // JSONObject cannot have type argument
	public JSONArray getHighestRated(int listLength) {
		JSONArray answer = new JSONArray();
		List<JSONObject> rankedObjects = new ArrayList<JSONObject>(listLength);
		JSONObject targetObj;

		for (int i = 0; i < items.size(); i++) {
			targetObj = (JSONObject) items.get(i);
			rankedObjects = evaluateHighestDouble(rankedObjects, targetObj, "volumeInfo", "averageRating", listLength,
					i);
		}

		for (JSONObject obj : rankedObjects) {
			JSONObject outputObject = new JSONObject();
			outputObject.put("title", getTitle(obj));
			outputObject.put("averageRating", getAverageRating(obj));
			answer.add(outputObject);
		}

		return answer;
	}

	/**
	 * 
	 * @param objs
	 * @param targetObj
	 * @param tertiaryKey
	 * @param valueKey
	 * @param listLength
	 * @param count
	 * @return
	 */
	private List<JSONObject> evaluateHighestDouble(List<JSONObject> objs, JSONObject targetObj, String tertiaryKey,
			String valueKey, int listLength, int count) {

		if (objs.size() < listLength) {
			objs.add(count, targetObj);
		} else {
			JSONObject targetVolumeInfo = (JSONObject) targetObj.get(tertiaryKey);
			JSONObject iterationVolumeInfo;
			int pos = 0;
			for (JSONObject obj : objs) {
				iterationVolumeInfo = (JSONObject) obj.get(tertiaryKey);

				if (iterationVolumeInfo.containsKey(valueKey) && targetVolumeInfo.containsKey(valueKey)) {
					double targetRating, iterationRating;

					targetRating = (double) targetVolumeInfo.get(valueKey);
					iterationRating = (double) iterationVolumeInfo.get(valueKey);

					if (targetRating > iterationRating) {
						return replaceIndexAndReturn(pos, objs, targetObj);
					}

					pos++;
				} else if (iterationVolumeInfo.containsKey(valueKey) || targetVolumeInfo.containsKey(valueKey)) {
					return targetVolumeInfo.containsKey(valueKey) ? replaceIndexAndReturn(pos, objs, targetObj) : objs;
				}
			}

		}

		return objs;
	}

	private List<JSONObject> replaceIndexAndReturn(int index, List<JSONObject> objs, JSONObject replacementObj) {
		objs.set(index, replacementObj);
		return objs;
	}

	/***
	 * Formats all data from search to : JSONObject publishers : { publisher :
	 * (JSONArray) [
	 * 
	 * n* instance(s) :(JSONObject){ title: "" publishedDate:"" } ] }
	 * 
	 * @return publishers
	 */
	public JSONObject booksByPublisher() {
		JSONObject item;
		JSONObject publishers = new JSONObject();

		for (int i = 0; i < items.size(); i++) {
			item = (JSONObject) items.get(i);
			if (containsPublisher(item)) {
				if (publishersContains(publishers, getPublisher(item))) {
					publishers = addInstanceToExistingPublisher(publishers, item);
				} else {
					publishers = addNewPublisher(publishers, item);
				}
			}
		}

		return publishers;
	}

	private boolean publishersContains(JSONObject publishers, String publisher) {
		return publishers.containsKey("publisher");
	}

	@SuppressWarnings("unchecked")
	private JSONObject addNewPublisher(JSONObject publishers, JSONObject item) {

		JSONObject instance = new JSONObject();
		instance.put("title", getTitle(item));
		instance.put("publishedDate", getPublishedDate(item));
		publishers.put(getPublisher(item), instance);
		return publishers;
	}

	@SuppressWarnings("unchecked")
	private JSONObject addInstanceToExistingPublisher(JSONObject publishers, JSONObject item) {

		JSONArray publisher = (JSONArray) publishers.get(getPublisher(item));
		publishers.remove(publishers.get(getPublisher(item)));
		JSONObject instance = new JSONObject();
		instance.put("title", getTitle(item));
		instance.put("publishedDate", getPublishedDate(item));
		publisher.add(instance);
		publishers.put(getPublisher(item), publisher);

		return publishers;
	}

	/***
	 * * Formats all data from search to :
	 * 
	 * JSONArray(listLength) [ n* JSONObject{ title:"" costPerPage:"" }n ]
	 * 
	 * based on desired length
	 * 
	 * @param length
	 * @return topCheapestBooks
	 */
	public JSONArray topCheapestBooks(int length) {
		JSONArray topCheapestBooks = new JSONArray();
		JSONObject item;

		for (int i = 0; i < items.size(); i++) {
			item = (JSONObject) items.get(i);
			if (getSaleInfo(item).containsKey("listPrice") && getVolumeInfo(item).containsKey("pageCount")) {
				if (topCheapestBooks.size() < length) {
					topCheapestBooks = addNewToTopCheapestBooks(item, topCheapestBooks);
				} else {
					topCheapestBooks = evaluateTopCheapestBooks(item, topCheapestBooks);
				}
			}
		}

		return topCheapestBooks;
	}

	@SuppressWarnings("unchecked")
	private JSONArray evaluateTopCheapestBooks(JSONObject item, JSONArray topCheapestBooks) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", getCostPerPage(getAmount(getListPrice(getSaleInfo(item))), getPageCount(getVolumeInfo(item))));
		newObj.put("costPerPage", getCostPerPage(getAmount(getListPrice(getSaleInfo(item))), getPageCount(getVolumeInfo(item))));

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

	@SuppressWarnings("unchecked")
	private JSONArray addNewToTopCheapestBooks(JSONObject item, JSONArray topCheapestBooks) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", getTitle(item));
		newObj.put("costPerPage", getCostPerPage(getAmount(getListPrice(getSaleInfo(item))), getPageCount(getVolumeInfo(item))));
		topCheapestBooks.add(newObj);

		return topCheapestBooks;
	}

	private double getCostPerPage(double amount, long pageCount) {
		return (amount / (double) pageCount);
	}

	private double getAmount(JSONObject listPrice) {
		return listPrice.containsKey("amount") ? (double) listPrice.get("amount") : (double) 0;
	}

	private long getPageCount(JSONObject volumeInfo) {
		return volumeInfo.containsKey("pageCount") ? (long) volumeInfo.get("pageCount") : (long) 0;
	}

	private boolean containsPublisher(JSONObject item) {
		return getVolumeInfo(item).containsKey("publisher");
	}

	private double getAverageRating(JSONObject item) {
		return getVolumeInfo(item).containsKey("averageRating") ? (double) getVolumeInfo(item).get("averageRating") : (double) 0;
	}

	private String getPublishedDate(JSONObject item) {
		return getVolumeInfo(item).containsKey("publishedDate") ? (String) getVolumeInfo(item).get("publishedDate") : "";
	}

	private String getTitle(JSONObject item) {
		return getVolumeInfo(item).containsKey("title") ? (String) getVolumeInfo(item).get("title") : "";
	}

	private String getPublisher(JSONObject item) {
		return getVolumeInfo(item).containsKey("publisher") ? (String) getVolumeInfo(item).get("publisher") : "";
	}

	private JSONObject getVolumeInfo(JSONObject item) {
		return (JSONObject) item.get("volumeInfo");
	}

	private JSONObject getSaleInfo(JSONObject item) {
		return (JSONObject) item.get("saleInfo");
	}
	
	private JSONObject getListPrice(JSONObject saleInfo) { 
		return (JSONObject) saleInfo.get("listPrice");
	}

}
