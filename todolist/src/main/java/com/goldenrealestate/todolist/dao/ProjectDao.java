package com.goldenrealestate.todolist.dao;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import com.goldenrealestate.todolist.entity.Building;
import com.goldenrealestate.todolist.entity.Project;

public class ProjectDao implements Serializable {
	
	private static final long serialVersionUID = -5879108917192890736L;
	
	public void create(Project project) {

		EntityManager em = EntityManagerUtil.createEntityManager();		

		em.getTransaction().begin();
		em.persist(project);
		em.getTransaction().commit();

		em.close();
	}

	public void update(Project modelProject) {

		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Project entity = em.find(Project.class, modelProject.getId());
			entity.setAgent(modelProject.getAgent());
			entity.setProject(modelProject.getProject());
			entity.setStatus(modelProject.getStatus());
			em.getTransaction().commit();

		} finally {

			em.close();
		}
	}

	public void delete(Integer id) {
		
		EntityManager em = EntityManagerUtil.createEntityManager();

		try {

			em.getTransaction().begin();
			Project entity = em.find(Project.class, id);
			em.remove(entity);
			em.getTransaction().commit();

		} finally {

			em.close();
		}
		
	}

	public Project find(Integer id) {
		
		EntityManager em = EntityManagerUtil.createEntityManager();
		return em.find(Project.class, id);
	}
	
	
	public Collection<Project> findAll() {
			
		EntityManager em = EntityManagerUtil.createEntityManager();	
		
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();		
		
		Root<Project> projectRoot = cq.from(Project.class);
		
		cq.select(cb.construct(
								Project.class, 
								projectRoot.get("id"),
								projectRoot.get("building"), 
								projectRoot.get("project"), 
								projectRoot.get("agent"), 
								projectRoot.get("status")));

		Query query = em.createQuery(cq);
		List<Project> result = query.getResultList();
		
		em.close();
		
		return result;
	}
	
	public Collection<Project> findBySearchParams(Building building) {
		
		if (building == null || 
			building.getName() == null || 
			building.getName().isEmpty()) {

			return findAll();
		}
		
		String buildingName = building.getName();
		EntityManager em = EntityManagerUtil.createEntityManager();

		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Object> cq = cb.createQuery();
		
		Root<Project> projectRoot = cq.from(Project.class);	
		
		Join<Project, Building> join = projectRoot.join(
										"building",JoinType.INNER);		

		join.on(cb.equal(join.get("name"),buildingName));
		
		cq.select(projectRoot);

		Query query = em.createQuery(cq);		
		
		List<Project> result = query.getResultList();

		em.close();

		return result;
	}
	
	
}
