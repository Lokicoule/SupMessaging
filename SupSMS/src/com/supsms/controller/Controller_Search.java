/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Friend;
import com.supsms.model.entity.User;

public class Controller_Search {

	public Controller_Search() {
		
		
	}
	
	public void doPostSearch(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		User user = (User) request.getSession().getAttribute("sessionUser");
		String toSearch = request.getParameter("searchField");
		if (toSearch == null)
			return;
		String buffReturn = null;
		List<User> users = DaoFactory.getUserDao().getAllUsers();
		List<Friend> friends = DaoFactory.getFriendDao().getAllContacts(user);
		try {
			for (User u : users)
			{
				if (u!=null)
				{
					long id = u.getUserId();
					String username = u.getUserName();
					Date creationDate = u.getUserCreationDate();
					if (u.getUserName().contains(toSearch))
					{
						if (buffReturn == null)
							buffReturn = "<div id=\"morphsearch-content\" class=\"morphsearch-content\"><div class=\"dummy-column\"></div><div class=\"dummy-column\"><span class=\"dummy-media-object\" id=\"" + id + "\"  href=\"#\">";
						else
							buffReturn += "<span class=\"dummy-media-object\" id=\"" + id + "\"  href=\"#\">";
						buffReturn += "<img class=\"round\" src=\"http://t1.gstatic.com/images?q=tbn:ANd9GcQvTM8DNFbrB6iNSUHzFLHkPqBBpQfacTV5pdxx5b2CStTlazjfvg&t=1\" alt=\"" + username + "\"/>";
						buffReturn += "<h3>" + username + "<br />" + creationDate + "</h3>";
						boolean isFriendship = false;
						for (Friend f : friends)
						{
							if (f.getUserOwner().getUserId() == u.getUserId() || f.getUserOwned().getUserId() == u.getUserId())
								isFriendship = true;
						}
						if (user.getUserId() != id)
						{
							if (isFriendship == false)
								buffReturn += "<a href=\"#\" id=\"addContact_" + id + "\" onclick=\"addFriend(" + id + ")\" class=\"glyphicon glyphicon-plus\">Add</a>";
							if (isFriendship == true)
								buffReturn += "<span class=\"glyphicon glyphicon-ok\">Friend</span>";
						}
						else
							buffReturn += "<span >You are</span>";
						buffReturn += "</span>";
					}
				}
			}
			if (buffReturn != null)
				buffReturn += "</div></div>";
			else
				buffReturn = "<div id=\"morphsearch-content\" class=\"morphsearch-content\"><div class=\"dummy-column\"></div><div class=\"dummy-column\"><h1>No correspondance Found</h1></div></div>";
		} catch(Exception e) {
			e.printStackTrace();
		}
		response.setContentType("text/plain");
		response.getWriter().write(buffReturn);
	}
	
	public void doPostAddFriend(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller_Activity c_activity = new Controller_Activity();
		String contactId = request.getParameter("contactId");
		User userOwner = (User) request.getSession().getAttribute("sessionUser");
		User userOwned = DaoFactory.getUserDao().findUserById(Long.parseLong(contactId));
		Friend 	contact = new Friend();
				contact.setUserOwner(userOwner);
				contact.setUserOwned(userOwned);
				contact.setFriendshipCreationDate(new Date());
		DaoFactory.getFriendDao().addFriend(contact);
		c_activity.addActivity("A ajouté " + userOwned.getUserName() + " à sa liste de contact", userOwner);
		c_activity.addActivity("A été ajouté à la liste de contact de " + userOwner.getUserName(), userOwned);
		String isValid = "<span class=\"glyphicon glyphicon-ok\">Friend</span>";
		response.setContentType("text/plain");  // Set content type of the response so that jQuery knows what it can expect.
		response.getWriter().write(isValid);       // Write response body.
	}

}
