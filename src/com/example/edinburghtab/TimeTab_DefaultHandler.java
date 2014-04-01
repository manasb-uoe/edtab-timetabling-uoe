package com.example.edinburghtab;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class TimeTab_DefaultHandler extends DefaultHandler {
		
	//declare vars
		String str;
		Semester semester;
		ArrayList<Semester> semesters = new ArrayList<Semester>();
		Day day;
		ArrayList<Day> days = new ArrayList<Day>();
		Time time;
		ArrayList<Time> times = new ArrayList<Time>();
		Lecture lecture;
		ArrayList<Lecture> lectures = new ArrayList<Lecture>();
		ArrayList<Integer> years = new ArrayList<Integer>();
		ArrayList<String> selectedItems;
		
		public TimeTab_DefaultHandler(ArrayList<String> selectedItems) {
		this.selectedItems = selectedItems;	
		}
		
		@Override
		public void characters(char[] ch, int start, int length)
				throws SAXException {
			// TODO Auto-generated method stub
			super.characters(ch, start, length);
			str = new String(ch, start, length);
		}

		@Override
		public void startElement(String uri, String localName, String qName,
				Attributes attributes) throws SAXException {
			// TODO Auto-generated method stub
			super.startElement(uri, localName, qName, attributes);
			
			if (qName.equalsIgnoreCase("SEMESTER")){
				semester = new Semester();
				semester.setNumber(Integer.parseInt(attributes.getValue("number")));
//				Log.d("TEST", "SEMESTER " + semester.num);
			}
			
			else if (qName.equalsIgnoreCase("DAY")){
				day = new Day();
				day.setName(attributes.getValue("name"));
//				Log.d("TEST", day.name);
			}
			
			else if (qName.equalsIgnoreCase("TIME")){
				time = new Time();
				time.setStart(attributes.getValue("start"));
				time.setEnd(attributes.getValue("finish"));
			}
			
			else if (qName.equalsIgnoreCase("LECTURE")){
				lecture = new Lecture();
			}
		}
		
		@Override
		public void endElement(String uri, String localName, String qName)
				throws SAXException {
			// TODO Auto-generated method stub
			super.endElement(uri, localName, qName);
			
			if (qName.equalsIgnoreCase("COURSE")){
				lecture.setCourse(str);
			}
			
			else if (qName.equalsIgnoreCase("YEAR")){
				years.add(Integer.parseInt(str));
			}
			
			else if (qName.equalsIgnoreCase("YEARS")){
				lecture.setYear(years);
				
				years = new ArrayList<Integer>();
			}
			
			else if (qName.equalsIgnoreCase("ROOM")){
				lecture.setRoom(str);
			}
			
			else if (qName.equalsIgnoreCase("BUILDING")){
				lecture.setBuilding(str);
			}
			
			else if (qName.equalsIgnoreCase("LECTURE")){
				//check if any courses were selected or not
				if (selectedItems == null) {					
					lectures.add(lecture);
				}
				else {
					if (selectedItems.contains(lecture.course)) {
						lectures.add(lecture);
					}
				}
//				Log.d("TEST", lecture.course);
			}
			
			else if (qName.equalsIgnoreCase("TIME")){
				if (lectures.size() != 0){
					time.setLectures(lectures);
					times.add(time);				
				}
				
				lectures = new ArrayList<Lecture>();
			}
			
			else if (qName.equalsIgnoreCase("DAY")){
				day.setTimes(times);
				days.add(day);
				
				times = new ArrayList<Time>();
			}
			
			
			
			else if (qName.equalsIgnoreCase("SEMESTER")){
				semester.setDays(days);
				semesters.add(semester);
				
				days = new ArrayList<Day>();
			}
		
		}
}

class Venue_DefaultHandler extends DefaultHandler {
	
	String str;
//	HashMap<String, Building> buildingMap = new HashMap<String, Building>();
	ArrayList<Building> buildings = new ArrayList<Building>();
//	HashMap<String, Room> roomMap = new HashMap<String, Room>();
	ArrayList<Room> rooms = new ArrayList<Room>();
	Building building;
	boolean bBuilding = false;
	boolean bRoom = false;
	Room room;
	
	
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		str = new String(ch,start,length);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		
		if (qName.equalsIgnoreCase("BUILDING")){
			building = new Building();
			bBuilding = true;
		}
		else if (qName.equalsIgnoreCase("ROOM")){
			room = new Room();
			bRoom = true;
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		
		if (bBuilding){
			if (qName.equalsIgnoreCase("NAME")){
				building.name = str;
			}
			else if(qName.equalsIgnoreCase("DESCRIPTION")){
				building.description = str;
			}
			else if(qName.equalsIgnoreCase("MAP")){
				try {
					building.map = new URL(str);
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			else if(qName.equalsIgnoreCase("BUILDING")){
				bBuilding = false;
//				buildingMap.put(building.name, building);
				buildings.add(building);
			}
		}
		
		if (bRoom){
			if (qName.equalsIgnoreCase("NAME")){
				room.name = str;
			}
			else if(qName.equalsIgnoreCase("DESCRIPTION")){
				room.description = str;
			}
			else if(qName.equalsIgnoreCase("ROOM")){
				bRoom = false;
//				roomMap.put(room.name, room);
				rooms.add(room);
			}
		}
	}	
}


class Course_DefaultHandler extends DefaultHandler {

	String str;
	HashMap<String, Course> map = new HashMap<String, Course>();
	String acronym;
	Course course;
	ArrayList<String> courseTags = new ArrayList<String>();
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		// TODO Auto-generated method stub
		super.characters(ch, start, length);
		str = new String(ch,start,length);
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		// TODO Auto-generated method stub
		super.startElement(uri, localName, qName, attributes);
		if (qName.equalsIgnoreCase("COURSE")){
			course = new Course();
		}
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		// TODO Auto-generated method stub
		super.endElement(uri, localName, qName);
		
		if (qName.equalsIgnoreCase("URL")){
			try {
				course.setUrl(new URL(str));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (qName.equalsIgnoreCase("NAME")){
			course.setName(str);
		}
		
		else if (qName.equalsIgnoreCase("DRPS")){
			try {
				course.setDrpsURL(new URL(str));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		else if (qName.equalsIgnoreCase("EUCLID")){
			course.setEuclid(str);
		}
		
		else if (qName.equalsIgnoreCase("ACRONYM")){
			acronym = str;
			course.setAcronym(acronym);
		}
		
		else if (qName.equalsIgnoreCase("AI")){
			if (str.equals("")){
				courseTags.add(str);
			}
		}
		
		else if (qName.equalsIgnoreCase("CG")){
			if (!str.equals("")){
				courseTags.add(str);
			}
		}
		
		else if (qName.equalsIgnoreCase("CS")){
			if (!str.equals("")){
				courseTags.add(str);
			}
		}
		
		else if (qName.equalsIgnoreCase("SE")){
			if (!str.equals("")){
				courseTags.add(str);
			}
		}
		
		else if (qName.equalsIgnoreCase("LEVEL")){
			course.setLevel(Integer.parseInt(str));
		}
		
		else if (qName.equalsIgnoreCase("POINTS")){
			course.setPoints(Integer.parseInt(str));
		}
		
		else if (qName.equalsIgnoreCase("YEAR")){
			course.setYear(Integer.parseInt(str));
		}
		
		else if (qName.equalsIgnoreCase("DELIVERYPERIOD")){
			course.setSemester(str);
		}
		
		else if (qName.equalsIgnoreCase("LECTURER")){
			course.setLecturer(str);
		}
		
		else if (qName.equalsIgnoreCase("COURSE")){
			course.setCourseTags(courseTags);
			map.put(acronym, course);
			
			courseTags = new ArrayList<String>();
			acronym = new String();
			course = new Course();
		}
		
	}

	
	
}

