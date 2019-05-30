package com.nano.shoppingsite.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.nano.shoppingsite.filters.CorsSiteFilter;
import com.nano.shoppingsite.filters.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{
	
//	public UserRepository repository;
//	
//	WebSecurityConfig(UserRepository repository){
//		this.repository = repository;
//	}
	
	@Autowired
	JwtAuthenticationFilter jwtAuthenticationFilter;
	
	WebSecurityConfig(){
	}
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
    		.authorizeRequests()
//    		.antMatchers("**").permitAll() // for test
    		.antMatchers("/h2-console/**").permitAll() //for test
            .antMatchers(HttpMethod.POST, "/login").permitAll() 
            .antMatchers(HttpMethod.POST, "/register").permitAll()
            .antMatchers(HttpMethod.GET,"/product/**").permitAll()
            .antMatchers(HttpMethod.GET,"/search/*").permitAll()
            .anyRequest().authenticated()
            .and().headers().frameOptions().disable()
            .and()
            .addFilterBefore(new CorsSiteFilter(), ChannelProcessingFilter.class)
        	.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

    }
}