/*
 * Author : Lokicoule
 */
package com.supsms.model;

import com.supsms.model.dao.ActivityDao;
import com.supsms.model.dao.ContactDao;
import com.supsms.model.dao.FriendDao;
import com.supsms.model.dao.InboxDao;
import com.supsms.model.dao.MessageDao;
import com.supsms.model.dao.SentDao;
import com.supsms.model.dao.StatsDao;
import com.supsms.model.dao.UserDao;
import com.supsms.model.dao.jpa.JpaActivityDao;
import com.supsms.model.dao.jpa.JpaContactDao;
import com.supsms.model.dao.jpa.JpaFriendDao;
import com.supsms.model.dao.jpa.JpaInboxDao;
import com.supsms.model.dao.jpa.JpaMessageDao;
import com.supsms.model.dao.jpa.JpaSentDao;
import com.supsms.model.dao.jpa.JpaStatsDao;
import com.supsms.model.dao.jpa.JpaUserDao;
public class DaoFactory {
	private static boolean jpa = true;
	
	private DaoFactory() {
	}
	
	public static UserDao getUserDao()
	{
		return (jpa) ? new JpaUserDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static FriendDao getFriendDao()
	{
		return (jpa) ? new JpaFriendDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static MessageDao getMessageDao()
	{
		return (jpa) ? new JpaMessageDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static ActivityDao getActivityDao()
	{
		return (jpa) ? new JpaActivityDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static StatsDao getStatsDao()
	{
		return (jpa) ? new JpaStatsDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static InboxDao getInboxDao()
	{
		return (jpa) ? new JpaInboxDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static SentDao getSentDao()
	{
		return (jpa) ? new JpaSentDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
	
	public static ContactDao getContactDao()
	{
		return (jpa) ? new JpaContactDao(PersistenceManager.getEntityManagerFactory()) : null;
	}
}
