package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataPrices extends AbstractRankedJSONArray {
	private String targetParameter = "costPerPage";
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put(targetParameter, JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));
		return newObj;
	}

	@Override
	public boolean conditionForRankedArrayObjectDisplacement(JSONObject item, JSONObject arrayCurrentIteration) {
		return (double)(item.get(targetParameter)) < (double)(arrayCurrentIteration.get(targetParameter));
	}

	@Override
	public boolean containsTargetData(JSONObject item) {
		return JSONData.getSaleInfo(item).containsKey("listPrice") && JSONData.getVolumeInfo(item).containsKey("pageCount");
	}


}
