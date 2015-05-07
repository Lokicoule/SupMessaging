/*
 * Author : Lokicoule
 */
package com.supsms.controller.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.supsms.model.entity.User;

/**
 * Servlet Filter implementation class Filter_Member
 */
@WebFilter("/app/*")
public class Filter_Member implements Filter {

    /**
     * Default constructor. 
     */
    public Filter_Member() {
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 HttpServletRequest httpRequest = (HttpServletRequest) request;
	     HttpServletResponse httpResponse = (HttpServletResponse) response;
	     User user = (User)httpRequest.getSession().getAttribute("sessionUser");
	     try{
		     if (user == null)
		    	 {
		    	 	httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/login");
		    	 	return;
		    	 }
		     if (user.getUserAdmin())
		     {
		    	 httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/root/home");
		    	 return;
		     }
	     } catch(Exception e) {
	    	 e.printStackTrace();
	     }
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
