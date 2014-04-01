package com.example.edinburghtab;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import android.widget.TextView;
import android.widget.SearchView.OnQueryTextListener;

public class FragmentRooms extends Fragment implements OnQueryTextListener {
	ListView listView;
	CustomRoomsListAdapter adapter;
	SearchView searchView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_rooms, container, false);
		listView = (ListView) view.findViewById(R.id.listViewRooms);
		adapter = new CustomRoomsListAdapter(getActivity(), MainActivity.rooms);
		listView.setAdapter(adapter);
		
		//set to true if fragment has an options menu
		setHasOptionsMenu(true);
		
		return view;
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
	public boolean onQueryTextChange(String newText) {
		// TODO Auto-generated method stub
		adapter.getFilter().filter(newText);
		return false;
	}

	@Override
	public boolean onQueryTextSubmit(String query) {
		// TODO Auto-generated method stub
		searchView.clearFocus();
		return false;
	}
}	

class CustomRoomsListAdapter extends BaseAdapter implements Filterable {
	Context c;
	ArrayList<Room> rooms;
	ArrayList<Room> roomsCopy;
	public CustomRoomsListAdapter(Context c, ArrayList<Room> rooms){
		this.c = c;
		this.rooms = rooms;
		this.roomsCopy = rooms;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return rooms.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return rooms.get(arg0);
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
			row = inflater.inflate(R.layout.room_row, parent, false);
		}
		
		TextView tvDesc = (TextView) row.findViewById(R.id.tvDescription);
		TextView tvName = (TextView) row.findViewById(R.id.tvName);
		
		tvDesc.setText(rooms.get(pos).description);
		tvName.setText("Code: " + rooms.get(pos).name);
		return row;
	}

	@Override
	public Filter getFilter() {
		// TODO Auto-generated method stub
		return new Filter() {
			ArrayList<Room> filteredRooms = new ArrayList<Room>();
			
			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				// TODO Auto-generated method stub
				FilterResults results = new FilterResults();
				if (constraint == null || constraint.length() == 0) {
					results.values = roomsCopy;
					results.count = roomsCopy.size();
				}
				else {
					for (int i=0;i<roomsCopy.size();i++) {
						if (roomsCopy.get(i).description.toUpperCase().contains(constraint.toString().toUpperCase()) || roomsCopy.get(i).name.toUpperCase().contains(constraint.toString().toUpperCase())) {
							filteredRooms.add(roomsCopy.get(i));
						}
					}
					results.values = filteredRooms;
					results.count = filteredRooms.size();
				}
				
				return results;
			}

			@Override
			protected void publishResults(CharSequence constraint,
					FilterResults results) {
				// TODO Auto-generated method stub
				if (constraint == null || constraint.length() == 0) {
					rooms = roomsCopy;
					notifyDataSetChanged();
				}
				else {
					rooms = (ArrayList<Room>) results.values;
					notifyDataSetChanged();
				}
			}
			
		};
	}
	
}