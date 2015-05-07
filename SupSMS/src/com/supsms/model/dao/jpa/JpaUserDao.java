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

import com.supsms.model.dao.UserDao;
import com.supsms.model.entity.User;

@Stateless
public class JpaUserDao implements UserDao {
	@PersistenceContext
	private EntityManager em;
	
	public JpaUserDao(EntityManagerFactory emf) {
		em = emf.createEntityManager();
	}

	@Override
	public User addUser(User user) {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.persist(user);
			t.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return user;
	}
	
	@Override
	public User updateUser(User user)
	{
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.merge(user);
			t.commit();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findUserByAdminStatus() {
		EntityTransaction t = em.getTransaction();
		List<User> users = null;
		try {
			t.begin();
			users = em.createNamedQuery("Users.FindUsersByAdminStatus")
					.setParameter("status", true)
					.getResultList();
			t.commit();
		} catch (Exception e) {
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return users;
	}

	@Override
	public User findUserById(long id) {
		return em.find(User.class, id);
	}
	
	@Override
	public User findUserByName(String name) {
		User user = null;
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			Query query = em.createQuery("SELECT p FROM User p WHERE p.userName LIKE :userName");
			query.setParameter("userName", name);
			user = (User) query.getSingleResult();
			t.commit();
		} catch (Exception e) {
			return null;
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		
		return user;
	}

	@Override
	public void isUserExist(String username) throws Exception {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			@SuppressWarnings("unchecked")
			List<User> user = em.createNamedQuery("Users.isExistUser")
					.setParameter("username", username)
					.getResultList();
			if (user == null)
				System.out.println("is null");
			if (!user.isEmpty())
				throw new Exception("User "+username+"found");
			t.commit();
		} catch (Exception e){
			throw new Exception("User "+username+"found");
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		
	}

	@Override
	public void isPhoneExist(String phone) throws Exception {
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			@SuppressWarnings("unchecked")
			List<User> user = em.createNamedQuery("Users.isExistPhone")
					.setParameter("phone", phone)
					.getResultList();
			if (!user.isEmpty())
				throw new Exception("Phone already registred");
			t.commit();
		} catch (Exception e){
			throw new Exception("Phone already registred");
		}finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
	}

	@Override
	public void isEmailExist(String email) throws Exception {
		EntityTransaction t = em.getTransaction();
		try{
			t.begin();
			@SuppressWarnings("unchecked")
			List<User> user = em.createNamedQuery("Users.isExistEmail")
					.setParameter("email", email)
					.getResultList();
			if (!user.isEmpty())
				throw new Exception("Email already registred");
			t.commit();
		} catch (Exception e){
			throw new Exception("Email already registred");
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		
	}

	@Override
	public User findUserByUsername(String username) throws Exception {
		EntityTransaction t = em.getTransaction();
		User user = null;
		try {
			t.begin();
			Query query = em.createQuery("SELECT p FROM User p WHERE p.userName LIKE :userName");
			query.setParameter("userName", username);
			user = (User) query.getSingleResult();
			t.commit();
		} catch (Exception e) {
			if (user == null)
				throw new Exception("User not found");
			return null;
		} finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getAllUsers() {
		EntityTransaction t = em.getTransaction();
		List<User> users = null;
		try {
			t.begin();
			Query query = em.createQuery("SELECT u FROM User u ORDER BY u.userName");
			users = query.getResultList();
			t.commit();
		} catch (Exception e) {
			return null;
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}
		return users;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getOnlineUsers() {
		EntityTransaction t = em.getTransaction();
		List<User> users = null;
		try {
			t.begin();
			Query query = em.createNamedQuery("Users.getOnlineUsers")
					.setParameter("userStatus", true);
			users = query.getResultList();
			t.commit();
		} catch (Exception e) {
			return null;
		}
		finally {
			if (t.isActive())
				t.rollback();
			em.close();
		}		
		return users;
	}
}
