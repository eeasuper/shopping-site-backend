package com.nano.shoppingsite.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.Product;
import com.nano.shoppingsite.services.CartService;

@RestController
public class CartController {
	
	@Autowired
	CartService cartService;
	
	@RequestMapping(method=RequestMethod.GET, value="/cart/{id}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> getOneCart(@PathVariable("id") Long cartId){
		return ResponseEntity.ok().body(cartService.getOneCart(cartId));
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/user/{id}/cart", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> getOneCartByUser(@PathVariable("id") Long userId){
		return ResponseEntity.ok().body(cartService.getOneCartByUser(userId));
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cart/{id}/{productId}/{quantity}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> addItemToCart(@PathVariable("id") Long cartId,@PathVariable("productId") Long productId,@PathVariable("quantity") Integer quantity){
//		if(jwtService.isAuthenticated(authorization)) {
			Cart cart = cartService.addItemToCart(cartId,productId,quantity);
			return ResponseEntity.status(HttpStatus.CREATED).body(cart);
//		}else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
	}
	
	@RequestMapping(method=RequestMethod.POST, value="/cart/{userId}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> createOneCart(@PathVariable("userId") Long userId){
//		if(jwtService.isAuthenticated(authorization)) {
			Cart cart = cartService.createOneCart(userId);
			return ResponseEntity.status(HttpStatus.CREATED).body(cart);
//		}else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
	}
	
	@RequestMapping(method=RequestMethod.DELETE, value="/cart/{id}/{cartItemId}", produces={MediaType.APPLICATION_JSON_VALUE})
	public ResponseEntity<Cart> deleteItemFromCart(@PathVariable("id") Long cartId, @PathVariable("cartItemId")Long cartItemId){
//		if(jwtService.isAuthenticated(authorization)) {
			cartService.deleteItemFromCart(cartId,cartItemId);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
//		}else {
//			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
//		}
	}
	
	
}
