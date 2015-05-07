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

import com.supsms.model.dao.MessageDao;
import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;

@Stateless
public class JpaMessageDao implements MessageDao{
	@PersistenceContext
	private EntityManager em;
	
	public JpaMessageDao(EntityManagerFactory emf){
		em = emf.createEntityManager();
	}

	@Override
	public Message AddMessage(Message msg) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(msg);
			et.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (et.isActive())
				et.rollback();
			em.close();
		}
		return msg;
	}

	@Override
	public Message findMessage(long id) {
		return em.find(Message.class, id);
	}

	@Override
	public Message updateMessage(Message msg) {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.merge(msg);
			t.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return msg;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllMessages(User user) {
		EntityTransaction t = em.getTransaction();
		List<Message> messages = null;
		try {
			t.begin();
			Query query = em.createNamedQuery("Messages.findAllMessages");
			query.setParameter("user", user);
			messages = query.getResultList();
			t.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return messages;
	}

	@Override
	public int findNewMessages(User user) {
		EntityTransaction t = em.getTransaction();
		int nbMsg = 0;
		try {
			t.begin();
			Query query = em.createNamedQuery("Messages.findNewMessages");
			query.setParameter("user", user);
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

	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllMessagesByContactAndObject(User user,
			User contact, String object) {
		EntityTransaction t = em.getTransaction();
		List<Message> messages = null;
		try {
			t.begin();
			Query query = em.createNamedQuery("Messages.findAllMessagesByContactAndObject")
							.setParameter("user", user)
							.setParameter("contact", contact)
							.setParameter("object", object);
			messages = query.getResultList();
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
		return messages;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Message> findAllMessagesByContact(User user,
			User contact) {
		EntityTransaction t = em.getTransaction();
		List<Message> messages = null;
		try {
			t.begin();
			Query query = em.createNamedQuery("Messages.findAllMessagesByContact")
							.setParameter("user", user)
							.setParameter("contact", contact);
			messages = query.getResultList();
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
		return messages;
	}

}
