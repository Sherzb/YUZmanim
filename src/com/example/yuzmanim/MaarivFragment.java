package com.example.yuzmanim;

import com.example.yuzmanim.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MaarivFragment extends Fragment {

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
	}
}
