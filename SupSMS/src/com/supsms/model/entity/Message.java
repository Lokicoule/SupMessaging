/*
 * Author : Lokicoule
 */
package com.supsms.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "\"Messages\"")
@NamedQueries({
	@NamedQuery(name="Messages.findAllMessages", 
		query="SELECT m FROM Message m WHERE m.fk_msgReceiver = :user ORDER BY m.msgCreationDate DESC"),
	@NamedQuery(name="Messages.findAllMessagesByContactAndObject", 
		query="SELECT m FROM Message m WHERE "
				+ "(m.fk_msgReceiver = :user OR m.fk_msgSender = :user) "
				+ "AND (m.fk_msgReceiver = :contact OR m.fk_msgSender = :contact) "
				+ "AND m.msgObject = :object "
				+ "ORDER BY m.msgCreationDate"),
	@NamedQuery(name="Messages.findAllMessagesByContact", 
		query="SELECT m FROM Message m WHERE "
				+ 	"(m.fk_msgReceiver = :user OR m.fk_msgSender = :user) "
				+ 	"AND (m.fk_msgReceiver = :contact OR m.fk_msgSender = :contact) "
				+	"AND (m.msgStatus < 2) "
				+ 	"ORDER BY m.msgObject"),
	@NamedQuery(name="Messages.findNewMessages",
		query="SELECT Count(m) FROM Message m WHERE m.fk_msgReceiver = :user AND m.msgStatus = 0"),
	@NamedQuery(name="Messages.getCountMessages", 
		query="SELECT Count(m) FROM Message m")
})
public class Message implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "\"msgId\"", unique = true, nullable = false)
	private long msgId;
	
	@Column(name = "\"msgObject\"", nullable = false, length = 2147483647)
	private String msgObject;
	
	@Column(name = "\"msgBody\"", nullable = false, length = 2147483647)
	private String msgBody;
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "\"msgCreationDate\"", nullable = false)
	private Date msgCreationDate;
	
	@Column(name = "\"msgStatus\"", nullable = false)
	private int msgStatus;
	
	@ManyToOne
	@JoinColumn(name = "\"fk_sender\"")
	private User fk_msgSender = new User();
	
	@ManyToOne
	@JoinColumn(name = "\"fk_receiver\"", nullable = false)
	private User fk_msgReceiver = new User();
	
	public Message() {
	}
	public long getMsgId() {
		return msgId;
	}
	public void setMsgId(long msgId) {
		this.msgId = msgId;
	}
	public String getMsgObject() {
		return msgObject;
	}
	public void setMsgObject(String msgObject) {
		this.msgObject = msgObject;
	}
	public String getMsgBody() {
		return msgBody;
	}
	public void setMsgBody(String msgBody) {
		this.msgBody = msgBody;
	}
	public Date getMsgCreationDate() {
		return msgCreationDate;
	}
	public void setMsgCreationDate(Date msgCreationDate) {
		this.msgCreationDate = msgCreationDate;
	}
	public int isMsgStatus() {
		return msgStatus;
	}
	public void setMsgStatus(int msgStatus) {
		this.msgStatus = msgStatus;
	}
	
	public int getMsgStatus() {
		return this.msgStatus;
	}
	
	public User getUserSender() {
		return fk_msgSender;
	}
	public void setUserSender(User fk_sender) {
		this.fk_msgSender = fk_sender;
	}
	public User getUserReceiver() {
		return fk_msgReceiver;
	}
	public void setUserReceiver(User fk_receiver) {
		this.fk_msgReceiver = fk_receiver;
	}

}
