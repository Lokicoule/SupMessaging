/*
 * Author : Lokicoule
 */
package com.supsms.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.supsms.model.dao.SentDao;
import com.supsms.model.entity.Sent;
import com.supsms.model.entity.User;

public class JpaSentDao implements SentDao{
	@PersistenceContext
	private EntityManager em;
	
	public JpaSentDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Sent saveSMS(Sent sms) {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(sms);
			t.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return sms;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Sent> getAllSmsByUser(User user) {
		EntityTransaction et = em.getTransaction();
		List<Sent> sms = null;
		try {
			et.begin();
			sms = em.createNamedQuery("Sent.findAllSmsByUser")
							.setParameter("user", user)
							.getResultList();
			et.commit();
		} catch (Exception e) {
			return null;
		}
		finally {
			if (et.isActive())
				et.rollback();
			em.close();
		}		
		return sms;
	}

}
