/*
 * Author : Lokicoule
 */
package com.supsms.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.controller.ParalleleUtils.ParralleleVerificator;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.User;

public class Controller_Profile {
	private boolean isValid = true;
	
	public Controller_Profile() {
		
	}
	
	public void doPostUpdateUserProfile(HttpServletRequest request, HttpServletResponse response)
	{
		User user = (User) request.getSession().getAttribute("sessionUser");
		String rep = "<span class=\"glyphiconProfile glyphicon-ok\">Done</span>";
		user = ParralleleVerificator.ProfileUpdateVerificator(request, user, this);
		try{
			DaoFactory.getUserDao().updateUser(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isValid == false)
			rep = "<span id=\"phoneProfileButton\" class=\"glyphiconProfile glyphicon-edit\">Invalid</span>";
		try {
			response.setContentType("text/plain");
			response.getWriter().write(rep);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setIsValid(boolean isValid)
	{
		this.isValid = isValid;
	}
	
	//Useless
	/*public List <Friend> getFriendsUser(List<Friend> initFriends)
	{
		List <Friend> finalFriends = new ArrayList<Friend>();
		int i = 0;
		for (Friend fTmp : initFriends)
		{
			boolean isExist = false;
			long idOwnedTmp = fTmp.getUserOwned().getUserId();
			long idOwnerTmp = fTmp.getUserOwner().getUserId();
			for (Friend f : finalFriends)
			{
				long idOwned = f.getUserOwned().getUserId();
				long idOwner = f.getUserOwner().getUserId();
				if (idOwned == idOwnedTmp)
					isExist = true;
				System.out.println("owner" + f.getUserOwner().getUserId() + " + owned:" + f.getUserOwned().getUserId());
				System.out.println("owner" + fTmp.getUserOwner().getUserId() + " + owned:" + fTmp.getUserOwned().getUserId());
			}
			System.out.println(isExist);
			if (isExist == false)
				finalFriends.add(fTmp);
			i++;
		}
		System.out.println("is");
		for (Friend fr : finalFriends)
			System.out.println(fr.getUserOwned().getUserName());
		return finalFriends;
	}*/

}
