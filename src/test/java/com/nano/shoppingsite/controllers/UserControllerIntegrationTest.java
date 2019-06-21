package com.nano.shoppingsite.controllers;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.nano.shoppingsite.models.SiteUser;
import com.nano.shoppingsite.repositories.CartRepository;
import com.nano.shoppingsite.repositories.UserRepository;
import com.nano.shoppingsite.services.JwtService;
import com.nano.shoppingsite.services.UserService;
import com.nano.shoppingsite.services.utilities.JsonUtil;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerIntegrationTest {
	
	@TestConfiguration
	static class UserControllerTestcontextConfiguration{
		@Bean 
		public BCryptPasswordEncoder passwordEncoder() {
			return new BCryptPasswordEncoder();
		}
		@Bean
		public JwtService jwtService() {
			return new JwtService();
		}
	}
	
	@Before
	public void setUp() {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		
		Mockito.when(userRepository.existsByUsername(user.getUsername())).thenReturn(true);
	}
	
	@Autowired
	private MockMvc mvc;
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@MockBean
	private UserService userService;
	@MockBean
	private UserRepository userRepository;
	@MockBean
	private CartRepository cartRepository;
	
	@Test
	public void whenRegisterUser_thenCreateUser() throws Exception {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		given(userService.registerUser(Mockito.any(), Mockito.any())).willReturn(user);
		
		mvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andExpect(status().isCreated()).andExpect(jsonPath("$.username",is("testUsername")));
		verify(userService,VerificationModeFactory.times(1)).registerUser(Mockito.any(),Mockito.any());
		reset(userService);
	}
	
	@Test
	public void whenLoginUser_thenReturnUser() throws IOException, Exception {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		given(userService.loginUser(Mockito.any(), Mockito.any(), Mockito.any())).willReturn(user);
		
		mvc.perform(post("/login").contentType(MediaType.APPLICATION_JSON).content(JsonUtil.toJson(user))).andExpect(status().isCreated()).andExpect(jsonPath("$.username",is("testUsername")));
		verify(userService,VerificationModeFactory.times(1)).loginUser(Mockito.any(), Mockito.any(),Mockito.any());
		reset(userService);
	}
	
	@Test
	public void whenGetOneUser_thenReturnUser() throws Exception {
		SiteUser user = new SiteUser(11L, "testname","testUsername",passwordEncoder.encode("1234"),"test@test.com");
		given(userService.getOneUser(Mockito.anyLong())).willReturn(user);

		mvc.perform(get("/user/"+user.getId()).header(HttpHeaders.AUTHORIZATION,"Bearer "+JwtService.createToken(user.getUsername(), user.getId()))).andExpect(status().isOk()).andExpect(jsonPath("$.username",is("testUsername")));
		verify(userService,VerificationModeFactory.times(1)).getOneUser(Mockito.anyLong());
		reset(userService);
	}
}
