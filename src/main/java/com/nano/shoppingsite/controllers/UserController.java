package com.nano.shoppingsite.controllers;

import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.services.UserService;

@RestController
public class UserController {
	
	@Autowired
	UserService userService;
	
	@RequestMapping(method = RequestMethod.POST, value = "/register", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> registerUser(@RequestBody SiteUser newUser, HttpServletResponse res) throws URISyntaxException {
		SiteUser user = userService.registerUser(newUser,res);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> loginUser(@RequestBody SiteUser newUser, HttpServletResponse res) throws URISyntaxException{
		SiteUser user = userService.loginUser(newUser.getUsername(), newUser.getPassword(), res);
		int status = res.getStatus();
		
		if(status == 201 || status == 200) {
			return ResponseEntity.status(HttpStatus.CREATED).body(user);
		}else {
			return ResponseEntity.status(status).build();
		}
	}
	
//	@RequestMapping(method=RequestMethod.GET, value="/user/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE})
//	public ResponseEntity<User> getUser(@PathVariable("id") Long id) {
//		User user = userService.getUser(id);
//		return ResponseEntity.ok(user);
//	}
}
