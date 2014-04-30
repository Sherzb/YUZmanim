package com.example.yuzmanim;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	OnRefreshSelectedListener mCallback;
	Button mRefreshButton;

	private String nextMinchaTime1;
	private String nextMinchaTime2;
	private String nextMinchaInfo1;
	private String nextMinchaInfo2;
	private String finalMinyanTime;
	private String finalMinyanInfo;
	private String refreshTime;
	
	public final String LOG = "Home Fragment";

	//To let the refresh button communicate with the main activity
	public interface OnRefreshSelectedListener {
		public void onRefreshSelected();
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);

		// This makes sure that the container activity has implemented
		// the callback interface. If not, it throws an exception. ie,
		// needed for the refresh button to talk to the MainActivity
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

		/* All of this has been replaced by adding freezeText="true" to all the textViews
		if (nextMinchaTime1 != null) {
			TextView text = (TextView)rootView.findViewWithTag("nextMinchaTime1");
			text.setText(nextMinchaTime1);
			Log.i("hFrag", "Next Mincha Time 1 isn't null");
		}
		if (nextMinchaTime2 != null) {
			TextView text = (TextView)rootView.findViewWithTag("nextMinchaTime2");
			text.setText(nextMinchaTime2);
		}
		if (nextMinchaInfo1 != null) {
			TextView text = (TextView)rootView.findViewWithTag("nextMinchaInfo1");
			text.setText(nextMinchaInfo1);
		}
		if (nextMinchaInfo2 != null) {
			TextView text = (TextView)rootView.findViewWithTag("nextMinchaInfo2");
			text.setText(nextMinchaInfo2);
		}
		 */

		Log.i("HomeFragment", "OnCreateView was called on the HomeFragment");
		return rootView;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//WHY DON'T I NEED THIS ANYMORE!?!?
		//setRetainInstance(true);
		Log.i("HomeFragment", "OnCreate was called on the HomeFragment");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		if (savedInstanceState != null) {
			nextMinchaTime1 = savedInstanceState.getString("nextMinchaTime1");
			nextMinchaTime2 = savedInstanceState.getString("nextMinchaTime2");
			nextMinchaInfo1 = savedInstanceState.getString("nextMinchaInfo1");
			nextMinchaInfo2 = savedInstanceState.getString("nextMinchaInfo2");
			finalMinyanInfo = savedInstanceState.getString("finalMinyanInfo");
			finalMinyanTime = savedInstanceState.getString("finalMinyanTime");
			refreshTime = savedInstanceState.getString("refreshTime");
			Log.i("homeFragment", "Not null");
		}
		if (nextMinchaInfo1 != null && nextMinchaInfo2 != null && nextMinchaTime1 != null && nextMinchaTime2 != null
				&& refreshTime != null && finalMinyanInfo != null && finalMinyanTime != null) {
			update();
			Log.i("homeFragment", "Update Called");
		}
		Log.i("HomeFragment", "OnViewCreated was called on the HomeFragment");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) 
	{	
		super.onSaveInstanceState(outState);
		outState.putString("nextMinchaTime1", nextMinchaTime1);
		outState.putString("nextMinchaTime2", nextMinchaTime2);
		outState.putString("nextMinchaInfo1", nextMinchaInfo1);
		outState.putString("nextMinchaInfo2", nextMinchaInfo2);
		outState.putString("finalMinyanTime", finalMinyanTime);
		outState.putString("finalMinyanInfo", finalMinyanInfo);
		outState.putString("refreshTime", refreshTime);
		Log.i(LOG, "onSavedInstanceState Called");
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	public void setNextMinchaTime1(String time) {
		nextMinchaTime1 = time;
	}

	public void setNextMinchaTime2(String time) {
		nextMinchaTime2  = time;
	}

	public void setNextMinchaInfo1(String info) {
		nextMinchaInfo1  = info;
	}

	public void setNextMinchaInfo2(String info) {
		nextMinchaInfo2  = info;
	}

	public void setFinaltMinyanTime(String info) {
		finalMinyanTime = info;
	}

	public void setFinalMinyanInfo(String info) {
		finalMinyanInfo = info;
	}

	public void setRefreshTime() {
		Time now = new Time();
		now.setToNow();
		String AMPM;
		int hour = now.hour;
		String minute = "" + now.minute;
		if (hour > 12) {
			hour = hour - 12;
			AMPM = "PM";
		}
		else {
			AMPM = "AM";
		}
		
		if (now.minute < 10) {
			minute = "0" + minute;
		}
		String time = (now.month + 1) + "/" + (now.monthDay) + " " + (hour) + ":" + (now.minute) + " " + AMPM;
		refreshTime = time;
	}

	public void update() 
	{
		TextView nextMincha1View = (TextView)getView().findViewWithTag("nextMinchaTime1");
		TextView nextMincha2View = (TextView)getView().findViewWithTag("nextMinchaTime2");
		TextView nextMincha1Info = (TextView)getView().findViewWithTag("nextMinchaInfo1");
		TextView nextMincha2Info = (TextView)getView().findViewWithTag("nextMinchaInfo2");
		TextView finalMinyanTime = (TextView)getView().findViewWithTag("finalMinyanTime");
		TextView finalMinyanInfo = (TextView)getView().findViewWithTag("finalMinyanInfo");
		TextView refreshTime = (TextView)getView().findViewWithTag("refreshTime");
		nextMincha1View.setText(nextMinchaTime1);
		nextMincha2View.setText(nextMinchaTime2);
		nextMincha1Info.setText(nextMinchaInfo1);
		nextMincha2Info.setText(nextMinchaInfo2);
		finalMinyanTime.setText(this.finalMinyanTime);
		finalMinyanInfo.setText(this.finalMinyanInfo);
		refreshTime.setText(this.refreshTime);

		//Adjusts the text size so that it fits exactly a single line. I need new names D:
		Paint paint = new Paint();
		float nextMinchaInfo1Width = paint.measureText(nextMinchaInfo1);
		float nextMinchaInfo2Width = paint.measureText(nextMinchaInfo2);
		float finalMinyanInfoWidth = paint.measureText(this.finalMinyanInfo);
		float a = paint.measureText("Glueck Beis Yeshiva");
		Log.i(LOG, "Size: " + a);
		nextMincha1Info.setTextSize((float)(2664/nextMinchaInfo1Width));
		nextMincha2Info.setTextSize((float)(2664/nextMinchaInfo1Width));
		finalMinyanInfo.setTextSize((float)(2664/finalMinyanInfoWidth));
	}
}

