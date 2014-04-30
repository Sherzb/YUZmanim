package com.example.yuzmanim;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Scanner;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

public class MinchaFragment extends Fragment {

	private ArrayList<ArrayList<Minyan>> minchaTables = new ArrayList<ArrayList<Minyan>>();
	public final String LOG = "MinchaFragment";
	public final String FILE_LOCATION = "mincha_fragment_file";
	private int spinnerPosition = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_mincha, container, false);

		Log.i("MinchaFragment", "OnCreateView was called on the MinchaFragment");
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("MinchaFragment", "OnDestroy was called on the MinchaFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MinchaFragment", "OnCreate was called on the MinchaFragment");
		setRetainInstance(true);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Spinner spinner = (Spinner)getView().findViewById(R.id.minchaSpinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
				R.array.mincha_days, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		spinner.setPromptId(R.string.shacharis_spinner);
		spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner.setSelection(spinnerPosition);
	}

	private class CustomOnItemSelectedListener implements OnItemSelectedListener {

		public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
			if (spinnerPosition != pos) {
				spinnerPosition = pos;
				update();
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

	@Override
	public void onPause() {
		super.onPause();
		Context context = getActivity();
		Log.i(LOG, "onPause Called");

		try {
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(context.openFileOutput(FILE_LOCATION, Context.MODE_PRIVATE)));
			Log.i(LOG, "Did this work...?");
			//Start writing
			String test = "";
			for (int i = minchaTables.size() - 1; i >= 0; i--) {
				for (Minyan minyan : minchaTables.get(i)) {
					Log.i(LOG, "Writing: " + minyan.getTime());
					writer.write(minyan.getLocation() + "QQQ");
					writer.write(minyan.getTime() + "QQQ");
					test = test + minyan.getTime() + "QQQ";
					test = test + minyan.getLocation() + "QQQ";
				}
				writer.write("ZZZ");
				test = test + "ZZZ";
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
				Log.i(LOG, "FOUND THE MINCHA FILE!");
				if (!pass.hasNext()) {
					Log.i(LOG, "DIDN'T FIND THE MINCHA FILE D:");
					pass.close();
					return;
				}
				String[] allTables = pass.nextLine().split("ZZZ");
				ArrayList<ArrayList<Minyan>> minyanTables = new ArrayList<ArrayList<Minyan>>();
				for (int i = allTables.length - 1; i >= 0; i--) {
					String table = allTables[i];
					String[] splitTable = table.split("QQQ");
					ArrayList<Minyan> singleMinyan = new ArrayList<Minyan>();
					for (int j = 0; j < splitTable.length - 1; j = j + 2) {
						singleMinyan.add(new Minyan(splitTable[j + 1], splitTable[j]));
					}
					minyanTables.add(singleMinyan);
				}
				minchaTables = minyanTables;
			} catch (IOException e) {
				e.printStackTrace();
			}
			update();
		}
	}


	public void update() {

		RelativeLayout tableBorder = new RelativeLayout(getActivity());
		RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tableParams.addRule(RelativeLayout.BELOW, R.id.all_the_mincha_text);
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
		Paint p = new Paint();
		if (minchaTables.size() > spinnerPosition) {
			for (Minyan minyan : minchaTables.get(spinnerPosition)) {
				counter++;
				TableRow tbrow = new TableRow(getActivity());

				TextView t1v = new TextView(getActivity());
				t1v.setText(minyan.getTime());
				t1v.setTextColor(Color.BLACK);
				t1v.setGravity(Gravity.CENTER);
				t1v.setTextSize(25);

				TextView t2v = new TextView(getActivity());
				t2v.setText(minyan.getLocation());
				t2v.setTextColor(Color.BLACK);
				t2v.setGravity(Gravity.CENTER);
				float size = p.measureText(minyan.getLocation());
				if (size > 113) {
					t2v.setTextSize(2825/size);
				}
				else {
					t2v.setTextSize(25);
				}

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
			tableBorder.addView(stk);
			((RelativeLayout)getView().findViewById(R.id.RelativeLayout1)).addView(tableBorder, tableParams);
		}
	}

	public void setMinyanTables(ArrayList<ArrayList<Minyan>> tables) {
		minchaTables = tables;
	}
}
