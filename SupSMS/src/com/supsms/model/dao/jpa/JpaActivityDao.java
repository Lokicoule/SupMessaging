/*
 * Author : Lokicoule
 */
package com.supsms.model.dao.jpa;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.supsms.model.dao.ActivityDao;
import com.supsms.model.entity.Activity;
import com.supsms.model.entity.User;

@Stateless
public class JpaActivityDao implements ActivityDao{
	@PersistenceContext
	private EntityManager em;

	public JpaActivityDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Activity addActivity(Activity activity) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(activity);
			et.commit();
			et.begin();
			em.merge(activity.getUserActivity());
			et.commit();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (et.isActive())
				et.rollback();
			em.close();
		}
		return activity;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Activity> getUserActivity(User user) {
		EntityTransaction t = em.getTransaction();
		List<Activity> activities = null;
		try {
			t.begin();
			Query query = em.createNamedQuery("Activities.userActivities")
							.setParameter("user", user);
			activities = query.getResultList();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return activities;
	}

}
