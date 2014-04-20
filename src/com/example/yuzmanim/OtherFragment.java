package com.example.yuzmanim;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
 
public class OtherFragment extends Fragment {
	
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
		if (savedInstanceState != null) {
			shabbosLink = savedInstanceState.getString("link");
			Log.i(LOG, "savedInstance isn't null");
		}
		if (shabbosLink != null) {
			update();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) 
    {
        super.onSaveInstanceState(outState);
        outState.putString("link", shabbosLink);
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
