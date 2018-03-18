package com.forum.service;

import java.util.List;

import com.forum.model.Topic;

public interface TopicService {
	public void saveTopic(Topic topic);
	
	public Topic findByTopicId(int topicId);
	
	public List<Topic> findByStatus(int status);
	
	public List<Topic> findAllByStatus();
}
