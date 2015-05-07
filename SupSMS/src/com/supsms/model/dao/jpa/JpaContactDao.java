/*
 * Author : Lokicoule
 */
package com.supsms.model.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;

import com.supsms.model.dao.ContactDao;
import com.supsms.model.entity.Contact;
import com.supsms.model.entity.User;

public class JpaContactDao implements ContactDao{
	@PersistenceContext
	private EntityManager em;
	
	public JpaContactDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Contact saveContact(Contact contact) {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(contact);
			t.commit();
		} catch(Exception e) {
			//e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return contact;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Contact> findAllContactsByUser(User user) {
		EntityTransaction et = em.getTransaction();
		List<Contact> contacts = null;
		try {
			et.begin();
			contacts = em.createNamedQuery("Contact.findAllContactsByUser")
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
		return contacts;
	}

	@Override
	public void removeContact(Contact contact) {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			Contact toDelete = em.merge(contact);
			em.remove(toDelete);
			t.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
	}

}
