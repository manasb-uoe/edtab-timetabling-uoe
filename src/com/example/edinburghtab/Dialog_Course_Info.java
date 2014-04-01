package com.example.edinburghtab;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dialog_Course_Info extends DialogFragment{
	
	LinearLayout linearLayoutMAIN;
	TextView tvAcronym;
	TextView tvName;
	TextView tvTag;
	TextView tvYear;
	TextView tvSemester;
	TextView tvLevel;
	TextView tvPoints;
	TextView tvLecturer;
	TextView tvWebpage;
	TextView tvDRPS;


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		
		//get course map key from arguments
		String key = getArguments().getString("key");

		//check if key exists in course map
		if (MainActivity.courseMap.containsKey(key)){

			view = inflater.inflate(R.layout.course_info_dialog, container, false);
			findViews(view);

			getDialog().setTitle("Course Info");
//			getDialog().getWindow().setDimAmount(new Float(0.70));
			getDialog().requestWindowFeature(STYLE_NO_TITLE);

			tvName.setText(key + " - " + MainActivity.courseMap.get(key).euclid);
			tvAcronym.setText(MainActivity.courseMap.get(key).name);
			StringBuilder tags = new StringBuilder();
			for (int i=0;i<MainActivity.courseMap.get(key).courseTags.size();i++){
				if (!MainActivity.courseMap.get(key).courseTags.get(i).equals("")){
					tags.append(MainActivity.courseMap.get(key).courseTags.get(i) + " ");
				}
			}
			if (tags.equals("")){
				linearLayoutMAIN.removeView(tvTag);
			}
			else {
				tvTag.setText(tags);
			}

			tvYear.setText(MainActivity.courseMap.get(key).year + "");
			tvSemester.setText(MainActivity.courseMap.get(key).semester);
			tvLevel.setText(MainActivity.courseMap.get(key).level + "");
			tvPoints.setText(MainActivity.courseMap.get(key).points + "");
			tvLecturer.setText(MainActivity.courseMap.get(key).lecturer);
			tvWebpage.setText(MainActivity.courseMap.get(key).url.toString());
			tvDRPS.setText(MainActivity.courseMap.get(key).drpsURL.toString());
		}
		
		else {
			view = inflater.inflate(R.layout.course_info_notfound_dialog, container, false);
			getDialog().setTitle("Course Not Found!");
//			getDialog().getWindow().setIcon(android.R.drawable.btn_dialog);
		}



		return view;
	}

	public void findViews(View view){
		linearLayoutMAIN = (LinearLayout) view.findViewById(R.id.LinearLayoutMAIN);
		tvAcronym = (TextView) view.findViewById(R.id.tvAcronym);
		tvName = (TextView) view.findViewById(R.id.tvName);
		tvTag = (TextView) view.findViewById(R.id.tvTag1);
		tvYear = (TextView) view.findViewById(R.id.tvYear);
		tvSemester = (TextView) view.findViewById(R.id.tvSem);
		tvLevel = (TextView) view.findViewById(R.id.tvLevel);
		tvPoints = (TextView) view.findViewById(R.id.tvPoints);
		tvLecturer = (TextView) view.findViewById(R.id.tvLecturer);
		tvWebpage = (TextView) view.findViewById(R.id.tvWebpage);
		tvDRPS = (TextView) view.findViewById(R.id.tvDRPS);
	}
}
