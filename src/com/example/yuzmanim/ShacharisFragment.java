package com.example.yuzmanim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
 
public class ShacharisFragment extends Fragment {
 
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
	}
}
