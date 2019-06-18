package com.nano.shoppingsite.services;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	JwtService jwtService;

	
	private String validate(SiteUser user) {
		String nameRegex = "^[\\w][^\\s]{1,}$";
		if(!user.getName().matches(nameRegex)) {
			return "Name should be more than 1 character";
		}
		String usernameRegex = "^.[^\\s]{4,24}$";
		if(!user.getUsername().matches(usernameRegex)) {
			return "Username should be between 4 and 24 characters";
		}
		String emailRegex = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";
		if(!user.getEmail().matches(emailRegex)) {
			return "Email is not in the correct format.";
		}
		String passwordRegex = "^.[^\\s]{7,}$";
		if(!user.getPassword().matches(passwordRegex)) {
			System.out.println(user.getPassword());
			return "Password should be more than 7 characters long.";
		}
		if(repository.existsByUsername(user.getUsername())) {
			return "Username already exists";
		}
		if(repository.existsByEmail(user.getEmail())) {
			return "Email already exists";
		}
		return "true";
	}
	
	public SiteUser registerUser(SiteUser user,HttpServletResponse res) throws IOException {
		jwtService.setAuthentication(user.getId(), user.getUsername(), user.getPassword(),res);
		String encryptedPassword = this.passwordEncoder.encode(user.getPassword());
		user.setPassword(encryptedPassword);
		Cart cart = new Cart(user);
		String validation = validate(user);
		if(validation == "true") {
			repository.save(user);
			cartRepository.save(cart);
		}else {
			res.sendError(409, validation);
			return null;
		}
		user.setPassword(null);
		return user;
	}
	
	public SiteUser loginUser(String email, String password, HttpServletResponse res) {
		SiteUser user = repository.findByEmail(email).orElseThrow(()->new ElementNotFoundException(" email: "+email));
		boolean matches = this.passwordEncoder.matches(password, user.getPassword());
		
		if(!matches) {
			System.out.println("credentials error");
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return user;
		}
		//does password inside spring token have to be encoded?
		if(matches) {
			jwtService.setAuthentication(user.getId(), user.getUsername(), password,res);
		}
		user.setPassword(null);
		return user;
	}
	
	public SiteUser getOneUser(Long userId) {
		SiteUser user = repository.findById(userId).orElseThrow(()->new ElementNotFoundException("UserId: "+userId));
		return user;
	}
}
