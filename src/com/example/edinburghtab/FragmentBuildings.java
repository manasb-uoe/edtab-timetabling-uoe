package com.example.edinburghtab;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class FragmentBuildings extends Fragment implements OnQueryTextListener{
	ListView listView;
	CustomBuildingsListAdapter adapter;
	SearchView searchView; 
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_buildings, container, false);
		listView = (ListView) view.findViewById(R.id.listViewBuildings);
		
		//set to true if fragment has an options menu
		setHasOptionsMenu(true);
		
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		
		adapter = new CustomBuildingsListAdapter(getActivity(), MainActivity.buildings);
		listView.setAdapter(adapter);
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		
		inflater.inflate(R.menu.venue_menu, menu);
		searchView = new SearchView(getActivity());
		menu.add(0, 1, 1, null)
		.setIcon(android.R.drawable.ic_search_category_default)
		.setActionView(searchView)
		.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
		
		searchView.setOnQueryTextListener(this);
		searchView.setQueryHint("Search");
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
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		searchView.clearFocus();
		return false;
	}
	
}

class CustomBuildingsListAdapter extends BaseAdapter implements Filterable{
	Context c;
	ArrayList<Building> buildings;
	ArrayList<Building> buildingsCopy;
	public CustomBuildingsListAdapter(Context c, ArrayList<Building> buildings){
		this.c = c;
		this.buildings = buildings;
		this.buildingsCopy = buildings;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return buildings.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return buildings.get(arg0);
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
		if (row == null){
			LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(R.layout.building_row, parent, false);
		}
		
		TextView tvDesc = (TextView) row.findViewById(R.id.tvDescription);
		TextView tvName = (TextView) row.findViewById(R.id.tvName);
		TextView tvMap = (TextView) row.findViewById(R.id.tvMap);
		
		tvDesc.setText(buildings.get(pos).description);
		tvName.setText("Code: " + buildings.get(pos).name);
		tvMap.setText("Map: " + buildings.get(pos).map.toString());
		return row;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter() {
			ArrayList<Building> filteredBuildings = new ArrayList<Building>();
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				if (constraint == null || constraint.length() == 0) {
					results.values = buildingsCopy;
					results.count = buildingsCopy.size();
				}
				else {
					for (int i=0;i<buildingsCopy.size();i++) {
						if (buildingsCopy.get(i).description.toUpperCase().contains(constraint.toString().toUpperCase()) || buildingsCopy.get(i).name.toUpperCase().contains(constraint.toString().toUpperCase())) {
							filteredBuildings.add(buildingsCopy.get(i));
						}
					}
					results.values = filteredBuildings;
					results.count = filteredBuildings.size();
				}
				
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				if (constraint == null || constraint.length() == 0) {
					buildings = buildingsCopy;
					notifyDataSetChanged();
				}
				else {
					buildings = (ArrayList<Building>) results.values;
					notifyDataSetChanged();
				}
			}
			
		};
	}
	
}
