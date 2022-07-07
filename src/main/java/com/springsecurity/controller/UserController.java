package com.springsecurity.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.model.UserModel;

@RestController
public class UserController {
	
	@Autowired
	private JdbcUserDetailsManager jdbcUserDetailsManager;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@RequestMapping(value="/register", method=RequestMethod.POST)
	public String register(@RequestBody UserModel userModel ) {
		List<GrantedAuthority>authorities=new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(userModel.getRoles()));
		String encodedPassword=passwordEncoder.encode(userModel.getPassword());
		User user=new User(userModel.getUserName(),encodedPassword, authorities);
		jdbcUserDetailsManager.createUser(user);
		return "User Created Successfully";
		}
	@RequestMapping(value="/admin", method=RequestMethod.GET)
	public String admin() {
		return"<h2> Welcome Admin!!</h2>";
	}

	@RequestMapping(value="/user", method=RequestMethod.GET)
	public String user() {
		return"<h2> Hello User!!</h2>";
}

	@RequestMapping(value="/", method=RequestMethod.GET)
	public String welcome() {
		return"<h2> Welcome</h2>";
	}
}