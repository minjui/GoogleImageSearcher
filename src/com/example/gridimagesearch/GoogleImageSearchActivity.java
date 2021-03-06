package com.example.gridimagesearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
//import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class GoogleImageSearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	Button btnNext;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	private int start;

    public GoogleImageSearchActivity() {
		start = 0;
	}


	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_image_search);
        setupViews();
        btnNext.setVisibility(View.GONE);
        imageAdapter = new ImageResultArrayAdapter(this, imageResults);
        gvResults.setAdapter(imageAdapter);
        gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
        });
    }


    private void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		btnNext = (Button) findViewById(R.id.btnNext);
	}


	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.google_image_search, menu);
        return true;
    }
	
	public void onSearchOptionClick(MenuItem item) {
		Intent i = new Intent(this, SearchOptionActivity.class);
		startActivity(i);
    	//Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();
	}
	
	public void onImageSearch(View v) {
		File optionDir = getFilesDir();
		File optionFile = new File(optionDir, "option.txt");
		ArrayList<String> options = new ArrayList<String>();
		try {
			options = new ArrayList<String>(FileUtils.readLines(optionFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String tag = (String) v.getTag();
		if (tag != null) start = 0;

		int rsz = 3;
		String query = etQuery.getText().toString();
//		Toast.makeText(this,  "Searching for " + query + " : " + start + " : " + tag, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		imageResults.clear();
		
		for (int i = 0; i < 3; i++) {
			String url = "https://ajax.googleapis.com/ajax/services/search/images?rsz=" + rsz +
					"&start=" + start + "&v=1.0&q=" + Uri.encode(query);

			if (options.get(0).length() > 0) {
				String size = options.get(0);
				url += "&imgsz=" + size;
			}

			if (options.get(1).length() > 0) {
				String color = options.get(1);
				url += "&imgcolor=" + color;
			}

			if (options.get(2).length() > 0) {
				String type = options.get(2);
				url += "&imgtype=" + type;
			}

			if (options.get(3).length() > 0) {
				String site = options.get(3);
				url += "&as_sitesearch=" + site;
			}

			// https://ajax.googleapis.com/ajax/services/search/image?q=Android&v=1.0
			client.get(url, 
					new JsonHttpResponseHandler() { 
				@Override
				public void onSuccess(JSONObject response) {
					JSONArray imageJsonResults = null;
					try {
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
		//				imageResults.clear();
						imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
						//Log.d("DEBUG", imageResults.toString());
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			});
			start += rsz;
		}
				
        btnNext.setVisibility(View.VISIBLE);
	}
    
}
