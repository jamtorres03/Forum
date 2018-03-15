package com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.forum.model.Comment;
import com.forum.model.Topic;
import com.forum.model.User;
import com.forum.service.CommentService;
import com.forum.service.TopicService;
import com.forum.service.UserService;

@Controller
public class TopicController {

	@Autowired
	private TopicService topicService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/topic", method = RequestMethod.GET)
	public ModelAndView topic() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("topic", new Topic());
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.addObject("comments", commentService.findByStatus(1));
		modelAndView.setViewName("admin/topic");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/topic", method = RequestMethod.POST)
	public ModelAndView addTopic(@Valid Topic topic, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/topic");
		} else {
			User user = userService.findUserByEmail(auth.getName());
			topic.setUser(user);
			topicService.saveTopic(topic);
			
			modelAndView.addObject("successMessage", "Successfully added.");

			modelAndView.addObject("topic", new Topic());
			modelAndView.addObject("comment", new Comment());
			modelAndView.addObject("topics", topicService.findByStatus(1));
			modelAndView.addObject("comments", commentService.findByStatus(1));
			modelAndView.setViewName("admin/topic");
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/topic/edit/{topicId}", method = RequestMethod.POST)
	public ModelAndView editTopic(@PathVariable(value = "topicId", required = false) int topicId, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();

		Topic topic = topicService.findByTopicId(topicId);
		modelAndView.addObject("topic", topic);
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.addObject("comments", commentService.findByStatus(1));
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
			
			commentService.saveComment(comment);
			
			modelAndView.addObject("successMessage", "Successfully added.");
			
			modelAndView.addObject("topic", new Topic());
			modelAndView.addObject("comment", new Comment());
			modelAndView.addObject("topics", topicService.findByStatus(1));
			modelAndView.addObject("comments", commentService.findByStatus(1));
			modelAndView.setViewName("admin/topic");
		}
		return modelAndView;
	}

}
