package com.example.yuzmanim;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.example.yuzmanim.adapter.TabsPagerAdapter;

/**
 * The YUZmanim app. The framework for the swiping and tabbing (before being modified) was obtained from this website:
 * http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/
 * @author Ariel Schreier and Steven Herzberg
 *
 */

public class MainActivity extends FragmentActivity implements ActionBar.TabListener
{
	//Viewpager is used to switch between screen with swiping.
	private ViewPager viewPager;
	//TabsPagerAdapter controls the flow of the tabs
	private TabsPagerAdapter mAdapter;
	//The ActionBar at the top of the app
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Home", "Shacharis", "Mincha", "Maariv", "Other" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);               //Initializes the viewPager. pager is created in activity_main.xml
		actionBar = getActionBar();                                     //Retrieves this activity's ActionBar
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());   //Initializes the adapter, passing the FragmentManager from FragmentActivity

		viewPager.setAdapter(mAdapter);			//Enables the swiping      

		actionBar.setDisplayShowHomeEnabled(false);    //Hide ActionBar but show tabs- Gets rid of the logo. Will see if we want to keep this
		//If we do, use this: http://stackoverflow.com/questions/16026818/actionbar-custom-view-with-centered-imageview-action-items-on-sides
		actionBar.setDisplayShowTitleEnabled(false);   //Hide ActionBar but show tabs- Gets rid of "YUZmanim"
		//actionBar.setHomeButtonEnabled(false);  //Disables the HomeButton in the corner of the ActionBar. Only in API 14+, amd doesn't seem necessary
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// Adding Tabs to the ActionBar (so now we don't have to use a different element to host tabs, like TabHost)
		for (String tab_name : tabs) {
			actionBar.addTab(  actionBar.newTab().setText(tab_name).setTabListener(this)   );
		}
	}

	//The next 3 methods are required by TabListener, which was implemented to add tabs to the ActionBar.
	//The first lets us switch between views via the tabs (before we could only switch via swiping) and 
	// the first and third change which tab is selected when changing the views via swiping.

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		//Sets the view to the position where the tab is
		viewPager.setCurrentItem(tab.getPosition());
		tabUpdate();
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		tabUpdate();
	}
	
	/**
	 * Changes which tab is selected when changing the views via swiping. Is called both by onTabSelected and onTabUnselected,
	 * so that you don't need to first change a tab for the tabs to update.
	 */
	public void tabUpdate()
	{
		
		viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				actionBar.setSelectedNavigationItem(position);
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
