/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.util.Date;

import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Activity;
import com.supsms.model.entity.User;

public class Controller_Activity {

	public Controller_Activity() {
		
	}
	
	public void addActivity(String type, User user)
	{
		Activity activity = new Activity();
		activity.setActivityDate(new Date());
		activity.setActivityType(type);
		activity.setUserActivity(user);
		activity = DaoFactory.getActivityDao().addActivity(activity);
		if (activity != null)
			user.addUserActivity(activity);
	}

}
