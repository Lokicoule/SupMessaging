/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.io.Serializable;

import javax.persistence.*;


/**
 * The persistent class for the "Inbox" database table.
 * 
 */
@Entity
@Table(name="\"Inbox\"")
@NamedQueries({
	@NamedQuery(name="Inbox.findAll", query="SELECT i FROM Inbox i"),
	@NamedQuery(name="Inbox.findAllSmsByUser", query="SELECT i FROM Inbox i WHERE i.user = :user")
})
public class Inbox implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private Integer smsId;

	private String address;

	private String body;
	
	@Column(name="date")
	private String date;
	
	@Column(name="_id")
	private Integer id;
	
	@Column(name="thread_id")
	private Integer threadId;
	
	//bi-directional many-to-one association to User
	@ManyToOne
	@JoinColumn(name="\"userId\"")
	private User user;

	public Inbox() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getBody() {
		return this.body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Integer getThreadId() {
		return this.threadId;
	}

	public void setThreadId(Integer threadId) {
		this.threadId = threadId;
	}

	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

}