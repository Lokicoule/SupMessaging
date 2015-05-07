/*
 * Author : Lokicoule
 */
package com.supsms.rest;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Contact;
import com.supsms.model.entity.Email;
import com.supsms.model.entity.Inbox;
import com.supsms.model.entity.Phone;
import com.supsms.model.entity.Sent;
import com.supsms.model.entity.User;
import com.supsms.utils.HashUtils;

/*
 * Rest WebService
 * Main get parameters, parse action parameter and dispatch to action task
 */
@Path("/")
public class MainResource {
	JsonParser parser = new JsonParser();
	Gson gson = new Gson();
	private User user;
	private String password;
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public String MainService(MultivaluedMap<String, String> params)
	{
		String action = params.getFirst("action");
		//for (Entry<String, List<String>> entry : params.entrySet())
		//	System.out.println(entry.getKey() + " " + entry.getValue());
		if (action.equalsIgnoreCase("login"))
			return LoginService(params);
		else if (action.equalsIgnoreCase("backupsms"))
			return SmsService(params);
		else if (action.equalsIgnoreCase("backupcontacts"))
			return ContactsService(params);
		else if (action.equalsIgnoreCase("dobackupsms"))
			return RecoverySmsService(params);
		else if (action.equalsIgnoreCase("dobackupcontacts"))
			return RecoveryContactService(params);
		return null;
	}
	
	/*
	 * RecoveryContactService parse list of contact send in parameter
	 * And compare with Contact present in Database
	 * If the list received has missing ID contact
	 * Recovery create a json array of missing contact to recreate in app
	 * And add it to response json object
	 */
	private String RecoveryContactService(MultivaluedMap<String, String> params) {
		JSONObject obj = new JSONObject();
		
		try {
			if (!isValidUserAuthentification(params))
				return obj.put("success", false).toString();
			List<Contact> contacts = DaoFactory.getContactDao().findAllContactsByUser(user);
			String content = params.getFirst("contacts");
	    	JsonObject jsonObject = gson.fromJson( content.toString(), JsonObject.class);
	    	JsonArray contactsJsonArray = jsonObject.getAsJsonArray("contacts");
    		JSONArray finalContact = new JSONArray();

	    	for (Contact c : contacts)
	    	{
	    		boolean isExist = false;
	    		for (int i = 0; i < contactsJsonArray.size(); i++)
	    			if (contactsJsonArray.get(i).getAsJsonObject().get("_ID").getAsLong() == c.getId())
	    				isExist = true;
	    		if (!isExist)
	    		{
	    			JSONObject tmpContact = new JSONObject();
	    			tmpContact.put("_ID", c.getId());
	    			tmpContact.put("DNAME", c.getDName());
	    			JSONArray emails = new JSONArray();
	    			JSONArray phones = new JSONArray();
	    			ExecutorService pool = Executors.newCachedThreadPool();
		    		Callable<Void> putEmailsTask = new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							for (Email e : c.getContactEmails())
			    			{
			    				JSONObject tmpEmail = new JSONObject();
			    				tmpEmail.put("EMAIL", e.getEmail());
			    				emails.put(tmpEmail);
			    			}
			    			tmpContact.put("emails", emails);
							return null;
						}
					};
					
					Callable<Void> putPhonesTask = new Callable<Void>() {
						@Override
						public Void call() throws Exception {
							for (Phone p : c.getContactPhones())
			    			{
			    				JSONObject tmpPhone = new JSONObject();
			    				tmpPhone.put("PNUM", p.getPhone());
			    				phones.put(tmpPhone);
			    			}
			    			tmpContact.put("phones", phones);
							return null;
						}
					};
					pool.submit(putEmailsTask);
					pool.submit(putPhonesTask);
					pool.shutdown();
					try {
						pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
					} catch (Exception e) {
						e.printStackTrace();
					}
	    			finalContact.put(tmpContact);
	    			DaoFactory.getContactDao().removeContact(c);
	    		}
	    	}
	    	obj.put("contacts", finalContact);
	    	obj.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	/*
	 * RecoverySmsService compare box and call correct function parseBox
	 */
	private String RecoverySmsService(MultivaluedMap<String, String> params) {
		JSONObject obj = new JSONObject();
		try {
			if (!isValidUserAuthentification(params))
				return obj.put("success", false).toString();
			String content = params.getFirst("sms");
	    	JsonObject jsonObject = gson.fromJson( content.toString(), JsonObject.class);
	    	JsonArray sms = jsonObject.getAsJsonArray("SMS");
	    	if (params.getFirst("box").equalsIgnoreCase("sent"))
	    		obj.put("sms", parseSentbox(sms));
	    	else
	    		obj.put("sms", parseInbox(sms));
			obj.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/*
	 * Same algorithm than Contact
	 * but with other criterias
	 */
	private JSONArray parseInbox(JsonArray sms) 
	{
		List<Inbox> smsList = DaoFactory.getInboxDao().getAllSmsByUser(user);
		JSONArray finalSms = new JSONArray();
		for (Inbox tmpSms : smsList)
    	{
    		boolean isExist = false;
    		for (int i = 0; i< sms.size(); i++)
    		{
    			JsonElement singleSms = sms.get(i);
        		JsonObject tmp = singleSms.getAsJsonObject();
    			if (tmpSms.getBody().equals(tmp.get("body").getAsString()) 
    					&& tmpSms.getDate().equals(tmp.get("date").getAsString())
    					&& tmpSms.getAddress().equals(tmp.get("address").getAsString()))
    				isExist = true;
    		}
    		if (!isExist)
    		{
    			JSONObject obj = new JSONObject();
    			try {
    				obj.put("_id", tmpSms.getId())
    					.put("date", tmpSms.getDate())
    					.put("body", tmpSms.getBody())
    					.put("thread_id", tmpSms.getThreadId())
    					.put("address", tmpSms.getAddress());
					finalSms.put(obj);
					System.out.println(obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    	}
		return finalSms;
	}
	
	private JSONArray parseSentbox(JsonArray sms) 
	{
		List<Sent> smsList = DaoFactory.getSentDao().getAllSmsByUser(user);
		JSONArray finalSms = new JSONArray();
		for (Sent tmpSms : smsList)
    	{
    		boolean isExist = false;
    		for (int i = 0; i< sms.size(); i++)
    		{
    			JsonElement singleSms = sms.get(i);
        		JsonObject tmp = singleSms.getAsJsonObject();
    			if (tmpSms.getBody().equals(tmp.get("body").getAsString()) 
    					&& tmpSms.getDate().equals(tmp.get("date").getAsString())
    					&& tmpSms.getAddress().equals(tmp.get("address").getAsString()))
    				isExist = true;
    		}
    		if (!isExist)
    		{
    			JSONObject obj = new JSONObject();
    			try {
    				obj.put("_id", tmpSms.getId())
    					.put("date", tmpSms.getDate())
    					.put("body", tmpSms.getBody())
    					.put("thread_id", tmpSms.getThreadId())
    					.put("address", tmpSms.getAddress());
					finalSms.put(obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
    		}
    	}
		return finalSms;
	}

	/*
	 * ContactsService Parse json contacts array
	 * and save in database without check because of the index which check doublon
	 * Parallele algorithm for improve performance
	 */
	private String ContactsService(MultivaluedMap<String, String> params)
	{
		JSONObject obj = new JSONObject();
		try {
			if (!isValidUserAuthentification(params))
				return obj.put("success", false).toString();
			String content = params.getFirst("contacts");
			JsonObject jsonObject = gson.fromJson( content.toString(), JsonObject.class);
	    	JsonArray contacts = jsonObject.getAsJsonArray("contacts");
			for (int i = 0; i < contacts.size(); i++)
			{
				Contact contact = new Contact();
	    		JsonObject tmp = contacts.get(i).getAsJsonObject();
	    		JsonArray phones = tmp.getAsJsonArray("phones");
	    		JsonArray emails = tmp.getAsJsonArray("emails");
	    		ExecutorService pool = Executors.newCachedThreadPool();
	    		Callable<Void> parsePhonesTask = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						for (int j = 0; j < phones.size(); j++)
			    		{
			    			Phone phone = new Phone();
			    			JsonObject tmpPhone = phones.get(j).getAsJsonObject();
			    			phone.setPhone(tmpPhone.get("PNUM").getAsString());
			    			phone.setContactPhone(contact);
			    			contact.addContactPhone(phone);
			    		}
						return null;
					}
				};
				
				Callable<Void> parseEmailsTask = new Callable<Void>() {
					@Override
					public Void call() throws Exception {
						for (int j = 0; j < emails.size(); j++)
			    		{
			    			Email email = new Email();
			    			JsonObject tmpEmail = emails.get(j).getAsJsonObject();
			    			email.setEmail(tmpEmail.get("EMAIL").getAsString());
			    			email.setContactEmail(contact);
			    			contact.addContactEmail(email);
			    		}
						return null;
					}
				};
				pool.submit(parseEmailsTask);
				pool.submit(parsePhonesTask);
	    		pool.shutdown();
	    		try {
					pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
				} catch (Exception e) {
					e.printStackTrace();
				}
	    		contact.setId(tmp.get("_ID").getAsLong());
	    		contact.setDName(tmp.get("DNAME").getAsString());
	    		contact.setUser(user);
	    		DaoFactory.getContactDao().saveContact(contact);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			obj.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}
	
	/*
	 * Same algorithm than ContactsService
	 */
	private String SmsService(MultivaluedMap<String, String> params)
	{
		JSONObject obj = new JSONObject();
		try {
			if (!isValidUserAuthentification(params))
				return obj.put("success", false).toString();
			String content = params.getFirst("sms");
	    	JsonObject jsonObject = gson.fromJson( content.toString(), JsonObject.class);
	    	JsonArray sms = jsonObject.getAsJsonArray("SMS");
	    	if (params.getFirst("box").equalsIgnoreCase("sent"))
	    		addSentBox(sms);
	    	else
	    		addInBox(sms);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			obj.put("success", true);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return obj.toString();
	}

	/*
	 * Save in database the sms
	 * doesn't compare if exist thanks to index constrainte
	 */
	private void addInBox(JsonArray sms) 
	{
		Inbox inbox = new Inbox();
		for (int i = 0; i< sms.size(); i++)
    	{
    		JsonElement singleSms = sms.get(i);
    		JsonObject tmp = singleSms.getAsJsonObject();
    		inbox.setBody(tmp.get("body").getAsString());
    		inbox.setAddress(tmp.get("address").getAsString());
    		inbox.setId(tmp.get("_id").getAsInt());
    		inbox.setThreadId(tmp.get("thread_id").getAsInt());
    		inbox.setDate(tmp.get("date").getAsString());
    		inbox.setUser(user);
    		DaoFactory.getInboxDao().saveSMS(inbox);
    	}
	}

	private void addSentBox(JsonArray sms)
	{
		Sent sent = new Sent();
		for (int i = 0; i< sms.size(); i++)
    	{
    		JsonElement singleSms = sms.get(i);
    		JsonObject tmp = singleSms.getAsJsonObject();
    		sent.setBody(tmp.get("body").getAsString());
    		sent.setAddress(tmp.get("address").getAsString());
    		sent.setId(tmp.get("_id").getAsInt());
    		sent.setThreadId(tmp.get("thread_id").getAsInt());
    		sent.setDate(tmp.get("date").getAsString());
    		sent.setUser(user);
    		DaoFactory.getSentDao().saveSMS(sent);
    	}
	}
	
	/*
	 * Return user object if valid login and password
	 */
	private String LoginService(MultivaluedMap<String, String> params)
	{
		JSONObject obj = new JSONObject();
		try {
			boolean success = isValidUserAuthentification(params);
			obj.put("success", success);
			if (success == true)
			{
				JSONObject tmp = new JSONObject();
				tmp.put("id", user.getUserId())
					.put("username", user.getUserName())
					.put("password", password)
					.put("lastname", user.getLastname())
					.put("firstname", user.getFirstname())
					.put("phone", user.getUserPhone())
					.put("address", user.getUserAddress())
					.put("postalCode", user.getPostalCode())
					.put("email", user.getUserEmail());
				obj.put("user", tmp);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
        return obj.toString();
	}
	
	/*
	 * Check login and password parameters
	 */
	private boolean isValidUserAuthentification(MultivaluedMap<String, String> params)
	{
		String username = params.getFirst("login");
		password = params.getFirst("password");
		user = DaoFactory.getUserDao().findUserByName(username);
		return (user == null || !user.getUserPassword().equals(HashUtils.hashStringInSHA256(password))) ? false : true;
	}
}
