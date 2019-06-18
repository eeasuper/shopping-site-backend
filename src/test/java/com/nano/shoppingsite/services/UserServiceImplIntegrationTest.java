package com.nano.shoppingsite.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.UserRepository;

@RunWith(SpringRunner.class)
public class UserServiceImplIntegrationTest {
	
	@TestConfiguration
	static class UserServiceImplTestContextConfiguration{
		@Bean
		public UserService userService() {
			return new UserService();
		}
		
		@Bean
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		
		@Bean
		public JwtService jwtService() {
			return new JwtService();
		}
	}
	@Autowired
	UserService userService;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private CartRepository cartRepository;
	
	@Before
	public void setUp() {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		user.setId(11L);
		
		Mockito.when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));
		Mockito.when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
	}
	
	@Test
	public void whenRegisterUser_thenNewUserShouldBeEqualToRequestedUser() throws IOException {
		SiteUser user = new SiteUser("testname","testUsername","1234","test@test.com");
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		SiteUser savedUser = userService.registerUser(user,res);
		
		assertThat(savedUser).isEqualTo(user);
	}
	
	@Test
	public void whenLoginUser_thenReturnedUserIdShouldBeEqualToRequestedUserId() {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		HttpServletResponse res = mock(HttpServletResponse.class);
		
		SiteUser loggedInUser = userService.loginUser(user.getEmail(), user.getPassword(), res);
		
		assertThat(loggedInUser.getId()).isEqualTo(user.getId());
	}
	
	@Test
	public void whenGetOneUser_thenReturnRequestedUser() {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		
		SiteUser requestedUser = userService.getOneUser(user.getId());
		
		assertThat(requestedUser.getId()).isEqualTo(user.getId());
	}
}
