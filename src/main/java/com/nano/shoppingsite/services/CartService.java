package com.nano.shoppingsite.services;

import java.io.IOException;
import java.util.Date;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nano.shoppingsite.configurations.MyUserPrincipal;
import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartItemRepository;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.ProductRepository;
import com.nano.shoppingsite.repositories.UserRepository;

@Service
public class CartService {

	@Autowired
	CartRepository cartRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired 
	ProductRepository productRepository;
	@Autowired
	CartItemRepository cartItemRepository;
	
	public Cart getOneCart(Long cartId) {
		if(!verifyCartIdIsUsers(cartId)) {
			return null;
		}
		return cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
	}
	
	public Cart getOneCartByUser(Long userId) {
		if(!verifyUserIdIsUsers(userId)) {
			return null;
		}
		
		SiteUser user = userRepository.findById(userId).orElseThrow(()->new ElementNotFoundException(" UserId: "+userId.toString()));
		return cartRepository.findByUser(user);
	}
	
	public Cart addItemToCart(Long cartId, Long productId, Integer quantity,HttpServletResponse res) throws IOException {
		if(!verifyCartIdIsUsers(cartId)) {
			return null;
		}
		
		Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
		Product prod = productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException(" ProductId: "+productId));
		if(cartItemRepository.existsByProductAndShoppingCart(prod, cart)) {
			res.sendError(409, "Item in cart already exists.");
			return null;
		}
		CartItem cartItem = new CartItem(prod,cart,quantity,new Date().getTime());
		Set<CartItem> list = cart.getCartItems();
		list.add(cartItem);
		cart.setCartItems(list);
		return cartRepository.save(cart);
	}
	
	public Cart createOneCart(Long userId) {
		if(!verifyUserIdIsUsers(userId)) {
			return null;
		}
		
		SiteUser user = userRepository.findById(userId).orElseThrow(()->new ElementNotFoundException(" UserId: "+userId.toString()));
		return cartRepository.save(new Cart(user));
	}
	
	public boolean deleteItemFromCart(Long cartId, Long cartItemId) {
		if(!verifyCartIdIsUsers(cartId)) {
			return false;
		}
		
		Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new ElementNotFoundException("CartItemId: "+cartItemId));

		cartItemRepository.delete(cartItem);
		Set<CartItem> list = cart.getCartItems();
		list.remove(cartItem);
		cart.setCartItems(list);
		cartRepository.save(cart);

		return true;
	}
	
	public CartItem changeQuantityOfCartItem(Long cartId, Long cartItemId, Integer quantity) {
		if(!verifyCartIdIsUsers(cartId)) {
			return null;
		}
		
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new ElementNotFoundException(" CartItemId: "+cartItemId));
		if(cartItem.getQuantity() != quantity) {
			cartItem.setQuantity(quantity);
			return cartItemRepository.save(cartItem);
		}
		return cartItem;
	}
	
	private boolean verifyCartIdIsUsers(Long cartId) {
		MyUserPrincipal userPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(userPrincipal.getCartId() != cartId) {
			return false;
		}
		return true;
	}
	
	private boolean verifyUserIdIsUsers(Long userId) {
		MyUserPrincipal userPrincipal = (MyUserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(userPrincipal.getId() != userId) {
			return false;
		}
		return true;
	}
}
