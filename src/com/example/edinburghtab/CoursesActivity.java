package com.example.edinburghtab;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import android.widget.Toast;

public class CoursesActivity extends FragmentActivity implements OnQueryTextListener, Communicator{
	ListView listView;
	CoursesListViewAdapter adapter;
	SearchView searchView;
//	ArrayList<Course> courses = new ArrayList<Course>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_course_filter);
		listView = (ListView) findViewById(R.id.listViewSearch);
		
		//make an arraylist from the static courseMap
//		courses.addAll(MainActivity.courseMap.values());
		
		adapter = new CoursesListViewAdapter(this, MainActivity.courseMap, null, null, listView, getSupportFragmentManager());
		listView.setAdapter(adapter);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.course_prefs, menu);
		searchView = new SearchView(this);
		
		menu.add(0, 1, 1, null)
		.setIcon(android.R.drawable.ic_search_category_default)
		.setActionView(searchView)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint("Search");
		
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if (item.getItemId() == R.id.action_prefs) {
			TimePreferencesDialogFragment dialog = new TimePreferencesDialogFragment();
			dialog.show(getSupportFragmentManager(), "dialog");
		}
		else if (item.getItemId() == R.id.action_apply) {
			if (adapter.selectedItems.size() == 0 || adapter.selectedItems == null) {
				Toast.makeText(this, "No courses selected!", Toast.LENGTH_LONG).show();
			}
			else {				
				MainActivity.mainActivity.finish();
				Intent goToTimeTable = new Intent(this, MainActivity.class);
				goToTimeTable.putStringArrayListExtra("selectedItems", adapter.selectedItems);
				startActivity(goToTimeTable);
				Toast.makeText(this, "Your courses have been saved", Toast.LENGTH_LONG).show();
				finish();
			}
		}
		return super.onOptionsItemSelected(item);
	}
	
	@Override
	public boolean onQueryTextChange(String str) {
		// TODO Auto-generated method stub
		if (str.isEmpty() || str == null) {
			adapter.getFilter().filter("");
		}
		else {	
			adapter.getFilter().filter(str);
		}
		return false;
	}
	@Override
	public boolean onQueryTextSubmit(String str) {
		// TODO Auto-generated method stub
		searchView.clearFocus();
		return false;
	}
	@Override
	public void respond(String[] semesters, int[] years) {
		// TODO Auto-generated method stub	
		adapter = new CoursesListViewAdapter(this, MainActivity.courseMap, semesters, years, listView, getSupportFragmentManager());
		listView.setAdapter(adapter);
	}
	
}

class CoursesListViewAdapter extends BaseAdapter implements Filterable, OnItemClickListener, OnItemLongClickListener {
	Context c;
	ArrayList<Course> coursesOriginal = new ArrayList<Course>();
	ArrayList<Course> coursesCopy = new ArrayList<Course>();
	ArrayList<String> selectedItems = new ArrayList<String>();
	android.support.v4.app.FragmentManager manager;
	public CoursesListViewAdapter(Context c, HashMap<String, Course> courseMap, String[] semesters, int[] years, ListView listView, android.support.v4.app.FragmentManager manager) {
		this.c = c;
		this.manager = manager;
		
		if (semesters == null || years == null) {
			this.coursesOriginal.addAll(courseMap.values());
			this.coursesCopy.addAll(courseMap.values());
			Log.d("TEST", "NULL");
		}
		else {
			ArrayList<Course> tempCourses = new ArrayList<Course>();
			tempCourses.addAll(courseMap.values());
			Log.d("TEST", "NOT NULL");
			
			for (int i=0;i<tempCourses.size();i++) {
				if ((tempCourses.get(i).semester.equals(semesters[0]) || tempCourses.get(i).semester.equals(semesters[1]))) {
					if (tempCourses.get(i).year == years[0] || tempCourses.get(i).year == years[1] || tempCourses.get(i).year == years[2] || tempCourses.get(i).year == years[3] || tempCourses.get(i).year == years[4] ) {
						coursesOriginal.add(tempCourses.get(i));
						coursesCopy.add(tempCourses.get(i));
					}
				}
				
				else {
					continue;
				}
			}
		}
		
		Collections.sort(coursesOriginal);
		Collections.sort(coursesCopy);
		
		listView.setOnItemClickListener(this);
		listView.setOnItemLongClickListener(this);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return coursesOriginal.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return coursesOriginal.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View row = convertView;
		if (row == null) {
			LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.row_courses, parent, false);
		}
		
		TextView tvCourse = (TextView) row.findViewById(R.id.tvName);
		TextView tvAcronym = (TextView) row.findViewById(R.id.tvAcronym);
		TextView tvEUCLID = (TextView) row.findViewById(R.id.tvEUCLID);
		TextView tvYear = (TextView) row.findViewById(R.id.tvYear);
		TextView tvSemester = (TextView) row.findViewById(R.id.tvSemester);
		
		tvCourse.setText(coursesOriginal.get(pos).name);
		tvAcronym.setText(coursesOriginal.get(pos).acronym);
		tvEUCLID.setText(coursesOriginal.get(pos).euclid);
		tvYear.setText("Year " + coursesOriginal.get(pos).year);
		tvSemester.setText("Semester " + coursesOriginal.get(pos).semester);
		
		String queryCourse = coursesOriginal.get(pos).acronym;
		if (!selectedItems.contains(queryCourse)) {
			row.setBackground(null);
		}
		else {			
			row.setBackgroundColor(Color.parseColor("#05313f"));
		}
		
		return row;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter() {
			
			@SuppressWarnings("unchecked")
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				// TODO Auto-generated method stub
				coursesOriginal.clear();
				coursesOriginal.addAll((Collection<? extends Course>) results.values);
				notifyDataSetChanged();
			}
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				ArrayList<Course> filteredCourses = new ArrayList<Course>();
				FilterResults results = new FilterResults();
				if (constraint.length() == 0 || constraint == null) {
					results.values = coursesCopy;
					results.count = coursesCopy.size();
				}
				else {
					for (int i=0;i<coursesCopy.size();i++) {
						String name = coursesCopy.get(i).name.toUpperCase();
						String code = coursesCopy.get(i).acronym.toUpperCase();
						if (name.contains(constraint.toString().toUpperCase()) || code.contains(constraint.toString().toUpperCase())) {
							filteredCourses.add(coursesCopy.get(i));
						}
						else {
							continue;
						}
					}
					results.values = filteredCourses;
					results.count = filteredCourses.size();
				}
				
				return results;
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> adv, View view, int pos, long arg3) {
		// TODO Auto-generated method stub
		String queryCourse = coursesOriginal.get(pos).acronym;
		if (selectedItems.contains(queryCourse)) {
			selectedItems.remove(queryCourse);
			view.setBackground(null);
		}
		else {			
			selectedItems.add(queryCourse);
			view.setBackgroundColor(Color.parseColor("#05313f"));
		}
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> adv, View view, int pos,
			long arg3) {
		// TODO Auto-generated method stub
		Dialog_Course_Info dialog = new Dialog_Course_Info();
		TextView tvAcronym = (TextView) view.findViewById(R.id.tvAcronym);
		Bundle bundle = new Bundle();
		bundle.putString("key", (String) tvAcronym.getText());
		dialog.setArguments(bundle);
		dialog.show(manager, "dialog");
		return false;
	}
	
}
