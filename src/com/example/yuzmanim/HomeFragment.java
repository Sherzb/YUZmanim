package com.example.yuzmanim;

import java.security.acl.LastOwnerException;

import android.app.Activity;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class HomeFragment extends Fragment {

	OnRefreshSelectedListener mCallback;
	Button mRefreshButton;

	private String nextMinyanTime1;
	private String nextMinyanTime2;
	private String nextMinyanInfo1;
	private String nextMinyanInfo2;
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
			nextMinyanTime1 = savedInstanceState.getString("nextMinyanTime1");
			nextMinyanTime2 = savedInstanceState.getString("nextMinyanTime2");
			nextMinyanInfo1 = savedInstanceState.getString("nextMinyanInfo1");
			nextMinyanInfo2 = savedInstanceState.getString("nextMinyanInfo2");
			finalMinyanInfo = savedInstanceState.getString("finalMinyanInfo");
			finalMinyanTime = savedInstanceState.getString("finalMinyanTime");
			refreshTime = savedInstanceState.getString("refreshTime");
			Log.i("homeFragment", "Not null");
		}
		if (nextMinyanInfo1 != null && nextMinyanInfo2 != null && nextMinyanTime1 != null && nextMinyanTime2 != null
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
		outState.putString("nextMinyanTime1", nextMinyanTime1);
		outState.putString("nextMinyanTime2", nextMinyanTime2);
		outState.putString("nextMinyanInfo1", nextMinyanInfo1);
		outState.putString("nextMinyanInfo2", nextMinyanInfo2);
		outState.putString("finalMinyanTime", finalMinyanTime);
		outState.putString("finalMinyanInfo", finalMinyanInfo);
		outState.putString("refreshTime", refreshTime);
		Log.i(LOG, "onSavedInstanceState Called");
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
	
	public void setNextMinyanTime1(String info) {
		nextMinyanTime1 = info;
	}
	
	public void setNextMinyanTime2(String info) {
		nextMinyanTime2 = info;
	}
	
	public void setNextMinyanInfo1(String info) {
		nextMinyanInfo1 = info;
	}
	
	public void setNextMinyanInfo2(String info) {
		nextMinyanInfo2 = info;
	}

	public void setFinaltMinyanTime(String info) {
		finalMinyanTime = info;
	}

	public void setFinalMinyanInfo(String info) {
		finalMinyanInfo = info;
	}
	
	public void setFinalMinyan(String time, String location) {
		finalMinyanTime = time;
		finalMinyanInfo = location;
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
		nextMincha1View.setText(nextMinyanTime1);
		nextMincha2View.setText(nextMinyanTime2);
		nextMincha1Info.setText(nextMinyanInfo1);
		nextMincha2Info.setText(nextMinyanInfo2);
		finalMinyanTime.setText(this.finalMinyanTime);
		finalMinyanInfo.setText(this.finalMinyanInfo);
		setRefreshTime();
		Log.i(LOG, this.refreshTime);
		refreshTime.setText(this.refreshTime);

		//Adjusts the text size so that it fits exactly a single line. I need new names D:
		Paint paint = new Paint();
		float nextMinyanInfo1Width = paint.measureText(nextMinyanInfo1 + "");
		float nextMinyanInfo2Width = paint.measureText(nextMinyanInfo2 + "");
		float finalMinyanInfoWidth = paint.measureText(this.finalMinyanInfo + "");
		float a = paint.measureText("Glueck Beis Yeshiva");
		Log.i(LOG, "Size: " + a);
		nextMincha1Info.setTextSize((float)(2664/nextMinyanInfo1Width));
		//nextMincha1Info.setGravity(Gravity.CENTER);
		nextMincha2Info.setTextSize((float)(2664/nextMinyanInfo2Width));
		//nextMincha2Info.setGravity(Gravity.CENTER);
		finalMinyanInfo.setTextSize((float)(2664/finalMinyanInfoWidth));
	}
}

