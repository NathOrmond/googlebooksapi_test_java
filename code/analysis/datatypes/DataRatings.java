package code.analysis.datatypes;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import code.analysis.JSONData;

public class DataRatings extends AbstractRankedJSONArray {
	

	//*****************NOT YET IMPLEMENTED**************************************************************************************************

	@Override
	public void iterationMethod(JSONObject item) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public boolean evaluateListForRanking(JSONObject item) {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	public JSONObject createDesiredObject(JSONObject item) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//*****************OLD WORKING METHODS BELOW**************************************************************************************************
	
	/**
	 * Formats Raw Data to a JSONArray of the top n rated books in the following format:
	 * <JSONArray highest rated books> [ n* <JSONOBject book> {title "", rating ""}]
	 * 
	 * @param listLength
	 * @return JSONArray
	 */
	@SuppressWarnings("unchecked") // JSONObject cannot have type argument
	public static JSONArray getHighestRated(int listLength) {
		JSONArray highestRated = new JSONArray();
		List<JSONObject> rankedObjects = new ArrayList<JSONObject>(listLength);
		
		JSONObject targetObj;
		for (int i = 0; i < JSONData.getItems().size(); i++) {
			targetObj = (JSONObject) JSONData.getItems().get(i);
			rankedObjects = evaluateHighestDouble(rankedObjects, targetObj, "volumeInfo", "averageRating", listLength,i);
		}

		JSONObject rankingObject;
		for (JSONObject obj : rankedObjects) {
			rankingObject= new JSONObject();
			rankingObject.put("title", JSONData.getTitle(obj));
			rankingObject.put("averageRating", JSONData.getAverageRating(obj));
			highestRated.add(rankingObject);
		}

		return highestRated;
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
	private static List<JSONObject> evaluateHighestDouble(List<JSONObject> objs, JSONObject targetObj, String tertiaryKey,
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

	private static List<JSONObject> replaceIndexAndReturn(int index, List<JSONObject> objs, JSONObject replacementObj) {
		objs.set(index, replacementObj);
		return objs;
	}




}
