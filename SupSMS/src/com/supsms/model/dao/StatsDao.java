/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import javax.ejb.Local;

@Local
public interface StatsDao {
	public double getCountUsers();
	public double getCountMessages();
}
