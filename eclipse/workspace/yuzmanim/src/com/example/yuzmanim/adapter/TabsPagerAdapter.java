package com.example.yuzmanim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuzmanim.MaarivFragment;
import com.example.yuzmanim.MinchaFragment;
import com.example.yuzmanim.ShacharisFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
	 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Shacharis fragment activity
            return new ShacharisFragment();
        case 1:
            // Games fragment activity
            return new MinchaFragment();
        case 2:
            // Movies fragment activity
            return new MaarivFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}