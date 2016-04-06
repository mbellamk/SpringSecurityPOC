package com.wex.poc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.wex.poc.model.User;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {

		User user = userService.loadUserbyUserName(userName);
		if (user == null) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(
				user.getUserName(), user.getPassword(), true, true, true, true,
				getGrantedAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getGrantedAuthorities(
			User user) {
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

		List<String> permissions = userService.getAuthoritiesByUser(user
				.getUserName());
		for (String permission : permissions) {
			authorities.add(new SimpleGrantedAuthority(permission));
		}
		return authorities;
	}

}
