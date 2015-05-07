/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the "Contacts" database table.
 * 
 */
@Entity
@Table(name="\"Friends\"")
@NamedQueries({
	@NamedQuery(name="Friends.isFriendshipExist", query="SELECT f FROM Friend f WHERE f.fk_userOwner = :ownerId OR f.fk_userOwned = :ownedId ORDER BY f.fk_userOwner")
})
public class Friend implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="\"friendId\"")
	private long contactId;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@JoinColumn(name="friendship_date")
	private Date friendship_date;
	
	@ManyToOne
	@JoinColumn(name="fk_owner")
	private User fk_userOwner;
	
	@ManyToOne
	@JoinColumn(name="fk_owned")
	private User fk_userOwned;

	public Friend() {
	}
	
	public Date getFriendshipCreationDate() {
		return this.friendship_date;
	}

	public void setFriendshipCreationDate(Date friendshipCreationDate) {
		this.friendship_date = friendshipCreationDate;
	}

	public User getUserOwner() {
		return this.fk_userOwner;
	}

	public void setUserOwner(User userOwner) {
		this.fk_userOwner = userOwner;
	}

	public User getUserOwned() {
		return this.fk_userOwned;
	}

	public void setUserOwned(User userOwned) {
		this.fk_userOwned = userOwned;
	}
}