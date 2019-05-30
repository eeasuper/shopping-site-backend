package com.nano.shoppingsite.services;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	@Autowired
	JwtService jwtService;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	public SiteUser registerUser(SiteUser user,HttpServletResponse res) {
		jwtService.setAuthentication(user.getId(), user.getUsername(), user.getPassword(),res);
		String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		repository.save(user);
		user.setPassword(null);
		return user;
	}
	
	public SiteUser loginUser(String username, String password, HttpServletResponse res) {
		SiteUser user = repository.findByUsername(username).orElseThrow(()->new ElementNotFoundException(" username: "+username));
		boolean matches = this.passwordEncoder.matches(password, user.getPassword());
		
		if(!matches) {
			System.out.println("credentials error");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return user;
		}
		//does password inside spring token have to be encoded?
		if(matches) {
			jwtService.setAuthentication(user.getId(), username, password,res);
		}
		user.setPassword(null);
		return user;
	}
}
