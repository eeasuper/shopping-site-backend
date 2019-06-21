package com.nano.shoppingsite.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.services.CartService;

@RestController
public class CartController {
	
	@Autowired
	CartService cartService;
	
	/* Methods whose purpose is a GET request in CartController.class is mapped as POST because it's easier that way to make Spring Security make it authorized.*/
	@RequestMapping(method=RequestMethod.GET, value="/cart/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> getOneCart(@PathVariable("id") Long cartId){
		Cart cart = cartService.getOneCart(cartId);
		if(cart==null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok().body(cart);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/user/{id}/cart", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> getOneCartByUser(@PathVariable("id") Long userId){
		Cart cart = cartService.getOneCartByUser(userId);
		if (cart == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.ok().body(cart);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cart/{id}/{productId}/{quantity}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> addItemToCart(@PathVariable("id") Long cartId,@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity,HttpServletResponse res) throws IOException{
		Cart cart = cartService.addItemToCart(cartId,productId,quantity,res);
		if (cart == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cart);
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cart/{userId}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> createOneCart(@PathVariable("userId") Long userId){
		Cart cart = cartService.createOneCart(userId);
		if (cart == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cart);
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/cart/{id}/{cartItemId}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> deleteItemFromCart(@PathVariable("id") Long cartId, @PathVariable("cartItemId")Long cartItemId){
		boolean deleted = cartService.deleteItemFromCart(cartId,cartItemId);
		if (!deleted) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	
	@RequestMapping(method=RequestMethod.PUT, value="/cart/{id}/{cartItemId}/{quantity}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<CartItem> changeQuantityOfCartItem(@PathVariable("id") Long cartId, @PathVariable("cartItemId")Long cartItemId, @PathVariable("quantity") Integer quantity){
		CartItem cartItem = cartService.changeQuantityOfCartItem(cartId,cartItemId,quantity);
		if (cartItem == null) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
	}	
}
