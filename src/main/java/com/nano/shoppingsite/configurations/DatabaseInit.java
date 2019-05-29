package com.nano.shoppingsite.configurations;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartItemRepository;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.ProductRepository;
import com.nano.shoppingsite.repositories.UserRepository;

@Configuration
public class DatabaseInit {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	CartRepository cartRepository;
	@Autowired
	ProductRepository productRepository;
	@Autowired
	CartItemRepository cartItemRepository;
	@Autowired
	BCryptPasswordEncoder passwordEncoder; 
	
	@Bean
	CommandLineRunner init() {
    	return (args) -> {
//    		productRepository.deleteAll();
//    		userRepository.deleteAll();
//    		cartRepository.deleteAll();
//    		cartItemRepository.deleteAll();
    		SiteUser user1 = new SiteUser("John", "johnny", passwordEncoder.encode("1234"),"john@email.com");
    		userRepository.save(user1);
    		Product siliconePlug = new Product(1,"Pillow Soft® Silicone Putty Ear Plugs",5000,"The ultimate in earplug comfort, Mack’s® silicone putty molds to the unique contours of any ear",
				new HashSet<>(Arrays.asList("'Provides a better, more comfortable fit and seal than custom ear plugs", "Great for sleeping, swimming, studying, bathing, travel, loud events, flying discomfort, etc.", "Noise Reduction Rating (NRR) – 22 decibels"))
			);
    		productRepository.save(siliconePlug);
    		Cart cart1 = cartRepository.save(new Cart());
    		cart1.setUser(user1);
    		CartItem cartItem1 = cartItemRepository.save(new CartItem(siliconePlug,cart1,1));
    		CartItem cartItem2 = cartItemRepository.save(new CartItem(siliconePlug,cart1,2));
    		cart1.setCartItems(new HashSet<>(Arrays.asList(cartItem1,cartItem2)));
    		cartRepository.save(cart1);
    	};
    }
	
}
