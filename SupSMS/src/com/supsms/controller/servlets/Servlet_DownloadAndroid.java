/*
 * Author : Lokicoule
 */
package com.supsms.controller.servlets;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Servlet_DownloadAndroid
 */
@WebServlet("/download-app")
public class Servlet_DownloadAndroid extends HttpServlet {
	private static final int BUFF_BYTES_DOWNLOAD = 1024;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Servlet_DownloadAndroid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition",
		"attachment;filename=supsms.apk");
		InputStream istream = getServletContext().getResourceAsStream("/www/resources/apk/AppSupSMS.apk");
		OutputStream ostream = response.getOutputStream();
		byte[] bytes = new byte[BUFF_BYTES_DOWNLOAD];
		int read = 0;
		while ((read = istream.read(bytes)) != -1)
			ostream.write(bytes, 0, read);
		ostream.flush();
		ostream.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
