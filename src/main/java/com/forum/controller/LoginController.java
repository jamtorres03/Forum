package com.forum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.forum.model.User;
import com.forum.service.TopicService;
import com.forum.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;

	@Autowired
	private TopicService topicService;
	
	@RequestMapping(value={"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
	@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		User user = new User();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}
	
	@RequestMapping(value="/topics", method = RequestMethod.GET)
	public ModelAndView topics(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("topics", topicService.findByStatus(1));
		modelAndView.setViewName("topics");
		return modelAndView;
	}
	
	@RequestMapping(value = "/registration", method = RequestMethod.POST)
	public ModelAndView createNewUser(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("registration");
		} else {
			if (user.getPassword().equals(user.getConfirmPassword())) {
				User userExists = userService.findUserByEmail(user.getEmail());
				if (userExists != null) {
					bindingResult.rejectValue("email", "error.user", "There is already a user registered with the email provided.");
				} else {
					userService.saveUser(user);
					modelAndView.addObject("successMessage", "User has been registered successfully.");
					modelAndView.addObject("user", new User());
					modelAndView.setViewName("registration");
				}
			} else {
				bindingResult.rejectValue("confirmPassword", "error.user", "Password does not match!");
			}
		}
		return modelAndView;
	}
	
	@RequestMapping(value="/forgot", method = RequestMethod.GET)
	public ModelAndView forgotPassword(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("forgot");
		return modelAndView;
	}
	
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ModelAndView sendResetEmail(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("successMessage", "Reset password link has been sent to your email.");
		modelAndView.setViewName("forgot");
		return modelAndView;
	}
	
	@RequestMapping(value="/enter-code", method = RequestMethod.GET)
	public ModelAndView enterCode(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("enter-code");
		return modelAndView;
	}
	
	@RequestMapping(value = "/enter-code", method = RequestMethod.POST)
	public ModelAndView verifyCode(@Valid User user, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}
	
}
