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

import com.supsms.controller.Controller_Chat;
import com.supsms.controller.Controller_Search;
import com.supsms.controller.ParalleleUtils.ParralleleRequestSession;
import com.supsms.model.entity.User;

/**
 * Servlet implementation class Servlet_Chat
 */
@WebServlet(urlPatterns={"/app/home", "/root/home"})
public class Servlet_Chat extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_Chat() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("sessionUser");
		request.setAttribute("origin", request.getRequestURL());
		request = ParralleleRequestSession.HomeParrallele(request, user);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/chat.jsp").forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		User user = (User) request.getSession().getAttribute("sessionUser");
		Controller_Chat c_msg = new Controller_Chat();
		Controller_Search c_search = new Controller_Search();
		String action = request.getParameter("action");
		
		request = ParralleleRequestSession.MessagesParrallele(request, user);
		if (action != null && action.equals("addFriendship"))
			c_search.doPostAddFriend(request, response);
		else if (action != null && action.equals("doSearch"))
			c_search.doPostSearch(request, response);
		else if (action != null && action.equals("showMsg"))
			c_msg.showMessageBody(request, response, " From : ");
		else if (action != null && action.equals("showMsgSent"))
			c_msg.showMessageBody(request, response, " To : ");
		else if (action != null && action.equals("composeMsg") 
				|| action.equals("answerMsg"))
			c_msg.showComposeNewMessage(request, response);
		else if (action != null && action.equals("sendNewMsg"))
			c_msg.sendNewMessage(request, response);
		else if (action != null && action.equals("delMsg"))
			c_msg.putMessageToTrash(request, response);
		else if (action != null && action.equals("showMsgDeleted"))
			c_msg.showMessageBody(request, response, "From : ");
		else if (action != null && action.equals("showListTopic"))
			c_msg.showTopicList(user, request, response);
		else if (action != null && action.equals("selectMessages"))
			c_msg.showSelectedContactAndObject(request, response);
	}

}
