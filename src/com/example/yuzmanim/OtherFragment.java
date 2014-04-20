package com.example.yuzmanim;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Scanner;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OtherFragment extends Fragment {

	private String shabbosLink;
	private String fakeInfo;
	public final String LOG = "OtherFragment";
	public final String FILE_LOCATION = "other_fragment_file";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_other, container, false);

		Log.i("OtherFragment", "OnCreateView was called on the OtherFragment");
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("OtherFragment", "OnDestroy was called on the OtherFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("OtherFragment", "OnCreate was called on the OtherFragment");
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) 
	{
		super.onViewCreated(view, savedInstanceState);	
		if (shabbosLink != null) {
			update();
		}
	}
	
	/**
	 * OnPuase is overwritten to save the info to a file. The info is reloaded on onResume. This means that the app will save state
	 * even across exiting the app, and (hopefully) even after turning off the device.
	 * Okay, here's how this will work. Starting at //Start writing, we write each piece of info, with a writer.newLine() between
	 * each one. Not sure why I can't see the line spacing in any text editors, but whatever, I guess...
	 */
	@Override
	public void onPause() {
		super.onPause();
		Context context = getActivity();
		
		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILE_LOCATION, Context.MODE_PRIVATE)));
			Log.i(LOG, "Did this work...?");
			//Start writing
			writer.write(shabbosLink);
			writer.newLine();
			writer.write(fakeInfo);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Overridden to reload the information in the files. infoStore stores the information gotten from the file in a String[].
	 * I guess I just have to keep track of which index has which piece of information. But this works. I'm going to use this one as
	 * the main OtherFragment, and keep in the fakeInfo, just so I can rereference what I did here.
	 */
	@Override
	public void onResume() {
		super.onResume();
		Context context = getActivity();
		if (context.getFileStreamPath(FILE_LOCATION).exists()) {
			try {
				Scanner pass = new Scanner(context.getFileStreamPath("myfile9"));
				Log.i(LOG, "FOUND THE FILE!");
				int counter = 0;
				String[] infoStore = new String[2];
			    while (pass.hasNextLine()) {
			    	infoStore[counter] = pass.nextLine();
			    	counter++;
			    }
			    shabbosLink = infoStore[0];
			    fakeInfo = infoStore[1];
			    Log.i(LOG, infoStore[1]);
			    pass.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			update();
		}
	}

	/*
	@Override
	public void onResume() {
		super.onResume();
		Context context = getActivity();
		if (context.getFileStreamPath("myfile1").exists()) {
			Log.i(LOG, "FOUND THE FILE!");

			try {
			    BufferedReader inputReader = new BufferedReader(new InputStreamReader(context.openFileInput("myfile1")));
			    String inputString;
			    StringBuffer stringBuffer = new StringBuffer();                
			    while ((inputString = inputReader.readLine()) != null) { //Basically while inputReader.readLine() != null
			        stringBuffer.append(inputString);
			    }
			    shabbosLink = stringBuffer.toString();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			update();
		}
	}
	*/
	
	
	
	

	public void setShabbosLink(String link)
	{
		shabbosLink = link;
	}
	
	public void setFakeInfO(String info) {
		fakeInfo = info;
	}

	public void update()
	{
		TextView text = (TextView)getView().findViewById(R.id.shabbosSchedule);
		text.setText(shabbosLink);
		TextView fakeText = (TextView)getView().findViewById(R.id.fakeInfo);
		fakeText.setText(fakeInfo);
	}
}
