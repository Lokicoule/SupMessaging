/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.supsms.controller.ParalleleUtils.ParralleleVerificator;
import com.supsms.model.entity.User;

public class Controller_SignUp {
	Map<String, String> erreurs = new HashMap<String, String>();
	String resultat;
	
	public Controller_SignUp() {
	}
	public User signUpUser(HttpServletRequest request)
	{
		return ParralleleVerificator.SignUpVerificator(request, this);		
	}
	
	public String getResultat() {
	    return resultat;
	}

	public Map<String, String> getErreurs() {
	    return erreurs;
	}
	
	public void setError(String field, String msg)
	{
		erreurs.put(field, msg);
	}
}
