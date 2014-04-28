package com.example.yuzmanim;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;

import com.example.yuzmanim.HomeFragment.OnRefreshSelectedListener;
import com.example.yuzmanim.adapter.TabsPagerAdapter;
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
	private String maarivString;
	private Multimap<String, String> maarivMap;
	private ArrayList<Minyan> maarivTable;
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
	}

	@Override
	public void onRefreshSelected() {
		//http://stackoverflow.com/a/9744146
		HomeFragment fHome = getHomehFrag();
		ShacharisFragment fShach = getShachFrag();
		MinchaFragment fMinch = getMinchFrag();
		MaarivFragment fMaar = getMaarFrag();
		OtherFragment fOther = getOtherFrag();

		Log.i(TAG, "Refresh button registered in MainActivity");

		if (!isConnected()) { 
			Toast.makeText(this, "Cannot Connect to the Internet", Toast.LENGTH_LONG).show();
			return;
		}
		
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

		/**
		//Maariv Fragment
		ArrayList<String> arrayInfo = new ArrayList<String>();
		String isNull = "" + (maarivString == null);
		Log.i("MainActivity", isNull);
		String[] maarivTimes = maarivString.split("QQQ");
		ArrayList<Minyan> minyanimForMaariv = new ArrayList<Minyan>();
		for (String string : maarivTimes) {
			arrayInfo.add(string);
		}
		for (int i = 0; i < arrayInfo.size(); i = i + 2) {
			String time = arrayInfo.get(i);
			String location = arrayInfo.get(i + 1);
			minyanimForMaariv.add(new Minyan(time, location));
		}
		maarivTable = minyanimForMaariv;
		fMaar.setmMinyanTable(maarivTable);
		fMaar.update();
		*/


		//Other Fragment
		fOther.setShabbosLink(shabbosLink);
		fOther.setFakeInfO("fhewuigfiesgfhoes");
		fOther.update();

	}
	
	public void update() {
		
	}

	/**
	 * This is I guess where the internet connection part will go. All that happens here are the assignments of values
	 * to the String or multimaps in this Activity. Another method handles the updating the fragments.
	 */
	private void setFragmentValues()
	{
		Toast.makeText(getBaseContext(), "Updating...", Toast.LENGTH_LONG).show();
		HttpAsyncTask task = new HttpAsyncTask();
		task.execute("http://yuzmanim.com/maariv/");







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
		/**
		String morg = "Morg Beis";
		String r101 = "Room 101";
		Multimap<String, String> maarivMap = LinkedListMultimap.create();
		ArrayList<Minyan> maarivTable = new ArrayList<Minyan>();
		maarivTable.add(new Minyan("7:26", morg));
		maarivTable.add(new Minyan("8:10", morg));
		maarivTable.add(new Minyan("9:00", morg));
		maarivTable.add(new Minyan("10:00", "Glueck Beis Yeshiva"));
		maarivTable.add(new Minyan("10:00", "Sefardi Beit"));
		maarivTable.add(new Minyan("10:00", "Rubin Shul"));
		maarivTable.add(new Minyan("10:00", morg));
		maarivTable.add(new Minyan("10:30", morg));
		maarivTable.add(new Minyan("11:00", morg));
		maarivTable.add(new Minyan("11:30", morg));
		maarivTable.add(new Minyan("12:00", morg));
		maarivTable.add(new Minyan("12:30", morg));
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
		this.maarivTable = maarivTable;
		 */

		//Other Fragment
		shabbosLink = "bit.ly/af5dd";
	}

	/**
	public void setNextMinyanValues()
	{
		HomeFragment fHome = getHomehFrag();
		ShacharisFragment fShach = getShachFrag();
		MinchaFragment fMinch = getMinchFrag();
		MaarivFragment fMaar = getMaarFrag();
		OtherFragment fOther = getOtherFrag();
		Time time = new Time();
		time.setToNow();

		String currentHour = "" + time.hour;
		if (time.hour > 12) {
			currentHour = (time.hour - 12) + "";
		}

		String currentTime = currentHour + ":" + time.minute;
		int currentDay = time.weekDay;

		//If the currentTime is between the second to last and last shacharis. Make sure that it goes weekday, mon/thurs
		//is second
		int shachChoice = 0;
		if (currentDay == 1 || currentDay == 4) {
			shachChoice = 1;
		}
		//First, only dealing with shacharis and mincha
		Multimap<String, String >shachMaps = getShachFrag().getMaps().get(shachChoice);
		String lastShachTime = shachMaps.keys().
		if (time.hour < 12) {
			if ( ) {}
		}
	}
	 */

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

	//Returns -1 if first time is earlier than the second, 1 if it's later, 0 if it's the same time
	public int compareTime(String a, String b) 
	{
		String[] aString = a.split(":");
		String[] bString = b.split(":");
		Integer[] aParts = new Integer[2];
		Integer[] bParts = new Integer[2];
		aParts[0] = Integer.parseInt(aString[0]);
		aParts[1] = Integer.parseInt(aString[1]);
		bParts[0] = Integer.parseInt(bString[0]);
		bParts[1] = Integer.parseInt(bString[1]);

		if (aParts[0].compareTo(bParts[0]) < 0) {
			return -1;
		}
		else if (aParts[0].compareTo(bParts[0]) > 0) {
			return 1;
		}
		else {
			//Same hour, different minutes
			if (aParts[1].compareTo(bParts[1]) < 0) {
				return -1;
			}
			else if (aParts[1].compareTo(bParts[1]) > 0) {
				return 1;
			}
			else {
				return 0;
			}
		}
	}

	/**
	 * Returns true if we can connect, false otherwise.
	 * @return
	 */
	public boolean isConnected(){
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) 
			return true;
		else
			return false;   
	}

	public static String GET(String url){
		InputStream inputStream = null;
		String result = "";
		try {
 
			// create HttpClient
			HttpClient httpclient = new DefaultHttpClient();
 
			// make GET request to the given URL
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
 
			// receive response as inputStream
			inputStream = httpResponse.getEntity().getContent();
 
			// convert inputstream to string
			if(inputStream != null)
				result = convertInputStreamToString(inputStream);
			else
				result = "Did not work!";
 
		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}
 
		return result;
	}
 
	private static String convertInputStreamToString(InputStream inputStream) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;
 
		inputStream.close();
		
		int startTable = result.indexOf("<td");
		int endTable = result.indexOf("table>");
		
		result = result.substring(startTable, endTable);
		
		String table = "";
		int count = 0;
		ArrayList<String> tableInfo = new ArrayList<String>();
		
		while(result.contains("<td")) {
			count++;
			result = result.substring(result.indexOf("<td>") + 4);
			table += result.substring(0, result.indexOf("</td>")).trim() + " QQQ ";		
			tableInfo.add(table);
		}
		
		return table;
 
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected String doInBackground(String... urls) { 
			return GET(urls[0]);
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Toast.makeText(getBaseContext(), "Updated!", Toast.LENGTH_LONG).show();
			Log.i("MainActivity", result);
			maarivString = result;
			ArrayList<String> arrayInfo = new ArrayList<String>();
			String isNull = "" + (maarivString == null);
			Log.i("MainActivity", isNull);
			String[] maarivTimes = maarivString.split("QQQ");
			ArrayList<Minyan> minyanimForMaariv = new ArrayList<Minyan>();
			for (String string : maarivTimes) {
				arrayInfo.add(string);
			}
			for (int i = 0; i < arrayInfo.size() - 1; i = i + 2) {
				String location = arrayInfo.get(i);
				String time = arrayInfo.get(i + 1);
				minyanimForMaariv.add(new Minyan(time, location));
				Log.i("MainActivity", time + " " + location);
			}
			maarivTable = minyanimForMaariv;
			
			MaarivFragment fMaar = getMaarFrag();
			fMaar.setmMinyanTable(maarivTable);
			fMaar.update();
		}
	}
}
