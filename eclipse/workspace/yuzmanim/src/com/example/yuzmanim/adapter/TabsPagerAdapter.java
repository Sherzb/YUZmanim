package com.example.yuzmanim.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.yuzmanim.MaarivFragment;
import com.example.yuzmanim.MinchaFragment;
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
            // Shacharis activity
            return new ShacharisFragment();
        case 1:
            // Mincha activity
            return new MinchaFragment();
        case 2:
            // Maariv fragment activity
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