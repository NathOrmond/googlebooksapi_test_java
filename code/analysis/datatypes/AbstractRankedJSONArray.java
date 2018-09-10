package code.analysis.datatypes;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import code.analysis.JSONData;

public abstract class AbstractRankedJSONArray {
	
	public JSONArray rankedJSONArray;
	public int listLength;

	public abstract JSONObject createDesiredObject(JSONObject item);
	public abstract boolean conditionForRankedArrayObjectDisplacement(JSONObject item, JSONObject arrayCurrentIteration);
	public abstract boolean containsTargetData(JSONObject item);
	
	public JSONArray getRankedArray(int listLength) {
		if (JSONData.isDatasPopulated()) {
			this.listLength = listLength;
			this.rankedJSONArray = new JSONArray();
			iterateThroughItemsLoop();
			return this.rankedJSONArray;
		} else {
			System.out.println("JSONData not populated");
			return null;
		}
	}

	public void iterateThroughItemsLoop() { 
		JSONObject item;
		for (int i = 0; i < JSONData.getItems().size(); i++) {
			item = (JSONObject) JSONData.getItems().get(i);
			iterationMethod(item);
		}
	}
	
	public void iterationMethod(JSONObject item) { 
		if (containsTargetData(item)) {
			if (this.rankedJSONArray.size() < getListLength()) {
				addNewToRankedArray(createDesiredObject(item));
			} else {
				evaluateListForRanking(createDesiredObject(item));
			}
		}
	}

	public boolean evaluateListForRanking(JSONObject item) { 
		JSONObject arrayCurrentIteration;
		for (int i = 0; i < this.rankedJSONArray.size(); i++) {
			arrayCurrentIteration = (JSONObject) this.rankedJSONArray.get(i);
			if (conditionForRankedArrayObjectDisplacement(item, arrayCurrentIteration)) {
				removeOldFromRankedArray(i);
				addNewToRankedArray(item);
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public void addNewToRankedArray(JSONObject item) { 
		this.rankedJSONArray.add(item);
	}
	
	public void removeOldFromRankedArray(int index) { 
		this.rankedJSONArray.remove(index);
	}
	
	public int getListLength() {
		return listLength;
	}
	
	public void setListLength(int listLength) {
		this.listLength = listLength;
	}
	
}
