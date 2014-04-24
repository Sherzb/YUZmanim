package com.example.yuzmanim;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

public class MaarivFragment extends Fragment {

	private Multimap<String, String> tableMap = LinkedListMultimap.create();
	public final String LOG = "MaarivFragment";
	public final String FILE_LOCATION = "maariv_fragment_file";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_maariv, container, false);

		Log.i("MaarivFragment", "OnCreateView was called on the MaarivFragment");
		return rootView;
	}

	public void setTableMap(Multimap<String, String> map) {
		tableMap = map;
	}

	@Override
	public void onPause() {
		super.onPause();
		Context context = getActivity();

		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILE_LOCATION, Context.MODE_PRIVATE)));
			Log.i(LOG, "Did this work...?");
			//Start writing
			for (String time : tableMap.keySet()) {
				for (String location : tableMap.get(time)) {
					writer.write(time + "QQQ");
					writer.write(location + "QQQ");
				}
			}
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Context context = getActivity();
		if (context.getFileStreamPath(FILE_LOCATION).exists()) {
			try {
				Scanner pass = new Scanner(context.getFileStreamPath(FILE_LOCATION));
				Log.i(LOG, "FOUND THE FILE!");
				String[] info = pass.nextLine().split("QQQ");
				ArrayList<String> arrayInfo = new ArrayList<String>();
				Multimap<String, String> scannerMap = LinkedListMultimap.create();
				for (String string : info) {
					arrayInfo.add(string);
				}
				for (int i = 0; i < arrayInfo.size(); i = i + 2) {
					scannerMap.put(arrayInfo.get(i), arrayInfo.get(i + 1));
				}
				tableMap = scannerMap;
			    pass.close();
			} catch (IOException e) {
			    e.printStackTrace();
			}
			update();
		}
	}

	public void update() {

		RelativeLayout tableBorder = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tableParams.addRule(RelativeLayout.BELOW, R.id.top);
		tableParams.addRule(RelativeLayout.CENTER_HORIZONTAL);

		TableLayout stk = new TableLayout(getActivity());

		RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		params.addRule(RelativeLayout.CENTER_IN_PARENT);

		TableRow tbrow0 = new TableRow(getActivity());

		TextView tv0 = new TextView(getActivity());
		tv0.setTextColor(Color.WHITE);
		tv0.setTextSize(40);
		tv0.setText("Time");
		tv0.setBackgroundResource(R.drawable.table_dark_gray_background);
		tbrow0.addView(tv0);


		TextView tv1 = new TextView(getActivity());
		tv1.setTextColor(Color.WHITE);
		tv1.setTextSize(40);
		tv1.setText("    Location    ");
		tv1.setBackgroundResource(R.drawable.table_dark_gray_background);
		tv1.setGravity(Gravity.CENTER);
		tbrow0.addView(tv1);

		stk.addView(tbrow0);

		int counter = 0;
		for (String time : tableMap.keySet()) {
			for (String location : tableMap.get(time)) {
				counter++;
				TableRow tbrow = new TableRow(getActivity());

				TextView t1v = new TextView(getActivity());
				t1v.setText(time);
				t1v.setTextColor(Color.BLACK);
				t1v.setGravity(Gravity.CENTER);
				t1v.setTextSize(25);

				TextView t2v = new TextView(getActivity());
				t2v.setText(location);
				t2v.setTextColor(Color.BLACK);
				t2v.setGravity(Gravity.CENTER);
				t2v.setTextSize(25);

				RelativeLayout r1v = new RelativeLayout(getActivity());
				TextView sizeBuffer1 = new TextView(getActivity());
				sizeBuffer1.setText(" ");
				sizeBuffer1.setTextSize(30);
				r1v.addView(sizeBuffer1);

				RelativeLayout r2v = new RelativeLayout(getActivity());
				TextView sizeBuffer2 = new TextView(getActivity());
				sizeBuffer2.setText(" ");
				sizeBuffer2.setTextSize(30);
				r2v.addView(sizeBuffer2);								

				//Sets the background
				if (counter % 2 == 0) {
					r1v.setBackgroundResource(R.drawable.table_gray_background);
					r2v.setBackgroundResource(R.drawable.table_gray_background);
				}
				else {
					r1v.setBackgroundResource(R.drawable.table_white_background);
					r2v.setBackgroundResource(R.drawable.table_white_background);
				}

				r1v.addView(t1v, params);
				tbrow.addView(r1v);

				r2v.addView(t2v, params);
				tbrow.addView(r2v);

				stk.addView(tbrow);
			}
		}
		tableBorder.addView(stk);
		((RelativeLayout)getView().findViewById(R.id.RelativeLayout1)).addView(tableBorder, tableParams);
	}
}
