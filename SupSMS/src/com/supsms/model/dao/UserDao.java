/*
 * Author : Lokicoule
 */
package com.supsms.model.dao;

import java.util.List;

import javax.ejb.Local;

import com.supsms.model.entity.User;

@Local
public interface UserDao {
	public User addUser(User user);
	public User updateUser(User user);
	public List<User> getAllUsers();
	public List<User> findUserByAdminStatus();
	public User findUserById(long id);
	public User findUserByName(String name);
	public User findUserByUsername(String username) throws Exception;
	public void isUserExist(String username) throws Exception;
	public void isPhoneExist(String phone) throws Exception;
	public void isEmailExist(String email) throws Exception;
	public List<User> getOnlineUsers();
}
