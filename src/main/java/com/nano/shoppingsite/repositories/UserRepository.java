package com.nano.shoppingsite.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nano.shoppingsite.models.SiteUser;

@Repository
public interface UserRepository extends CrudRepository<SiteUser, Long>{
	public boolean existsByUsername(String username);
	public boolean existsByEmail(String email);
	public Optional<SiteUser> findByEmail(String email);
	public Optional<SiteUser> findByUsername(String username);
}
