package com.nano.shoppingsite.services.utilities;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;

public class CartInstanceInit {
	public static Cart cart = new Cart();
	
	public static final SiteUser user = new SiteUser(11L, "testname","testUsername","1234","test@test.com");
	
	public static final Product siliconePlug = new Product(1,"Pillow Soft® Silicone Putty Ear Plugs",5000,"The ultimate in earplug comfort, Mack’s® silicone putty molds to the unique contours of any ear",
		new HashSet<>(Arrays.asList("Provides a better, more comfortable fit and seal than custom ear plugs", "Great for sleeping, swimming, studying, bathing, travel, loud events, flying discomfort, etc.", "Noise Reduction Rating (NRR) – 22 decibels"))
	);
	
	public static final Product testProduct = new Product(2,"Testing Earplugs",5000,"The ultimate in earplug comfort, Mack’s® silicone putty molds to the unique contours of any ear",
		new HashSet<>(Arrays.asList("Provides a better, more comfortable fit and seal than custom ear plugs", "Great for sleeping, swimming, studying, bathing, travel, loud events, flying discomfort, etc.", "Noise Reduction Rating (NRR) – 22 decibels"))
	);
	
	public static final Product acousticPlug = new Product(16,"Acoustic Foam Earplugs",7000,"These ear plugs’ innovative hollow-cut and grooved design provides clearer acoustics and allows for improved communication",
		new HashSet<>(Arrays.asList("Noise Reduction Rating (NRR) – 20 decibels", "Great for concerts, jam sessions, nightclubs, shop work, etc."))
	);
	
	public static final CartItem cartItem1 =  new CartItem(1L,siliconePlug,cart,1,new Date().getTime());
	
	public static final CartItem cartItem2 = new CartItem(2L,acousticPlug,cart,2,new Date().getTime());

	public static final Cart getCart() {
		cart.setCartItems(new HashSet<>(Arrays.asList(cartItem1,cartItem2)));
		cart.setUser(user);
		cart.setId(user.getId());
		return cart;
	}
}
