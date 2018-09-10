package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataRatings extends AbstractRankedJSONArray {
	

	@Override
	public void iterationMethod(JSONObject item) {
		if (JSONData.getVolumeInfo(item).containsKey("averageRating")) {
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
			if ((double)(item.get("averageRating")) > (double)(arrayObj.get("averageRating"))) {
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
		newObj.put("averageRating", JSONData.getAverageRating(item));
		return newObj;
	}
	

}
