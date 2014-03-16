package com.example.yuzmanim;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.yuzmanim.adapter.TabsPagerAdapter;

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	//Viewpager is used to switch between screen with swiping.
	private ViewPager viewPager;
	//TabsPagerAdapter controls the flow of the tabs
	private TabsPagerAdapter mAdapter;
	//The ActionBar at the top of the app
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Shacharis", "Mincha", "Maariv" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);               //Initializes the viewPager
		actionBar = getActionBar();                                     //Retrieves this activity's ActionBar
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());   //Initializes the adapter, passing the FragmentManager from FragmentActivity

		viewPager.setAdapter(mAdapter);			//Basically enables the swiping      

		actionBar.setHomeButtonEnabled(false);  //Disables the HomeButton in the corner of the ActionBar
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);     

		// Adding Tabs to the ActionBar (so now we don't have to use a different element to host tabs, like TabHost)
		//ADD ALL THIS BACK TOO!!!
		//for (String tab_name : tabs) {
			//actionBar.addTab(  actionBar.newTab().setText(tab_name).setTabListener(this)   );
		//}
	}

	//The next 3 methods are required by TabListener, which was implemented to add tabs to the ActionBar. The only one
	//we care to implement is onTabSelected, which lets us switch between views via the tabs (before we could only switch
	//via swiping

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		//Sets the view to the position where the tab is
		//ADD THIS BACK!!
		//viewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {

		/**
		 * on swiping the viewpager make respective tab selected
		 * */
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// on changing the page
				// make respected tab selected
				
				//ADD THIS BACK!
				//actionBar.setSelectedNavigationItem(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
}
