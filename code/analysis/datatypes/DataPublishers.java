package code.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataPublishers {

	/**
	 * Formats all raw data into JSON Object containing each publisher, paired to a JSONArray of books they have published 
	 * in the following data format
	 * <JSONObject publishers> { n* <JSONArray publisher> [n* <JSONObject published book> { title: "" ,  publishedDate:"" } ] }
	 * 
	 * @return publishers
	 */
	public static JSONObject booksByPublisher() {
		JSONObject item;
		JSONObject publishers = new JSONObject();

		for (int i = 0; i < JSONData.getItems().size(); i++) {
			item = (JSONObject) JSONData.getItems().get(i);
			if (JSONData.containsPublisher(item)) {
				if (JSONData.publishersContainsPublisher(publishers, JSONData.getPublisher(item))) {
					publishers = addInstanceToExistingPublisher(publishers, item);
				} else {
					publishers = addNewPublisher(publishers, item);
				}
			}
		}
		return publishers;
	}

	/**
	 * @param publishers
	 * @param item
	 * @return publishers (plus new publisher & relevant book).
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject addNewPublisher(JSONObject publishers, JSONObject item) {
		JSONObject instance = new JSONObject();
		instance.put("title", JSONData.getTitle(item));
		instance.put("publishedDate", JSONData.getPublishedDate(item));
		publishers.put(JSONData.getPublisher(item), instance);
		return publishers;
	}

	/**
	 * @param publishers
	 * @param item
	 * @return publishers (plus new book inside an existing publisher).
	 */
	@SuppressWarnings("unchecked")
	private static JSONObject addInstanceToExistingPublisher(JSONObject publishers, JSONObject item) {
		JSONArray publisher = (JSONArray) publishers.get(JSONData.getPublisher(item));
		publishers.remove(publishers.get(JSONData.getPublisher(item)));
		JSONObject instance = new JSONObject();
		instance.put("title", JSONData.getTitle(item));
		instance.put("publishedDate", JSONData.getPublishedDate(item));
		publisher.add(instance);
		publishers.put(JSONData.getPublisher(item), publisher);
		return publishers;
	}
	
}
