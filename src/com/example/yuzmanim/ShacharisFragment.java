package com.example.yuzmanim;

import java.util.ArrayList;

import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
 
public class ShacharisFragment extends Fragment {
	
	private ArrayList<Multimap<String, String>> shacharisMaps = new ArrayList<Multimap<String, String>>();
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_shacharis, container, false);
        Log.i("ShacharisFragment", "OnCreateView was called on the ShacharisFragment"); 
        
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
		setRetainInstance(true);
	}
	
	public ArrayList<Multimap<String, String>> getMaps() {
		return shacharisMaps;
	}
}
