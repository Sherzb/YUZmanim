package com.example.yuzmanim;

import com.example.yuzmanim.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
 
public class OtherFragment extends Fragment {
 
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
		setRetainInstance(true);
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
    {

	    outState.putString("tab", "yourAwesomeFragmentsTab");
		
        super.onSaveInstanceState(outState);
        

    }
}
