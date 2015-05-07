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
 * Servlet implementation class Servlet_Trash
 */
@WebServlet("/Servlet_Trash")
public class Servlet_Trash extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_Trash() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User user = (User) request.getSession().getAttribute("sessionUser");		
		request = ParralleleRequestSession.MessagesParrallele(request, user);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/templates/chat/refresh/refreshTrash.jsp").forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
