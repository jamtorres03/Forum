package com.forum.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.forum.model.Comment;

@Repository("commentRepository")
public interface CommentRepository extends JpaRepository<Comment, Long> {
	List<Comment> findByStatus(int status);

	Comment findByCommentId(int commentId);
}
