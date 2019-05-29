package com.nano.shoppingsite.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class CartItem {

	private @Id @GeneratedValue(strategy=GenerationType.SEQUENCE) @Column(name="cart_item_id") long id;
    @ManyToOne
    private Product product;
    @ManyToOne
    @JoinColumn(name="cart_id",nullable=false)
	@JsonIgnore
    private Cart shoppingCart;
    private int quantity;
    
    public CartItem() {
    	
    }
    
    public CartItem(Product product,int quantity) {
    	super();
		this.product = product;
		this.quantity = quantity;
    }
    
	public CartItem(Product product, Cart shoppingCart, int quantity) {
		super();
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.quantity = quantity;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Cart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(Cart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    

   
   
}
