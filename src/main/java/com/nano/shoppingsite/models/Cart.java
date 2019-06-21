package com.nano.shoppingsite.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="cart")
public class Cart {
	
	@Id 
	@Column(name="cart_id")
	@GeneratedValue(generator="gen") 
	@GenericGenerator(name="gen", strategy="foreign",parameters=@Parameter(name="property",value="user"))
	private long id;
	
	@OneToMany(mappedBy="shoppingCart",cascade=CascadeType.ALL)

    private Set<CartItem> cartItems;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private SiteUser user;
	
	public Cart() {
		
	}
	
	public Cart(long id, Set<CartItem> cartItems, SiteUser user) {
		super();
		this.id = id;
		this.cartItems = cartItems;
		this.user = user;
	}

	public Cart(long id, Set<CartItem> cartItems) {
		super();
		this.id = id;
		this.cartItems = cartItems;
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
