package com.example.edinburghtab;

import java.net.URL;
import java.util.ArrayList;

public class Semester {
	int num;
	ArrayList<Day> days;
	
	public Semester(){
		//empty constructor
	}
	
	public void setNumber(int num){
		this.num = num;
	}
	
	public void setDays(ArrayList<Day> days){
		this.days = days;
	}
}

class Day {
	String name;
	ArrayList<Time> times;
	
	public Day(){
		//empty constructor
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTimes(ArrayList<Time> times){
		this.times = times;
	}
}

class Time {
	String start;
	String end;
	ArrayList<Lecture> lectures;
	
	public Time(){
		//empty constructor
	}
	
	public void setStart(String start){
		this.start = start;
	}
	
	public void setEnd(String end){
		this.end = end;
	}
	
	public void setLectures(ArrayList<Lecture> lectures){
		this.lectures = lectures;
	}
}

class Lecture {
	String course;
	ArrayList<Integer> years;
	String room;
	String building;
	
	public Lecture(){
		//empty constructor
	}
	
	public void setCourse(String course){
		this.course = course;
	}
	public void setYear(ArrayList<Integer> years){
		this.years = years;
	}
	public void setRoom(String room){
		this.room = room;
	}
	public void setBuilding(String building){
		this.building = building;
	}
}

class Course implements Comparable<Course> {
	
	URL url;
	String name;
	URL drpsURL;
	String euclid;
	ArrayList<String> courseTags;
	int level;
	int points;
	int year;
	String semester;
	String lecturer;
	String acronym;
	
	
	
	public Course() {
		//empty constructor
	}



	public void setUrl(URL url) {
		this.url = url;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void setDrpsURL(URL drpsURL) {
		this.drpsURL = drpsURL;
	}



	public void setEuclid(String euclid) {
		this.euclid = euclid;
	}



	public void setCourseTags(ArrayList<String> courseTags) {
		this.courseTags = courseTags;
	}



	public void setLevel(int level) {
		this.level = level;
	}



	public void setPoints(int points) {
		this.points = points;
	}



	public void setYear(int year) {
		this.year = year;
	}



	public void setSemester(String semester) {
		this.semester = semester;
	}



	public void setLecturer(String lecturer) {
		this.lecturer = lecturer;
	}
	
	public void setAcronym(String acronym) {
		this.acronym = acronym;
	}



	@Override
	public int compareTo(Course another) {
		// TODO Auto-generated method stub
		return name.compareTo(another.name); 
	}
	
}

class Building {
	String name;
	String description;
	URL map;

	public Building(String name, String description, URL map){
		this.name = name;
		this.description = description;
		this.map = map;
	}

	public Building(){
		//empty connstructor
	}
}

class Room {
	String name;
	String description;

	public Room(String name, String description){
		this.name = name;
		this.description = description;
	}

	public Room(){
		//empty constructor
	}
}

