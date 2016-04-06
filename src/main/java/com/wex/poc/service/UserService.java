package com.wex.poc.service;

import java.util.List;

import com.wex.poc.model.User;

public interface UserService {

	public User loadUserbyUserName(String userName);

	public List<String> getAuthoritiesByUser(String userName);
}
