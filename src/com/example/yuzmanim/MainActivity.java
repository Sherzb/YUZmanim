package com.example.yuzmanim;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.example.yuzmanim.HomeFragment.OnRefreshSelectedListener;
import com.example.yuzmanim.adapter.TabsPagerAdapter;
import com.google.common.collect.LinkedListMultimap;
import com.google.common.collect.Multimap;

/**
 * The YUZmanim app. The framework for the swiping and tabbing (before being modified) was obtained from this website:
 * http://www.androidhive.info/2013/10/android-tab-layout-with-swipeable-views-1/
 * @author Ariel Schreier and Steven Herzberg
 *
 */

public class MainActivity extends FragmentActivity implements ActionBar.TabListener, OnRefreshSelectedListener
{
	private String nextMinchaTime1;
	private String nextMinchaTime2;
	private String nextMinchaInfo1;
	private String nextMinchaInfo2;
	private String finalMinyanTime;
	private String finalMinyanInfo;
	//In case we need to add more, I want to have a variable number of "day types" allowed
	private ArrayList<Multimap<String, String>> shacharisMaps = new ArrayList<Multimap<String, String>>();
	private ArrayList<Multimap<String, String>> minchaMaps = new ArrayList<Multimap<String, String>>();
	private Multimap<String, String> maarivMap;
	private String shabbosLink;
		
	//Viewpager is used to switch between screen with swiping.
	private ViewPager viewPager;
	//TabsPagerAdapter controls the flow of the tabs
	private TabsPagerAdapter mAdapter;
	//The ActionBar at the top of the app
	private ActionBar actionBar;
	// Tab titles
	private String[] tabs = { "Home", "Shacharis", "Mincha", "Maariv", "Other" };
	public static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Initialization
		viewPager = (ViewPager) findViewById(R.id.pager);               //Initializes the viewPager. pager is created in activity_main.xml
		actionBar = getActionBar();                                     //Retrieves this activity's ActionBar
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());   //Initializes the adapter, passing the FragmentManager from FragmentActivity

		viewPager.setAdapter(mAdapter);			//Enables the swiping    
		viewPager.setOffscreenPageLimit(4);

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
	
	@Override
	protected void onResume() {
		super.onResume();
		setNextMinyanValues();
	}

	/**
	 * When the refresh button of HomeFragment is hit, magic happens!
	 * ARIEL ALL THE REFRESH MAGIC HAPPENS HERE!
	 */
	@Override
	public void onRefreshSelected() {
		Log.i(TAG, "Refresh button registered in MainActivity");
		boolean successful = true; //No idea how DOM works

		//http://stackoverflow.com/a/9744146
		HomeFragment fHome = getHomehFrag();
		ShacharisFragment fShach = getShachFrag();
		MinchaFragment fMinch = getMinchFrag();
		MaarivFragment fMaar = getMaarFrag();
		OtherFragment fOther = getOtherFrag();

		if (successful) {
			
			setFragmentValues();
			
			//Home Fragment
			fHome.setNextMinchaTime1(nextMinchaTime1);
			fHome.setNextMinchaTime2(nextMinchaTime2);
			fHome.setNextMinchaInfo1(nextMinchaInfo1);
			fHome.setNextMinchaInfo2(nextMinchaInfo2);
			fHome.setFinalMinyanInfo(finalMinyanInfo);
			fHome.setFinaltMinyanTime(finalMinyanTime);
			fHome.setRefreshTime();
			fHome.update();
			
			//Maariv Fragment
			fMaar.setTableMap(maarivMap);
			fMaar.update();
			
			
			//Other Fragment
			fOther.setShabbosLink(shabbosLink);
			fOther.setFakeInfO("fhewuigfiesgfhoes");
			fOther.update();
		}

	}

	/**
	 * This is I guess where the internet connection part will go. All that happens here are the assignments of values
	 * to the String or multimaps in this Activity. Another method handles the updating the fragments.
	 */
	private void setFragmentValues()
	{
		//Home Fragment
		nextMinchaTime1 = "2:33";
		nextMinchaTime2 = "2:40";
		nextMinchaInfo1 = "(Mincha) Room 101";
		nextMinchaInfo2 = "(Mincha) Gluck Beis";
		finalMinyanInfo = "(Mincha) Zysman Beis";
		finalMinyanTime = "7:10";	
		
		//Shacharis Fragment
		
		//Mincha Fragment
		
		//Maariv Fragment
		//Need an ordered, duplicate-allowing map. Hello, Guava.
		String morg = "Morg Beis";
		String r101 = "Room 101";
		Multimap<String, String> maarivMap = LinkedListMultimap.create();
		maarivMap.put("7:26", morg);
		maarivMap.put("8:10", morg);
		maarivMap.put("9:00", morg);
		maarivMap.put("10:00", "Glueck Beis Yeshiva");
		maarivMap.put("10:00", "Sefardi Beit");
		maarivMap.put("10:00", "Rubin Shul");
		maarivMap.put("10:00", morg);
		maarivMap.put("10:30", morg);
		maarivMap.put("11:00", morg);
		maarivMap.put("11:30", morg);
		maarivMap.put("12:00", morg);
		maarivMap.put("12:30", morg);
		this.maarivMap = maarivMap;
		
		//Other Fragment
		shabbosLink = "bit.ly/af5dd";
	}
	
	public void setNextMinyanValues()
	{
		HomeFragment fHome = getHomehFrag();
		ShacharisFragment fShach = getShachFrag();
		MinchaFragment fMinch = getMinchFrag();
		MaarivFragment fMaar = getMaarFrag();
		OtherFragment fOther = getOtherFrag();
	}
	
	public HomeFragment getHomehFrag() {
		return (HomeFragment)this.getSupportFragmentManager().findFragmentByTag(getFragmentTag(0));
	}
	
	public ShacharisFragment getShachFrag() {
		return (ShacharisFragment)this.getSupportFragmentManager().findFragmentByTag(getFragmentTag(1));
	}
	
	public MinchaFragment getMinchFrag() {
		return (MinchaFragment)this.getSupportFragmentManager().findFragmentByTag(getFragmentTag(2));
	}
	
	public MaarivFragment getMaarFrag() {
		return (MaarivFragment)this.getSupportFragmentManager().findFragmentByTag(getFragmentTag(3));
	}
	
	public OtherFragment getOtherFrag() {
		return (OtherFragment)this.getSupportFragmentManager().findFragmentByTag(getFragmentTag(4));
	}
	
	private String getFragmentTag(int pos){
		return "android:switcher:"+R.id.pager+":"+pos;
	}
}
