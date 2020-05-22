package com.serverside.SpringSecurity.domain;

public class Student {
	int id; 
	double gpa; 
	String firstName; 
	String lastName; 
	
	public Student() { 
		
	}
	
	public Student(int id, double gpa, String firstName, String lastName) { 
		this.id = id;
		this.gpa = gpa;
		this.firstName = firstName; 
		this.lastName = lastName; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
