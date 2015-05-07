/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import com.supsms.model.entity.Sent;
import com.supsms.model.entity.User;

public interface SentDao {
	public Sent saveSMS(Sent sms);
	public List<Sent> getAllSmsByUser(User user);
}
