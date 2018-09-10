package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataRatings extends AbstractRankedJSONArray {
	
	private String targetedParameter = "averageRating";
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put(targetedParameter, JSONData.getAverageRating(item));
		return newObj;
	}

	@Override
	public boolean conditionForRankedArrayObjectDisplacement(JSONObject item, JSONObject arrayCurrentIteration) {
		return (double)(item.get(targetedParameter)) > (double)(arrayCurrentIteration.get(targetedParameter));
	}

	@Override
	public boolean containsTargetData(JSONObject item) {
		return JSONData.getVolumeInfo(item).containsKey(targetedParameter);
	}
	

}
