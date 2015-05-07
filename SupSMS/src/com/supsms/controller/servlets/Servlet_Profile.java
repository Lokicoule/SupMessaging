/*
 * Author : Lokicoule
 */
package com.supsms.controller.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.controller.Controller_Profile;
import com.supsms.controller.Controller_Search;
import com.supsms.controller.ParalleleUtils.ParralleleRequestSession;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.User;

/**
 * Servlet implementation class Servlet_Profile
 */
@WebServlet(urlPatterns={"/app/profile", "/app/profile/*", "/root/profile", "/root/profile/*"})
public class Servlet_Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Servlet_Profile() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User target = null;
		User user = (User) request.getSession().getAttribute("sessionUser");
		String userId = request.getParameter("profileUserId");
		String targetPath = request.getPathInfo();
		request.setAttribute("origin", request.getRequestURL());
		if (targetPath != null)
			targetPath = targetPath.substring(1);
		try {
			target = DaoFactory.getUserDao().findUserById(Long.parseLong(userId));
		} catch(Exception e) {
			if (targetPath != null)
				target = DaoFactory.getUserDao().findUserByName(targetPath);
		}
		if (target == null)
			target = user;
		ParralleleRequestSession.ProfileParrallele(request, user, target);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/profile.jsp").forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Controller_Search c_search = new Controller_Search();
		Controller_Profile c_profile = new Controller_Profile();
		String action = request.getParameter("action");
		if (action != null && action.equals("addFriendship"))
			c_search.doPostAddFriend(request, response);
		else if (action != null && action.equals("doSearch"))
			c_search.doPostSearch(request, response);
		else if (action != null && action.equals("updateProfileUser"))
			c_profile.doPostUpdateUserProfile(request, response);
	}

}
