/*
 * Author : Lokicoule
 */
package com.supsms.model;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * Application Lifecycle Listener implementation class PersistanceListener
 *
 */
@WebListener
public class PersistanceListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public PersistanceListener() {
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         PersistenceManager.closeEntityManagerFactory();
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
    }
	
}
