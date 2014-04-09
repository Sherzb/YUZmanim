package com.example.yuzmanim;

import com.example.yuzmanim.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class MinchaFragment extends Fragment {
 
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
	public void onSaveInstanceState(Bundle outState) 
    {

	    outState.putString("tab", "yourAwesomeFragmentsTab");
		
        super.onSaveInstanceState(outState);
        

    }
}
