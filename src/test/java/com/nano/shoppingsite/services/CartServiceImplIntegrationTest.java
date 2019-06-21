package com.nano.shoppingsite.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;

import com.nano.shoppingsite.configurations.MyUserPrincipal;
import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartItemRepository;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.ProductRepository;
import com.nano.shoppingsite.repositories.UserRepository;
import com.nano.shoppingsite.services.utilities.CartInstanceInit;

@RunWith(SpringRunner.class)
public class CartServiceImplIntegrationTest {
	
	@TestConfiguration
	static class CartServiceImplTestContextConfiguration{
		@Bean
		public CartService cartService() {
			return new CartService();
		}
	}
	@MockBean
	CartRepository cartRepository;
	@MockBean
	UserRepository userRepository;
	@MockBean
	CartItemRepository cartItemRepository;
	@MockBean
	ProductRepository productRepository;
	@Autowired
	private CartService cartService;
	
	@Before
	public void setUp() {
		Cart cart = CartInstanceInit.getCart();
		SiteUser user = CartInstanceInit.user;
		CartItem cartItem1 = CartInstanceInit.cartItem1;
		Product testProduct = CartInstanceInit.testProduct;
		
		Authentication authentication = Mockito.mock(Authentication.class);
		SecurityContext securityContext = Mockito.mock(SecurityContext.class);
		Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
		SecurityContextHolder.setContext(securityContext);
		Mockito.when(authentication.getPrincipal()).thenReturn(new MyUserPrincipal(user));
		
		Mockito.when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
		Mockito.when(cartRepository.findByUser(user)).thenReturn(cart);
		Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
		Mockito.when(cartRepository.findById(cart.getId())).thenReturn(Optional.of(cart));
		Mockito.when(productRepository.findById(testProduct.getId())).thenReturn(Optional.of(testProduct));
		Mockito.when(cartItemRepository.existsByProductAndShoppingCart(testProduct, cart)).thenReturn(false);
		Mockito.when(cartItemRepository.findById(cartItem1.getId())).thenReturn(Optional.of(cartItem1));
		Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
	}
	
	@Test
	public void whenGetOneCart_thenCartShouldBeFound() {
		Long cartId = 11L;
		
		Cart foundCart = cartService.getOneCart(cartId);
		
		verifyThatCartFindByIdIsCalledOnce();
		assertThat(foundCart.getId()).isEqualTo(cartId);
	}
	
	@Test
	public void whenGetOneCartByUser_thenCartShouldBeFound() {
		Long userId = 11L;
		
		Cart foundCart = cartService.getOneCartByUser(userId);
		
		assertThat(foundCart.getUser().getId()).isEqualTo(userId);
	}
	
	@Test
	public void whenAddItemToCart_thenReturn3CartItems() throws IOException {
		HttpServletResponse res = mock(HttpServletResponse.class);
		Long cartId = 11L;
		Long productId = 2L;
		Cart cart = CartInstanceInit.getCart();
		given(cartRepository.save(cart)).willReturn(cart);
		
		Cart foundCart = cartService.addItemToCart(cartId, productId, 1, res);
		
		assertThat(foundCart.getCartItems()).hasSize(3).extracting(CartItem::getShoppingCart).contains(foundCart);
	}
	
	@Test
	public void whenCreateOneCart_thenUserShouldHaveACart() {
		Long userId = 11L;
		Cart cart = new Cart(CartInstanceInit.user);
		Mockito.when(cartRepository.save(Mockito.any(Cart.class))).thenReturn(cart);
		
		Cart foundCart = cartService.createOneCart(userId);
		
		assertThat(foundCart.getUser().getId()).isEqualTo(userId);
	}
	
	@Test
	public void whenDeleteItemFromCart_thenCartItemShouldBeDeleted() {
		CartItem cartItem = CartInstanceInit.cartItem1;
		Cart cart = CartInstanceInit.getCart();
		
		cartService.deleteItemFromCart(cart.getId(), cartItem.getId());
		
		Mockito.verify(cartItemRepository,VerificationModeFactory.times(1)).delete(cartItem);
		Mockito.reset(cartItemRepository);
		verifyThatCartSaveIsCalledOnce();
	}
	
	@Test
	public void whenChangeQuantityOfCartItem_returnCartItem() {
		CartItem cartItem = CartInstanceInit.cartItem1;
		Cart cart = CartInstanceInit.getCart();
		final int quantity = 5;
		Mockito.when(cartItemRepository.save(cartItem)).thenReturn(cartItem);
		
		CartItem changedCartItem = cartService.changeQuantityOfCartItem(cart.getId(), cartItem.getId(), quantity);

		Mockito.verify(cartItemRepository,VerificationModeFactory.times(1)).save(cartItem);
		Mockito.reset(cartItemRepository);
		assertThat(changedCartItem.getQuantity()).isEqualTo(quantity);
	}
	
	private void verifyThatCartSaveIsCalledOnce() {
		Mockito.verify(cartRepository,VerificationModeFactory.times(1)).save(CartInstanceInit.getCart());
		Mockito.reset(cartRepository);
	}
	
	private void verifyThatCartFindByIdIsCalledOnce() {
		Mockito.verify(cartRepository,VerificationModeFactory.times(1)).findById(CartInstanceInit.getCart().getId());
		Mockito.reset(cartRepository);
	}
	
}
