package com.nano.shoppingsite.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nano.shoppingsite.models.CartItem;

public interface CartItemRepository  extends CrudRepository<CartItem, Long>{


}
