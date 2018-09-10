package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataPrices extends AbstractRankedJSONArray {
	private String targetParameter = "costPerPage";
	
	/**
	 * The returned item contains the books title and its cost per page
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put(targetParameter, JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));
		return newObj;
	}

	/**
	 * the condition for addition of an item to an already full list is that its costPerPage is less than 
	 * the costPerPage of the object in the list (for the cheapest one).
	 */
	@Override
	public boolean conditionForRankedArrayObjectDisplacement(JSONObject item, JSONObject arrayCurrentIteration) {
		return (double)(item.get(targetParameter)) < (double)(arrayCurrentIteration.get(targetParameter));
	}

	/**
	 * checks that the JSON item contains the necessary data for being included in the ranked list
	 */
	@Override
	public boolean containsTargetData(JSONObject item) {
		return JSONData.getSaleInfo(item).containsKey("listPrice") && JSONData.getVolumeInfo(item).containsKey("pageCount");
	}


}
