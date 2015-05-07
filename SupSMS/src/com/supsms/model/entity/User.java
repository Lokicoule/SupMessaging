/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

import com.supsms.model.DaoFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the "Users" database table.
 * 
 */
//TODO ISEXISTUSER == FINDUSERBYUSERNAME
@Entity
@Table(name="\"Users\"")
@NamedQueries({
	@NamedQuery(name="Users.isExistUser", query="SELECT c FROM User c WHERE c.userName LIKE :username"),
	@NamedQuery(name="Users.isExistPhone", query="SELECT c FROM User c WHERE c.userPhone LIKE :phone"),
	@NamedQuery(name="Users.isExistEmail", query="SELECT c FROM User c WHERE c.userEmail LIKE :email"),
	@NamedQuery(name="Users.FindUserByUsername", query="SELECT c FROM User c WHERE c.userName LIKE :username"),
	@NamedQuery(name="Users.FindUsersByAdminStatus", query="SELECT c FROM User c WHERE c.userAdmin = :status"),
	@NamedQuery(name="Users.getCountUsers", 
		query="SELECT Count(m) FROM User m"),
	@NamedQuery(name="Users.getOnlineUsers", 
		query="SELECT c FROM User c WHERE c.userStatus = :userStatus ORDER BY c.userName")
})
@XmlRootElement
public class User implements Serializable, HttpSessionBindingListener {
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="\"userId\"")
	private long userId;

	@Column(name="\"userAddress\"")
	private String userAddress;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="\"userCreationDate\"")
	private Date userCreationDate;

	@Column(name="\"userEmail\"")
	private String userEmail;

	@Column(name="\"userName\"")
	private String userName;

	@Column(name="\"userPassword\"")
	private String userPassword;
	
	@Column(name="\"userAdmin\"")
	private Boolean userAdmin;

	@Column(name="\"userPhone\"")
	private String userPhone;
	
	@Column(name="\"userStatus\"")
	private boolean userStatus;
	
	@Column(name="\"lastname\"")
	private String lastname;
	
	@Column(name="\"firstname\"")
	private String firstname;
	
	@Column(name="\"postalCode\"")
	private String postalCode;
	
	//bi-directional many-to-one association to Friend
	@OneToMany(mappedBy="fk_userOwner")
	private List<Friend> contactsOwner;

	//bi-directional many-to-one association to Friend
	@OneToMany(mappedBy="fk_userOwned")
	private List<Friend> contactsOwned;
	
	@OneToMany(fetch=FetchType.EAGER, targetEntity=Message.class,
			mappedBy="fk_msgSender")
	private List<Message> msgSender;
	
	@OneToMany(fetch=FetchType.EAGER, targetEntity=Message.class,
			mappedBy="fk_msgReceiver")
	private List<Message> msgReceiver;
	
	@OneToMany(fetch=FetchType.EAGER, targetEntity=Activity.class,
			mappedBy="userActivity")
	private List<Activity> userActivity;

	//bi-directional many-to-one association to Inbox
	@OneToMany(mappedBy="user")
	private List<Inbox> inboxs;

	//bi-directional many-to-one association to Sent
	@OneToMany(mappedBy="user")
	private List<Sent> sents;
	
	@OneToMany(mappedBy="user", cascade = CascadeType.PERSIST)
	private List<Contact> contacts;
	
	public User() {
		
	}

	public User(String userName, String userPassword)
	{
		this.userName = userName;
		this.userPassword = userPassword;
	}
	
	public User(String userName, String userPassword, String userEmail, String userPhone, 
			Date userCreationDate, String lastname, String firstname, String postalCode, String address)
	{
		this.userName = userName;
		this.userPassword = userPassword;
		this.userEmail = userEmail;
		this.userPhone = userPhone;
		this.userAddress = address;
		this.userAdmin = false;
		this.userCreationDate = new Date();
		this.lastname = lastname;
		this.firstname = firstname;
		this.postalCode = postalCode;
	}
	
	public long getUserId() {
		return this.userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public String getUserAddress() {
		return this.userAddress;
	}

	public void setUserAddress(String userAddress) {
		this.userAddress = userAddress;
	}

	public Date getUserCreationDate() {
		return this.userCreationDate;
	}

	public void setUserCreationDate(Date userCreationDate) {
		this.userCreationDate = userCreationDate;
	}

	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public Boolean getUserAdmin() {
		return this.userAdmin;
	}

	public void setUserAdmin(Boolean userAdmin) {
		this.userAdmin = userAdmin;
	}

	public String getUserPhone() {
		return this.userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	public List<Activity> getUserActivity()
	{
		return this.userActivity;
	}
	
	public void addUserActivity(Activity userActivity)
	{
		//On initialise notre conteneur userActivity (utilité dans le cas de l'inscription uniquement)
		if (this.userActivity == null)
			this.userActivity = new ArrayList<Activity>();
		this.userActivity.add(userActivity);
	}
	
	public void setUserActivity(List<Activity> userActivities)
	{
		this.userActivity = userActivities;
	}
	
	public List<Friend> getFriendsUserOwner()
	{
		if (this.contactsOwner == null)
			this.contactsOwner = new ArrayList<Friend>();
		return this.contactsOwner;
	}
	
	public void addFriendsUserOwner(Friend owner)
	{
		if (this.contactsOwner == null)
			this.contactsOwner = new ArrayList<Friend>();
		this.contactsOwner.add(owner);
	}
	
	public void setFriendsUserOwner(List<Friend> owners)
	{
		this.contactsOwner = owners;
	}
	
	public List<Friend> getFriendsUserOwned()
	{
		if (this.contactsOwner == null)
			this.contactsOwner = new ArrayList<Friend>();
		return this.contactsOwner;
	}
	
	public void addFriendsUserOwned(Friend owned)
	{
		if (this.contactsOwned == null)
			this.contactsOwned = new ArrayList<Friend>();
		this.contactsOwned.add(owned);
	}
	
	public void setFriendsUserOwned(List<Friend> owneds)
	{
		this.contactsOwned = owneds;
	}
	
	public List<Message> getUserMessageReceive()
	{
		if (this.msgReceiver == null)
			this.msgReceiver = new ArrayList<Message>();
		return this.msgReceiver;
	}
	
	public void addMessageReceive(Message msg)
	{
		if (this.msgReceiver == null)
			this.msgReceiver = new ArrayList<Message>();
		this.msgReceiver.add(msg);
	}
	
	public void setMessageReceive(List<Message> msg)
	{
		this.msgReceiver = msg;
	}
	
	public void addMessageSend(Message msg)
	{
		if (this.msgSender == null)
			this.msgSender = new ArrayList<Message>();
		this.msgSender.add(msg);
	}
	
	public void setMessageSend(List<Message> msg)
	{
		this.msgSender = msg;
	}
	
	public List<Message> getUserMessageSend()
	{
		if (this.msgSender == null)
			this.msgSender = new ArrayList<Message>();
		return this.msgSender;
	}

	@Override
	public void valueBound(HttpSessionBindingEvent event) {
		this.userStatus = true;
		DaoFactory.getUserDao().updateUser(this);
	}

	@Override
	public void valueUnbound(HttpSessionBindingEvent event) {
		this.userStatus = false;
		DaoFactory.getUserDao().updateUser(this);
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	
	public List<Inbox> getInboxs() {
		return this.inboxs;
	}

	public void setInboxs(List<Inbox> inboxs) {
		this.inboxs = inboxs;
	}

	public Inbox addInbox(Inbox inbox) {
		getInboxs().add(inbox);
		inbox.setUser(this);

		return inbox;
	}

	public Inbox removeInbox(Inbox inbox) {
		getInboxs().remove(inbox);
		inbox.setUser(null);

		return inbox;
	}

	public List<Sent> getSents() {
		return this.sents;
	}

	public void setSents(List<Sent> sents) {
		this.sents = sents;
	}

	public Sent addSent(Sent sent) {
		getSents().add(sent);
		sent.setUser(this);

		return sent;
	}

	public Sent removeSent(Sent sent) {
		getSents().remove(sent);
		sent.setUser(null);

		return sent;
	}
	/*@Override
	public String toString() {
		return ", userId=" + userId + ", userName=" + userName + 
				", userPassword=" + userPassword + ", userEmail=" + userEmail +
				", userAddress=" + userAddress + ", userPhone=" + userPhone + 
				", userCreationDate=" + userCreationDate + ", userAdmin=" + userAdmin +
				", userStatus=" + userStatus + "]";
	}*/
}