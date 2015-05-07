/*
 * Author : Lokicoule
 */
package com.supsms.controller.ParalleleUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import com.supsms.controller.Controller_Chat;
import com.supsms.model.DaoFactory;
import com.supsms.model.entity.Activity;
import com.supsms.model.entity.Friend;
import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;

public class ParralleleRequestSession {

	public ParralleleRequestSession() 
	{
		
	}
	
	public static HttpServletRequest ProfileParrallele(HttpServletRequest request, User user, User target)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setAllUsersTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allUsers", DaoFactory.getUserDao().getAllUsers());
					return null;
				}
			};
			
			Callable<Void> setAllFriendsTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allFriends", DaoFactory.getFriendDao().getAllContacts(user));
					return null;
				}
			};
			
			Callable<Void> setAllFriendsTargetProfileTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allFriendsProfile", DaoFactory.getFriendDao().getAllContacts(target));
					return null;
				}
			};
			
			Callable<Void> setActivitiesTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					List<Activity> activities = DaoFactory.getActivityDao().getUserActivity(target);
					if (!activities.isEmpty())
					{
						Collections.sort(activities, new Comparator<Activity>() {
				
							@Override
							public int compare(Activity o1, Activity o2) {
								return o2.getActivityDate().compareTo(o1.getActivityDate());
							}
						});
					}
					request.setAttribute("Activities", activities);
					return null;
				}
			};
			
			Callable<Void> setTargetProfile = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("userProfile", target);
					return null;
				}
			};
			
			pool.submit(setTargetProfile);
			pool.submit(setActivitiesTask);
			pool.submit(setAllFriendsTargetProfileTask);
			pool.submit(setAllFriendsTask);
			pool.submit(setAllUsersTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static HttpServletRequest HomeParrallele(HttpServletRequest request, User user)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setAllUsersTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allUsers", DaoFactory.getUserDao().getAllUsers());
					return null;
				}
			};
			
			Callable<Void> setAllFriendsTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allFriends", DaoFactory.getFriendDao().getAllContacts(user));
					return null;
				}
			};
			Callable<Void> setAllMsgReceivedTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allMsgReceived", DaoFactory.getMessageDao().findAllMessages(user));
					return null;
				}
			};
			
			Callable<Void> setNewMessagesTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("newMessages", DaoFactory.getMessageDao().findNewMessages(user));
					return null;
				}
			};
			
			Callable<Void> setAllMessagesSentTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					List<Message> listSent = user.getUserMessageSend();
					if (!listSent.isEmpty())
					{
						Collections.sort(listSent, new Comparator<Message>() {
							
							@Override
							public int compare(Message o1, Message o2) {
								return o2.getMsgCreationDate().compareTo(o1.getMsgCreationDate());
							}
						});
					}
					request.setAttribute("allMsgSent", user.getUserMessageSend());
					return null;
				}
			};
			
			Callable<Void> setOnlineUsersSessionTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.getSession().setAttribute("onlineUsers", DaoFactory.getUserDao().getOnlineUsers());
					return null;
				}
			};
			
			Callable<Void> setContactsTaskAsync = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					Controller_Chat c_chat = new Controller_Chat();
					ExecutorService localPool = Executors.newCachedThreadPool();
					Callable<List<Message>> getUserMessagesSent = new Callable<List<Message>>() {
						@Override
						public List<Message> call() throws Exception {
							return user.getUserMessageSend();
						}
					};
					Callable<List<Message>> getAllMessages = new Callable<List<Message>>() {
						@Override
						public List<Message> call() throws Exception {
							return DaoFactory.getMessageDao().findAllMessages(user);
						}
					};
					Callable<List<Friend>> getAllContacts = new Callable<List<Friend>>() {
						@Override
						public List<Friend> call() throws Exception {
							return DaoFactory.getFriendDao().getAllContacts(user);
						}
					};
					Future<List<Message>> userMsgSent = localPool.submit(getUserMessagesSent);
					Future<List<Message>> userAllMsg = localPool.submit(getAllMessages);
					Future<List<Friend>> userFriends = localPool.submit(getAllContacts);
					localPool.shutdown();
					request.setAttribute("contacts", c_chat.getContacts(user, userMsgSent.get(), userAllMsg.get(), userFriends.get()));
					return null;
				}
			};
			pool.submit(setContactsTaskAsync);
			pool.submit(setOnlineUsersSessionTask);
			pool.submit(setNewMessagesTask);
			pool.submit(setAllMsgReceivedTask);
			pool.submit(setAllMessagesSentTask);
			pool.submit(setAllFriendsTask);
			pool.submit(setAllUsersTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static HttpServletRequest ContactSendMessageParrallele(HttpServletRequest request, Message message)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> addMessageTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					DaoFactory.getMessageDao().AddMessage(message);
					return null;
				}
			};
			
			Callable<Void> setUserMessageSendTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					User user = (User) request.getSession().getAttribute("sessionUser");
					user.addMessageSend(message);
					return null;
				}
			};
			pool.submit(setUserMessageSendTask);
			pool.submit(addMessageTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static HttpServletRequest ContactParrallele(HttpServletRequest request, User user)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setAllUsersTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allUsers", DaoFactory.getUserDao().getAllUsers());
					return null;
				}
			};
			
			Callable<Void> setAllFriendsTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allFriends", DaoFactory.getFriendDao().getAllContacts(user));
					return null;
				}
			};
			pool.submit(setAllFriendsTask);
			pool.submit(setAllUsersTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static HttpServletRequest VisitorHomeParrallele(HttpServletRequest request)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setStatsUsersTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("StatsUsers", DaoFactory.getStatsDao().getCountUsers());
					return null;
				}
			};
			
			Callable<Void> setStatsMessagesTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("StatsMessages", DaoFactory.getStatsDao().getCountMessages());
					return null;
				}
			};
			pool.submit(setStatsMessagesTask);
			pool.submit(setStatsUsersTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
	
	public static HttpServletRequest MessagesParrallele(HttpServletRequest request, User user)
	{
		try {
			ExecutorService pool = Executors.newCachedThreadPool();
			Callable<Void> setAllMsgReceivedTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("allMsgReceived", DaoFactory.getMessageDao().findAllMessages(user));
					return null;
				}
			};
			
			Callable<Void> setNewMessagesTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					request.setAttribute("newMessages", DaoFactory.getMessageDao().findNewMessages(user));
					return null;
				}
			};
			
			Callable<Void> setAllMessagesSentTask = new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					List<Message> listSent = user.getUserMessageSend();
					if (!listSent.isEmpty())
					{
						Collections.sort(listSent, new Comparator<Message>() {
							
							@Override
							public int compare(Message o1, Message o2) {
								return o2.getMsgCreationDate().compareTo(o1.getMsgCreationDate());
							}
						});
					}
					request.setAttribute("allMsgSent", user.getUserMessageSend());
					return null;
				}
			};
			pool.submit(setNewMessagesTask);
			pool.submit(setAllMsgReceivedTask);
			pool.submit(setAllMessagesSentTask);
			pool.shutdown();
			try {
				pool.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return request;
	}
}
