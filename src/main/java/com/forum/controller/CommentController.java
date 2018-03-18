package com.forum.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.forum.model.Comment;
import com.forum.model.Topic;
import com.forum.model.User;
import com.forum.service.CommentService;
import com.forum.service.TopicService;
import com.forum.service.UserService;

@Controller
public class CommentController {

	@Autowired
	private TopicService topicService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/comment/edit", method = RequestMethod.POST)
	public ModelAndView editTopic(@RequestParam int commentId, @RequestParam String content) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Comment comment = commentService.findByCommentId(commentId);
		comment.setContent(content);
		commentService.saveComment(comment);
		
		User loggedUser = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("loggedUser", loggedUser);
		modelAndView.addObject("topic", new Topic());
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.setViewName("admin/topic");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/comment/delete", method = RequestMethod.POST)
	public ModelAndView deleteTopic(@RequestParam int commentId) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		Comment comment = commentService.findByCommentId(commentId);
		comment.setStatus(0);
		commentService.saveComment(comment);
		
		modelAndView.addObject("successMessage", "Successfully deleted.");
		
		User loggedUser = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("loggedUser", loggedUser);
		modelAndView.addObject("topic", new Topic());
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.setViewName("admin/topic");
		return modelAndView;
    }

	@RequestMapping(value = "/admin/comment/{topicId}", method = RequestMethod.POST)
	public ModelAndView addComment(@Valid Comment comment, @PathVariable(value = "topicId", required = false) int topicId, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/topic");
		} else {
			User user = userService.findUserByEmail(auth.getName());
			comment.setUser(user);
			
			Topic topic = topicService.findByTopicId(topicId);
			comment.setTopic(topic);
			comment.setStatus(1);
			comment.setCreatedDate(new Date());
			commentService.saveComment(comment);
			
			modelAndView.addObject("successMessage", "Successfully added.");
			
			User loggedUser = userService.findUserByEmail(auth.getName());
			modelAndView.addObject("loggedUser", loggedUser);
			modelAndView.addObject("topic", new Topic());
			modelAndView.addObject("comment", new Comment());
			modelAndView.addObject("topics", topicService.findByStatus(1));
			modelAndView.setViewName("admin/topic");
		}
		return modelAndView;
	}

}
