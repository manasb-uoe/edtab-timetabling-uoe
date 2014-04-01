package com.example.edinburghtab;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class FragmentDay extends Fragment implements OnChildClickListener {
	ExpandableListView listView;
	RelativeLayout relativeLayoutNoLectures;
	ExpandableListViewAdapter adapter;
	int SEMESTER;
	int DAY;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view =  inflater.inflate(R.layout.fragment_day, container, false);
		listView = (ExpandableListView) view.findViewById(R.id.expandableListView1);
		relativeLayoutNoLectures = (RelativeLayout) view.findViewById(R.id.RelativeLayoutNoLectures);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (getArguments().getInt("SEMESTER") == -1) {
			SEMESTER = 0;
		}
		else {
			SEMESTER = getArguments().getInt("SEMESTER") -1;
			Log.d("TEST", getArguments().getInt("SEMESTER") + "");
		}
		DAY = getArguments().getInt("pos");
		
		adapter = new ExpandableListViewAdapter(getActivity(), MainActivity.semesters.get(SEMESTER).days.get(DAY).times);
		listView.setAdapter(adapter);
		listView.setOnChildClickListener(this);
		
		if (adapter.getGroupCount() == 0) {
			relativeLayoutNoLectures.setVisibility(RelativeLayout.VISIBLE);
			listView.setVisibility(ListView.GONE);
		}
		else {
			for (int i=0;i<adapter.getGroupCount();i++) {
				listView.expandGroup(i);
			}
		}
		
		
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		String course = adapter.map.get(adapter.keys[groupPosition]).get(childPosition).course;
		
		Dialog_Course_Info dialog = new Dialog_Course_Info();
		Bundle bundle = new Bundle();
		bundle.putString("key", course);
		dialog.setArguments(bundle);
		dialog.show(getActivity().getSupportFragmentManager(), "dialog");
		return false;
		
	}
}

class ExpandableListViewAdapter extends BaseExpandableListAdapter {
	Context c;
	LinkedHashMap<String, ArrayList<Lecture>> map = new LinkedHashMap<String, ArrayList<Lecture>>();
	String[] keys;
	public ExpandableListViewAdapter(Context c, ArrayList<Time> times){
		this.c = c;
		for (int i=0;i<times.size();i++) {
			String key = times.get(i).start + " - " + times.get(i).end;
			map.put(key, times.get(i).lectures);
		}
		keys = new String[map.size()];
		for (int i=0;i<map.size();i++) {
			keys[i] = (String) map.keySet().toArray()[i];
		}
		
	}
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return map.get(keys[groupPosition]).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.expandable_listview_child_row, parent, false);
		}
		
		TextView course = (TextView) row.findViewById(R.id.tvCourse);
		TextView building = (TextView) row.findViewById(R.id.tvBuilding);
		TextView room = (TextView) row.findViewById(R.id.tvRoom);
		
		course.setText(map.get(keys[groupPosition]).get(childPosition).course);
		building.setText(map.get(keys[groupPosition]).get(childPosition).building);
		room.setText(map.get(keys[groupPosition]).get(childPosition).room);
		
		return row;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return map.get(keys[groupPosition]).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return keys[groupPosition];
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return keys.length;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.expandable_listview_group_row, parent, false);
		}
		
		TextView time = (TextView) row.findViewById(R.id.tvTime);
		
		time.setText(keys[groupPosition]);
		
		Calendar cal = Calendar.getInstance();
		String CURRENT_HOUR = cal.get(Calendar.HOUR_OF_DAY) + "";
		String HOUR = keys[groupPosition].substring(0,keys[groupPosition].indexOf(":"));
		if (HOUR.equals(CURRENT_HOUR)) {
			time.setTextColor(Color.GREEN);
		}
		else {
			time.setTextColor(Color.WHITE);
		}
		
		return row;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return true;
	}
	
}
