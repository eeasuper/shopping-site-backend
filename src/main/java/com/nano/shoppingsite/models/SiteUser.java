package com.nano.shoppingsite.models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class SiteUser {
	private @Id @GeneratedValue(strategy=GenerationType.SEQUENCE) @Column(name="user_id") long id;
	private @Column(nullable=false) String  name;
	private @Column(nullable=false) String username;
	private @Column(nullable=false) String password;
	private @Column(nullable=false) String email;
	
	//add nullable=false later.
	private @OneToOne(mappedBy="user",cascade=CascadeType.ALL)@JsonIgnore Cart cart;
	
	public SiteUser(){
	}
	
	public SiteUser(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	
	public SiteUser(String username) {
		this.username = username;
	}
	
	//This constructor is for creating a SiteUser in JwtService.class
	public SiteUser(long id, String username) {
		super();
		this.id = id;
		this.username = username;
	}

	public SiteUser(String name, String username, String password, String email) {
		super();
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public SiteUser(Long id, String name, String username, String password, String email) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
}
