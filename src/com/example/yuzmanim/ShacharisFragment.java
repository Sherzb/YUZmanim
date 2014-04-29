package com.example.yuzmanim;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.common.collect.Multimap;

public class ShacharisFragment extends Fragment {

	private ArrayList<ArrayList<Minyan>> shacharisTables = new ArrayList<ArrayList<Minyan>>();
	private int spinnerPosition = 0;
	private boolean startSpinner = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_shacharis, container, false);
		Log.i("ShacharisFragment", "OnCreateView was called on the ShacharisFragment"); 
		startSpinner = false;
		Log.i("ShacharisFragment", startSpinner + "");
		
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("ShacharisFragment", "OnDestroy was called on the ShacharisFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("ShacharisFragment", "OnCreate was called on the ShacharisFragment");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Spinner spinner = (Spinner)getView().findViewById(R.id.spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.shacharis_days, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setPromptId(R.string.shacharis_spinner);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner.setSelection(spinnerPosition);
	}

	public class CustomOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			if (spinnerPosition != pos) {
				spinnerPosition = pos;
				Toast.makeText(parent.getContext(), 
						"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString() + " " + spinnerPosition,
						Toast.LENGTH_SHORT).show();
			}
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("spinnerPosition", spinnerPosition);
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState) {
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null) {
			spinnerPosition = savedInstanceState.getInt("spinnerPosition");
		}
	}
}
