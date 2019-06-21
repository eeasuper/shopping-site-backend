package com.nano.shoppingsite.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.repositories.CartItemRepository;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.ProductRepository;
import com.nano.shoppingsite.repositories.UserRepository;
import com.nano.shoppingsite.services.CartService;
import com.nano.shoppingsite.services.JwtService;
import com.nano.shoppingsite.services.utilities.CartInstanceInit;

@RunWith(SpringRunner.class)
@WebMvcTest(CartController.class)
public class CartControllerIntegrationTest {
	@TestConfiguration
	static class CartControllerTestcontextConfiguration{
		@Bean
		public JwtService jwtService() {
			return new JwtService();
		}
	}
	@Autowired
	private MockMvc mvc;
	@MockBean
	CartRepository cartRepository;
	@MockBean
	UserRepository userRepository;
	@MockBean
	CartItemRepository cartItemRepository;
	@MockBean
	ProductRepository productRepository;
	@MockBean
	private CartService cartService;
	
	private final Long userId = CartInstanceInit.user.getId();
	private final String userUsername = CartInstanceInit.user.getUsername();
	private final Cart cart = CartInstanceInit.getCart();
	@Before
	public void setUp() {
	
		Mockito.when(userRepository.existsByUsername(userUsername)).thenReturn(true);
	}
	@Test
	public void whenGetOneCart_thenReturnCart() throws Exception {
		given(cartService.getOneCart(cart.getId())).willReturn(cart);
		
		mvc.perform(get("/cart/"+cart.getId()).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isOk()).andExpect(jsonPath("$.id",is(11)));
		verify(cartService,VerificationModeFactory.times(1)).getOneCart(cart.getId());
		reset(cartService);
	}
	
	@Test
	public void whenGetOneCartByUser_thenReturnCart() throws Exception {
		given(cartService.getOneCartByUser(userId)).willReturn(cart);
		
		mvc.perform(get("/user/"+userId+"/cart").header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isOk()).andExpect(jsonPath("$.id",is(11)));
		verify(cartService,VerificationModeFactory.times(1)).getOneCartByUser(userId);
		reset(cartService);
	}
	
	@Test
	public void whenAddItemToCart_thenReturnCart() throws Exception {
		Long productId = 1L;
		int quantity = 1;
		HttpServletResponse res = mock(HttpServletResponse.class);
		given(cartService.addItemToCart(Mockito.eq(cart.getId()), Mockito.eq(productId), Mockito.eq(quantity), Mockito.any(HttpServletResponse.class))).willReturn(cart);

		mvc.perform(post("/cart/"+cart.getId()+"/"+productId+"/"+quantity).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isCreated()).andExpect(jsonPath("$.id",is(11)));
		verify(cartService,VerificationModeFactory.times(1)).addItemToCart(Mockito.eq(cart.getId()), Mockito.eq(productId), Mockito.eq(quantity), Mockito.any(HttpServletResponse.class));
		reset(cartService);
	}
	
	@Test
	public void whenCreateOneCart_thenReturnCart() throws Exception {
		given(cartService.createOneCart(userId)).willReturn(cart);
		
		mvc.perform(post("/cart/"+userId).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isCreated()).andExpect(jsonPath("$.id",is(11)));
		verify(cartService, VerificationModeFactory.times(1)).createOneCart(userId);
		reset(cartService);
	}
	
	@Test
	public void whenDeleteItemFromCart_thenReturnNoContent() throws Exception {
		Long cartItemId = CartInstanceInit.cartItem1.getId();
		given(cartService.deleteItemFromCart(cart.getId(),cartItemId)).willReturn(true);
		
		mvc.perform(delete("/cart/"+cart.getId()+"/"+cartItemId).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isNoContent());
		verify(cartService,VerificationModeFactory.times(1)).deleteItemFromCart(cart.getId(), cartItemId);
		reset(cartService);
	}
	
	@Test
	public void whenChangeQuantityOfCartItem_thenReturnCartItem() throws Exception {
 		Long cartId = cart.getId();
 		CartItem cartItem = CartInstanceInit.cartItem1;
 		int quantity = 5;
 		CartItem configuredCartItem = new CartItem(cartItem.getId(),cartItem.getProduct(),cartItem.getShoppingCart(),quantity,cartItem.getAddedDate());
 		
 		given(cartService.changeQuantityOfCartItem(Mockito.eq(cartId), Mockito.eq(cartItem.getId()), Mockito.eq(quantity))).willReturn(configuredCartItem);
 		
		mvc.perform(put("/cart/"+cartId+"/"+cartItem.getId()+"/"+quantity).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(userUsername, userId))).andExpect(status().isCreated()).andExpect(jsonPath("$.quantity",is(5)));
		verify(cartService,VerificationModeFactory.times(1)).changeQuantityOfCartItem(Mockito.eq(cartId), Mockito.eq(cartItem.getId()), Mockito.eq(quantity));
		reset(cartService);
	}
}
