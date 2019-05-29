package com.nano.shoppingsite.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nano.shoppingsite.models.Cart;
import com.nano.shoppingsite.models.SiteUser;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long>{
	Cart findByUser(SiteUser user);
}
