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
 * Servlet Filter implementation class Filter_Visitor
 */
@WebFilter(urlPatterns={"/login", "/home"})
public class Filter_Visitor implements Filter {

    /**
     * Default constructor. 
     */
    public Filter_Visitor() {
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
	    if (user != null)
	    {
	    	if (user.getUserAdmin())
		     {
		    	 httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/root/home");
		    	 return;
		     }
	    	 httpResponse.sendRedirect(request.getServletContext().getContextPath() + "/app/home");
	    	 return;
	    }
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
