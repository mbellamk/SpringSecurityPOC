package com.wex.poc.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wex.poc.model.Permission;
import com.wex.poc.model.PermissionSetPermission;
import com.wex.poc.model.User;
import com.wex.poc.repository.UserRepository;
import com.wex.poc.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Transactional
	public User loadUserbyUserName(String userName) {

		return userRepository.getUserByUserName(userName);
	}

	@Transactional
	public List<String> getAuthoritiesByUser(String userName) {
		List<String> authorities = new ArrayList<String>();
		List<PermissionSetPermission> permissions = userRepository
				.getAuthoritiesByUserName(userName);
		for (PermissionSetPermission p : permissions) {
			authorities.add(p.getPermission().getCode());
		}
		System.out.println(authorities.toString());
		return authorities;
	}

}
