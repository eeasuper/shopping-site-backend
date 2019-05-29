package com.nano.shoppingsite.models;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Product {
	//@GeneratedValue(strategy=GenerationType.SEQUENCE)
	private @Id  @Column(name="product_id")long id;
	private @Column(nullable=false) String name;
	private @Column(nullable=false) int price;
	private @Column(nullable=false) String description;
	@ElementCollection 
	private @Column(nullable=false,name="description_ul") Set<String> descriptionUl;
	
	public Product() {
	}
	public Product(Long id) {
		super();
		this.id = id;
	}
	public Product(String name, int price, String description, Set<String> descriptionUl) {
		super();
		this.name = name;
		this.price = price;
		this.description = description;
		this.descriptionUl = descriptionUl;
	}
	
	public Product(long id, String name, int price, String description, Set<String> descriptionUl) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.description = description;
		this.descriptionUl = descriptionUl;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Set<String> getDescriptionUl() {
		return descriptionUl;
	}

	public void setDescriptionUl(Set<String> descriptionUl) {
		this.descriptionUl = descriptionUl;
	}
}
