/*
 * Author : Lokicoule
 */
package com.supsms.utils;

/*
 * CHECK INFORMATION FROM DIFFERENT PROCESS
 * CREATE Exception if not valid data given
 */
public class ValidatorUtils {

	public static void isValidEmail(String email) throws Exception 
	{
		int email_size = email.trim().length();
		if ( email != null && email_size != 0 && email_size < 200)
		{
	        if ( !email.matches( "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)" ))
	            throw new Exception( "field email is not correct" );
	    } 
		else
	        throw new Exception( "Field email is empty or Too Long max 200" );
	}
	
	public static void isValidPostalCode(String postalcode) throws Exception
	{
		if (postalcode != null && postalcode.trim().length() != 0)
		{
			if (!postalcode.matches("^[0-9]{5}$"))
				throw new Exception("field postal code is not correct");
		}
		else
			throw new Exception("field ostal code is empty");
	}
	
	public static void isValidPhone(String phone) throws Exception
	{
		if (phone != null && phone.trim().length() != 0)
		{
			if (!phone.matches("^0[0-9]{9}$"))
				throw new Exception("field phone number is not correct");
		}
		else
			throw new Exception("field phone number is empty");
	}

	public static void isValidPassword(String pwd, String pwdTmp) throws Exception
	{
	    if (pwd != null && pwd.trim().length() != 0 && pwdTmp != null && pwdTmp.trim().length() != 0)
	    {
	        if (!pwd.equals(pwdTmp))
	            throw new Exception("Incorrect password");
	        else if (pwd.trim().length() < 3)
	            throw new Exception("Password too short");
	    } 
	    else
	        throw new Exception("Field password is empty");
	}

	public static void isValidUsername(String username) throws Exception
	{
		int size_user = username.trim().length();
	    if (username != null && size_user < 3 || size_user > 25)
	        throw new Exception( "Min - Max : 3 - 25" );
	}
	
	public static void isValidMessage(String msg) throws Exception
	{
		int size_msg = msg.trim().length();
		if (msg != null && size_msg < 1 || size_msg > 500)
			 throw new Exception( "Min - Max : 1 à 500" );
	}
}
