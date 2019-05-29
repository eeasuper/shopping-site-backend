package com.nano.shoppingsite.services;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
	}
	
	public Cart getOneCartByUser(Long userId) {
		SiteUser user = userRepository.findById(userId).orElseThrow(()->new ElementNotFoundException(" UserId: "+userId.toString()));
		return cartRepository.findByUser(user);
	}
	
	public Cart addItemToCart(Long cartId, Long productId, Integer quantity) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
		Product prod = productRepository.findById(productId).orElseThrow(()->new ElementNotFoundException(" ProductId: "+productId));
		CartItem cartItem = new CartItem(prod,cart,quantity);
		cartItemRepository.save(cartItem);
		Set<CartItem> list = cart.getCartItems();
		list.add(cartItem);
		cart.setCartItems(list);
		return cartRepository.save(cart);
	}
	
	public Cart createOneCart(Long userId) {
		SiteUser user = userRepository.findById(userId).orElseThrow(()->new ElementNotFoundException(" UserId: "+userId.toString()));
		return cartRepository.save(new Cart(user));
	}
	
	public void deleteItemFromCart(Long cartId, Long cartItemId) {
		Cart cart = cartRepository.findById(cartId).orElseThrow(()->new ElementNotFoundException(" CartId: "+cartId.toString()));
		CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(()->new ElementNotFoundException(" CartItemId: "+cartItemId));
		cartItemRepository.delete(cartItem);
		Set<CartItem> list = cart.getCartItems();
		list.remove(cartItem);
		cart.setCartItems(list);
		cartRepository.save(cart);
	}
}
