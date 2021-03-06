package com.forum.controller;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
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
public class TopicController {

	@Autowired
	private TopicService topicService;

	@Autowired
	private CommentService commentService;

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/admin/topic", method = RequestMethod.GET)
	public ModelAndView topic() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User loggedUser = userService.findUserByEmail(auth.getName());
		
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("loggedUser", loggedUser);
		modelAndView.addObject("topic", new Topic());
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
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
			topic.setStatus(1);
			topic.setCreatedDate(new Date());
			topic.setUser(user);
			topicService.saveTopic(topic);
			
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
	
	@RequestMapping(value="/admin/topic/delete", method = RequestMethod.POST)
	public ModelAndView deleteTopic(@RequestParam int topicId) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		Topic topic = topicService.findByTopicId(topicId);
		topic.setStatus(0);
		topicService.saveTopic(topic);
		
		modelAndView.addObject("successMessage", "Successfully deleted.");
		User loggedUser = userService.findUserByEmail(auth.getName());
		modelAndView.addObject("loggedUser", loggedUser);
		modelAndView.addObject("topic", new Topic());
		modelAndView.addObject("comment", new Comment());
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.setViewName("admin/topic");
		return modelAndView;
    }
}
