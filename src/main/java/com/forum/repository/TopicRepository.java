package com.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.model.Topic;

@Repository("topicRepository")
public interface TopicRepository extends JpaRepository<Topic, Long> {
	List<Topic> findByStatus(int status);

	Topic findByTopicId(int topicId);
}
