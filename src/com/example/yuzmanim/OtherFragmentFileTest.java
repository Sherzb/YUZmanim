package com.example.yuzmanim;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class OtherFragmentFileTest extends Fragment {

	private String shabbosLink;
	public final String LOG = "OtherFragment";

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
	
	
	@Override
	public void onPause() {
		super.onPause();
		String filename = "myfile1";
		Context context = getActivity();
		context.getFileStreamPath(filename);
		String string = "Hello world!";
		FileOutputStream outputStream;

		try {
			outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
			outputStream.write(string.getBytes());
			outputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		String filename = "data/data/com.example.yuzmanim.files.myfile1";
		Context context = getActivity();
		if (context.getFileStreamPath("myfile1").exists()) {
			Log.i(LOG, "FOUND THE FILE!");

			try {
			    BufferedReader inputReader = new BufferedReader(new InputStreamReader(context.openFileInput("myfile1")));
			    String inputString;
			    StringBuffer stringBuffer = new StringBuffer();                
			    while ((inputString = inputReader.readLine()) != null) {
			        stringBuffer.append(inputString + "\n");
			    }
			    shabbosLink = stringBuffer.toString();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			update();
		}
	}

	public void setShabbosLink(String link)
	{
		shabbosLink = link;
	}

	public void update()
	{
		TextView text = (TextView)getView().findViewById(R.id.shabbosSchedule);
		text.setText(shabbosLink);
	}
}
