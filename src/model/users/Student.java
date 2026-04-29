package model.users;

import model.academic.*;


public class Student extends User{
	private int year;
	private int credits;
	private double gpa;
	private Transcript transcript;
	
	public void registerCourse(Course c) {
		
	}
	
	public Transcript getTranskript() {
		return transcript;
	}
	
	
	

}
