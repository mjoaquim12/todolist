package com.goldenrealestate.todolist.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.goldenrealestate.todolist.entity.Agent;

/**
 * 
 * Class used to interact with Agent Entity persistence 
 * 
 * @author Joaquim
 *
 */
public class AgentDao {

	/**
	 * Persists a new Agent Entity
	 * 
	 * @param agent The entity to be persisted
	 */
	public void create(Agent agent) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		em.getTransaction().begin();
		em.persist(agent);
		em.getTransaction().commit();

		em.close();
	}
	

	/**
	 * Updates existing persisted entity
	 * 
	 * @param modelAgent Updated entity  
	 */
	public void update(Agent modelAgent) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Agent agent = em.find(Agent.class, modelAgent.getId());
			agent.setName(modelAgent.getName());
			em.getTransaction().commit();

		} finally {

			em.close();
		}
	}

	
	/**
	 * Deletes persisted entity
	 * 
	 * @param id primary key
	 */
	public void delete(Integer id) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Agent agent = em.find(Agent.class, id);
			em.remove(agent);
			em.getTransaction().commit();

		} finally {

			em.close();
		}
	}

	
	/**
	 * Finds all persisted agent entities
	 * 
	 * @return List of agent entities
	 */
	public List<Agent> findAll() {

		EntityManager em = EntityManagerUtil.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();

		Root<Agent> queryRoot = cq.from(Agent.class);

		cq.select(cb.construct(Agent.class, queryRoot.get("id"), queryRoot.get("name")));

		Query query = em.createQuery(cq);
		List<Agent> result = query.getResultList();

		em.close();

		return result;
	}

	
	/**
	 * Case sensitive like query using the search string 
	 * 
	 * @param modelObject The search string 
	 * @return List of agent entities
	 */
	public List<Agent> findBySearchString(String modelObject) {

		if (modelObject == null || modelObject.isEmpty()) {

			return findAll();
		}

		EntityManager em = EntityManagerUtil.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();

		Root<Agent> agentRoot = cq.from(Agent.class);

		cq.select(
					cb.construct(
							Agent.class, 
							agentRoot.get("id"), 
							agentRoot.get("name")));

		List<Predicate> criteria = new ArrayList<>();

		boolean modelObjectNotNull = modelObject != null;
		
		if (modelObjectNotNull) {
			
			ParameterExpression<String> p = cb.parameter(String.class, "name");
			//stack overflow forum assisted to fix this
			criteria.add(cb.like(agentRoot.<String>get("name"), p));
			cq.where(criteria.get(0));

		}

		Query query = em.createQuery(cq);
		
		if (modelObjectNotNull) {
			
			query.setParameter("name", "%" + modelObject + "%");
		}
		
		List<Agent> result = query.getResultList();

		em.close();

		return result;
	}

	
	/**
	 * Find agent by primary key
	 * 
	 * @param id primary key
	 * @return Agent entity
	 */
	public Agent find(Integer id) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Agent agent = em.find(Agent.class, id);
			em.getTransaction().commit();
			return agent;

		} finally {

			em.close();
		}
	}

}
