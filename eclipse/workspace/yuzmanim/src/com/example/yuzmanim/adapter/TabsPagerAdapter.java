package com.example.yuzmanim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuzmanim.HomeFragment;
import com.example.yuzmanim.MaarivFragment;
import com.example.yuzmanim.MinchaFragment;
import com.example.yuzmanim.OtherFragment;
import com.example.yuzmanim.ShacharisFragment;

/**
 * The adapter for YUZmanim. FragmentPagerAdapter seems to be the standard when using tabs. Furthermore, since each tab
 * will be storing a minimal amount of data (just some text fields), the memory overhead is minimal. See BNRG pgs 204-209.
 * @author Shmuel
 *
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
	 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0: 
        	//The home fragment activity
        	return new HomeFragment();
        case 1:
            // Shacharis fragment activity
            return new ShacharisFragment();
        case 2:
            // Mincha fragment activity
            return new MinchaFragment();
        case 3:
            // Maariv fragment activity
            return new MaarivFragment();
        case 4:
        	return new OtherFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get Item count - equal to number of tabs
        return 5;
    }
 
}