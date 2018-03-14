package com.forum.service;

import java.util.List;

import com.forum.model.Comment;

public interface CommentService {
	public void saveComment(Comment Comment);
	
	public Comment findByCommentId(int commentId);
	
	public List<Comment> findByStatus(int status);
}
