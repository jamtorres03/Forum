package com.forum.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "topic")
public class Topic {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "topic_id")
	private int topicId;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "active")
	private int active;
	
	@Column(name = "created_by")
	private String createdBy;
	
	@Column(name = "created_date", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;

	public int getTopicId() {
		return topicId;
	}

	public void setTopicId(int topicId) {
		this.topicId = topicId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getActive() {
		return active;
	}

	public void setActive(int active) {
		this.active = active;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
}
