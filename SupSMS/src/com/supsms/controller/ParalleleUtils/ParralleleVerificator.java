/*
 * Author : Lokicoule
 */
package com.supsms.controller.ParalleleUtils;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.supsms.controller.Controller_Profile;
import com.supsms.controller.Controller_SignUp;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.User;
import com.supsms.utils.HashUtils;
import com.supsms.utils.ValidatorUtils;

public class ParralleleVerificator {

	public ParralleleVerificator() {
	}

	public static User ProfileUpdateVerificator(HttpServletRequest request, User user, Controller_Profile context)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			
			Callable<Void> CheckEmailTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String email = request.getParameter("emailProfile");
					if (!email.isEmpty())
						try {
							ValidatorUtils.isValidEmail(email);
							DaoFactory.getUserDao().isEmailExist(email);
							user.setUserEmail(email);
						} catch (Exception e) {
							context.setIsValid(false);
						}
					return null;
				}
			};
			
			Callable<Void> CheckPhoneTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String phone = request.getParameter("phoneProfile");
					if (!phone.isEmpty())
						try {
				        	ValidatorUtils.isValidPhone(phone);
				        	DaoFactory.getUserDao().isPhoneExist(phone);
				        	user.setUserPhone(phone);
				        } catch (Exception e) {
				        	context.setIsValid(false);
				        }
					return null;
				}
			};
			
			Callable<Void> CheckAddressTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String address = request.getParameter("addressProfile");
					if (!address.isEmpty())
						try {
					        ValidatorUtils.isValidMessage(address);
					        user.setUserAddress(address);
						} catch(Exception e) {
							context.setIsValid(false);
					    }
					return null;
				}
			};
			
			Callable<Void> CheckLastnameTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String lastname = request.getParameter("lastnameProfile");
					if (!lastname.isEmpty())
				        try {
				        	ValidatorUtils.isValidUsername(lastname);
				        	user.setLastname(lastname);
				        } catch (Exception e) {
				        	context.setIsValid(false);
				        }
					return null;
				}
			};
			
			Callable<Void> CheckFirstnameTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String firstname = request.getParameter("firstnameProfile");
					if (!firstname.isEmpty())
				        try {
				        	ValidatorUtils.isValidUsername(firstname);
				        	user.setFirstname(firstname);
				        } catch (Exception e) {
				        	context.setIsValid(false);
				        }
					return null;
				}
			};
			
			Callable<Void> CheckPostalCodeTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					String postalcode = request.getParameter("postalCodeProfile");
					if (!postalcode.isEmpty())
				        try {
				        	ValidatorUtils.isValidPostalCode(postalcode);
				        	user.setPostalCode(postalcode);
				        } catch (Exception e) {
				        	context.setIsValid(false);
				        }
					return null;
				}
			};
			pool.submit(CheckEmailTask);
			pool.submit(CheckPhoneTask);
			pool.submit(CheckPostalCodeTask);
			pool.submit(CheckFirstnameTask);
			pool.submit(CheckLastnameTask);
			pool.submit(CheckAddressTask);
			pool.shutdown();
			try {
				//Wait until all pool are closed
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public static User SignUpVerificator(HttpServletRequest request, Controller_SignUp context)
	{
		User user = null;
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			
			Callable<String> CheckEmailTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String email = request.getParameter("email");
					try {
			            ValidatorUtils.isValidEmail(email);
			          	DaoFactory.getUserDao().isEmailExist(email);
			        } catch (Exception e) {
			        	context.setError("email", e.getMessage());
			        }
					return email;
				}
			};
			
			Callable<String> CheckPhoneTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String phone = request.getParameter("phone");
					try {
			        	ValidatorUtils.isValidPhone(phone);
			        	DaoFactory.getUserDao().isPhoneExist(phone);
			        } catch (Exception e) {
			        	context.setError("phone", e.getMessage());
			        }
					return phone;
				}
			};
			
			Callable<String> CheckPasswordTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String pwd = request.getParameter("password");
					String pwdTmp = request.getParameter("password_confirm");
					try {
						if (pwd.length() > 25)
							context.setError("password", "Password trop long");
			        	ValidatorUtils.isValidPassword(pwd, pwdTmp);
			        } catch (Exception e) {
			        	context.setError("password", e.getMessage());
			        }
					return pwd;
				}
			};
			
			Callable<String> CheckAddressTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String address = request.getParameter("address");
					try {
				        ValidatorUtils.isValidMessage(address);
					} catch(Exception e) {
						context.setError("address", e.getMessage());
				    }
					return address;
				}
			};
			
			Callable<String> CheckUsernameTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String username = request.getParameter("username");

			        try {
			        	ValidatorUtils.isValidUsername(username);
			        	DaoFactory.getUserDao().isUserExist(username);
			        } catch (Exception e) {
			        	context.setError("username", e.getMessage());
			        }
					return username;
				}
			};
			
			Callable<String> CheckLastnameTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String lastname = request.getParameter("lastname");					
			        try {
			        	ValidatorUtils.isValidUsername(lastname);
			        } catch (Exception e) {
			        	context.setError("lastname", e.getMessage());
			        }
					return lastname;
				}
			};
			
			Callable<String> CheckFirstnameTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String firstname = request.getParameter("firstname");					
			        try {
			        	ValidatorUtils.isValidUsername(firstname);
			        } catch (Exception e) {
			        	context.setError("firstname", e.getMessage());
			        }
					return firstname;
				}
			};
			
			Callable<String> CheckPostalCodeTask = new Callable<String>() {
				@Override
				public String call() throws Exception {
					String postalcode = request.getParameter("postalcode");					
			        try {
			        	ValidatorUtils.isValidPostalCode(postalcode);
			        } catch (Exception e) {
			        	context.setError("postalcode", e.getMessage());
			        }
					return postalcode;
				}
			};
			Future<String> password = pool.submit(CheckPasswordTask);
			Future<String> email = pool.submit(CheckEmailTask);
			Future<String> phone = pool.submit(CheckPhoneTask);
			Future<String> postalcode = pool.submit(CheckPostalCodeTask);
			Future<String> firstname = pool.submit(CheckFirstnameTask);
			Future<String> lastname = pool.submit(CheckLastnameTask);
			Future<String> username = pool.submit(CheckUsernameTask);
			Future<String> address = pool.submit(CheckAddressTask);
			pool.shutdown();
			return new User(username.get(), HashUtils.hashStringInSHA256(password.get()), email.get(), phone.get(), 
					new Timestamp(new Date(0).getTime()), lastname.get(), firstname.get(), postalcode.get(), address.get());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
}
