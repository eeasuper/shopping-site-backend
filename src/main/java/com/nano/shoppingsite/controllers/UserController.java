package com.nano.shoppingsite.controllers;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
	ResponseEntity<?> registerUser(@RequestBody SiteUser newUser, HttpServletResponse res) throws URISyntaxException, IOException {
		SiteUser user = userService.registerUser(newUser,res);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/login", produces = {MediaType.APPLICATION_JSON_VALUE})
	ResponseEntity<?> loginUser(@RequestBody SiteUser newUser, HttpServletResponse res) throws URISyntaxException{
		SiteUser user = userService.loginUser(newUser.getEmail(), newUser.getPassword(), res);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(user);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/user/{id}",  produces = {MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<SiteUser> getOneUser(@PathVariable("id") Long id) {
		SiteUser user = userService.getOneUser(id);
		if(user==null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok(user);
	}
}
