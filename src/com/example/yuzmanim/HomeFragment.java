package com.example.yuzmanim;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {
	
	OnRefreshSelectedListener mCallback;
	Button mRefreshButton;

	//To let the refresh button communicate with the main activity
	public interface OnRefreshSelectedListener {
		public void onRefreshSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception
		try {
			mCallback = (OnRefreshSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(activity.toString()
					+ " must implement OnRefreshSelectedListener");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home_test, container, false);
		
		mRefreshButton = (Button)rootView.findViewById(R.id.refresh_button);
		mRefreshButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				mCallback.onRefreshSelected();
			}
		});

		 Log.i("HomeFragment", "OnCreateView was called on the HomeFragment");
		return rootView;
	}
	
	//Whats the difference between onDestroy and onDestroyView!?
	@Override
	public void onDestroyView() {
		super.onDestroyView();
		Log.i("HomeFragment", "OnDestroy was called on the HomeFragment");
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("HomeFragment", "OnCreate was called on the HomeFragment");
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Log.i("HomeFragment", "OnViewCreated was called on the HomeFragment");
	}
}
