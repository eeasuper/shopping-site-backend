package com.nano.shoppingsite.models;

import java.util.Date;

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
    private @JoinColumn(nullable=false) Product product;
    @ManyToOne
    @JoinColumn(name="cart_id",nullable=false)
	@JsonIgnore
    private Cart shoppingCart;
    private int quantity;
    private @Column(nullable=false) Long addedDate;
    
    public CartItem() {
    	
    }
    
    public CartItem(Product product,int quantity) {
    	super();
		this.product = product;
		this.quantity = quantity;
    }
    
	public CartItem(Product product, Cart shoppingCart, int quantity, Long addedDate) {
		super();
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.quantity = quantity;
		this.addedDate = addedDate;
	}
	
	public CartItem(Long id,Product product, Cart shoppingCart, int quantity, Long addedDate) {
		super();
		this.id = id;
		this.product = product;
		this.shoppingCart = shoppingCart;
		this.quantity = quantity;
		this.addedDate = addedDate;
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

	public Long getAddedDate() {
		return addedDate;
	}

	public void setAddedDate(Long addedDate) {
		this.addedDate = addedDate;
	}
    
    

   
   
}
