package com.forum.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.model.Topic;
import com.forum.repository.TopicRepository;

@Service("topicService")
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicRepository topicRepository;


	@Override
	public void saveTopic(Topic topic) {
		topic.setStatus(1);
		topic.setCreatedDate(new Date());
		topicRepository.save(topic);
	}

	@Override
	public List<Topic> findByStatus(int status) {
		return topicRepository.findByStatus(status);
	}

	@Override
	public Topic findByTopicId(int topicId) {
		return topicRepository.findByTopicId(topicId);
	}
}
