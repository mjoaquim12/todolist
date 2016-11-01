package com.goldenrealestate.todolist.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerUtil {
	
	private static EntityManagerFactory entityManagerFactory;
	
	private EntityManagerUtil() {
		
	}

	public static EntityManagerFactory getEntityManagerFactory() {
		
		if(entityManagerFactory == null) {
			
			entityManagerFactory = Persistence.createEntityManagerFactory("entityManager");
		}
		
		return entityManagerFactory;
	}

	public static EntityManager createEntityManager() {
		
		return getEntityManagerFactory().createEntityManager();
	};

}
