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

/**
 * Servlet implementation class Servlet_HomeVisitor
 */
@WebServlet("/Servlet_HomeVisitor")
public class Servlet_HomeVisitor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_HomeVisitor() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request = ParralleleRequestSession.VisitorHomeParrallele(request);
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/templates/home/homeRefresh.jsp").forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

}
