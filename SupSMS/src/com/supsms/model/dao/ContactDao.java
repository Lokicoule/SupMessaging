/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import com.supsms.model.entity.Contact;
import com.supsms.model.entity.User;

public interface ContactDao {
	public Contact saveContact(Contact contact);
	public List<Contact> findAllContactsByUser(User user);
	public void removeContact(Contact contact);
}
