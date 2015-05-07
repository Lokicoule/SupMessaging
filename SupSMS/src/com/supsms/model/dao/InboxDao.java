/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import com.supsms.model.entity.Inbox;
import com.supsms.model.entity.User;

public interface InboxDao {
	public Inbox saveSMS(Inbox sms);
	public List<Inbox> getAllSmsByUser(User user);
}
