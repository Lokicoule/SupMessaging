/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;
import com.supsms.utils.ValidatorUtils;

public class Controller_Contact {
    Map<String, String> erreurs = new HashMap<String, String>();
    public Controller_Contact()
    {
    	
    }
    
    /*
     * Not forget to create admin account before use
     */
    public Message SendMsgFromContact(HttpServletRequest request)
	{
    	Message message = new Message();
		User userSender = (User) request.getSession().getAttribute("sessionUser");
		try {
			List <User> listUser = (List<User>) DaoFactory.getUserDao().findUserByAdminStatus();
			User userAdmin = listUser.get(0);
			
			String msgObject = request.getParameter("q1");
			String msgBody = request.getParameter("q2");
			try {
				ValidatorUtils.isValidUsername(msgObject);
			} catch (Exception e) {
				setError("contactObject", e.getMessage() + " pour le champs objet");
			}
			try {
				ValidatorUtils.isValidMessage(msgBody);
			} catch (Exception e) {
				setError("contactMsg", e.getMessage() + " pour le body du message");
			}
			message.setMsgObject(msgObject);
			message.setMsgBody(msgBody);
			message.setMsgCreationDate(new Date());
			message.setUserSender(userSender);
			message.setUserReceiver(userAdmin);
			message.setMsgStatus(5);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public Map<String, String> getErreurs() {
	    return erreurs;
	}

	private void setError(String field, String msg)
	{
		erreurs.put(field, msg);
	}
}
