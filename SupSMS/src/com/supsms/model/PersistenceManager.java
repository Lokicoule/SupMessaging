/*
 * Author : Lokicoule
 */
package com.supsms.model;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceManager {

	private PersistenceManager() {
	}
	
	private static EntityManagerFactory emf;
	
	public static EntityManagerFactory getEntityManagerFactory()
	{
		return (emf == null) ? Persistence.createEntityManagerFactory("SupSMS") : emf;
	}

	public static void closeEntityManagerFactory()
	{
		if (emf != null && emf.isOpen())
			emf.close();
	}
}
