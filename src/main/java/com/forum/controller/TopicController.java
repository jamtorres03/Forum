package com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.forum.model.Topic;
import com.forum.service.TopicService;

@Controller
public class TopicController {

	@Autowired
	private TopicService topicService;

	@RequestMapping(value = "/admin/topic", method = RequestMethod.GET)
	public ModelAndView profile() {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("topic", new Topic());
		modelAndView.setViewName("admin/topic");
		return modelAndView;
	}

	@RequestMapping(value = "/admin/topic", method = RequestMethod.POST)
	public ModelAndView updateUserDetails(@Valid Topic topic, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("admin/topic");
		} else {
			topic.setCreatedBy(auth.getName());
			topicService.saveTopic(topic);
			modelAndView.addObject("successMessage", "Successfully added.");
			modelAndView.addObject("topic", new Topic());
			modelAndView.setViewName("admin/topic");
		}
		return modelAndView;
	}

}
