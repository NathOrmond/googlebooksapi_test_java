package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataPrices extends AbstractRankedJSONArray {
	
	@Override
	public void iterationMethod(JSONObject item) {
		if (JSONData.getSaleInfo(item).containsKey("listPrice") && JSONData.getVolumeInfo(item).containsKey("pageCount")) {
			if (this.rankedJSONArray.size() < getListLength()) {
				addNewToRankedArray(createDesiredObject(item));
			} else {
				evaluateListForRanking(createDesiredObject(item));
			}
		}
	}

	@Override
	public boolean evaluateListForRanking(JSONObject item) {
		JSONObject arrayObj;
		for (int i = 0; i < this.rankedJSONArray.size(); i++) {
			arrayObj = (JSONObject) this.rankedJSONArray.get(i);
			if ((double)(item.get("costPerPage")) < (double)(arrayObj.get("costPerPage"))) {
				removeOldFromRankedArray(i);
				addNewToRankedArray(item);
				return true;
			}
		}
		return false;
	}

	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put("costPerPage", JSONData.getCostPerPage(JSONData.getAmount(JSONData.getListPrice(JSONData.getSaleInfo(item))), JSONData.getPageCount(JSONData.getVolumeInfo(item))));
		return newObj;
	}


}
