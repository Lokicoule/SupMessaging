/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import javax.ejb.Local;

import com.supsms.model.entity.Friend;
import com.supsms.model.entity.User;

@Local
public interface FriendDao {
	public Friend addFriend(Friend contact);
	public List<Friend> getAllContacts(User user);
}