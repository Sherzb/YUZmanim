package com.example.yuzmanim;

import java.util.List;

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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_maariv, container, false);

		Log.i("MaarivFragment", "OnCreateView was called on the MaarivFragment");
		return rootView;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.i("MaarivFragment", "OnDestroy was called on the MaarivFragment");
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.i("MaarivFragment", "OnCreate was called on the MaarivFragment");
		setRetainInstance(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
    {
	    outState.putString("tab", "yourAwesomeFragmentsTab");	
        super.onSaveInstanceState(outState);
    }
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		update();
	}
	
	public void setTableMap(Multimap<String, String> map) {
		tableMap = map;
	}
	
	public void update() {
		
		TableLayout stk = new TableLayout(getActivity());
		RelativeLayout.LayoutParams tableParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tableParams.addRule(RelativeLayout.BELOW, R.id.top);
		tableParams.addRule(RelativeLayout.INVISIBLE);
		//tableParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
		
		RelativeLayout border = (RelativeLayout) getView().findViewById(R.id.top);
		border.addView(stk, tableParams);
		
        //TableLayout stk = (TableLayout) getView().findViewById(R.id.table_main);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        
        TableRow tbrow0 = new TableRow(getActivity());
        
        TextView tv0 = new TextView(getActivity());
        tv0.setTextColor(Color.WHITE);
        tv0.setTextSize(40);
        tv0.setText("Time");
        tv0.setBackgroundResource(R.drawable.table_dark_gray_background);
        tv0.setPadding(5, 0, 5, 0);
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
        	counter++;
            TableRow tbrow = new TableRow(getActivity());
            TextView t1v = new TextView(getActivity());
            t1v.setText(time);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            t1v.setTextSize(30);
            
            TextView t2v = new TextView(getActivity());
            t2v.setText(   ((List<String>)tableMap.get(time)).get(0)   );
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            t2v.setTextSize(15);
            
            RelativeLayout r2v = new RelativeLayout(getActivity());
            TextView sizeBuffer = new TextView(getActivity());
            sizeBuffer.setText("x"); //Replace x with " " eventually
            sizeBuffer.setTextSize(30);
            r2v.addView(sizeBuffer);
            
            //Sets the background
            if (counter % 2 == 0) {
            	t1v.setBackgroundResource(R.drawable.table_gray_background);
            	r2v.setBackgroundResource(R.drawable.table_gray_background);
            }
            else {
            	t1v.setBackgroundResource(R.drawable.table_white_background);
            	r2v.setBackgroundResource(R.drawable.table_white_background);
            }
            
            tbrow.addView(t1v);
            r2v.addView(t2v, params);
            tbrow.addView(r2v);
            
            stk.addView(tbrow);
        }
    }
}
