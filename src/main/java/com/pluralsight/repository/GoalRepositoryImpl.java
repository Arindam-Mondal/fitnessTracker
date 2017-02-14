package com.pluralsight.repository;

import java.util.List;


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.pluralsight.model.Goal;

@Repository("goalRepository")
@Transactional
public class GoalRepositoryImpl implements GoalRepository{
	
	
	@PersistenceContext
	private EntityManager em;
	
	public Goal save(Goal goal) {
		em.persist(goal);
		em.flush();
		return goal;
	}

	public List<Goal> loadAll() {
		System.out.println("Inside GoalRepositoryImpl");
		Query query = em.createQuery("select g from Goal g");
		System.out.println("Size");
		System.out.println(query.getResultList().size());
		return query.getResultList();
	}
	

}
