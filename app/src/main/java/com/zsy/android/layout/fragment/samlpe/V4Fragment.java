package com.zsy.android.layout.fragment.samlpe;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.zsy.android.R;

public class V4Fragment extends Fragment {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		TextView textView = new TextView(container.getContext());
		textView.setTextSize(50);
		textView.setText("v4Fragment");
		return textView;
	}
}
