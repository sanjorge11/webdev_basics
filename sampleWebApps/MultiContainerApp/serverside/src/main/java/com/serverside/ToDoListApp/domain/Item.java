package com.serverside.ToDoListApp.domain;

public class Item {
	int id; 
	boolean completed; 
	String description; 
	
	public Item() { 
		
	}
	
	public Item(int id, boolean completed, String description) { 
		this.id = id;
		this.completed = completed;
		this.description = description; 
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getCompleted() {
		return completed;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
