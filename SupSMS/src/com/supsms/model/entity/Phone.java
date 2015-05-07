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
@Table(name="\"Phones\"")
public class Phone {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="\"phoneId\"")
	private long phoneId;
	
	@Column(name="\"phone\"")
	private String phone;
	
	@ManyToOne
	@JoinColumn(name="\"contact_id\"")
	private Contact contactPhone = new Contact();
	
	
	public Phone() {
	}


	public Contact getContactPhone() {
		return contactPhone;
	}


	public void setContactPhone(Contact contactPhone) {
		this.contactPhone = contactPhone;
	}


	public long getPhoneId() {
		return phoneId;
	}


	public void setPhoneId(long phoneId) {
		this.phoneId = phoneId;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}

}
