package com.nano.shoppingsite.repositories;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.nano.shoppingsite.exceptions.ElementNotFoundException;
import com.nano.shoppingsite.models.SiteUser;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserRepository userRepository;	
	
	@Test
	public void whenExistsByUsername_thenReturnBoolean() {
		//given
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		entityManager.persist(user);
		entityManager.flush();
		
		//when
		boolean exists = userRepository.existsByUsername(user.getUsername());
		
		//then
		assertThat(exists).isTrue();
	}
	
	@Test
	public void whenExistsByEmail_thenReturnBoolean() {
//		SiteUser user = new SiteUser("test@test.com",passwordEncoder.encode("1234"));
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		entityManager.persist(user);
		entityManager.flush();
		
		boolean exists = userRepository.existsByEmail(user.getEmail());
		
		assertThat(exists).isTrue();
	}
	
	@Test
	public void whenFindByEmail_thenReturnSiteUser() {
//		SiteUser user = new SiteUser("test@test.com",passwordEncoder.encode("1234"));
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		entityManager.persist(user);
		entityManager.flush();
		
		SiteUser foundUser = userRepository.findByEmail(user.getEmail()).orElseThrow(()->new ElementNotFoundException(" email: "+user.getEmail()));
		
		assertThat(foundUser.getEmail())
			.isEqualTo(user.getEmail());
	}

	@Test
	public void whenFindByUsername_thenReturnSiteUser() {
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		entityManager.persist(user);
		entityManager.flush();
		
		SiteUser foundUser = userRepository.findByUsername(user.getUsername()).orElseThrow(()->new ElementNotFoundException(" username: "+user.getUsername()));
		
		assertThat(foundUser.getUsername())
			.isEqualTo(user.getUsername());
	}
}
