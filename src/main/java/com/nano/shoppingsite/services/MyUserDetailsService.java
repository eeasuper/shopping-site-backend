package com.nano.shoppingsite.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.configurations.MyUserPrincipal;
import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.UserRepository;;

@Service
public class MyUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SiteUser user = userRepository.findByUsername(username).orElseThrow(()->new ElementNotFoundException("username: "+username));
		if (user == null) {
            throw new UsernameNotFoundException(username);
        }
		return new MyUserPrincipal(user);
	}


	

}
