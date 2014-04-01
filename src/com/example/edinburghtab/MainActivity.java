package com.example.edinburghtab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.SelectableChannel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends FragmentActivity {

	public static MainActivity mainActivity;
	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;
	public static ArrayList<Semester> semesters;
	public static HashMap<String, Course> courseMap;
	public static ArrayList<Building> buildings;
	public static ArrayList<Room> rooms;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (isConnected()) {
			setContentView(R.layout.activity_main);
			mainActivity = this;

			// Create the adapter that will return a fragment for each of the three
			// primary sections of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);
			Calendar cal = Calendar.getInstance();
			int DAY = cal.get(Calendar.DAY_OF_WEEK);
			if (DAY == Calendar.SUNDAY || DAY == Calendar.SATURDAY) {
				mViewPager.setCurrentItem(0);			//set to monday
			}
			else {
				mViewPager.setCurrentItem(DAY-2);
			}
			
			//get data using background tasks
			try {
				semesters = getSemesters();
				courseMap = getCourses();
				buildings = getBuildings();
				rooms = getRooms();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else {
			setContentView(R.layout.no_connection);
			getActionBar().hide();
		}
		

	}
	
	public ArrayList<Semester> getSemesters() throws InterruptedException, ExecutionException {
		return new AsyncTask<String, Void, ArrayList<Semester>>() {

			@Override
			protected ArrayList<Semester> doInBackground(String... params) {
				// TODO Auto-generated method stub
				ArrayList<Semester> semestersList = null;
				try {
					URL url = new URL(params[0]);
					TimeTab_DefaultHandler handler = new TimeTab_DefaultHandler(getIntent().getStringArrayListExtra("selectedItems"));
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					sp.parse(url.openStream(), handler);
					semestersList = handler.semesters;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return semestersList;
			}
		}.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/xml/timetable.xml").get();	
	}
	
	public HashMap<String, Course> getCourses() throws InterruptedException, ExecutionException {
		return new AsyncTask<String, Void, HashMap<String, Course>>() {

			@Override
			protected HashMap<String, Course> doInBackground(String... params) {
				// TODO Auto-generated method stub
				HashMap<String, Course> courses = null;
				try {
					URL url = new URL(params[0]);
					Course_DefaultHandler handler = new Course_DefaultHandler();
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					sp.parse(url.openStream(), handler);
					courses = handler.map;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return courses;
			}
		}.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/xml/courses.xml").get();	
	}
	
	public ArrayList<Building> getBuildings() throws InterruptedException, ExecutionException {
		return new AsyncTask<String, Void, ArrayList<Building>>() {

			@Override
			protected ArrayList<Building> doInBackground(String... params) {
				// TODO Auto-generated method stub
				ArrayList<Building> buildingsList = null;
				try {
					URL url = new URL(params[0]);
					Venue_DefaultHandler handler = new Venue_DefaultHandler();
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					sp.parse(url.openStream(), handler);
					buildingsList = handler.buildings;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return buildingsList;
			}
		}.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/xml/venues.xml").get();	
	}
	
	public ArrayList<Room> getRooms() throws InterruptedException, ExecutionException {
		return new AsyncTask<String, Void, ArrayList<Room>>() {

			@Override
			protected ArrayList<Room> doInBackground(String... params) {
				// TODO Auto-generated method stub
				ArrayList<Room> roomsList = null;
				try {
					URL url = new URL(params[0]);
					Venue_DefaultHandler handler = new Venue_DefaultHandler();
					SAXParserFactory spf = SAXParserFactory.newInstance();
					SAXParser sp = spf.newSAXParser();
					sp.parse(url.openStream(), handler);
					roomsList = handler.rooms;
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParserConfigurationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SAXException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return roomsList;
			}
		}.execute("http://www.inf.ed.ac.uk/teaching/courses/selp/xml/venues.xml").get();	
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_venues) {
			Intent goToVenues = new Intent(this, VenueActivity.class);
			startActivity(goToVenues);
		}
		else if (item.getItemId() == R.id.action_courses) {
			Intent goToCourses = new Intent(this, CoursesActivity.class);
			startActivity(goToCourses);
		}
		return super.onOptionsItemSelected(item);
	}
	
	public boolean isConnected() {
		boolean wifi = false;
		boolean mobile = false;
		ConnectivityManager manager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] allInfo = manager.getAllNetworkInfo();
		for (NetworkInfo n : allInfo) {
			if (n.isConnectedOrConnecting() && n.getType() == ConnectivityManager.TYPE_MOBILE) {
				mobile = true;
			}
			else if (n.isConnectedOrConnecting() && n.getType() == ConnectivityManager.TYPE_WIFI) {
				wifi = true;
			}
		}
		return wifi || mobile;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			for (int i=0;i<5;i++) {
				if (position == i) {
					Fragment frag = new FragmentDay();
					Bundle bundle = new Bundle();
					bundle.putInt("pos", i);
					if (getIntent().getStringArrayListExtra("selectedItems") == null) {
						bundle.putInt("SEMESTER", -1);
					}
					else {						
						String sem = courseMap.get(getIntent().getStringArrayListExtra("selectedItems").get(0)).semester; 
						bundle.putInt("SEMESTER", Integer.parseInt(sem.substring(1)));
					}
					frag.setArguments(bundle);
					return frag;
				}
			}
			return null;
		}

		@Override
		public int getCount() {
			// Show 3 total pages.
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			String[] days = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
			for (int i=0;i<days.length;i++) {
				if (position == i) {
					return days[i];
				}
			}
			return null;
		}
	}
}



