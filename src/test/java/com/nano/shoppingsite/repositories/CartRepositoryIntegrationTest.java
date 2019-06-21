package com.nano.shoppingsite.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CartRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	CartRepository cartRepository;
	@MockBean
	CartItemRepository cartItemRepository;
	@MockBean
	UserRepository userRepository;
	
	@Before
	public void setUp() {
		
		
	}
	
	@Test
	public void whenFindByUser_thenReturnCart() {
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		Cart cart = new Cart();
		entityManager.persist(user);
		Product siliconePlug = new Product(1,"Pillow Soft® Silicone Putty Ear Plugs",5000,"The ultimate in earplug comfort, Mack’s® silicone putty molds to the unique contours of any ear",
				new HashSet<>(Arrays.asList("Provides a better, more comfortable fit and seal than custom ear plugs", "Great for sleeping, swimming, studying, bathing, travel, loud events, flying discomfort, etc.", "Noise Reduction Rating (NRR) – 22 decibels"))
			);
		Product acousticPlug = new Product(16,"Acoustic Foam Earplugs",7000,"These ear plugs’ innovative hollow-cut and grooved design provides clearer acoustics and allows for improved communication",
				new HashSet<>(Arrays.asList("Noise Reduction Rating (NRR) – 20 decibels", "Great for concerts, jam sessions, nightclubs, shop work, etc."))
			);
		entityManager.persist(siliconePlug);
		entityManager.persist(acousticPlug);
		cart.setUser(user);
		entityManager.persist(cart);
		CartItem cartItem1 = entityManager.persist(new CartItem(siliconePlug,cart,1,new Date().getTime()));
		CartItem cartItem2 = entityManager.persist(new CartItem(acousticPlug,cart,2,new Date().getTime()));
		cart.setCartItems(new HashSet<>(Arrays.asList(cartItem1,cartItem2)));
		entityManager.persist(cart);
		
		Cart foundCart = cartRepository.findByUser(user);
		
		assertThat(foundCart.getId()).isEqualTo(cart.getId());
		
	}

}
