/*
 * Author : Lokicoule
 */
package com.supsms.controller.servlets.refresh;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.controller.ParalleleUtils.ParralleleRequestSession;
import com.supsms.model.entity.User;

/**
 * Servlet implementation class Servlet_Sent
 */
@WebServlet("/Servlet_Sent")
public class Servlet_Sent extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_Sent() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("sessionUser");		
		
		request = ParralleleRequestSession.MessagesParrallele(request, user);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/templates/chat/refresh/refreshSent.jsp").forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
	}

}
