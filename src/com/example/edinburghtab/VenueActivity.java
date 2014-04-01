package com.example.edinburghtab;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

public class VenueActivity extends FragmentActivity implements TabListener, OnPageChangeListener{
	ActionBar actionBar; 
	ViewPager pager;
	
	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		setContentView(R.layout.activity_venue);
		
		pager = (ViewPager) findViewById(R.id.pager_venue);
		CustomPagerAdapterVenue adapter = new CustomPagerAdapterVenue(getSupportFragmentManager());
		pager.setAdapter(adapter);
		pager.setOnPageChangeListener(this);
		
		actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		ActionBar.Tab tab1 = actionBar.newTab();
		tab1.setText("Buildings");
		tab1.setTabListener(this);
		actionBar.addTab(tab1);
		
		ActionBar.Tab tab2 = actionBar.newTab();
		tab2.setText("Rooms");
		tab2.setTabListener(this);
		actionBar.addTab(tab2);
		
		
		
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		actionBar.setSelectedNavigationItem(arg0);
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		pager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
}

class CustomPagerAdapterVenue extends FragmentPagerAdapter {
	
	Fragment fragBuilding = new FragmentBuildings();
	Fragment fragRoom = new FragmentRooms();
	
	public CustomPagerAdapterVenue(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int pos) {
		// TODO Auto-generated method stub
		if (pos == 0){
			return fragBuilding;
		}
		if (pos == 1){
			return fragRoom;
		}
		return null;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
}
