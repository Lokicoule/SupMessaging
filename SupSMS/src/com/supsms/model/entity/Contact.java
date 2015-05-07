/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="\"Contacts\"")
@NamedQueries({
	@NamedQuery(name="Contact.findAllContactsByUser", query="SELECT i FROM Contact i WHERE i.user = :user")
})
public class Contact {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="\"contactId\"")
	private long contactId;
	
	@OneToMany(targetEntity=Email.class, mappedBy="contactEmail", cascade = CascadeType.ALL)
	private List<Email> contactEmails;
	
	@OneToMany(mappedBy="contactPhone", cascade = CascadeType.ALL)
	private List<Phone> contactPhones;
	
	@Column(name="\"dname\"")
	private String dname;
	
	@Column(name="\"_id\"")
	private long id;
	
	@ManyToOne
	@JoinColumn(name="\"userId\"")
	User user;
	
	public Contact() {
	}

	public String getDName() {
		return dname;
	}

	public void setDName(String dname) {
		this.dname = dname;
	}

	public long getContactId() {
		return contactId;
	}

	public void setContactId(long contactId) {
		this.contactId = contactId;
	}

	public List<Email> getContactEmails() {
		if (contactEmails == null)
			contactEmails = new ArrayList<Email>();
		return contactEmails;
	}

	public void setContactEmails(List<Email> contactEmails) {
		this.contactEmails = contactEmails;
	}
	
	public Email addContactEmail(Email email) {
		getContactEmails().add(email);
		email.setContactEmail(this);
		return email;
	}

	public List<Phone> getContactPhones() {
		if (contactPhones == null)
			contactPhones = new ArrayList<Phone>();
		return contactPhones;
	}

	public void setContactPhones(List<Phone> contactPhones) {
		this.contactPhones = contactPhones;
	}
	
	public Phone addContactPhone(Phone phone) {
		getContactPhones().add(phone);
		phone.setContactPhone(this);
		return phone;
	}
	
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
}
