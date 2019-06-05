package com.nano.shoppingsite.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.CartItem;
import com.nano.shoppingsite.models.Product;

public interface CartItemRepository  extends CrudRepository<CartItem, Long>{
	boolean existsByProductAndShoppingCart(Product product, Cart cart);

}
