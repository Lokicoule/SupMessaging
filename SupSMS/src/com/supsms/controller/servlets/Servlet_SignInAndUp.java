/*
 * Author : Lokicoule
 */
package com.supsms.controller.servlets;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.supsms.controller.Controller_Activity;
import com.supsms.controller.Controller_Chat;
import com.supsms.controller.Controller_SignIn;
import com.supsms.controller.Controller_SignUp;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.User;

/**
 * Servlet implementation class Controller_Register
 */
@WebServlet("/login")
public class Servlet_SignInAndUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Controller_Activity c_activity = new Controller_Activity();


    public Servlet_SignInAndUp() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward( request, response );
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String form = request.getParameter("nameForm");
		if (form.equals("Sign up"))
			doPostSignUp(request, response);
		else if (form.equals("Sign in"))
			doPostSignIn(request, response);
	}
	
	private void doPostSignIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller_SignIn signIn = new Controller_SignIn();
		User user = signIn.signInUser(request);
        try {
	        if (signIn.getErreurs().size() == 0)
	        	{
		    		setAttributesSuccess(request, user, "Connexion");
					if (user.getUserAdmin())
		    			response.sendRedirect("/SupSMS/root/home");
		    		else
		    			response.sendRedirect("/SupSMS/app/home");
	        	}
	        else
	        	{
		        	request.setAttribute("erreurs_signIn", signIn);
		            request.setAttribute( "user_signIn", user);
		            request = setAttributesError(request, signIn, user, "erreurs_signIn", "user_signIn");
	                request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);
	        	}
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	private void doPostSignUp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Controller_SignUp signUp = new Controller_SignUp();
		User user = signUp.signUpUser(request);
        
        if (signUp.getErreurs().size() == 0)
        {
    		setAttributesSuccess(request, user, "Inscription");
       		response.sendRedirect("/SupSMS/app/home");
    	}
        else
        {
        	request = setAttributesError(request, signUp, user, "erreurs_signUp", "user_signUp");
        	request.getRequestDispatcher("/WEB-INF/pages/Login.jsp").forward(request, response);		
        }
	}
	
	private HttpServletRequest setAttributesError(HttpServletRequest request, Object sign, User user, String nameSetError, String nameSetUser)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setErrorSignInTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					request.setAttribute(nameSetError, sign);
					return null;
				}
			};
			Callable<Void> setErrorSignUpTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					request.setAttribute(nameSetError, sign);
					return null;
				}
			};
			Callable<Void> setUserSignTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					request.setAttribute(nameSetUser, user);
					return null;
				}
			};
			if (nameSetError.contains("In"))
				pool.submit(setErrorSignInTask);
			else
				pool.submit(setErrorSignUpTask);
			pool.submit(setUserSignTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return request;
	}

	private void setAttributesSuccess(HttpServletRequest request, User user, String activity)
	{
		HttpSession session = request.getSession();
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setOnlineUsersToSessionTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
			    	session.setAttribute("onlineUsers", DaoFactory.getUserDao().getOnlineUsers());
					return null;
				}
			};
			Callable<Void> setUsersTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
		    		request.setAttribute("Users", DaoFactory.getUserDao().getAllUsers());
					return null;
				}
			};
			Callable<Void> addActivityTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					c_activity.addActivity(activity, user);
					return null;
				}
			};
			Callable<Void> addUserTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					DaoFactory.getUserDao().addUser(user);
					c_activity.addActivity(activity, user);
					session.setAttribute("sessionUser", Controller_Chat.sendWelcomeMessage(user));
					return null;
				}
			};
			Callable<Void> setSessionTask = new Callable<Void>() {

				@Override
				public Void call() throws Exception {
					session.setAttribute("sessionUser", user);
					return null;
				}
			};
			/*
			 * Do verification before submit job task 
			 * in order to stay in asynchronous way
			 */
			if (activity.contains("Inscription"))
			{
				pool.submit(setOnlineUsersToSessionTask);
				pool.submit(setUsersTask);
				pool.submit(addUserTask);
			}
			else
			{
				pool.submit(setOnlineUsersToSessionTask);
				pool.submit(setUsersTask);
				pool.submit(setSessionTask);
				pool.submit(addActivityTask);
			}
			pool.shutdown();
			try {
				//Wait until all pool are closed
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
