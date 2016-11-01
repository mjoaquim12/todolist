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

import com.goldenrealestate.todolist.entity.Building;

public class BuildingDao {

	public void create(Building building) {

		EntityManager em = EntityManagerUtil.createEntityManager();		

		em.getTransaction().begin();
		em.persist(building);
		em.getTransaction().commit();

		em.close();
	}

	public void update(Integer id, String newName) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Building entity = em.find(Building.class, id);
			entity.setName(newName);
			em.getTransaction().commit();

		} finally {

			em.close();
		}
	}

	public void delete(Integer id) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Building entity = em.find(Building.class, id);
			em.remove(entity);
			em.getTransaction().commit();

		} finally {

			em.close();
		}
	}

	public Building find(Integer id) {
		
		EntityManager em = EntityManagerUtil.createEntityManager();
		return em.find(Building.class, id);
	}

	public List<Building> findAll() {

		EntityManager em = EntityManagerUtil.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();
			
		Root<Building> building = cq.from(Building.class);

		cq.select(cb.construct(Building.class, building.get("id"), building.get("name")));

		Query query = em.createQuery(cq);
		List<Building> result = query.getResultList();

		em.close();

		return result;
	}

	public List<Building> findBySearchString(String searchText) {

		if (searchText == null || searchText.isEmpty()) {

			return findAll();
		}

		EntityManager em = EntityManagerUtil.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();
		
		Root<Building> buildingRoot = cq.from(Building.class);

		cq.select(cb.construct(Building.class, buildingRoot.get("id"), buildingRoot.get("name")));

		List<Predicate> criteria = new ArrayList<Predicate>();

		boolean searTextNotNull = searchText != null;
		if (searTextNotNull) {
			
			ParameterExpression<String> p = cb.parameter(String.class, "name");
			criteria.add(cb.like(buildingRoot.<String>get("name"), p));
			cq.where(criteria.get(0));
		}

		Query query = em.createQuery(cq);
		
		if (searTextNotNull) {
			query.setParameter("name", "%" + searchText + "%");
		}
		List<Building> result = query.getResultList();

		em.close();

		return result;
	}

}
