package com.goldenrealestate.todolist.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_project")
public class Project implements Serializable{
	
	private static final long serialVersionUID = 1319364701445108885L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne (optional=false)
	
	private Building building;	
	
	@Column( name = "project")
	private String project;
	
	@ManyToOne (optional = false)
	private Agent agent;
	
	@Enumerated(EnumType.STRING)
	@Column( name = "status")
	private Status status;		
	
	public Project() {
		
	}	

	public Project(Integer id, Building building, String project, Agent agent, Status status) {
		
		this.id = id;
		this.building = building;
		this.project = project;
		this.agent = agent;
		this.status = status;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Building getBuilding() {
		return building;
	}

	public void setBuilding(Building building) {
		this.building = building;
	}

	public String getProject() {
		return project;
	}

	public void setProject(String project) {
		this.project = project;
	}

	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	
}
