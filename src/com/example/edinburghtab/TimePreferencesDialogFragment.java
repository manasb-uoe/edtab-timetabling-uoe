package com.example.edinburghtab;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class TimePreferencesDialogFragment extends android.support.v4.app.DialogFragment implements OnClickListener, OnCheckedChangeListener {
	CheckBox sem1;
	CheckBox sem2;
	CheckBox y1;
	CheckBox y2;
	CheckBox y3;
	CheckBox y4;
	CheckBox y5;
	Button btnApply;
	Button btnClose;
	Intent goToCourses;
	String[] semesters = new String[]{"S1","-1"};
	int[] years = new int[]{1,2,-1,-1,-1};
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		getDialog().setTitle("Time Preferences");
		setCancelable(false);
		View view = inflater.inflate(R.layout.dialog_fragment_preferences, container, false);
		findViews(view);
		goToCourses = new Intent(getActivity(), CoursesActivity.class);
		
		btnApply.setOnClickListener(this);
		btnClose.setOnClickListener(this);
		sem1.setOnCheckedChangeListener(this);
		sem2.setOnCheckedChangeListener(this);
		y1.setOnCheckedChangeListener(this);
		y2.setOnCheckedChangeListener(this);
		y3.setOnCheckedChangeListener(this);
		y4.setOnCheckedChangeListener(this);
		y5.setOnCheckedChangeListener(this);
		return view;
	}
	
	public void findViews(View view) {
		sem1 = (CheckBox) view.findViewById(R.id.checkSem1);
		sem2 = (CheckBox) view.findViewById(R.id.checkSem2);
		y1 = (CheckBox) view.findViewById(R.id.checkY1);
		y2 = (CheckBox) view.findViewById(R.id.checkY2);
		y3 = (CheckBox) view.findViewById(R.id.checkY3);
		y4 = (CheckBox) view.findViewById(R.id.checkY4);
		y5 = (CheckBox) view.findViewById(R.id.checkY5);
		btnApply = (Button) view.findViewById(R.id.btnApply);
		btnClose = (Button) view.findViewById(R.id.btnClose);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.btnClose) {
			dismiss();
		}
		if (v.getId() == R.id.btnApply) {
			Communicator comm = (Communicator) getActivity();
			comm.respond(semesters, years);
			dismiss();
			Toast.makeText(getActivity(), "Changes have been applied", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (buttonView.getId() == R.id.checkSem1) {
			if (isChecked) {
				semesters[0] = "S1";
			}
			else {
				semesters[0] = "-1";
			}
		}
		else if (buttonView.getId() == R.id.checkSem2) {
			if (isChecked) {
				semesters[1] = "S2";
			}
			else {
				semesters[1] = "-1";
			}

		}
		else if (buttonView.getId() == R.id.checkY1) {
			if (isChecked) {
				years[0] = 1;
			}
			else {
				years[0] = -1;
			}

		}
		else if (buttonView.getId() == R.id.checkY2) {
			if (isChecked) {
				years[1] = 2;
			}
			else {
				years[1] = -1;
			}

		}
		else if (buttonView.getId() == R.id.checkY3) {
			if (isChecked) {
				years[2] = 3;
			}
			else {
				years[2] = -1;
			}

		}
		else if (buttonView.getId() == R.id.checkY4) {
			if (isChecked) {
				years[3] = 4;
			}
			else {
				years[3] = -1;
			}

		}
		else if (buttonView.getId() == R.id.checkY5) {
			if (isChecked) {
				years[4] = 5;
			}
			else {
				years[4] = -1;
			}

		}

		
		
	}
}
