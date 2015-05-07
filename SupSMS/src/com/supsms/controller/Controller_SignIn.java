/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.supsms.model.DaoFactory;
import com.supsms.model.entity.User;
import com.supsms.utils.HashUtils;
import com.supsms.utils.ValidatorUtils;

public class Controller_SignIn {
    Map<String, String> erreurs = new HashMap<String, String>();

	public Controller_SignIn() {
	}
	
	public User signInUser(HttpServletRequest request)
	{
		String username = request.getParameter("username");
		String pwd = request.getParameter("password");
		User user = null;
		try {
			user = DaoFactory.getUserDao().findUserByUsername(username);
		} catch(Exception e)
		{
			setError("username_signIn", e.getMessage());
		}
    	try {
        	ValidatorUtils.isValidPassword(HashUtils.hashStringInSHA256(pwd), user.getUserPassword());
        } catch ( Exception e ) {
            setError("password_signIn", e.getMessage());
        }
        try {
        	ValidatorUtils.isValidUsername(username);
        } catch ( Exception e ) {
        	setError("username_signIn", e.getMessage());
        }
		return user;
	}

	public Map<String, String> getErreurs() {
	    return erreurs;
	}

	private void setError(String field, String msg)
	{
		erreurs.put(field, msg);
	}
	}
