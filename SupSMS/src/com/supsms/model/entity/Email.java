/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="\"Emails\"")
public class Email {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="\"emailId\"")
	private long emailId;
	
	@Column(name="\"email\"")
	private String email;
	
	@ManyToOne
	@JoinColumn(name="\"contactId\"")
	private Contact contactEmail = new Contact();
	
	public Email() {
	}

	public long getEmailId() {
		return emailId;
	}

	public void setEmailId(long emailId) {
		this.emailId = emailId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Contact getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(Contact contactEmail) {
		this.contactEmail = contactEmail;
	}

}
