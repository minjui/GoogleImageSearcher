package com.example.gridimagesearch;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.loopj.android.image.SmartImageView;

public class ImageResultArrayAdapter extends ArrayAdapter<ImageResult> {
	public ImageResultArrayAdapter(Context context, List<ImageResult> images) {
		super(context, R.layout.item_image_result, images);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageResult imageInfo = this.getItem(position);
		View gridView;
		if (convertView == null) {
			LayoutInflater inflator = LayoutInflater.from(getContext());
			gridView = inflator.inflate(R.layout.item_image_result, null);
		} else {
			gridView = convertView;
		}
		
		SmartImageView ivImage = (SmartImageView) gridView.findViewById(R.id.sivResult);
		ivImage.setImageUrl(imageInfo.getThumbUrl());
			
		TextView tvImage = (TextView) gridView.findViewById(R.id.tvImage);
		String title = imageInfo.getTitle();
		if (title.length() > 12) {
			title = title.substring(0, 12) + " ...";
		}
		
		tvImage.setText(title);
		
		return gridView;
	}

}
