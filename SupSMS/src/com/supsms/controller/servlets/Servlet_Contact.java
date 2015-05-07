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

import com.supsms.controller.Controller_Contact;
import com.supsms.controller.Controller_Search;
import com.supsms.controller.ParalleleUtils.ParralleleRequestSession;
import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;

/**
 * Servlet implementation class Servlet_Contact
 */
@WebServlet("/contact")
public class Servlet_Contact extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_Contact() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("sessionUser");
		request = ParralleleRequestSession.ContactParrallele(request, user);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/contact.jsp").forward( request, response );
	}

	/**
	 * @throws IOException 
	 * @throws ServletException 
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Controller_Search c_search = new Controller_Search();
		String action = request.getParameter("action");
		if (action != null && action.equals("addFriendship"))
			c_search.doPostAddFriend(request, response);
		else if (action != null && action.equals("doSearch"))
			c_search.doPostSearch(request, response);
		else{
			Controller_Contact contact = new Controller_Contact();
			Message message = contact.SendMsgFromContact(request);
			String isValid = null;
			if (contact.getErreurs().size() == 0)
			{
				ParralleleRequestSession.ContactSendMessageParrallele(request, message);
				isValid = "<span class=\"glyphicon glyphicon-ok-sign\">Sent</span>";
			}
			else
			{
				isValid = "<span id=\"contactShowError\" class=\"glyphiconContact glyphicon-remove-sign erreur\"><br />";
				try {
					if (contact.getErreurs().get("contactObject") != null)
						isValid += contact.getErreurs().get("contactObject") + "<br />";
					if (contact.getErreurs().get("contactMsg") != null)
						isValid += contact.getErreurs().get("contactMsg");
				} catch (Exception e) {
					e.printStackTrace();
				}
				isValid+="</span>";
			}
			response.setContentType("text/plain");
			try {
				response.getWriter().write(isValid);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
