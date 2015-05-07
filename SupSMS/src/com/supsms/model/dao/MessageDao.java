/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import javax.ejb.Local;

import com.supsms.model.entity.Message;
import com.supsms.model.entity.User;

@Local
public interface MessageDao {
	public Message AddMessage(Message msg);
	public Message findMessage(long id);
	public Message updateMessage(Message msg);
	public List<Message> findAllMessages(User user);
	public int findNewMessages(User user);
	public List<Message> findAllMessagesByContactAndObject(User user, User contact, String object);
	public List<Message> findAllMessagesByContact(User user, User contact);
}
