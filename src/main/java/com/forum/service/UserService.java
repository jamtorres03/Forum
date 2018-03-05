package com.forum.service;

import com.forum.model.User;

public interface UserService {
	public User findUserByEmail(String email);
	public void saveUser(User user);
	public void updateUser(User user);
}
