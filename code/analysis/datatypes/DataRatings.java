package code.analysis.datatypes;

import org.json.simple.JSONObject;
import code.analysis.JSONData;

public class DataRatings extends AbstractRankedJSONArray {
	
	private String targetedParameter = "averageRating";
	 
	/**
	 * Desired objects in ranked list contain both book title and averageRating
	 */
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		JSONObject newObj = new JSONObject();
		newObj.put("title", JSONData.getTitle(item));
		newObj.put(targetedParameter, JSONData.getAverageRating(item));
		return newObj;
	}

	/**
	 * condition for an item being analysed to replace an existing object within the rankedJSONArray 
	 * is that the target object has an averageRating greater than that of one currently within the list
	 */
	@Override
	public boolean conditionForRankedArrayObjectDisplacement(JSONObject item, JSONObject arrayCurrentIteration) {
		return (double)(item.get(targetedParameter)) > (double)(arrayCurrentIteration.get(targetedParameter));
	}

	/**
	 * checks that the targeted item in current iteration contains the necessary target parameter to
	 * be included within the list.
	 */
	@Override
	public boolean containsTargetData(JSONObject item) {
		return JSONData.getVolumeInfo(item).containsKey(targetedParameter);
	}
	

}
