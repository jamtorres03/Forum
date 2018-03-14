package com.forum.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.forum.model.Comment;
import com.forum.repository.CommentRepository;

@Service("commentService")
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;


	@Override
	public void saveComment(Comment comment) {
		comment.setStatus(1);
		comment.setCreatedDate(new Date());
		commentRepository.save(comment);
	}

	@Override
	public List<Comment> findByStatus(int status) {
		return commentRepository.findByStatus(status);
	}

	@Override
	public Comment findByCommentId(int commentId) {
		return commentRepository.findByCommentId(commentId);
	}
}
