package com.example.gridimagesearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ImageResult implements Serializable {
	private static final long serialVersionUID = 6309945168105461554L;
	private String fullUrl;
	private String thumbUrl;
	private String title;
	
	public ImageResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
			String title = json.getString("title");
			this.title = title.replaceAll("<.*?>", "");
		} catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
			this.title = null;
		}
	}
	public String getFullUrl() {
		return fullUrl;
	}
	
	public String getThumbUrl() {
		return thumbUrl;
	}
	
	public String getTitle() {
		return title;
	}
	
	public String toString() {
		return this.thumbUrl;
	}
	public static ArrayList<ImageResult> fromJSONArray(
			JSONArray array) {
		ArrayList<ImageResult> results = new ArrayList<ImageResult>();
		for (int x = 0; x < array.length(); x++) {
			try {
				results.add(new ImageResult(array.getJSONObject(x)));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return results;
	}
}
