package com.goldenrealestate.todolist.entity;

/**
 * Status enum for all the project status
 * 
 * @author Joaquim
 *
 */
public enum Status {

	NOT_STARTED ("Not Started"), 
	IN_PROGESS ("In Progress"), 
	COMPLETED ("Completed"), 
	ON_HOLD ("On Hold");
  
  private String description;
	
	Status(String description){
	  
	  this.description = description;
	}
	
	public String getDescription(){
	  
	  return description;
	}
	
	
}
