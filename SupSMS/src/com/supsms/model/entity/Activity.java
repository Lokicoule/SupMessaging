/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"Activities\"")
@NamedQuery(name="Activities.userActivities", query="SELECT c FROM Activity c WHERE c.userActivity = :user")
public class Activity implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "\"activityId\"")
	private long activityId;
	
	@Column(name = "\"activityType\"", nullable = false, length = 2147483647)
	private String activityType;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"activityDate\"", nullable = false)
	private Date activityDate;
	
	@ManyToOne
	@JoinColumn(name="fk_user")
	private User userActivity = new User();
	
	public Activity() {
	}

	public long getActivityId() {
		return activityId;
	}

	public void setActivityId(long activityId) {
		this.activityId = activityId;
	}

	public String getActivityType() {
		return activityType;
	}

	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}

	public Date getActivityDate() {
		return activityDate;
	}

	public void setActivityDate(Date activityDate) {
		this.activityDate = new Timestamp(new Date().getTime());
	}

	public User getUserActivity() {
		return userActivity;
	}

	public void setUserActivity(User userActivity) {
		this.userActivity = userActivity;
	}

}
