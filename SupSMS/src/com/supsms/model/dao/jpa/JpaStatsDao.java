/*
 * Author : Lokicoule
 */
package com.supsms.model.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.supsms.model.dao.StatsDao;

public class JpaStatsDao implements StatsDao{
	@PersistenceContext
	private EntityManager em;

	public JpaStatsDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public double getCountUsers() {
		EntityTransaction t = em.getTransaction();
		double nbUsers = 0;
		try {
			t.begin();
			Query query = em.createNamedQuery("Users.getCountUsers");
			nbUsers = ((Long)query.getSingleResult()).intValue();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return nbUsers;
	}

	@Override
	public double getCountMessages() {
		EntityTransaction t = em.getTransaction();
		double nbMsg = 0;
		try {
			t.begin();
			Query query = em.createNamedQuery("Messages.getCountMessages");
			nbMsg = ((Long)query.getSingleResult()).intValue();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return nbMsg;
	}

}
