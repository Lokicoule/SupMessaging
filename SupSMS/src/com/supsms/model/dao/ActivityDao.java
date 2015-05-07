/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import com.supsms.model.entity.Activity;
import com.supsms.model.entity.User;

import javax.ejb.Local;

@Local
public interface ActivityDao {
	public Activity addActivity(Activity activity);
	public List<Activity> getUserActivity(User user);
}
