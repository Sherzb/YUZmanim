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
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
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
	private ArrayList<ArrayList<Minyan>> shacharisTables = new ArrayList<ArrayList<Minyan>>();
	private ArrayList<ArrayList<Minyan>> minchaTables = new ArrayList<ArrayList<Minyan>>();
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
	public void onRefreshSelected() {
		//http://stackoverflow.com/a/9744146
		HomeFragment fHome = getHomehFrag();
		fHome.update();
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
	}

	/**
	 * This is I guess where the internet connection part will go. All that happens here are the assignments of values
	 * to the String or multimaps in this Activity. Another method handles the updating the fragments.
	 */
	private void setFragmentValues()
	{
		Toast.makeText(getBaseContext(), "Updating...", Toast.LENGTH_SHORT).show();
		lockScreenOrientation();
		HttpAsyncTask task1 = new HttpAsyncTask();
		HttpAsyncTask task2 = new HttpAsyncTask();
		HttpAsyncTask task3 = new HttpAsyncTask();
		HttpAsyncTask task4 = new HttpAsyncTask();
		task1.execute("http://yuzmanim.com/shacharis/");
		task2.execute("http://yuzmanim.com/mincha/");
		task3.execute("http://yuzmanim.com/maariv/");
		task4.execute("http://yuzmanim.com/shabbos/");
		

		//Home Fragment
		nextMinchaTime1 = "2:33";
		nextMinchaTime2 = "2:40";
		nextMinchaInfo1 = "(Mincha) Room 101";
		nextMinchaInfo2 = "(Mincha) Gluck Beis";
		finalMinyanInfo = "(Mincha) Zysman Beis";
		finalMinyanTime = "7:10";	
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
		if (networkInfo != null && networkInfo.isConnected()) {
			return true; 
		}
		else {
			return false;   
		}
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
				result = convertInputStreamToString(inputStream, url);
			else
				result = "Did not work!";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());
		}

		return result;
	}

	private static String convertInputStreamToString(InputStream inputStream, String url) throws IOException{
		BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
		String line = "";
		String result = "";
		while((line = bufferedReader.readLine()) != null)
			result += line;

		inputStream.close();

		//Maariv
		if (url.contains("maariv")) {
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
			table = "3" + table;
			return table;
		}
		//Mincha
		else if (url.contains("mincha")) {
			result = result.substring(result.indexOf("<table") + 5);
			result = result.substring(result.indexOf("<table") + 5);

			int startTable1 = result.indexOf("<td");
			int endTable1 = result.indexOf("table>");

			String table1 = result.substring(startTable1, endTable1);

			String table2 = result.substring(endTable1);

			table2 = table2.substring(result.indexOf("<td"));
			table2 = table2.substring(result.indexOf("<td"));

			int endTable2 = table2.indexOf("table>");

			table2 = table2.substring(0, endTable2);

			String table = "";

			while(table1.contains("<td")) {
				table1 = table1.substring(table1.indexOf("<td") + 4);
				table += table1.substring(0, table1.indexOf("</td")).trim() + "QQQ";		
			}

			table += "ZZZ";

			while(table2.contains("<td")) {
				table2 = table2.substring(table2.indexOf("<td") + 4);
				table2 = table2.substring(table2.indexOf(">") + 1);
				table += table2.substring(0, table2.indexOf("</td")).trim() + "QQQ";		
			}

			return "2" + table;
		}
		//Shacharis
		else if (url.contains("shacharis")){
			result = result.substring(result.indexOf("<table") + 5);

			int startTable = result.indexOf("<td");
			int endTable = result.indexOf("table>");

			result = result.substring(startTable, endTable);

			int count = 0;

			ArrayList<String> locations = new ArrayList<String>();

			ArrayList<String> regular =  new ArrayList<String>();
			ArrayList<String> mondayThursday =  new ArrayList<String>();
			ArrayList<String> roshChodesh =  new ArrayList<String>();
			ArrayList<String> fastDay =  new ArrayList<String>();
			ArrayList<String> endTime =  new ArrayList<String>();


			while(result.contains("<td")) {
				result = result.substring(result.indexOf("<td") + 3);

				if (!result.substring(0,1).equals(">")) {
					result = result.substring(result.indexOf(">") + 1);
				}
				else {
					result = "XX" + result.substring(1);
				}

				String s = result.substring(0, result.indexOf("</td")).trim();
				if (s.length() == 0) {
					s = "XX";
				}

				if (count % 6 == 0) {
					locations.add(s);
				}
				else if (count % 6 == 1) {
					regular.add(s);
				}
				else if (count % 6 == 2) {
					mondayThursday.add(s);
				}
				else if (count % 6 == 3) {
					roshChodesh.add(s);
				}
				else if (count % 6 == 4) {
					fastDay.add(s);
				}
				else if (count % 6 == 5) {
					endTime.add(s);
				}		
				count++;
			}

			ArrayList<ArrayList<String>> allMinyanim = new ArrayList<ArrayList<String>>();
			allMinyanim.add(regular);
			allMinyanim.add(mondayThursday);
			allMinyanim.add(roshChodesh);
			allMinyanim.add(fastDay);

			result = "";

			for(ArrayList<String> a: allMinyanim) {
				for(int i = 0; i < locations.size(); i++) {
					result += locations.get(i) + "QQQ" + a.get(i) + "QQQ";
				}
				result += "ZZZ";
			}

			return "1" + result;
		}
		else if (url.contains("shabbos")) {
			result = result.substring(result.indexOf("<table") + 5);

			result = result.substring(result.indexOf("<h2>Shabbos"));

			int startUrl = result.indexOf("http");
			int endUrl = result.indexOf("</p>");


			String link = result.substring(startUrl, endUrl);

			return "4" + link;
		}
		else {
			return "0";
		}
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {

		@Override
		protected String doInBackground(String... urls) { 
			return GET(urls[0]);
		}
		// onPostExecute displays the results of the AsyncTask.
		@Override
		protected void onPostExecute(String result) {
			Log.i("MainActivity", "Result: " + result);
			if (result.length() == 0) {
				Toast.makeText(getApplicationContext(), "Error: Internet Connection", Toast.LENGTH_SHORT);
				Log.i("MainActivity", "Major Failure! Getting the error 'Connection to [url] refused', or s/t like that");
				return;
			}
			//Maariv
			if (result.substring(0,1).equals("3")) {
				Toast.makeText(getBaseContext(), "Updated Maariv", Toast.LENGTH_SHORT).show();
				Log.i("MainActivity", result);
				String maarivString = result.substring(1, result.length());
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
			//Mincha
			else if (result.substring(0, 1).equals("2")) {
				Toast.makeText(getBaseContext(), "Updated Mincha", Toast.LENGTH_SHORT).show();
				String minchaString = result.substring(1, result.length());
				String[] allTables = minchaString.split("ZZZ");
				ArrayList<ArrayList<Minyan>> minyanTables = new ArrayList<ArrayList<Minyan>>();
				for (int i = allTables.length - 1; i >= 0; i--) {
					String table = allTables[i];
					String[] splitTable = table.split("QQQ");
					ArrayList<Minyan> singleMinyan = new ArrayList<Minyan>();
					for (int j = 0; j < splitTable.length - 1; j = j + 2) {
						singleMinyan.add(new Minyan(splitTable[j + 1], splitTable[j]));
					}
					minyanTables.add(singleMinyan);
				}
				minchaTables = minyanTables;
				MinchaFragment minchFrag = getMinchFrag();
				minchFrag.setMinyanTables(minchaTables);
				minchFrag.update();

			}
			//Shacharis
			else if (result.substring(0, 1).equals("1")) {
				Toast.makeText(getBaseContext(), "Updated Shacharis", Toast.LENGTH_SHORT).show();
				String shacharisString = result.substring(1, result.length());
				String[] allTables = shacharisString.split("ZZZ");
				ArrayList<ArrayList<Minyan>> minyanTables = new ArrayList<ArrayList<Minyan>>();
				for (int i = 0; i < allTables.length; i++) {
					String table = allTables[i];
					String[] splitTable = table.split("QQQ");
					ArrayList<Minyan> singleMinyan = new ArrayList<Minyan>();
					for (int j = 0; j < splitTable.length - 1; j = j + 2) {
						singleMinyan.add(new Minyan(splitTable[j + 1], splitTable[j]));
					}
					minyanTables.add(singleMinyan);
				}
				shacharisTables = minyanTables;
				ShacharisFragment shachFrag = getShachFrag();
				Log.i("MainActivity", "" + shachFrag);
				shachFrag.setMinyanTables(shacharisTables);
				shachFrag.update();			
			}
			else if (result.substring(0,1).equals("4")) {
				Toast.makeText(getBaseContext(), "Updated Shabbos", Toast.LENGTH_SHORT).show();
				String shabbosString = result.substring(1, result.length());
				shabbosLink = shabbosString;

				OtherFragment otherFrag = getOtherFrag();
				otherFrag.setShabbosLink(shabbosLink);
				otherFrag.update();
				
				unlockScreenOrientation();
			}
			else {
				Log.i("MainActivity", "Error: onPostExecute Failed");
			}
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putSerializable("shacharisTables", shacharisTables);
		outState.putSerializable("minchaTables", minchaTables);
		outState.putSerializable("maarivTables", maarivTable);
		outState.putString("shabbosLink", shabbosLink);
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			shacharisTables = (ArrayList<ArrayList<Minyan>>)savedInstanceState.get("shacharisTables");
			minchaTables = (ArrayList<ArrayList<Minyan>>)savedInstanceState.get("minchaTables");
			maarivTable = (ArrayList<Minyan>)savedInstanceState.get("maarivTable");
			shabbosLink = savedInstanceState.getString("shabbosLink");
		}
	}
	
	private void lockScreenOrientation() {
		Log.i("MainActivity", "Locked");
	    int currentOrientation = getResources().getConfiguration().orientation;
	    if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
	    } else {
	        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
	    }
	}
	 
	private void unlockScreenOrientation() {
		Log.i("MainActivity", "Unlocked");
	    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
	}
}
