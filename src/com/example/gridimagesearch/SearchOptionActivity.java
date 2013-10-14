package com.example.gridimagesearch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchOptionActivity extends Activity {
	Spinner spImageSize;
	Spinner spColorFilter;
	Spinner spImageType;
	EditText etSiteFilter;
	ArrayAdapter<CharSequence> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_option);
		setupViews();
		
		adapter = ArrayAdapter.createFromResource(this,  R.array.sp_image_size, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageSize.setAdapter(adapter);
		
		adapter = ArrayAdapter.createFromResource(this,  R.array.sp_color_filter, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spColorFilter.setAdapter(adapter);
		
		adapter = ArrayAdapter.createFromResource(this,  R.array.sp_image_type, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spImageType.setAdapter(adapter);
		
	}

	private void setupViews() {
		spImageSize = (Spinner) findViewById(R.id.spImageSize);
		spColorFilter= (Spinner) findViewById(R.id.spColorFilter);
		spImageType = (Spinner) findViewById(R.id.spImageType);
		etSiteFilter = (EditText) findViewById(R.id.etSiteFilter);
	}

	public void onClickSearchOption(View v) {
		ArrayList<String> options = new ArrayList<String>();
		options.add(String.valueOf(spImageSize.getSelectedItem()));
		options.add(String.valueOf(spColorFilter.getSelectedItem()));
		options.add(String.valueOf(spImageType.getSelectedItem()));
		options.add(etSiteFilter.getText().toString());
		
		File optionDir = getFilesDir();
		File optionFile = new File(optionDir, "option.txt");
		try {
			FileUtils.writeLines(optionFile, options);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Toast.makeText(this,  "Clicked on Search Option: " 
				+ String.valueOf(spImageSize.getSelectedItem()), Toast.LENGTH_SHORT).show();
		this.finish();
	}
}
