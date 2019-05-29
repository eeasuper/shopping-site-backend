package com.nano.shoppingsite.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="cart")
public class Cart {
	private @Id @GeneratedValue(strategy=GenerationType.SEQUENCE) @Column(name="cart_id")long id;
	@OneToMany(mappedBy="shoppingCart",cascade=CascadeType.ALL)

    private Set<CartItem> cartItems;
	@OneToOne
	private SiteUser user;
	
	public Cart() {
		
	}
	
	public Cart(SiteUser user) {
		super();
		this.user = user;
	}
	
	public Cart(Set<CartItem> cartItems, SiteUser user) {
		super();
		this.cartItems = cartItems;
		this.user = user;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public Set<CartItem> getCartItems() {
		return cartItems;
	}
	public void setCartItems(Set<CartItem> cartItems) {
		this.cartItems = cartItems;
	}
	public SiteUser getUser() {
		return user;
	}
	public void setUser(SiteUser user) {
		this.user = user;
	}
	
	
}
