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

import com.supsms.model.dao.FriendDao;
import com.supsms.model.entity.Friend;
import com.supsms.model.entity.User;

@Stateless
public class JpaFriendDao implements FriendDao{
	@PersistenceContext
	private EntityManager em;

	public JpaFriendDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public Friend addFriend(Friend friend) {
		EntityTransaction et = em.getTransaction();
		try {
			et.begin();
			em.persist(friend);
			et.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (et.isActive())
				et.rollback();
			em.close();
		}
		return friend;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Friend> getAllContacts(User user) {
		EntityTransaction et = em.getTransaction();
		List<Friend> friends = null;
		try {
			et.begin();
			friends = em.createNamedQuery("Friends.isFriendshipExist")
							.setParameter("ownerId", user)
							.setParameter("ownedId", user)
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
		return friends;
	}

}
