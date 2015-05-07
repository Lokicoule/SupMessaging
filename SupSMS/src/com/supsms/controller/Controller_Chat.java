/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Friend;
import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;

public class Controller_Chat {

	public Controller_Chat() {
		
	}
	
	/*
	 * A contact is a person who send message to user or receive message from user
	 * Return Users list
	 */
	public List<User> getContacts(User user, List<Message> messagesSent, List<Message> messagesReceived, List<Friend> friends)
	{
		List<User> contactsReceived = new ArrayList<User>();
		List<User> contactsSent = new ArrayList<User>();
		List<User> contacts = new ArrayList<User>();

		for (Message m : messagesReceived)
			contactsReceived.add(m.getUserSender());
		for (User contact : contactsReceived)
		{
			boolean isExist = false;
			for (User c : contacts)
			{
				try {
				if (c.getUserId() == contact.getUserId())
					isExist = true;
				} catch (Exception e) {
					
				}
			}
			if (isExist == false)
				contacts.add(contact);
		}
		if (contactsSent != null)
		{
			for (Message m : messagesSent)
				contactsSent.add(m.getUserReceiver());
			for (User contact : contactsSent)
			{
				boolean isExist = false;
				for (User c : contacts)
				{
					try {
					if (c.getUserId() == contact.getUserId())
						isExist = true;
					} catch(Exception e) {
						
					}
				}
				if (isExist == false)
					contacts.add(contact);
			}
		}
		return contacts;
	}
	
	
	public void putMessageToTrash(HttpServletRequest request, HttpServletResponse response)
	{
		Message msg = DaoFactory.getMessageDao().findMessage(
				Long.parseLong(request.getParameter("msgId")));
		msg.setMsgStatus(3);
		msg = DaoFactory.getMessageDao().updateMessage(msg);
	}
	
	public void showSelectedContactAndObject(HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute("sessionUser");
		String contactName = request.getParameter("selectContact");
		String object = request.getParameter("selectObject");
		User contact = DaoFactory.getUserDao().findUserByName(contactName);
		List<Message> messages = DaoFactory.getMessageDao().findAllMessagesByContactAndObject(user, contact, object);
		StringBuffer resp = new StringBuffer();
		DateFormat shortDateFormat = DateFormat.getDateTimeInstance(
			DateFormat.SHORT,
			DateFormat.SHORT);
      if (messages != null)
      {
    	  	for (Message m : messages)
			{
				User sender = m.getUserSender();
				String nameSender = null;
				String nameReceiver = m.getUserReceiver().getUserName();
				try {
					nameSender = sender.getUserName();
				} catch(Exception e) {
					nameSender = "Anonymous";
				}
				String name = null;
				if (nameReceiver.equals(user.getUserName()))
					name = nameSender;
				else
					name = nameReceiver;
				object = m.getMsgObject();
				String body = m.getMsgBody();
				String summary = body;
				if (body.length() > 10)
				    summary = body.substring(0, 10) + "...";
				String date = shortDateFormat.format(m.getMsgCreationDate());
				if (m.getMsgStatus() < 2) 
				{	
					resp.append("<li id=\"" + m.getMsgId() + "\" class=\"msgReceived\">")
	         			.append("<input type=\"checkbox\" /><div class=\"preview\"><h3>")
	         			.append(name)
	         			.append("<small id=\"small_" + m.getMsgId() + "\"> "+ date + "<br />");
	            if (m.getMsgStatus() == 0 && name.equals(nameSender))
	              	resp.append("<strong class=\"new_" + m.getMsgId() + "\"><font size=\"3\">NEW</font></strong>");
	            resp.append("</small></h3><p><strong>")
	            	.append(object).append("</strong>").append(summary).append("</p></div></li>");
				}	
			}
		}
      try {
			response.setContentType("text/plain");
			response.getWriter().write(resp.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * When new user signup this message is send to him
	 * Not forget to create an admin account in order to activate this
	 */
	public static User sendWelcomeMessage(User user)
	{
		try {
			Message message = new Message();
			message.setMsgObject("Hey !");
			message.setMsgBody("Welcome " + user.getUserName() + "\nThank you for your loosing time !!!");
			message.setMsgCreationDate(new Date());
			message.setMsgStatus(0);
			message.setUserSender(DaoFactory.getUserDao().findUserByAdminStatus().get(0));
			message.setUserReceiver(user);
			message = DaoFactory.getMessageDao().AddMessage(message);
			user.addMessageSend(message);
		} catch(Exception e) {
			
		}
		return user;
	}
	
	/*
	 * Send new Message
	 */
	public void sendNewMessage(HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute("sessionUser");
		String ids = request.getParameter("receiversId");
		String msgBody = request.getParameter("bodyNewMsg");
		String msgObject = request.getParameter("objetNewMsg");		
		try {
			String[] tabId = ids.split(";");
			for (int i = 0; i < tabId.length; i++)
			{
				Message message = new Message();
				message.setMsgObject(msgObject);
				message.setMsgBody(msgBody);
				message.setMsgCreationDate(new Date());
				message.setMsgStatus(0);
				message.setUserSender(user);
				long id = Long.parseLong(tabId[i].toString());
				User userTarget = DaoFactory.getUserDao().findUserById(id);
				message.setUserReceiver(userTarget);
				message = DaoFactory.getMessageDao().AddMessage(message);
				user.addMessageSend(message);
			}			
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/*
	 * Create response which return a part of page
	 * Need to be improve for better performance
	 */
	public void showMessageBody(HttpServletRequest request, HttpServletResponse response, String mode)
	{
		Message msg = DaoFactory.getMessageDao().findMessage(
				Long.parseLong(request.getParameter("msgId")));
		String userSender = null;
		try {
			userSender = msg.getUserSender().getUserName();
		} catch(Exception e) {
			userSender = "Anonymous";
		}
		StringBuilder resp = new StringBuilder()
			.append("<div id=\"divForAjax\" class=\"message\">")//BEGIN DIVFORAJAX
			.append("<h2><span class=\"icon icon-star-large\"></span>")
			.append("<label for=\"objetNewMsg\" class=\"objectInput\"> Object : </label>")
			.append("<input id=\"objetNewMsg\"  name=\"objetNewMsg\" type=\"text\" placeholder=\"Object\" value=\"")
			.append(msg.getMsgObject())
			.append("\" required/>")
			.append("<span class=\"icon icon-reply-large\"></span><span class=\"icon icon-delete-large\"></span></h2>")
			.append("<div class=\"meta-data\"><p>")//BEGIN META-DATA
			.append("<img src=\"/SupSMS/www/resources/images/avatar.png\" class=\"avatar\" alt=\"\" />")
			.append(mode)
			.append(userSender)
			.append("<span class=\"date\">")
			.append(msg.getMsgCreationDate())
			.append("</span></p></div>") //END META-DATA
			.append("<div class=\"body\">") //BEGIN BODY
			.append(msg.getMsgBody().replaceAll("\\n", "<br />"))
			.append("</div>"); //END BODY
		try {
			if (mode.equals(" From : "))
			{
				resp.append("<div class=\"action\">")//BEGIN ACTION
					.append("<ul class=\"options\">")
					.append("<li><a href=\"#\" class=\"active\">Answering</a></li>")
					.append("<div class=\"clr\"></div></ul>")
					.append("<div class=\"textarea\">")
					.append("<textarea id=\"bodyNewMsg\" name=\"bodyNewMsg\"></textarea>")
					.append("</div><a href=\"#\" id=\"");
				if (userSender.equals("Anonymous"))
					resp.append("none");
				else
					resp.append(msg.getUserSender().getUserId());
				resp.append("\" class=\"btn btn-primary answerMsg\">Send Message</a></div></div></div>");
				User user = (User) request.getSession().getAttribute("sessionUser");
				if (msg.getMsgStatus() == 5 || msg.getMsgStatus() == 6)
					msg.setMsgStatus(6);
				/* if msgdelete fail to be deleted just remove commentary here
				 * else if (msg.getMsgStatus() == 3)
					msg.setMsgStatus(3);*/
				else
					msg.setMsgStatus(1);
				msg = DaoFactory.getMessageDao().updateMessage(msg);
				user.addMessageReceive(msg);
			}
			response.setContentType("text/plain");
			response.getWriter().write(resp.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * Create part of page 
	 * Need to be improve for better performance
	 */
	public void showComposeNewMessage(HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute("sessionUser");
		List<Friend> friends = DaoFactory.getFriendDao().getAllContacts(user);
		@SuppressWarnings("unchecked")
		List<User> logins = (List<User>) request.getSession().getAttribute("onlineUsers");
		StringBuilder resp = new StringBuilder()
		.append("<div id=\"divForAjax\" class=\"message\">")//BEGIN DIVFORAJAX
		.append("<h2><span class=\"icon icon-star-large\">New Mail</span>")
		.append("<span class=\"icon icon-reply-large\"></span><span class=\"icon icon-delete-large\"></span></h2>")
		.append("<div class=\"meta-data\"><p>")//BEGIN META-DATA
		.append("<img src=\"/SupSMS/www/resources/images/avatar.png\" class=\"avatar\" id=\"avatar\" alt=\"\" />")
		.append("<label for=\"toSend\" class=\"objectInput\"> To : </label>")
		.append("<input id=\"toSend\"  name=\"toSend\" type=\"text\" placeholder=\"destinataire\" required/>")
		.append("<label for=\"containerFriends\">Users Online & Friends</label>")
		.append("<div class=\"containerFriends\">")
		.append("Online : <br />");
		for (User u : logins)
			resp.append("<input type=\"checkbox\" id=\"" + u.getUserId() + "\" value=\"" + u.getUserName() + "\" class=\"inputFriend\"/>" + u.getUserName() + "<br />");
		
		resp.append("<br />______<Br />Friends :<br />");
		for (Friend f : friends)
		{
			boolean isExist = true;
			for (User u : logins)
			{
				if (f.getUserOwned().getUserId() != u.getUserId() 
						&& f.getUserOwner().getUserId() != u.getUserId()
						|| logins.size() <= 1)
					isExist = false;
			}
			if (isExist == false)
			{
				if (f.getUserOwned().getUserId() == user.getUserId())
				{
					resp.append("<input type=\"checkbox\" id=\""+ f.getUserOwner().getUserId() +"\" class=\"inputFriend\" value=\""+ f.getUserOwner().getUserName() +"\" /> ");
					resp.append(f.getUserOwner().getUserName());
				}
				else
				{
					resp.append("<input type=\"checkbox\" id=\""+ f.getUserOwned().getUserId() +"\" class=\"inputFriend\" value=\""+ f.getUserOwned().getUserName() +"\"/> ");
					resp.append(f.getUserOwned().getUserName());
				}
				resp.append("<br />");
			}
		}
		resp.append("</div>");
		resp.append("<a href=\"#\" class=\"btn btn-primary addList\">Add</a>");
		resp.append("<span class=\"date\"></span></p>");
		resp.append("<label for=\"objetNewMsg\" class=\"objectInput\"> Object : </label>");
		resp.append("<input id=\"objetNewMsg\"  name=\"objetNewMsg\" type=\"text\" placeholder=\"Object\" required/>");
		resp.append("</div>"); //END META-DATA
		resp.append("<div class=\"body\">"); //BEGIN BODY
		resp.append("</div>"); //END BODY
		resp.append("<div class=\"action\">");//BEGIN ACTION
		resp.append("<ul class=\"options\">");
		resp.append("<li><a href=\"#\" class=\"active\">Answering</a></li>");
		resp.append("<div class=\"clr\"></div></ul>");
		resp.append("<div class=\"textarea\">"); //BEGIN TEXTAREA
		resp.append("<textarea id=\"bodyNewMsg\" name=\"bodyNewMsg\"></textarea>");
		resp.append("</div><a href=\"#\" class=\"btn btn-primary SendMsg\">Send Message</a></div></div>"); //END TEXTAREA, ACTION AND DIVFORAJAX
		try {
			response.setContentType("text/plain");
			response.getWriter().write(resp.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Create response which return all topic about user and friend user selected
	 */
	public void showTopicList(User user, HttpServletRequest request, HttpServletResponse response)
	{
		String contactName = request.getParameter("contact");
		User contact = DaoFactory.getUserDao().findUserByName(contactName);
		List<Message> objects = DaoFactory.getMessageDao().findAllMessagesByContact(user, contact);
		StringBuilder resp = new StringBuilder();
		List<Message> messages = new ArrayList<Message>();
		for (Message m : objects)
		{
			boolean isExist = false;
			for (Message msg : messages)
			{
				if (msg.getMsgObject().equals(m.getMsgObject()))
					isExist = true;
			}
			if (isExist == false)
				messages.add(m);
		}
		for (Message m : messages)
		{
			resp.append("<option id=\"")
				.append("object_" + m.getMsgId())
				.append("\" value=\"")
				.append(m.getMsgObject())
				.append("\" class=\"no\">")
				.append(m.getMsgObject())
				.append("</option><br />");
		}
		try {
			response.setContentType("text/plain");
			response.getWriter().write(resp.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
